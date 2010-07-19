/**
 * @author  Claus Matzinger
 * @date    Jul 2, 2010
 * @file    TCPTransmissionServer
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
package at.clma.fileputter.listener;

import at.clma.fileputter.events.TransferEvent;
import at.clma.fileputter.events.ITransferEventListener;
import at.clma.fileputter.transmission.TCPTransmission;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author claus
 */
public class TCPTransmissionServer implements INetworkListener {

    private ServerSocketChannel server;
    private Socket socket;
    private int port;
    private List<ITransferEventListener> listeners;
    private boolean stop = false;

    public TCPTransmissionServer(int port) throws IOException {
        this.port = port;
        listeners = new ArrayList<ITransferEventListener>();
    }

    public void run() {
        try {
            server = ServerSocketChannel.open();
            server.socket().bind(new InetSocketAddress(port));
        } catch (IOException ex) {
            System.err.println("error while binding: " + ex.getMessage());
            ex.printStackTrace();
            return;
        }
        while (!stop) {
            try {
                SocketChannel clientChannel = server.accept();
                Socket client = clientChannel.socket();
                notifyListenersIncoming(client);
            } catch (IOException ex) {
                System.err.println("error while accepting " + ex.getMessage());
                ex.printStackTrace();
            }

        }

    }

    private void notifyListenersIncoming(Socket socket) {
        TransferEvent e = new TransferEvent(this, new TCPTransmission(socket));
        for (ITransferEventListener s : listeners) {
            s.onIncomingTransfer(e);
        }
    }

    public synchronized void stop() {
        stop = true;
    }

    public synchronized void addTransmissionListener(ITransferEventListener listener) {
        listeners.add(listener);
    }

    public synchronized void removeTransmissionListener(ITransferEventListener listener) {
        listeners.remove(listener);
    }
}
