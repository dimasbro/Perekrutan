/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package perekrutan;
import com.sun.glass.events.KeyEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.DB_form;
/**
 *
 * @author dimas
 */
public class karyawan extends javax.swing.JFrame {
    DB_form con;
    private Object[][] TblKaryawan= null;
    private String[] label={"ID KARYAWAN","NAMA","POSISI","TANGGAL MASUK","STATUS","GAJI POKOK"};
    /**
     * Creates new form karyawan
     */
    public karyawan() {
        initComponents();
        con = new DB_form();
        con.Class();
        BacaTabel();
        kId.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
    private void BacaTabel(){
        try {
            con.ss= (Statement) con.cc.createStatement();
            String sql= "select * from karyawan_baru order by id_karyawan";
            con.rs= con.ss.executeQuery(sql);
            ResultSetMetaData m= con.rs.getMetaData();
            int kolom= m.getColumnCount();
            int baris=0;
            while(con.rs.next()){
                baris= con.rs.getRow();
            }
            TblKaryawan= new Object[baris][kolom];
            int x=0;
            con.rs.beforeFirst();
            while(con.rs.next()){
                TblKaryawan[x][0]= con.rs.getString("id_karyawan");
                TblKaryawan[x][1]= con.rs.getString("nama_karyawan");
                TblKaryawan[x][2]= con.rs.getString("posisi_karyawan");
                TblKaryawan[x][3]= con.rs.getDate("tgl_masuk");
                TblKaryawan[x][4]= con.rs.getString("status");
                TblKaryawan[x][5]= con.rs.getString("gapok");
                x++;
            }
            kTabel.setModel(new DefaultTableModel(TblKaryawan,label));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void setTabel(){
        int row= kTabel.getSelectedRow();
        kId.setText((String)kTabel.getValueAt(row, 0));
        kNama.setText((String)kTabel.getValueAt(row, 1));
        kPosisi.setSelectedItem((String)kTabel.getValueAt(row, 2));
        kTgl.setDate((Date)kTabel.getValueAt(row, 3));
        if(kTabel.getValueAt(row, 4).toString().equalsIgnoreCase("KONTRAK")){
            kKontrak.setSelected(true);
        }else{
            kTetap.setSelected(true);
        }
        kGapok.setText((String)kTabel.getValueAt(row, 5));
    }

    public final void tgl(){
        Date skrg= new Date();
        SimpleDateFormat format= new SimpleDateFormat("dd MMMM yyyy");
        String tanggal= format.format(skrg);
    }
    
    public void simpan_karyawan(){
        String id= this.kId.getText();
        String nama= this.kNama.getText();
        String posisi= (String)this.kPosisi.getSelectedItem();
        java.util.Date tanggal= (java.util.Date)this.kTgl.getDate();
        String status="";
        if(kKontrak.isSelected()){
            status="kontrak";
        }else{
            status="tetap";
        }
        String gapok= (String)this.kGapok.getText();
        
        try {
            String sql= "insert into karyawan_baru values (?,?,?,?,?,?)";
            PreparedStatement p= (PreparedStatement) con.cc.prepareStatement(sql);
            p.setString(1, id);
            p.setString(2, nama);
            p.setString(3, posisi);
            p.setDate(4, new java.sql.Date(tanggal.getTime()));
            p.setString(5, status);
            p.setString(6, gapok);
            p.executeUpdate();
            
            BacaTabel();
            
            JOptionPane.showMessageDialog(this, "Data berhasil di input");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void edit_karyawan(){
        String id= this.kId.getText();
        String nama= this.kNama.getText();
        String posisi= (String)this.kPosisi.getSelectedItem();
        java.util.Date tanggal= (java.util.Date)this.kTgl.getDate();
        String status="";
        if(kKontrak.isSelected()){
            status="kontrak";
        }else{
            status="tetap";
        }
        String gapok= (String)this.kGapok.getText();
        
        try {
            String sql= "update karyawan_baru set nama_karyawan=?, posisi_karyawan=?, tgl_masuk=?, status=?, gapok=? where id_karyawan=?";
            PreparedStatement p= (PreparedStatement) con.cc.prepareStatement(sql);
            p.setString(6, id);
            p.setString(1, nama);
            p.setString(2, posisi);
            p.setDate(3, new java.sql.Date(tanggal.getTime()));
            p.setString(4, status);
            p.setString(5, gapok);
            p.executeUpdate();
            
            BacaTabel();
            
            JOptionPane.showMessageDialog(this, "Data berhasil di ubah");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void hapus_karyawan(){
        try{
            String sql= "delete from karyawan_baru where id_karyawan='"+kId.getText()+"'";
            con.ss.executeUpdate(sql);
            con.ss.close();
            JOptionPane.showMessageDialog(null, "Data berhasil di hapus");
            BacaTabel();
            kId.requestFocus();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void baru_karyawan(){
        kId.setText("");
        kNama.setText("");
        kPosisi.setSelectedItem("SALES");
        kTgl.setDate(null);
        kKontrak.setText("KONTRAK");
        kGapok.setText("");
    }
    
    private void cari_karyawan(){
        try{
            con.ss= con.cc.createStatement();
            String sql= "select * from karyawan_baru where id_karyawan like '%"+kCariKr.getText()+"%' or nama_karyawan like '%"+kCariKr.getText()+"%'";
            con.rs= con.ss.executeQuery(sql);
            ResultSetMetaData m= con.rs.getMetaData();
            int kolom= m.getColumnCount();
            int baris=0;
            while(con.rs.next()){
                baris= con.rs.getRow();
            }
            TblKaryawan= new Object[baris][kolom];
            int x=0;
            con.rs.beforeFirst();
            while(con.rs.next()){
                TblKaryawan[x][0]= con.rs.getString("id_karyawan");
                TblKaryawan[x][1]= con.rs.getString("nama_karyawan");
                TblKaryawan[x][2]= con.rs.getString("posisi_karyawan");
                TblKaryawan[x][3]= con.rs.getDate("tgl_masuk");
                TblKaryawan[x][4]= con.rs.getString("status");
                TblKaryawan[x][5]= con.rs.getString("gapok");
                x++;
            }
            kTabel.setModel(new DefaultTableModel(TblKaryawan,label));
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BGkaryawan = new javax.swing.ButtonGroup();
        kCari = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        kId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        kNama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        kPosisi = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        kTgl = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        kKontrak = new javax.swing.JRadioButton();
        kTetap = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        kGapok = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBaru = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        kCariKr = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        kTabel = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        kCari.setBackground(new java.awt.Color(102, 153, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("FORM KARYAWAN BARU");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("INPUT KARYAWAN BARU"));

        jLabel2.setText("ID KARYAWAN");

        jLabel3.setText("NAMA");

        jLabel4.setText("POSISI");

        kPosisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SALES", "ADMINISTRASI", "TECHNICAL SUPPORT", "KASIR", "IT", "OPT. TELEPON", "DRIVER", "OB" }));

        jLabel5.setText("TANGGAL MASUK");

        kTgl.setDateFormatString("dd-MM-yyyy");

        jLabel6.setText("STATUS");

        BGkaryawan.add(kKontrak);
        kKontrak.setText("KONTRAK");

        BGkaryawan.add(kTetap);
        kTetap.setText("TETAP");

        jLabel7.setText("GAJI POKOK");

        kGapok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                kGapokKeyTyped(evt);
            }
        });

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Save.png"))); // NOI18N
        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Modify.png"))); // NOI18N
        btnEdit.setText("UBAH");
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Erase.png"))); // NOI18N
        btnHapus.setText("HAPUS");
        btnHapus.setEnabled(false);
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Create.png"))); // NOI18N
        btnBaru.setText("BARU");
        btnBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaruActionPerformed(evt);
            }
        });

        btnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Exit.png"))); // NOI18N
        btnKeluar.setText("KELUAR");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBaru)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKeluar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(47, 47, 47)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(kId, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(kNama)))
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(kKontrak)
                                        .addGap(10, 10, 10)
                                        .addComponent(kTetap))
                                    .addComponent(kPosisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(kGapok, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(kTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(kId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(kNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kPosisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(kTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(kKontrak)
                    .addComponent(kTetap))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(kGapok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnEdit)
                    .addComponent(btnHapus)
                    .addComponent(btnBaru)
                    .addComponent(btnKeluar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("TABEL KARYAWAN BARU"));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Find.png"))); // NOI18N
        jLabel8.setText("CARI");

        kCariKr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                kCariKrKeyReleased(evt);
            }
        });

        kTabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        kTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kTabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(kTabel);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(kCariKr, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(kCariKr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout kCariLayout = new javax.swing.GroupLayout(kCari);
        kCari.setLayout(kCariLayout);
        kCariLayout.setHorizontalGroup(
            kCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kCariLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(kCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        kCariLayout.setVerticalGroup(
            kCariLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kCariLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(614, 635));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void kTabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kTabelMouseClicked
        setTabel();
        btnEdit.setEnabled(true);
        btnHapus.setEnabled(true);
        btnSimpan.setEnabled(false);
    }//GEN-LAST:event_kTabelMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpan_karyawan();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        edit_karyawan();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapus_karyawan();
        baru_karyawan();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaruActionPerformed
        baru_karyawan();
        btnEdit.setEnabled(false);
        btnHapus.setEnabled(false);
        btnSimpan.setEnabled(true);
    }//GEN-LAST:event_btnBaruActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        MenuUtama mu= new MenuUtama();
        mu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void kCariKrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kCariKrKeyReleased
        cari_karyawan();
    }//GEN-LAST:event_kCariKrKeyReleased

    private void kGapokKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kGapokKeyTyped
        char c = evt.getKeyChar();
        if (!((Character.isDigit(c)
                || (c == KeyEvent.VK_BACKSPACE)
                || (c == KeyEvent.VK_DELETE)))) {
            evt.consume();
        }
    }//GEN-LAST:event_kGapokKeyTyped

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
            java.util.logging.Logger.getLogger(karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new karyawan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGkaryawan;
    private javax.swing.JButton btnBaru;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel kCari;
    private javax.swing.JTextField kCariKr;
    private javax.swing.JTextField kGapok;
    private javax.swing.JTextField kId;
    private javax.swing.JRadioButton kKontrak;
    private javax.swing.JTextField kNama;
    private javax.swing.JComboBox kPosisi;
    private javax.swing.JTable kTabel;
    private javax.swing.JRadioButton kTetap;
    private com.toedter.calendar.JDateChooser kTgl;
    // End of variables declaration//GEN-END:variables
}
