#include "SystemController.h"

#include <iostream>
#include <qcoreapplication.h>

SystemController::SystemController(double poissonKoeff, long minUniform,
                                   long maxUniform, int bufferSize, long totalTasksRequired)
    :totalTasksRequired(totalTasksRequired)
{
    initSourceDevices(poissonKoeff);
    initEventSet();
    initOperatingDevices(minUniform, maxUniform);

    bufferQueue = new BufferQueue(bufferSize);
}

void SystemController::executeAutoMode() {
    while (!eventSet.empty()) {
		QCoreApplication::processEvents();
        doForwardStep();
    }
}


std::multiset<SystemEvent>* SystemController::goToNextState()
{
    if (!eventSet.empty()) {
        doForwardStep();
    }
    return &eventSet;
}

void SystemController::initSourceDevices(double poissonKoeff)
{
    for (int i = 0; i < sourceDevicesCount; i++)
    {
        sourceDevices.push_back(std::unique_ptr<SourceDevice>(new SourceDevice(poissonKoeff, i)));
    }
}

void SystemController::initEventSet()
{
    for (int i = 0; i < sourceDevicesCount; i++)
    {
        eventSet.insert(SystemEvent(sourceDevices.at(i)->nextTaskGenerationTime(), EventType::GenerateTask, i));
    }
}

void SystemController::initOperatingDevices(long minUniform, long maxUniform)
{
    for (int i = 0; i < operatingDevicesCount; i++)
    {
        operatingDevices.insert(std::shared_ptr<OperatingDevice>(new OperatingDevice(i, minUniform, maxUniform)));
    }
}

BufferQueue *SystemController::getBufferQueue() const
{
    return bufferQueue;
}

volatile long SystemController::getCurrentTime() const
{
    return currentTime;
}

void SystemController::doForwardStep()
{
    SystemEvent event = *eventSet.begin();
    eventSet.erase(eventSet.begin());
    currentTime = event.getEventTime();
    int deviceId = event.getAssignedDevice();
    switch (event.getEventType()) {
    case GenerateTask:
        if (statistics->getTotalTasksCreated() <= totalTasksRequired) {
            bufferQueue->addTask(sourceDevices.at(deviceId)->generateNextTask(currentTime));
            eventSet.insert(SystemEvent(currentTime + sourceDevices.at(deviceId)->nextTaskGenerationTime(),
                                        EventType::GenerateTask, deviceId));
            eventSet.insert(SystemEvent(currentTime, EventType::TaskUnbuffer));
            statistics->taskCreated(deviceId);
        }
        break;
    case TaskUnbuffer:
        if((*operatingDevices.begin())->isAvaliable()) {
            std::shared_ptr<OperatingDevice> currentDevice = *operatingDevices.begin();
			operatingDevices.erase(operatingDevices.begin());
            if (bufferQueue->isEmpty() || !currentDevice->isAvaliable()) {
				operatingDevices.insert(currentDevice);
                break;
            }
            MyTaskPointer taskPointer = bufferQueue->getNextTask();
            eventSet.insert(SystemEvent(currentTime + currentDevice->setNextTask(taskPointer, currentTime),
                                        EventType::TaskCompleted, currentDevice->getDeviceNumber()));
			operatingDevices.insert(currentDevice);
		}
        break;
    case TaskCompleted:
    {
        auto currentDeviceIt =
                std::find_if(operatingDevices.begin(), operatingDevices.end(),
                              [deviceId](std::shared_ptr<OperatingDevice> const device)
        {return device->getDeviceNumber() == deviceId; });
		std::shared_ptr<OperatingDevice> currentDevice = *currentDeviceIt;
		operatingDevices.erase(currentDeviceIt);
        statistics->taskFinished(currentDevice->getCurrentTask()->getSourceNumber(),
                                 deviceId,
                                 (currentTime - currentDevice->getCurrentTask()->getStartTime()),
                                 (currentTime - currentDevice->getTaskStartTime()));
        currentDevice->setNextTask(nullptr, currentTime);
		operatingDevices.insert(currentDevice);
        eventSet.insert(SystemEvent(currentTime, EventType::TaskUnbuffer));
        break;
    }
    default:
        break;
    }
}

std::multiset<std::shared_ptr<OperatingDevice>, OperatingDevice::MyCompare> *SystemController::getOperatingDevices()
{
    return &operatingDevices;
}
