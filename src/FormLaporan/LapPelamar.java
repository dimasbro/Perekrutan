/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FormLaporan;

import perekrutan.MenuUtama;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;
import koneksi.DB_laporan;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author dimas
 */
public class LapPelamar extends javax.swing.JFrame {

    /**
     * Creates new form Lap_keseluruhan
     */
    public LapPelamar() {
        initComponents();
        Dimension dim= Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(dim.width, dim.height);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtampilP = new javax.swing.JButton();
        BkeluarP = new javax.swing.JButton();
        jScrollPanel = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BtampilP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Print.png"))); // NOI18N
        BtampilP.setText("TAMPIL");
        BtampilP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtampilPActionPerformed(evt);
            }
        });

        BkeluarP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Exit.png"))); // NOI18N
        BkeluarP.setText("KELUAR");
        BkeluarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BkeluarPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtampilP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BkeluarP)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtampilP)
                    .addComponent(BkeluarP))
                .addGap(18, 18, 18)
                .addComponent(jScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        setBounds(0, 0, 1316, 739);
    }// </editor-fold>//GEN-END:initComponents

    private void BtampilPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtampilPActionPerformed
        try{
            String reportName= DB_laporan.PathReport + "Pelamar.jasper";
            HashMap parameter= new HashMap();
            File reportFile= new File(reportName);
            
            JasperReport JReport= (JasperReport) JRLoader.loadObject(reportFile.getPath());
            JasperPrint jPrint= JasperFillManager.fillReport(JReport, parameter, DB_laporan.getkoneksi());
            JRViewer viewer= new JRViewer(jPrint);
            viewer.setOpaque(true);
            viewer.setVisible(true);
            jScrollPanel.add(viewer);
            jScrollPanel.setViewportView(viewer);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }//GEN-LAST:event_BtampilPActionPerformed

    private void BkeluarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BkeluarPActionPerformed
        MenuUtama zz= new MenuUtama();
        zz.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_BkeluarPActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LapPelamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LapPelamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LapPelamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LapPelamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LapPelamar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BkeluarP;
    private javax.swing.JButton BtampilP;
    private javax.swing.JScrollPane jScrollPanel;
    // End of variables declaration//GEN-END:variables
}
