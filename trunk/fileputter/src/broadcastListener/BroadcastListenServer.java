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

package at.clma.fileputter.broadcastListener;

import at.clma.fileputter.fileputterGUI.StationList;
import at.clma.fileputter.stationData.IStationInfo;
import at.clma.fileputter.stationData.StationInfo;
import at.clma.fileputter.stationData.StationInfoFactory;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.ParseException;

public class BroadcastListenServer implements Runnable {

    private int port;
    private Handler messageHandler = new Handler();
    private DatagramSocket socket;
    private Runnable backPoster;
    private boolean interrupt = false;
    private IStationInfo result;

    public BroadcastListenServer(int port, Runnable backPoster)
            throws SocketException {
        this.backPoster = backPoster;
        this.port = port;
        createSocket(port);
    }

    private void createSocket(int port) throws SocketException {
        socket = new DatagramSocket(port);
        socket.setReuseAddress(true);
    }

    @Override
    public void run() {
        String tmp = null;
        while (!interrupt) {
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                tmp = new String(buf);
                result = StationInfoFactory.newInstanceFromString(tmp);
                result.setStationAddress(packet.getAddress());
                messageHandler.post(backPoster);
            } catch (IOException e) {
                StationList.error("Mover.NET", e.getMessage());
            } catch (ParseException e) {
                StationList.error("Mover.NET", e.getMessage()
                        + " at token " + e.getErrorOffset());
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        socket.close();
    }

    /**
     * @return the results
     */
    public IStationInfo getResult() {
        return result;
    }

    /**
     * @param results
     *            the results to set
     */
    public void setResult(StationInfo result) {
        this.result = result;
    }

    public void interrupt() {
        interrupt = true;
        socket.close();
    }

    public void resume() throws SocketException {
        interrupt = false;
        createSocket(port);
    }
}
