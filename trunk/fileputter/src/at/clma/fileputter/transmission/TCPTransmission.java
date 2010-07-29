/**
 * @author  Claus Matzinger
 * @date    Jul 1, 2010
 * @file    TCPTransmission
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
package at.clma.fileputter.transmission;

import at.clma.fileputter.stationData.IStationInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ByteChannel;
import java.nio.channels.SocketChannel;

/**
 *
 * @author Claus Matzinger
 */
public class TCPTransmission implements ITransmission {

    private IStationInfo station;
    private InetAddress recipient;
    private int port;
    private Socket sock;
    private SocketChannel sockChannel;

    public TCPTransmission(IStationInfo station, Socket socket) {
        this.station = station;
        this.port = socket.getPort();
        this.sock = socket;
        this.sockChannel = socket.getChannel();
        this.recipient = station.getStationAddress();
    }

    public TCPTransmission(IStationInfo station, int port) {
        this.station = station;
        this.port = port;
        this.recipient = station.getStationAddress();
    }

    @Deprecated
    public TCPTransmission(InetAddress recipient, int port) {
        this.recipient = recipient;
        this.port = port;

    }

    @Deprecated
    public TCPTransmission(Socket socket) {
        this.recipient = socket.getInetAddress();
        this.port = socket.getPort();
        this.sock = socket;
        this.sockChannel = socket.getChannel();
    }

    public void open() throws IOException {
        if (!isConnectionOpen()) {
            sockChannel = SocketChannel.open(new InetSocketAddress(recipient, port));
            sock = sockChannel.socket();
        }

    }

    public void close() throws IOException {
        if (sock != null) {
            sock.close();
        }
    }

    public boolean isConnectionOpen() {
        return sock != null;
    }

    public IStationInfo getPartner() {
        return station;
    }

    public ByteChannel getChannel() {
        return sockChannel;

    }

    public InputStream getInputStream() throws IOException {
        if (isConnectionOpen()) {
            return sock.getInputStream();
        } else {
            return null;
        }
    }

    public OutputStream getOutputStream() throws IOException {
        if (isConnectionOpen()) {
            return sock.getOutputStream();
        } else {
            return null;
        }
    }
}
