/**
 * @author  Claus Matzinger
 * @date    Jun 15, 2010
 * @file    StationBox
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

import at.clma.fileputter.attributes.ApplicationData;
import at.clma.fileputter.broadcastAnnouncer.AnnouncementBroadcast;
import at.clma.fileputter.broadcastAnnouncer.AnnouncementResponse;
import at.clma.fileputter.events.INeighborhoodListener;
import at.clma.fileputter.events.NeighborhoodEvent;
import at.clma.fileputter.events.TransferEvent;
import at.clma.fileputter.events.TransmissionEvent;
import at.clma.fileputter.listener.TCPTransmissionServer;
import at.clma.fileputter.events.ITransferEventListener;
import at.clma.fileputter.events.ITransmissionEventListener;
import at.clma.fileputter.listener.BroadcastListenServer;
import at.clma.fileputter.stationData.IStationInfo;
import at.clma.fileputter.stationData.StationInfo;
import at.clma.fileputter.transmission.DefaultInitializer;
import at.clma.fileputter.transmission.FileDownload;
import at.clma.fileputter.transmission.FileUpload;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 *
 * @author Claus Matzinger
 */
public class StationBox extends JFrame implements INeighborhoodListener, ITransferEventListener, ITransmissionEventListener {

    private static final int WINDOW_WIDTH = 200;
    private static final int WINDOW_HEIGHT = 400;
    private static final Color WINDOW_BACKGROUND = Color.WHITE;
    // Data
    private IStationList stations;
    private JList stationList;
    private ITransferEventListener listener = this;
    // Services
    private TCPTransmissionServer tcpServer;
    private BroadcastListenServer broadcastListenServer;
    private SystemTrayIcon icon;

    public StationBox() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(WINDOW_BACKGROUND);
        setJMenuBar(createMenuBar());
        setTitle(ApplicationData.NAME
                + " v" + ApplicationData.VERSION);

        stations = new StationList();
        stationList = new JList(stations);
        stationList.setLayoutOrientation(JList.VERTICAL);
        stationList.setCellRenderer((StationList) stations);

        JScrollPane scrollPane = new JScrollPane(stationList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);


        stationList.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (stationList.getSelectedValue() != null) {
                    JFileChooser fc = new JFileChooser();
                    if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        FileUpload ul = new DefaultInitializer().createUpload(
                                (IStationInfo) stationList.getSelectedValue(),
                                fc.getSelectedFile());
                        ul.addTransferEventListener(listener);
                        System.out.println("Starting upload");
                        new Thread(ul).start();
                    }
                }

            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });

        if (Preferences.userNodeForPackage(ApplicationData.class).getBoolean(ApplicationData.OPT_AUTOANNOUNCE, true)) {
        }


        startServices();
    }

    private JMenuBar createMenuBar() {
        JMenuBar mainMenu = new JMenuBar();

        JMenu fileputter = new JMenu(ApplicationData.NAME);
        JMenu help = new JMenu("Help");


        JMenuItem add = new JMenuItem("Add Station");
        add.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                AddStationBox asb = new AddStationBox();
                asb.setVisible(true);
                if (asb.getStationInfo() != null) {
                    stations.addStation(asb.getStationInfo());
                }

            }
        });

        JMenuItem auto = new JMenuItem("Autofind Stations");
        add.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    broadcastListenServer.setLastAnnouncedID(announce());
                } catch (IOException ex) {
                    error("Error", ex.getMessage());
                }
            }
        });

        JMenuItem preferences = new JMenuItem("Preferences");
        preferences.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new PreferencesBox().setVisible(true);
            }
        });

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new AboutBox().setVisible(true);
            }
        });

        fileputter.add(add);
        fileputter.add(auto);
        fileputter.addSeparator();
        fileputter.add(preferences);
        fileputter.addSeparator();
        fileputter.add(exit);
        help.add(about);

        mainMenu.add(fileputter);
        mainMenu.add(help);
        return mainMenu;
    }

    public static void main(String[] args) throws UnknownHostException, SocketException {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-v")) {
                ApplicationData.setVerbose(true);
            }
        }
        new StationBox().setVisible(true);
    }

    private IStationInfo getLocalhost() {
        IStationInfo s = null;
        try {
            s = new StationInfo(getStationName(), ApplicationData.NO_RESPONSE_ID, InetAddress.getLocalHost());
            System.out.println("localhost: " + InetAddress.getLocalHost());
        } catch (UnknownHostException ex) {
            error("General Exception", ex.getMessage());
            System.exit(1);
        }
        return s;
    }

    public static void error(String tag, String msg) {
        JOptionPane.showMessageDialog((JFrame) null, msg, tag, JOptionPane.ERROR_MESSAGE);
        System.err.println(tag + " Error:" + msg);
    }

    private void startServices() {
        try {
            try {
                icon = new SystemTrayIcon(this);
            } catch (AWTException ex) {
                error("Error", ex.getMessage());
            }
            tcpServer = new TCPTransmissionServer(stations, ApplicationData.TCPPORT);
            tcpServer.addTransferEventListener(this);

            broadcastListenServer = new BroadcastListenServer(ApplicationData.MULTICASTPORT);
            broadcastListenServer.addNeighborhoodListener(this);

            if (isAutoAnnounce()) {
                broadcastListenServer.setLastAnnouncedID(announce());
            }

            new Thread(broadcastListenServer).start();
            new Thread(tcpServer).start();
        } catch (Exception ex) {
            error("Error", ex.getMessage());
            System.exit(1);
        }
    }

    private int announce() throws UnknownHostException, IOException {
        return new AnnouncementBroadcast(getLocalhost(), InetAddress.getByName(ApplicationData.MULTICASTGROUP)).send();
    }

    private String getStationName() {
        Preferences prefs = Preferences.userNodeForPackage(ApplicationData.class);
        return prefs.get(ApplicationData.OPT_STATIONNAME, "Unnamed");
    }

    private boolean isAutoAnnounce() {
        Preferences prefs = Preferences.userNodeForPackage(ApplicationData.class);
        return prefs.getBoolean(ApplicationData.OPT_AUTOANNOUNCE, true);
    }

    private boolean isAutoReply() {
        Preferences prefs = Preferences.userNodeForPackage(ApplicationData.class);
        return prefs.getBoolean(ApplicationData.OPT_AUTOREPLY, false);
    }

    public void onTransferStarted(TransferEvent evt) {
    }

    public void onTransferFinished(TransferEvent evt) {
        JOptionPane.showMessageDialog(this, "Transfer of " + evt.getTransfer().getPath() + " finished", "Finished", JOptionPane.INFORMATION_MESSAGE);
    }

    public void onTransferAborted(TransferEvent evt, String reason) {
        error(evt.getTransfer().getPath() + " failed. Reason: " + reason, "Transfer failed!");
    }

    public void onNewTransmission(TransmissionEvent evt) {
        if (JOptionPane.showConfirmDialog(this, "Allow incoming transmission from "
                + evt.getTransmission().getPartner().getStationAddress().getHostAddress() + "?",
                "Allow?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            JFileChooser filechooser = new JFileChooser();
            filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (filechooser.showSaveDialog(this) != JFileChooser.ABORT) {
                FileDownload dl = new DefaultInitializer().createDownload(evt.getTransmission());
                dl.addTransferEventListener(evt.getTransmission().getPartner());
                dl.addTransferEventListener(this);
                dl.setFile(filechooser.getSelectedFile());
                dl.getStation().add(dl);
                new Thread(dl).start();
            }
        }
    }

    public void onNewNeighborFound(NeighborhoodEvent evt) {
        if (getLocalhost().equals(evt.getStation()) || stations.find(evt.getStation()) != null) {
            return;
        }
        if (isAutoReply() || JOptionPane.showConfirmDialog(this, "New Station " + evt.getStation().getStationName() + " (" + evt.getStation().getStationAddress().getHostAddress() + ") found! Add?",
                "Add?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                stations.addStation(evt.getStation());
                new AnnouncementResponse(getLocalhost(), evt.getStation()).send();
            } catch (IOException ex) {
                error("Error", ex.getLocalizedMessage());
            }
        }
    }

    public void onBroadcastResponse(NeighborhoodEvent evt) {
        stations.addStation(evt.getStation());
    }
}
