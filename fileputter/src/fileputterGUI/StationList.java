/**
 * @author  Claus Matzinger
 * @date    Jun 18, 2010
 * @file    StationList
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
import at.clma.fileputter.stationData.StationInfo;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * 
 */
public class StationList extends JFrame {

    private static final int WINDOW_WIDTH = 200;
    private static final int WINDOW_HEIGHT = 400;
    private static final Color WINDOW_BACKGROUND = Color.WHITE;
    private static final int MAX_ROWS = 10;
    private static final int MAX_COLS = 1;

    public StationList() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(MAX_ROWS, MAX_COLS));
        getContentPane().setBackground(WINDOW_BACKGROUND);
        setJMenuBar(createMenuBar());
        setTitle(ApplicationData.NAME
                + " v" + ApplicationData.VERSION);

        add(new StationEntry(new StationInfo()));
    }

    private JMenuBar createMenuBar() {
        JMenuBar mainMenu = new JMenuBar();

        JMenu mover = new JMenu(ApplicationData.NAME);
        JMenu help = new JMenu("Help");

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                AboutBox aboutb = new AboutBox();
                aboutb.setVisible(true);
            }
        });

        mover.add(exit);
        help.add(about);

        mainMenu.add(mover);
        mainMenu.add(help);
        return mainMenu;
    }

    public static void main(String[] args) {
        new StationList().setVisible(true);
        return;
    }

    public static void error(String tag, String msg) {
        System.err.println(tag + " Error:" + msg);
    }
}
