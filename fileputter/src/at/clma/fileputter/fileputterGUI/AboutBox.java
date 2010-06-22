/**
 * @author  Claus Matzinger
 * @date    Jun 14, 2010
 * @file    AboutBox
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author claus
 */
public class AboutBox extends javax.swing.JDialog {

    /** 
     * Creates new form AboutBox
     */
    public AboutBox() {
        super((JFrame) null, true);
        initComponents();

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setTitle("About " + ApplicationData.NAME);
        license.setText(ApplicationData.LICENSE);
        license.setWrapStyleWord(true);
        license.setLineWrap(true);

        copyright.setText(ApplicationData.COPYRIGHT);
        description.setText(ApplicationData.DESCRIPTION_SHORT);
        appName.setText(ApplicationData.NAME + " v" + ApplicationData.VERSION);
        web.setText(ApplicationData.WEB);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        close = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        license = new javax.swing.JTextArea();
        appName = new javax.swing.JLabel();
        copyright = new javax.swing.JLabel();
        description = new javax.swing.JLabel();
        web = new javax.swing.JLabel();

        setAlwaysOnTop(true);
        setModal(true);
        setResizable(false);

        close.setText("Close");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/at/clma/fileputter/attributes/logo64x64.png"))); // NOI18N

        license.setColumns(20);
        license.setRows(5);
        license.setEnabled(false);
        jScrollPane1.setViewportView(license);

        appName.setText("jLabel2");

        copyright.setText("jLabel3");

        description.setFont(new java.awt.Font("DejaVu Sans", 2, 13)); // NOI18N
        description.setText("jLabel2");

        web.setFont(new java.awt.Font("Courier New", 1, 13)); // NOI18N
        web.setForeground(new java.awt.Color(0, 17, 255));
        web.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(description)
                            .addComponent(copyright)
                            .addComponent(appName)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(web)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 250, Short.MAX_VALUE)
                        .addComponent(close)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(appName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(copyright)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(description)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(web)
                    .addComponent(close))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appName;
    private javax.swing.JButton close;
    private javax.swing.JLabel copyright;
    private javax.swing.JLabel description;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea license;
    private javax.swing.JLabel web;
    // End of variables declaration//GEN-END:variables
}