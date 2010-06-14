/**
 * @author  Claus Matzinger
 * @date    Jun 5, 2010
 * @file    AnnouncementMessage
 *
 * Simple filesharing over LAN.
 * Copyright (C) 2010  Claus Matzinger
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package at.clma.fileputter.broadcastAnnouncer;

import at.clma.fileputter.stationData.StationInfo;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;


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
