#include "SystemEvent.h"

SystemEvent::SystemEvent(const long eventTime, EventType eventType, int assignedDevice):
    eventTime(eventTime), eventType(eventType), assignedDevice(assignedDevice)
{
}

const EventType SystemEvent::getEventType() const
{
    return eventType;
}

const long SystemEvent::getEventTime() const
{
    return eventTime;
}

const int SystemEvent::getAssignedDevice() const
{
    return assignedDevice;
}

void SystemEvent::setAssignedDevice(int value)
{
    assignedDevice = value;
}
