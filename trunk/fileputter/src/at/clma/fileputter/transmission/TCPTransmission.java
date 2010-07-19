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
 * @author claus
 */
public class TCPTransmission implements ITransmission {

    private InetAddress recipient;
    private int port;
    private Socket sock;
    private SocketChannel sockChannel;

    public TCPTransmission(InetAddress recipient, int port) {
        this.recipient = recipient;
        this.port = port;
    }

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

    public InetAddress getPartner() {
        return recipient;
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
