#ifndef SYSTEMCONTROLLER_H
#define SYSTEMCONTROLLER_H

#include "SystemTask.h"
#include "SourceDevice.h"
#include "OperatingDevice.h"
#include "StatisticsController.h"
#include "BufferQueue.h"
#include "SystemEvent.h"

#include <set>
#include <memory>
#include <QObject>

class SystemController: public QObject
{
    Q_OBJECT
public:
    SystemController(double poissonKoeff, long minUniform,
                     long maxUniform, int bufferSize, long totalTasksRequired);
    std::multiset<SystemEvent>* goToNextState();
    void executeAutoMode();
    std::multiset<std::shared_ptr<OperatingDevice>, OperatingDevice::MyCompare>* getOperatingDevices();

    BufferQueue *getBufferQueue() const;

    volatile long getCurrentTime() const;

private:
    StatisticsController::StatisticsControllerPointer statistics = StatisticsController::getInstance();
    volatile long currentTime = 0;
    int operatingDevicesCount = statistics->getOperatingDevicesCount();
    int sourceDevicesCount = statistics->getSourceDevicesCount();
    const long totalTasksRequired;

    void initSourceDevices(double poissonKoeff);
    void initEventSet();
    void initOperatingDevices(long minUniform, long maxUniform);
    BufferQueue *bufferQueue;

    void doForwardStep();

    std::vector<std::unique_ptr<SourceDevice>> sourceDevices;
    std::multiset<std::shared_ptr<OperatingDevice>, OperatingDevice::MyCompare> operatingDevices;

    std::multiset<SystemEvent> eventSet;
};

#endif // SYSTEMCONTROLLER_H
