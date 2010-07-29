/**
 * @author  Claus Matzinger
 * @date    Jun 13, 2010
 * @file    StationInfo
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
package at.clma.fileputter.stationData;

import at.clma.fileputter.transmission.ITransfer;
import at.clma.fileputter.events.TransferEvent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Claus Matzinger
 */
public class StationInfo implements IStationInfo {

    protected static final String NAMEPREFIX = "_name";
    protected static final String RESPONSEPREFIX = "_responseid";
    protected static final String IDPREFIX = "_id";
    private int id;
    private String stationName;
    private int responseId;
    private InetAddress stationAddress;
    private List<ITransfer> transfers;
    /**
     *
     */
    private static final long serialVersionUID = -7345931383382764925L;

    public StationInfo(String stationName, int respondsTo) {
        this.stationName = stationName;
        this.responseId = respondsTo;
        this.id = new Random().nextInt(Integer.MAX_VALUE);
        transfers = new ArrayList<ITransfer>();
    }

    public StationInfo(String stationName, int respondsTo, int id) {
        this.stationName = stationName;
        this.responseId = respondsTo;
        this.id = id;
        transfers = new ArrayList<ITransfer>();
    }

    public StationInfo(String stationName, int respondsTo, InetAddress address) {
        this.stationName = stationName;
        this.responseId = respondsTo;
        this.stationAddress = address;
        transfers = new ArrayList<ITransfer>();
    }

    @Override
    public String toString() {
        return stationName;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getResponseId() {
        return responseId;
    }

    public int getId() {
        return id;
    }

    public String getNetworkString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NAMEPREFIX).append("=").append(stationName).append(";");
        sb.append(RESPONSEPREFIX).append("=").append(responseId).append(";");
        sb.append(IDPREFIX).append("=").append(id);
        return sb.toString();
    }

    public void setStationAddress(InetAddress stationAddress) {
        this.stationAddress = stationAddress;
    }

    public InetAddress getStationAddress() {
        return stationAddress;
    }

    public boolean equals(StationInfo s) {
        return this.stationAddress.equals(s.getStationAddress());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StationInfo) {
            return this.equals((StationInfo) obj);
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.id;
        hash = 43 * hash + (this.stationName != null ? this.stationName.hashCode() : 0);
        hash = 43 * hash + (this.stationAddress != null ? this.stationAddress.hashCode() : 0);
        return hash;
    }

    public synchronized void onTransferStarted(TransferEvent evt) {
        transfers.add(evt.getTransfer());
    }

    public synchronized void onTransferFinished(TransferEvent evt) {
        if (transfers.contains(evt.getTransfer())) {
            transfers.remove(evt.getTransfer());
        }
    }

    public synchronized void add(ITransfer transfer) {
        this.transfers.add(transfer);
    }

    public synchronized void remove(ITransfer transfer) {
        transfers.remove(transfer);
    }

    public List<ITransfer> getTransmissions() {
        return transfers;
    }

    public void onTransferAborted(TransferEvent evt, String reason) {
        if (transfers.contains(evt.getTransfer())) {
            transfers.remove(evt.getTransfer());
        }
    }

    public void setResponseId(int id) {
        responseId = id;
    }
}
