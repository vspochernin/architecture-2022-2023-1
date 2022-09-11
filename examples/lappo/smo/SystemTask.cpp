#include "SystemTask.h"

#include <exception>

SystemTask::SystemTask(const long startTime, const int sourceNumber, const std::string taskId):
    startTime(startTime),
    sourceNumber(sourceNumber),
    taskId(taskId)
{

}

std::string SystemTask::getTaskId() const
{
    return taskId;
}

const long SystemTask::getStartTime() const
{
    return startTime;
}

const int SystemTask::getProcessedBy() const
{
    return processedBy;
}

void SystemTask::setProcessedBy(const int value)
{
    processedBy = value;
}

const int SystemTask::getSourceNumber() const
{
    return sourceNumber;
}

std::shared_ptr<SystemTask> SystemTask::getNewTask(const long startTime, const int sourceNumber, const std::string taskId)
{
    return std::shared_ptr<SystemTask>(new SystemTask(startTime, sourceNumber, taskId));
}
