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

import java.net.InetAddress;
import java.util.Random;

public class StationInfo implements IStationInfo {

    protected static final String NAMEPREFIX = "_name";
    protected static final String RESPONSEPREFIX = "_responseid";
    protected static final String IDPREFIX = "_id";

    private int id;
    private String stationName;
    private int responseId;
    private InetAddress stationAddress;
    /**
     *
     */
    private static final long serialVersionUID = -7345931383382764925L;

    public StationInfo() {
        stationName = "testPackage";
        this.responseId = -1;
        this.id = -1;
    }

    public StationInfo(String stationName, int respondsTo) {
        this.stationName = stationName;
        this.responseId = respondsTo;
        this.id = new Random().nextInt(Integer.MAX_VALUE);
    }

    public StationInfo(String stationName, int respondsTo, int id) {
        this.stationName = stationName;
        this.responseId = respondsTo;
        this.id = id;
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
        sb.append(NAMEPREFIX + "=" + stationName + ";");
        sb.append(RESPONSEPREFIX + "=" + responseId + ";");
        sb.append(IDPREFIX + "=" + id);
        return sb.toString();
    }

    public void setStationAddress(InetAddress stationAddress) {
        this.stationAddress = stationAddress;
    }

    public InetAddress getStationAddress() {
        return stationAddress;
    }

    public boolean equals(StationInfo s) {
        return this.stationAddress.equals(s);
    }
}
