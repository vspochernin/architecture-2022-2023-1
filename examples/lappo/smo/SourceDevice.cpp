#include "SourceDevice.h"

#include <QString>

SourceDevice::SourceDevice(double distributionSeed, int deviceNumber):
    distribution(std::poisson_distribution<long>(distributionSeed)),
    deviceNumber(deviceNumber)
{
}

const long SourceDevice::nextTaskGenerationTime()
{
    return distribution(generator);
}

MyTaskPointer SourceDevice::generateNextTask(long currentTime)
{
    tasksGenerated++;
    return SystemTask::getNewTask(currentTime, deviceNumber, QString::number(deviceNumber).toStdString() + "." +QString::number(tasksGenerated).toStdString());
}
