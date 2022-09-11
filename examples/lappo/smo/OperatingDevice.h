#ifndef OPERATINGDEVICE_H
#define OPERATINGDEVICE_H

#include "SystemTask.h"

#include <random>
#include <memory>

class OperatingDevice
{
public:
    OperatingDevice(int deviceNumber, long min, long max);
    const long setNextTask(MyTaskPointer task, long currentTime); //returns processing time

    bool isAvaliable() const;

    int getDeviceNumber() const;

    MyTaskPointer getCurrentTask() const;
	struct MyCompare {
		bool operator()(std::shared_ptr<OperatingDevice> l, std::shared_ptr<OperatingDevice> r) const {
			if (l->isAvaliable()) {
				if (r->isAvaliable()) {
					if (l->getDeviceNumber() < r->getDeviceNumber()) {
						return true;
					}
					else
					{
						return false;
					}
				}
				else {
					return true;
				}
			}
			if (!r->isAvaliable()) {
				if (l->getDeviceNumber() < r->getDeviceNumber()) {
					return true;
				}
				else
				{
					return false;
				}
			}
			return false;
		}
	};

    long getTaskStartTime() const;

private:
    long taskStartTime = 0;
    const int deviceNumber;
    MyTaskPointer currentTask = nullptr;
    std::random_device rd;  //Will be used to obtain a seed for the random number engine
    std::shared_ptr<std::mt19937> genPointer; //Standard mersenne_twister_engine seeded with rd()
    std::uniform_int_distribution<long> distribution;
};

#endif // OPERATINGDEVICE_H
