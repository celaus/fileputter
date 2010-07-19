/**
 * @author  Claus Matzinger
 * @date    Jun 15, 2010
 * @file    MainWindow
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
import at.clma.fileputter.events.TransferEvent;
import at.clma.fileputter.transmission.ITransfer;
import at.clma.fileputter.listener.INetworkListener;
import at.clma.fileputter.listener.TCPTransmissionServer;
import at.clma.fileputter.events.IStationEventListener;
import at.clma.fileputter.events.ITransferEventListener;
import at.clma.fileputter.events.StationEvent;
import at.clma.fileputter.stationData.IStationInfo;
import at.clma.fileputter.transmission.DefaultInitializer;
import at.clma.fileputter.transmission.FileDownload;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.Thread;
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
public class MainWindow extends JFrame implements IStationEventListener, ITransferEventListener {

    private static final int WINDOW_WIDTH = 200;
    private static final int WINDOW_HEIGHT = 400;
    private static final Color WINDOW_BACKGROUND = Color.WHITE;
    // Data
    private IStationList stations;
    private JList stationList;
    // Services
    private INetworkListener tcpServer;

    public MainWindow() {
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
                        ITransfer ul = new DefaultInitializer().createUpload(
                                (IStationInfo) stationList.getSelectedValue(),
                                fc.getSelectedFile());
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
        fileputter.addSeparator();
        fileputter.add(preferences);
        fileputter.addSeparator();
        fileputter.add(exit);
        help.add(about);

        mainMenu.add(fileputter);
        mainMenu.add(help);
        return mainMenu;
    }

    public static void main(String[] args) {
        for(int i = 0; i < args.length; i++){
            if(args[i].equals("-v")) {
                ApplicationData.setVerbose(true);
            }
        }
        new MainWindow().setVisible(true);
    }

    public static void error(String tag, String msg) {
        System.err.println(tag + " Error:" + msg);
    }

    private void startServices() {
        try {
            tcpServer = new TCPTransmissionServer(ApplicationData.PORT);
            tcpServer.addTransmissionListener(this);
            new Thread(tcpServer).start();
        } catch (IOException ex) {
            System.err.println("IO Exception");
        }
    }

    public void onStationFound(StationEvent evt) {
        // nothing yet
    }

    public void onIncomingTransmission(StationEvent evt, ITransfer transfer) {
    }

    public void onIncomingTransfer(TransferEvent evt) {
        if (JOptionPane.showConfirmDialog(this, "Allow incoming transmission?",
                "Allow?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            JFileChooser filechooser = new JFileChooser();
            filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            filechooser.showSaveDialog(this);

            FileDownload dl = new FileDownload(evt.getTransmission(), filechooser.getSelectedFile());
            System.out.println("Download started!");
            stations.getForAddress(evt.getTransmission().getPartner()).add(dl);
            new Thread(dl).start();

        } else {
            try {
                evt.getTransmission().close();
            } catch (IOException ex) {
            }
        }
    }

    public void onTransferFinished(TransferEvent evt) {
        System.out.println("transfer done");
    }

    public void onTransferAborted(TransferEvent evt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
