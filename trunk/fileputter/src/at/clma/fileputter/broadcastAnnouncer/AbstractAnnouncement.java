/**
 * @author  Claus Matzinger (S0810307022)
 * @date    25.07.2010
 * @file    AbstractAnnouncement
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

import at.clma.fileputter.attributes.ApplicationData;
import at.clma.fileputter.stationData.IStationInfo;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Claus Matzinger
 */
public abstract class AbstractAnnouncement implements IAnnouncement {

    protected InetAddress interfAddress;
    protected IStationInfo stInfo;

    public int send() throws IOException {
        DatagramSocket socket = new DatagramSocket();

        socket.send(createPacket());
        socket.close();
        return stInfo.getId();
    }

    protected DatagramPacket createPacket() {
        DatagramPacket packet = new DatagramPacket(stInfo.getNetworkString().getBytes(),
                stInfo.getNetworkString().getBytes().length,
                interfAddress, ApplicationData.MULTICASTPORT);
        return packet;
    }
}
