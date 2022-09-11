#ifndef SOURCEDEVICE_H
#define SOURCEDEVICE_H

#include "SystemTask.h"

#include <random>

class SourceDevice
{
public:
    SourceDevice(const double distributionSeed, int deviceNumber);

    MyTaskPointer generateNextTask(long currentTime);
    const long nextTaskGenerationTime();
private:
    int deviceNumber;
    long tasksGenerated = 0;
    std::random_device generator;
    std::poisson_distribution<long> distribution;
};

#endif // SOURCEDEVICE_H
