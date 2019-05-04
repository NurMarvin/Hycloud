package de.nurmarvin.hycloud.master.networking.manager;

import de.nurmarvin.hycloud.master.Master;
import de.nurmarvin.hycloud.master.networking.packet.PacketIn;
import de.nurmarvin.hycloud.master.networking.packet.packets.in.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author NurMarvin
 */
public class PacketManager {
    private Map<Integer, Class<? extends PacketIn>> packetsIn = new HashMap<>();
    private Master master;

    public PacketManager(Master master) {
        this.master = master;
        start();
    }

    private void start() {
        registerPacketIn(0, PacketInRegisterDaemon.class);
        registerPacketIn(1, PacketInStartedServer.class);
        registerPacketIn(2, PacketInScreenLine.class);
        registerPacketIn(3, PacketInServerStopped.class);
        registerPacketIn(4, PacketInRegisterServer.class);
    }

    private void registerPacketIn(int id, Class<? extends PacketIn> packet) {
        master.logger().info("Registering incoming packet type {}", packet.getSimpleName());
        packetsIn.put(id, packet);
    }

    public Class<? extends PacketIn> getPacketById(int id) {
        return packetsIn.get(id);
    }
}
