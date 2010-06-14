/**
 * @author  Claus Matzinger (S0810307022)
 * @date    Jun 02, 2010
 * @file    StationEntry
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

import at.clma.fileputter.stationData.IStationInfo;
import at.clma.fileputter.stationData.StationInfo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * 
 */
public class StationEntry extends JPanel {

    private static final int SPACER = 10;
    private IStationInfo holdingObject;
    private boolean selected;
    private static Color backgroundIdle = Color.WHITE;
    private static Color backgroundHover = Color.LIGHT_GRAY;
    private static Color backgroundSelected = Color.DARK_GRAY;
    private static Color foregroundSelected = Color.WHITE;
    private static Color foregroundIdle = Color.BLACK;

    public StationEntry(StationInfo s) {
        holdingObject = s;
        setLayout(new GridLayout(2, 1));
        JLabel name = new JLabel(s.getStationName());
        Font fontStyleBold = new Font(name.getFont().getName(), Font.BOLD, name.getFont().getSize());
        name.setFont(fontStyleBold);
        add(name);

        JLabel ip = new JLabel("192.168.1.180");
        Font fontStyleItalic = new Font(ip.getFont().getName(), Font.ITALIC, ip.getFont().getSize());
        ip.setFont(fontStyleItalic);
        add(ip);

        setBackground(backgroundIdle);
        addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (!selected) {
                    select();
                } else {
                    unSelect();
                }
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
                hover();
            }

            public void mouseExited(MouseEvent e) {
                unHover();
            }
        });


        setPreferredSize(new Dimension(name.getHeight() + ip.getHeight() + SPACER * 3, this.getWidth() + SPACER * 2));

    }

    private void select() {
        selected = true;
        this.setBackground(backgroundSelected);
        for (Component c : this.getComponents()) {
            c.setForeground(foregroundSelected);
        }
    }

    private void unSelect() {
        selected = false;
        this.setBackground(backgroundIdle);
        for (Component c : this.getComponents()) {
            c.setForeground(foregroundIdle);
        }
    }

    private void hover() {
        this.setBackground(backgroundHover);
    }

    private void unHover() {
        if (!selected) {
            this.setBackground(backgroundIdle);
        } else {
            this.setBackground(backgroundSelected);
        }
    }
}
