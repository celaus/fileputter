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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

/**
 *
 * @author Claus Matzinger
 */
public class MainWindow extends JFrame {

    private static final int WINDOW_WIDTH = 200;
    private static final int WINDOW_HEIGHT = 400;
    private static final Color WINDOW_BACKGROUND = Color.WHITE;
    // Data
    private IStationList stations;
    private JList stationList;

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
        if (Preferences.userNodeForPackage(ApplicationData.class).getBoolean(ApplicationData.OPT_AUTOANNOUNCE, true)) {
        }
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
        new MainWindow().setVisible(true);
    }

    public static void error(String tag, String msg) {
        System.err.println(tag + " Error:" + msg);
    }
}
