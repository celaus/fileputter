/**
 * @author  Claus Matzinger
 * @date    Jul 2, 2010
 * @file    FileUpload
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
import at.clma.fileputter.events.ITransferEventListener;
import at.clma.fileputter.events.TransferEvent;
import at.clma.fileputter.stationData.IStationInfo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author claus
 */
public class FileUpload implements ITransfer {

    private static final int CHUNKSIZE = 1024;
    private ITransmission transmission;
    private IStationInfo station;
    private long status;
    private List<ITransferEventListener> listeners;
    private FileChannel fileChannel = null;
    private File file;
    private long speed;
    private boolean stop;

    public FileUpload(ITransmission transmission, File file) {
        this.transmission = transmission;
        listeners = new ArrayList<ITransferEventListener>();
        status = 0;
        speed = 0;
        stop = false;
        this.file = file;
    }

    public long getSize() {
        return file.length();
    }

    public long getStatus() {
        return status;
    }

    public IStationInfo getStation() {
        return station;
    }

    public long getSpeed() {
        return speed;
    }

    public File getFile() {
        return file;
    }

    private void printV(String s) {
        if (ApplicationData.isVerbose()) {
            System.out.println(s);
        }
    }

    public void run() {
        try {
            transmission.open();

            DataOutputStream out = new DataOutputStream(transmission.getOutputStream());
            DataInputStream in = new DataInputStream(transmission.getInputStream());

            printV("sending filename");
            out.writeUTF(file.getName());
            printV("sending filesize");
            out.writeLong(file.length());
            out.flush();

            printV("reading file " + file.getAbsolutePath() + ": " + file.length());
            fileChannel = new FileInputStream(file).getChannel();

            status = 0;

            printV("Sending file");

            //Date time = new Date();
            SocketChannel socketChannel = (SocketChannel) transmission.getChannel();
            printV("waiting for CTS");
            if (in.readUTF().equals(ApplicationData.CO_CTS)) {
                long chunk;
                while (!stop && status < file.length()) {
                    chunk = Math.min(CHUNKSIZE, file.length() - status);
                    fileChannel.transferTo(status, chunk, transmission.getChannel());
                    status += chunk;
                }


                /*while (!stop && bytesread != -1) {
                os.write(bytesread);
                status++;
                if (status > 50000) {
                speed = status / ((new Date().getTime() - time.getTime()) / 60);
                }
                //System.out.println("current speed = " + speed + "bytes/s");
                bytesread = is.read();
                } */

                System.out.println("upload done: " + status + " bytes sent");
                fileChannel.close();
                if (!stop) {
                    notifyListenersFinished(this);
                } else {
                    notifyListenersAborted(this);
                }
            } else {
                System.out.println("aborted");
                notifyListenersAborted(this);
            }

        } catch (IOException ex) {
            notifyListenersAborted(this);
            System.err.println("error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                transmission.close();
            } catch (IOException ex) {
                // nothing
            }
        }
    }

    public void stop() {
        stop = true;
    }

    private void notifyListenersFinished(FileUpload ul) {
        TransferEvent e = new TransferEvent(this, transmission);


        for (ITransferEventListener s : listeners) {
            s.onTransferFinished(e);
        }
    }

    private void notifyListenersAborted(FileUpload ul) {
        TransferEvent e = new TransferEvent(this, transmission);


        for (ITransferEventListener s : listeners) {
            s.onTransferAborted(e);
        }
    }

    public String getPath() {
        return file.getAbsolutePath();
    }
}
