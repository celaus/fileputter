/**
 * @author  Claus Matzinger
 * @date    Jun 15, 2010
 * @file    IStationList
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
package at.clma.fileputter.fileputterGUI;

import at.clma.fileputter.stationData.IStationInfo;
import java.util.List;
import javax.swing.ListModel;

/**
 *
 * @author Claus Matzinger
 */
public interface IStationList extends ListModel {

    /**
     * Adds a station.
     * @param station The station.
     */
    public void addStation(IStationInfo station);

    /**
     * Removes a station.
     * @param station The station.
     */
    public void removeStation(IStationInfo station);

    /**
     * Removes a station by its index.
     * @param index The index.
     */
    public void removeStation(int index);

    /**
     * Gets all known stations in a list.
     * @return A list of known stations.
     */
    public List<IStationInfo> getStations();
}
