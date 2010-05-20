/**
 * @author  Claus Matzinger (S0810307022)
 * @date    May 18, 2010
 * @file    Mover
 */
package fileputterGUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import stationData.StationInfo;

/**
 *
 * 
 */
public class Mover extends JFrame {

    public Mover() {
        setSize(200, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 1));
        getContentPane().setBackground(Color.WHITE);

        JMenuBar mainMenu = new JMenuBar();
        JMenu mover = new JMenu("Mover");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mover.add(exit);
        mainMenu.add(mover);
        setJMenuBar(mainMenu);

        add(new StationEntry(new StationInfo()));
    }

    public static void main(String[] args) {
        new Mover().setVisible(true);
        return;
    }

    public static void error(String tag, String msg) {
        System.err.println(tag + " Error:" + msg);
    }
}
