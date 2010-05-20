package broadcastAnnouncer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;

import stationData.StationInfo;

public class AnnouncementMessage {

    private int port;
    private InetAddress interfAddress;
    private int announcedID;
    private StationInfo stInfo;

    public AnnouncementMessage(StationInfo s, int port) throws IOException {
        this.interfAddress = s.getStationAddress();
        stInfo = s;
        this.port = port;
    }

    public AnnouncementMessage(InterfaceAddress boundInterface, int port)
            throws IOException {
        this.interfAddress = boundInterface.getBroadcast();
        this.port = port;
        stInfo = new StationInfo("Laptop", -1);
    }

    public int send() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);

        socket.send(createPacket());
        socket.close();
        return stInfo.getId();
    }

    private DatagramPacket createPacket() {
        DatagramPacket packet = new DatagramPacket(stInfo.getNetworkString().getBytes(),
                stInfo.getNetworkString().getBytes().length,
                interfAddress, port);
        return packet;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void setAnnouncedID(int announcedID) {
        this.announcedID = announcedID;
    }

    public int getAnnouncedID() {
        return announcedID;
    }
}
