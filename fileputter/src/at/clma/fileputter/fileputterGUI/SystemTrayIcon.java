/**
 * @author  Claus Matzinger (S0810307022)
 * @date    25.07.2010
 * @file    SystemTrayIcon
 */
package at.clma.fileputter.fileputterGUI;

import at.clma.fileputter.attributes.ApplicationData;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;

/**
 *
 * @author Claus Matzinger
 */
public class SystemTrayIcon implements MouseListener {

    private JFrame frame;
    private JPopupMenu popupMenu;

    public SystemTrayIcon(JFrame main) throws AWTException {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            URL imgURL = ApplicationData.class.getResource("logo64x64.png");
            Image img = Toolkit.getDefaultToolkit().getImage(imgURL);
            TrayIcon trayIcon = new TrayIcon(img);
            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(this);
            tray.add(trayIcon);
            frame = main;
            popupMenu = new JPopupMenu(ApplicationData.NAME);

            //popupMenu.add()
        }
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
        frame.setVisible(!frame.isVisible());
        

    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
