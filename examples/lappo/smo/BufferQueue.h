#ifndef BUFFERQUEUE_H
#define BUFFERQUEUE_H

#include "SystemTask.h"
#include "StatisticsController.h"

#include <vector>
#include <memory>

class BufferQueue
{
public:
    BufferQueue(const size_t bufferSize);
    void addTask(const MyTaskPointer pointer);
    bool isEmpty();
    MyTaskPointer getNextTask();

    size_t getBufferSize() const;
    size_t getConsumptedSpace() const;
    size_t getPlacementPosition() const;
    size_t getTakePosition() const;

    std::vector<MyTaskPointer> getTaskVector() const;

private:
    StatisticsController::StatisticsControllerPointer statistics = StatisticsController::getInstance();
    std::vector<MyTaskPointer> taskVector;
    size_t takePosition = 0;
    size_t placementPosition = 0;
    size_t consumptedSpace = 0;
    size_t bufferSize = 0;
};

#endif // BUFFERQUEUE_H
