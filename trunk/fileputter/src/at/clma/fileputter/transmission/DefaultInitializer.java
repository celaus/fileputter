/**
 * @author  Claus Matzinger
 * @date    Jul 5, 2010
 * @file    DefaultInitializer
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

import at.clma.fileputter.attributes.ApplicationData;
import at.clma.fileputter.stationData.IStationInfo;
import java.io.File;

/**
 *
 * @author claus
 */
public class DefaultInitializer implements IUploadInitializer, IDownloadInitializer {

    public DefaultInitializer() {
    }

    public FileUpload createUpload(IStationInfo to, String srcPath) {
        return new FileUpload(new TCPTransmission(to,
                ApplicationData.TCPPORT), new File(srcPath));
    }

    public FileUpload createUpload(IStationInfo to, File src) {
        return new FileUpload(new TCPTransmission(to,
                ApplicationData.TCPPORT), src);
    }

    public FileDownload createDownload(ITransmission transmission) {
        return new FileDownload(transmission);
    }
}
