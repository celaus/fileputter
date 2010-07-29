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

import at.clma.fileputter.events.ITransmissionEventListener;
import at.clma.fileputter.events.TransmissionEvent;
import at.clma.fileputter.fileputterGUI.IStationList;
import at.clma.fileputter.fileputterGUI.StationBox;
import at.clma.fileputter.stationData.IStationInfo;
import at.clma.fileputter.transmission.ITransmission;
import at.clma.fileputter.transmission.TCPTransmission;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author claus
 */
public class TCPTransmissionServer implements Runnable {

    private ServerSocketChannel server;
    private int port;
    private List<ITransmissionEventListener> listeners;
    private boolean stop = false;
    private IStationList stations;

    public TCPTransmissionServer(IStationList stations, int port) throws IOException {
        this.port = port;
        listeners = new ArrayList<ITransmissionEventListener>();
        this.stations = stations;
    }

    public void run() {
        try {
            server = ServerSocketChannel.open();
            server.socket().bind(new InetSocketAddress(port));
            while (!stop) {
                try {
                    SocketChannel clientChannel = server.accept();
                    Socket client = clientChannel.socket();
                    IStationInfo station = stations.getForAddress(client.getInetAddress());
                    if (station != null) {
                        notifyListenersIncoming(station, client);
                    }
                } catch (IOException ex) {
                    System.err.println("error while accepting " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex) {
            StationBox.error("TCP-Server Error", ex.getMessage());
            System.exit(1);
        }
    }

    private void notifyListenersIncoming(IStationInfo station, Socket socket) {
        ITransmission trans = new TCPTransmission(station, socket);
        TransmissionEvent e = new TransmissionEvent(this, trans);
        for (ITransmissionEventListener s : listeners) {
            s.onNewTransmission(e);
        }
    }

    public synchronized void stop() {
        stop = true;
    }

    public synchronized void addTransferEventListener(ITransmissionEventListener listener) {
        listeners.add(listener);
    }

    public synchronized void removeTransferEventListener(ITransmissionEventListener listener) {
        listeners.remove(listener);
    }
}
