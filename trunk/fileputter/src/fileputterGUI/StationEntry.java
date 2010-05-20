/**
 * @author  Claus Matzinger (S0810307022)
 * @date    May 18, 2010
 * @file    StationEntry
 */
package fileputterGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import stationData.StationInfo;

/**
 *
 * 
 */
public class StationEntry extends JPanel {

    private static final int SPACER = 10;
    private StationInfo holdingObject;
    private boolean selected;
    private static Color backgroundIdle = Color.WHITE;
    private static Color backgroundHover = Color.LIGHT_GRAY;
    private static Color backgroundSelected = Color.DARK_GRAY;

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
    }

    private void unSelect() {
        selected = false;
        this.setBackground(backgroundIdle);
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
