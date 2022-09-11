#include "OperatingDevice.h"

OperatingDevice::OperatingDevice(int deviceNumber, long min, long max):
    deviceNumber(deviceNumber),
    genPointer(std::shared_ptr<std::mt19937>(new std::mt19937(rd()))),
    distribution(std::uniform_int_distribution<long>(min, max))
{

}

const long OperatingDevice::setNextTask(MyTaskPointer task, long currentTime)
{
    taskStartTime = currentTime;
    currentTask = task;
    return distribution(*genPointer);
}

bool OperatingDevice::isAvaliable() const
{
    return currentTask == nullptr;
}

int OperatingDevice::getDeviceNumber() const
{
    return deviceNumber;
}

MyTaskPointer OperatingDevice::getCurrentTask() const
{
    return currentTask;
}

long OperatingDevice::getTaskStartTime() const
{
    return taskStartTime;
}
