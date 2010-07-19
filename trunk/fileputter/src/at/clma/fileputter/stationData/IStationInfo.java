/**
 * @author  Claus Matzinger
 * @date    Jun 13, 2010
 * @file    IStationInfo
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
import java.net.InetAddress;
import java.util.List;

/**
 *
 * @author Claus Matzinger
 */
public interface IStationInfo {

    public void setStationAddress(InetAddress stationAddress);

    public InetAddress getStationAddress();

    public String getStationName();

    public String getNetworkString();

    public void add(ITransfer transfers);

    public ITransfer remove(ITransfer transfers);

    public List<ITransfer> getTransmissions();
}
