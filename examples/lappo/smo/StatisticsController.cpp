#include "StatisticsController.h"
#define TOTAL_TIME 220000

StatisticsController::StatisticsControllerPointer StatisticsController::instance;

StatisticsController::StatisticsControllerPointer StatisticsController::getInstance()
{
    if (StatisticsController::instance == nullptr) {
        StatisticsController::instance
                = StatisticsController::StatisticsControllerPointer(new StatisticsController(220000, 10, 15));
    }
    return StatisticsController::instance;
}

StatisticsController::StatisticsController(long totalTime, size_t sourceDevicesCount, size_t operatingDevicesCount)
    :totalTime(totalTime),
      sourceDevicesCount(sourceDevicesCount),
      operatingDevicesCount(operatingDevicesCount),
      sourceDevicesVector(std::vector<SourceStat>(sourceDevicesCount)),
      operatingDevicesVector(std::vector<OperatingStat>(operatingDevicesCount))
{

}

long StatisticsController::getTotalTasksProcessed() const
{
    return totalTasksProcessed;
}

long StatisticsController::getTotalTasksCreated() const
{
    return totalTasksCreated;
}

int StatisticsController::getOperatingDevicesCount() const
{
    return operatingDevicesCount;
}

void StatisticsController::setOperatingDevicesCount(int value)
{
    operatingDevicesCount = value;
    operatingDevicesVector = std::vector<OperatingStat>(operatingDevicesCount);
}

int StatisticsController::getSourceDevicesCount() const
{
    return sourceDevicesCount;
}

void StatisticsController::setSourceDevicesCount(int value)
{
    sourceDevicesCount = value;
    sourceDevicesVector = std::vector<SourceStat>(sourceDevicesCount);
}

long StatisticsController::getTotalTime() const
{
    return totalTime;
}

void StatisticsController::setTotalTime(long value)
{
    totalTime = value;
}

void StatisticsController::taskCreated(const int source)
{
    totalTasksCreated++;
    sourceDevicesVector.at(source).addGeneratedTask();
}

void StatisticsController::taskRejected(const int source, const long timeConsumpted)
{
    taskFinished(source, -1, timeConsumpted, 0);
    sourceDevicesVector.at(source).addRejectedTask();
    totalTasksProcessed++;
}

void StatisticsController::taskFinished(const int source, const int processedBy, long timeConsumpted, long timeProcessed)
{
    if (processedBy != -1) {
        operatingDevicesVector.at(processedBy).addConsumptedTime(timeProcessed);
    }
    sourceDevicesVector.at(source).addFinishedTaskTime(timeConsumpted);
    sourceDevicesVector.at(source).addBufferedTime(timeConsumpted - timeProcessed);
    totalTasksProcessed++;
}

std::vector<SourceStat> StatisticsController::getSourceDevicesVector() const
{
    return sourceDevicesVector;
}

std::vector<OperatingStat> StatisticsController::getOperatingDevicesVector() const
{
    return operatingDevicesVector;
}

const int SourceStat::getGeneratedTasksCount() const
{
    return generatedTasksCount;
}

const int SourceStat::getRejectedTasksCount() const
{
    return rejectedTasksCount;
}

const long SourceStat::getTasksTotalTime() const
{
    return tasksTotalTime;
}

void SourceStat::addGeneratedTask()
{
    generatedTasksCount++;
}

void SourceStat::addRejectedTask()
{
    ++rejectedTasksCount;
}

void SourceStat::addFinishedTaskTime(long time)
{
    taskTotalSquaredTime += time * time;
    tasksTotalTime += time;
}

const double SourceStat::getTotalTimeDispersion() const
{
    return (taskTotalSquaredTime*1.0 / generatedTasksCount) -
            (tasksTotalTime*1.0 / generatedTasksCount) * (tasksTotalTime*1.0 / generatedTasksCount);
}

const double SourceStat::getBufferedTimeDispersion() const
{
    return (bufferedSquaredTime*1.0 / generatedTasksCount) -
            (bufferedTime*1.0 / generatedTasksCount) * (bufferedTime*1.0 / generatedTasksCount);
}

long SourceStat::getBufferedTime() const
{
    return bufferedTime;
}

void SourceStat::addBufferedTime(long value)
{
    bufferedSquaredTime += value * value;
    bufferedTime += value;
}

long OperatingStat::getTotalWorkingTime() const
{
    return totalWorkingTime;
}

void OperatingStat::addConsumptedTime(const long value)
{
    totalWorkingTime += value;
}
