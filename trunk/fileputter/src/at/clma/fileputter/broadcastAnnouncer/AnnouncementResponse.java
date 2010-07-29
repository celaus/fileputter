/**
 * @author  Claus Matzinger
 * @date    Jun 5, 2010
 * @file    AnnouncementMessage
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
package at.clma.fileputter.broadcastAnnouncer;

import at.clma.fileputter.stationData.IStationInfo;
import java.io.IOException;

public class AnnouncementResponse extends AbstractAnnouncement {
  
    public AnnouncementResponse(IStationInfo localhost, IStationInfo to) throws IOException {
        this.interfAddress = to.getStationAddress();
        stInfo = localhost;
        stInfo.setResponseId(to.getId());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
