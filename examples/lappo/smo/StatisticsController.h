#ifndef STATISTICSCONTROLLER_H
#define STATISTICSCONTROLLER_H

#include <memory>
#include <vector>

class SourceStat
{
public:
    SourceStat() {}
    const int getGeneratedTasksCount() const;
    void addGeneratedTask();

    const int getRejectedTasksCount() const;
    void addRejectedTask();

    const long getTasksTotalTime() const;
    void addFinishedTaskTime(long time);

    const double getTotalTimeDispersion() const;
    const double getBufferedTimeDispersion() const;

    long getBufferedTime() const;
    void addBufferedTime(long value);

private:
    int generatedTasksCount = 0;
    int rejectedTasksCount = 0;
    long tasksTotalTime = 0;
    long taskTotalSquaredTime = 0;
    long bufferedTime = 0;
    long bufferedSquaredTime = 0;
};

class OperatingStat
{
public:
    OperatingStat() {}

    long getTotalWorkingTime() const;
    void addConsumptedTime(const long value);
private:
    long totalWorkingTime = 0;
};

class StatisticsController
{
public:
    typedef std::shared_ptr<StatisticsController> StatisticsControllerPointer;

    static StatisticsControllerPointer getInstance();
    void taskCreated(const int source);
    void taskRejected(const int source, const long timeConsumpted);
    void taskFinished(const int source, const int processedBy, long timeConsumpted, long timeProcessed);
    std::vector<SourceStat> getSourceDevicesVector() const;
    std::vector<OperatingStat> getOperatingDevicesVector() const;

    long getTotalTime() const;
    void setTotalTime(long value);

    int getSourceDevicesCount() const;
    void setSourceDevicesCount(int value);

    int getOperatingDevicesCount() const;
    void setOperatingDevicesCount(int value);

    long getTotalTasksCreated() const;
    long getTotalTasksProcessed() const;

private:
    StatisticsController(long totalTime, size_t sourceDevicesCount, size_t operatingDevicesCount);
    static StatisticsControllerPointer instance;
    long totalTime;
    long totalTasksCreated;
    long totalTasksProcessed;
    int sourceDevicesCount = 10;
    int operatingDevicesCount = 15;
    std::vector<SourceStat> sourceDevicesVector;
    std::vector<OperatingStat> operatingDevicesVector;
};

#endif // STATISTICSCONTROLLER_H
