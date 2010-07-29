/**
 * @author  Claus Matzinger
 * @date    Jun 15, 2010
 * @file    ITransmission
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

import at.clma.fileputter.stationData.IStationInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.ByteChannel;

/**
 *
 * @author Claus Matzinger
 */
public interface ITransmission {

    public void open() throws IOException;

    public void close() throws IOException;

    public ByteChannel getChannel();

    public InputStream getInputStream() throws IOException;

    public OutputStream getOutputStream() throws IOException;

    public boolean isConnectionOpen();

    public IStationInfo getPartner();
}
