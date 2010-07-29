/**
 * @author  Claus Matzinger
 * @date    Jun 01, 2010
 * @file    BroadcastListenServer
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

import at.clma.fileputter.attributes.ApplicationData;
import at.clma.fileputter.events.INeighborhoodListener;
import at.clma.fileputter.events.NeighborhoodEvent;
import at.clma.fileputter.fileputterGUI.StationBox;
import at.clma.fileputter.stationData.IStationInfo;
import at.clma.fileputter.stationData.StationInfoFactory;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BroadcastListenServer implements Runnable {

    private int port;
    private MulticastSocket socket;
    private boolean stop = false;
    private IStationInfo result;
    private List<INeighborhoodListener> listeners;
    private int lastAnnouncedID = ApplicationData.NO_RESPONSE_ID;

    public BroadcastListenServer(int port)
            throws SocketException, IOException {
        this.port = port;
        createSocket();
        this.listeners = new ArrayList<INeighborhoodListener>();
    }

    private void createSocket() throws SocketException, IOException {
        socket = new MulticastSocket(port);
        socket.joinGroup(InetAddress.getByName(ApplicationData.MULTICASTGROUP));
        socket.setReuseAddress(true);
    }

    @Override
    public void run() {
        String tmp = null;
        while (!stop) {
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                tmp = new String(buf);
                result = new StationInfoFactory().newInstanceFromString(tmp);
                result.setStationAddress(packet.getAddress());
                if (result.getResponseId() == lastAnnouncedID) {
                    notifyListenersBroadcastResponse(result);
                } else if (result.getResponseId() == ApplicationData.NO_RESPONSE_ID) {
                    notifyListenersNewStation(result);
                }

            } catch (IOException e) {
                StationBox.error("Mover.NET", e.getMessage());
            } catch (ParseException e) {
                StationBox.error("Mover.NET", e.getMessage()
                        + " at token " + e.getErrorOffset());
            }
        }
        socket.close();
    }

    /**
     * @return the results
     */
    public IStationInfo getResult() {
        return result;
    }

    public void stop() {
        stop = true;
    }

    public void resume() throws SocketException, IOException {
        stop();
        stop = false;
        createSocket();
    }

    private void notifyListenersNewStation(IStationInfo station) {
        NeighborhoodEvent neighborhoodEvent = new NeighborhoodEvent(this, station);
        for (INeighborhoodListener s : listeners) {
            s.onNewNeighborFound(neighborhoodEvent);
        }
    }

    public void addNeighborhoodListener(INeighborhoodListener listener) {
        listeners.add(listener);
    }

    public void removeNeighborhoodListener(INeighborhoodListener listener) {
        listeners.remove(listener);
    }

    public int getLastAnnouncedID() {
        return lastAnnouncedID;
    }

    public void setLastAnnouncedID(int lastAnnouncedID) {
        this.lastAnnouncedID = lastAnnouncedID;
    }

    private void notifyListenersBroadcastResponse(IStationInfo result) {
        NeighborhoodEvent neighborhoodEvent = new NeighborhoodEvent(this, result);
        for (INeighborhoodListener s : listeners) {
            s.onBroadcastResponse(neighborhoodEvent);
        }
    }
}
