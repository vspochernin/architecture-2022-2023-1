#ifndef SYSTEMTASK_H
#define SYSTEMTASK_H

#include <string>
#include <memory>

class SystemTask
{
public:
    static std::shared_ptr<SystemTask> getNewTask(const long startTime, const int sourceNumber, const std::string taskId);

    const int getSourceNumber() const;

    const int getProcessedBy() const;
    void setProcessedBy(const int value);

    const long getStartTime() const;

    std::string getTaskId() const;

private:
    SystemTask(const long startTime, const int sourceNumber, const std::string taskId);

    const int sourceNumber;
    int processedBy = -1;
    long startTime;
    const std::string taskId;
};

typedef std::shared_ptr<SystemTask> MyTaskPointer;

#endif // SYSTEMTASK_H
