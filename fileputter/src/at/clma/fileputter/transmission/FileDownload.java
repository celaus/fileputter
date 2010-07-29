/**
 * @author  Claus Matzinger
 * @date    Jul 2, 2010
 * @file    FileDownload
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Claus Matzinger
 */
public class FileDownload implements ITransfer {

    private static final int CHUNKSIZE = 1024000;
    private ITransmission transmission;
    private long status;
    private List<ITransferEventListener> listeners;
    private File file;
    private long speed;
    private boolean stop;
    private long filesize;

    public FileDownload(ITransmission transmission, File file) {
        this.transmission = transmission;
        listeners = new ArrayList<ITransferEventListener>();
        status = 0;
        speed = 0;
        stop = false;
        if (file.isDirectory()) {
            this.file = file;
        } else {
            this.file = file.getParentFile();
        }
    }

    public FileDownload(ITransmission transmission) {
        this.transmission = transmission;
        listeners = new ArrayList<ITransferEventListener>();
        status = 0;
        speed = 0;
        filesize = 0;
        stop = false;
    }

    private void printV(String s) {
        if (ApplicationData.isVerbose()) {
            System.out.println(s);
        }
    }

    public void run() {
        try {
            printV("opening connection");
            notifyListenersStarted(this);
            transmission.open();
            DataInputStream in = new DataInputStream(transmission.getInputStream());
            DataOutputStream out = new DataOutputStream(transmission.getOutputStream());

            printV("sending CTS");
            out.writeUTF(ApplicationData.CO_CTS);
            out.flush();

            printV("getting filename");
            String filename = in.readUTF();
            printV("filename is " + filename);
            file = new File(file.getAbsolutePath() + "/" + filename);

            printV("getting filesize");
            filesize = in.readLong();

            printV("filesize " + filesize);

            printV("creating file " + file.getAbsolutePath());
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            SocketChannel sock = (SocketChannel) transmission.getChannel();
            FileChannel fileChannel = new FileOutputStream(file).getChannel();

            if (sock == null) {
                System.err.println("error sock is null");
            }
            printV("reading stream");

            status = 0;
            long chunk;
            Date start = new Date();
            while (!stop && status < filesize) {
                chunk = Math.min(CHUNKSIZE, filesize - status);
                fileChannel.transferFrom(sock, status, chunk);
                status += chunk;
                /*if (status > 50000) {
                speed = status / ((new Date().getTime() - time.getTime()) / 60);
                }*/ //System.out.println("current speed = " + speed + "bytes/s");
            }
            fileChannel.close();
            if (!stop) {
                long elapsed = (new Date().getTime() - start.getTime()) / 1000;
                if (elapsed == 0) {
                    elapsed = 1;
                }
                long bytesPerSecond = status / elapsed;
                printV("download done got " + status + " bytes, speed was " + bytesPerSecond / 1024.0 + " kb/s");

                notifyListenersFinished(this);
            } else {
                notifyListenersAborted(this, "Transfer aborted!");
            }

        } catch (IOException ex) {
            notifyListenersAborted(this, ex.getMessage());
            //System.err.println("error: " + ex.getMessage());
            //ex.printStackTrace();
        } finally {
            try {
                transmission.close();
            } catch (IOException ex) {
                // nothing
            }
        }
    }

    public IStationInfo getStation() {
        return transmission.getPartner();
    }

    public synchronized void addTransferEventListener(ITransferEventListener listener) {
        listeners.add(listener);
    }

    public synchronized void removeTransferEventListener(ITransferEventListener listener) {
        listeners.remove(listener);
    }

    private void notifyListenersStarted(FileDownload dl) {
        TransferEvent e = new TransferEvent(this, dl);

        for (ITransferEventListener s : listeners) {
            s.onTransferStarted(e);
        }
    }

    private void notifyListenersFinished(FileDownload dl) {
        TransferEvent e = new TransferEvent(this, dl);

        for (ITransferEventListener s : listeners) {
            s.onTransferFinished(e);
        }
    }

    private void notifyListenersAborted(FileDownload dl, String reason) {
        TransferEvent e = new TransferEvent(this, dl);

        for (ITransferEventListener s : listeners) {
            s.onTransferAborted(e, reason);
        }
    }

    public long getSize() {
        return filesize;
    }

    public long getStatus() {
        return status;
    }

    public long getSpeed() {
        return speed;
    }

    public String getPath() {
        return file.getAbsolutePath();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        if (file.isDirectory()) {
            this.file = file;
        } else {
            this.file = file.getParentFile();
        }
    }

    public synchronized void stop() {
        stop = true;
    }
}
