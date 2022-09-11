#ifndef SYSTEMEVENT_H
#define SYSTEMEVENT_H


enum EventType
{
    GenerateTask,
    TaskUnbuffer,
    TaskCompleted
};

class SystemEvent
{
public:
    SystemEvent(const long eventTime, EventType eventType, int assignedDevice = -1);

    const EventType getEventType() const;
    const long getEventTime() const;

    void setAssignedDevice(int value);
    const int getAssignedDevice() const;

    friend bool operator<(const SystemEvent l, const SystemEvent r) {
        if (l.getEventTime() < r.getEventTime()) {
            return true;
        } else if (l.getEventTime() > r.getEventTime()) {
            return false;
        } else {
            return l.getEventType() > r.getEventType();
        }
    }

private:
    const long eventTime;
    const EventType eventType;
    int assignedDevice;
};

#endif // SYSTEMEVENT_H
