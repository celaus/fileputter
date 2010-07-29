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
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author claus
 */
public class FileUpload implements ITransfer {

    private static final int CHUNKSIZE = 1024000;
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

            printV("waiting for CTS");
            if (in.readUTF().equals(ApplicationData.CO_CTS)) {

                printV("sending filename");
                out.writeUTF(file.getName());

                printV("sending filesize");
                out.writeLong(file.length());
                out.flush();

                printV("reading file " + file.getAbsolutePath() + ": " + file.length());
                fileChannel = new FileInputStream(file).getChannel();

                status = 0;

                printV("Sending file");

                Date start = new Date();
                SocketChannel socketChannel = (SocketChannel) transmission.getChannel();

                long chunk;
                while (!stop && status < file.length()) {
                    chunk = Math.min(CHUNKSIZE, file.length() - status);
                    fileChannel.transferTo(status, chunk, socketChannel);
                    status += chunk;
                }

                fileChannel.close();
                if (!stop) {
                    long elapsed = (new Date().getTime() - start.getTime()) / 1000;
                    if (elapsed == 0) {
                        elapsed = 1;
                    }
                    long bytesPerSecond = status / elapsed;
                    printV("upload done: " + status + " bytes sent. speed was " + bytesPerSecond / 1024.0 + " kb/s");
                    notifyListenersFinished(this);
                } else {
                    notifyListenersAborted(this, "Client aborted!");
                }
            } else {
                notifyListenersAborted(this, "Clear to send not received.");
            }

        } catch (IOException ex) {
            notifyListenersAborted(this, ex.getMessage());
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
        TransferEvent e = new TransferEvent(this, ul);


        for (ITransferEventListener s : listeners) {
            s.onTransferFinished(e);
        }
    }

    private void notifyListenersAborted(FileUpload ul, String reason) {
        TransferEvent e = new TransferEvent(this, ul);


        for (ITransferEventListener s : listeners) {
            s.onTransferAborted(e, reason);
        }
    }

    public String getPath() {
        return file.getAbsolutePath();
    }

    public synchronized void addTransferEventListener(ITransferEventListener listener) {
        listeners.add(listener);
    }

    public synchronized void removeTransferEventListener(ITransferEventListener listener) {
        listeners.remove(listener);
    }
}
