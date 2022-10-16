package simulation;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

// Набор всех приборов.
public class DeviceKit {

    @Getter
    private final List<Device> devices;

    public DeviceKit(int count) {
        devices = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            devices.add(new Device(i));
        }
    }
}
