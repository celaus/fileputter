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

import at.clma.fileputter.stationData.IStationInfo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Claus Matzinger
 */
public class StationList extends AbstractListModel implements IStationList, ListCellRenderer {

    private static Color backgroundIdle = Color.WHITE;
    private static Color backgroundHover = Color.LIGHT_GRAY;
    private static Color backgroundSelected = Color.DARK_GRAY;
    private static Color foregroundSelected = Color.WHITE;
    private static Color foregroundIdle = Color.BLACK;
    private List<IStationInfo> stations;

    public StationList() {
        stations = new ArrayList<IStationInfo>();
    }

    public void addStation(IStationInfo station) {
        if (find(station) == null) {
            this.stations.add(station);
            fireIntervalAdded(this, stations.indexOf(station), stations.indexOf(station));
        }
    }

    public List<IStationInfo> getStations() {
        return stations;
    }

    public int getSize() {
        return stations.size();
    }

    public Object getElementAt(int index) {
        return (Object) stations.get(index);
    }

    public IStationInfo getForAddress(InetAddress address) {
        for (IStationInfo info : stations) {
            if (info.getStationAddress().equals(address)) {
                return info;
            }
        }
        return null;
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        StationEntry renderer = null;
        if (value instanceof IStationInfo) {
            renderer = new StationEntry((IStationInfo) value);
            renderer.setSelected(isSelected);
        }
        return renderer;
    }

    public void removeStation(IStationInfo station) {
        int index = stations.indexOf(station);
        stations.remove(station);
        fireIntervalRemoved(this, index, index);
    }

    public void removeStation(int index) {
        stations.remove(index);
        fireIntervalRemoved(this, index, index);
    }

    public IStationInfo find(IStationInfo station) {
        int index = stations.indexOf(station);
        return index == -1 ? null : stations.get(index);
    }

    private class StationEntry extends JPanel {

        private static final int SPACER = 20;
        private static final int ITEM_WIDTH = 150;

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(Color.GRAY);
            int h = getHeight() - 1;
            int w = getWidth();
            GradientPaint gradient = new GradientPaint(0, h, backgroundHover, w, h, backgroundIdle);
            ((Graphics2D) g).setPaint(gradient);
            g.drawLine(0, h, w, h);
        }

        public StationEntry(IStationInfo s) {
            setLayout(new GridLayout(2, 1));
            setBackground(backgroundIdle);

            // name
            JLabel name = new JLabel(s.getStationName());
            Font fontStyleBold = new Font(name.getFont().getName(), Font.BOLD, name.getFont().getSize());
            name.setFont(fontStyleBold);
            add(name);

            // ip
            JLabel ip = new JLabel(s.getStationAddress().getHostAddress());
            Font fontStyleItalic = new Font(ip.getFont().getName(), Font.ITALIC, ip.getFont().getSize());
            ip.setFont(fontStyleItalic);
            add(ip);

            // downloads
            /*JPanel dls = new JPanel();
            dls.setLayout(new GridLayout(s.getTransmissions().size(), 1));
            for (ITransfer t : s.getTransmissions()) {
            dls.add(new JLabel("transfer"));
            }*/
            setPreferredSize(new Dimension(ITEM_WIDTH, name.getHeight() + ip.getHeight() + 1 + SPACER * 2));
        }

        private void setSelected(boolean selected) {
            if (selected) {
                this.setBackground(backgroundSelected);
                for (Component c : this.getComponents()) {
                    c.setForeground(foregroundSelected);
                }
            } else {
                this.setBackground(backgroundIdle);
                for (Component c : this.getComponents()) {
                    c.setForeground(foregroundIdle);
                }
            }
        }
    }
}
