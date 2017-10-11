/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package perekrutan;
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
public class seleksi extends javax.swing.JFrame {
    DB_form con;
    private Object[][] TblTes= null;
    private String[] label={"ID TES","NAMA","POSISI","INTERVIEW","PSIKOTES","KEMAMPUAN","HASIL"};
    /**
     * Creates new form seleksi
     */
    public seleksi() {
        initComponents();
        con = new DB_form();
        con.Class();
        BacaTabel();
        tId.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    
    private void BacaTabel(){
        try {
            con.ss= (Statement) con.cc.createStatement();
            String sql= "select * from tes_calon_karyawan order by id_tes";
            con.rs= con.ss.executeQuery(sql);
            ResultSetMetaData m= con.rs.getMetaData();
            int kolom= m.getColumnCount();
            int baris=0;
            while(con.rs.next()){
                baris= con.rs.getRow();
            }
            TblTes= new Object[baris][kolom];
            int x=0;
            con.rs.beforeFirst();
            while(con.rs.next()){
                TblTes[x][0]= con.rs.getString("id_tes");
                TblTes[x][1]= con.rs.getString("nama_tes");
                TblTes[x][2]= con.rs.getString("posisi_tes");
                TblTes[x][3]= con.rs.getString("interview_tes");
                TblTes[x][4]= con.rs.getString("psikotes_tes");
                TblTes[x][5]= con.rs.getString("kemampuan_tes");
                TblTes[x][6]= con.rs.getString("hasil_tes");
                x++;
            }
            tTabel.setModel(new DefaultTableModel(TblTes,label));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void setTabel(){
        int row= tTabel.getSelectedRow();
        tId.setText((String)tTabel.getValueAt(row, 0));
        tNama.setText((String)tTabel.getValueAt(row, 1));
        tPosisi.setSelectedItem((String)tTabel.getValueAt(row, 2));
        if(tTabel.getValueAt(row, 3).toString().equalsIgnoreCase("lolos")){
            iLolos.setSelected(true);
        }else{
            iGagal.setSelected(true);
        }
        if(tTabel.getValueAt(row, 4).toString().equalsIgnoreCase("lolos")){
            pLolos.setSelected(true);
        }else{
            pGagal.setSelected(true);
        }
        if(tTabel.getValueAt(row, 5).toString().equalsIgnoreCase("lolos")){
            tLolos.setSelected(true);
        }else{
            tGagal.setSelected(true);
        }
        tHasil.setText((String)tTabel.getValueAt(row, 6));
    }
    
    public void simpan_tes(){
        String id= this.tId.getText();
        String nama= this.tNama.getText();
        String posisi= (String)this.tPosisi.getSelectedItem();
        String interview="";
        if(iLolos.isSelected()){
            interview="lolos";
        }else{
            interview="gagal";
        }
        String psikotes="";
        if(pLolos.isSelected()){
            psikotes="lolos";
        }else{
            psikotes="gagal";
        }
        String tes="";
        if(tLolos.isSelected()){
            tes="lolos";
        }else{
            tes="gagal";
        }
        String hasil= this.tHasil.getText();
        
        try {
            String sql= "insert into tes_calon_karyawan values (?,?,?,?,?,?,?)";
            PreparedStatement p= (PreparedStatement) con.cc.prepareStatement(sql);
            p.setString(1, id);
            p.setString(2, nama);
            p.setString(3, posisi);
            p.setString(4, interview);
            p.setString(5, psikotes);
            p.setString(6, tes);
            p.setString(7, hasil);
            p.executeUpdate();
            
            BacaTabel();
            
            JOptionPane.showMessageDialog(this, "Data berhasil di input");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void edit_tes(){
        String id= this.tId.getText();
        String nama= this.tNama.getText();
        String posisi= (String)this.tPosisi.getSelectedItem();
        String interview="";
        if(iLolos.isSelected()){
            interview="lolos";
        }else{
            interview="gagal";
        }
        String psikotes="";
        if(pLolos.isSelected()){
            psikotes="lolos";
        }else{
            psikotes="gagal";
        }
        String tes="";
        if(tLolos.isSelected()){
            tes="lolos";
        }else{
            tes="gagal";
        }
        String hasil= (String)this.tHasil.getText();
        
        try {
            String sql= "update tes_calon_karyawan set nama_tes=?, posisi_tes=?, interview_tes=?, psikotes_tes=?, kemampuan_tes=?, hasil_tes=? where id_tes=?";
            PreparedStatement p= (PreparedStatement) con.cc.prepareStatement(sql);
            p.setString(7, id);
            p.setString(1, nama);
            p.setString(2, posisi);
            p.setString(3, interview);
            p.setString(4, psikotes);
            p.setString(5, tes);
            p.setString(6, hasil);
            p.executeUpdate();
            
            BacaTabel();
            
            JOptionPane.showMessageDialog(this, "Data berhasil di ubah");
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void hapus_tes(){
        try{
            String sql= "delete from tes_calon_karyawan where id_tes='"+tId.getText()+"'";
            con.ss.executeUpdate(sql);
            con.ss.close();
            JOptionPane.showMessageDialog(null, "Data berhasil di hapus");
            BacaTabel();
            tId.requestFocus();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void baru_tes(){
        tId.setText("");
        tNama.setText("");
        tPosisi.setSelectedItem("SALES");
        BGinterview.clearSelection();
        BGpsikotes.clearSelection();
        BGkemampuan.clearSelection();
        tHasil.setText("");
    }
    
    private void cari_tes(){
        try{
            con.ss= con.cc.createStatement();
            String sql= "select * from tes_calon_karyawan where id_tes like '%"+tCari.getText()+"%' or nama_tes like '%"+tCari.getText()+"%'";
            con.rs= con.ss.executeQuery(sql);
            ResultSetMetaData m= con.rs.getMetaData();
            int kolom= m.getColumnCount();
            int baris=0;
            while(con.rs.next()){
                baris= con.rs.getRow();
            }
            TblTes= new Object[baris][kolom];
            int x=0;
            con.rs.beforeFirst();
            while(con.rs.next()){
                TblTes[x][0]= con.rs.getString("id_tes");
                TblTes[x][1]= con.rs.getString("nama_tes");
                TblTes[x][2]= con.rs.getString("posisi_tes");
                TblTes[x][3]= con.rs.getString("interview_tes");
                TblTes[x][4]= con.rs.getString("psikotes_tes");
                TblTes[x][5]= con.rs.getString("kemampuan_tes");
                TblTes[x][6]= con.rs.getString("hasil_tes");
                x++;
            }
            tTabel.setModel(new DefaultTableModel(TblTes,label));
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void hasil_tes(){
        if(iLolos.isSelected() && pLolos.isSelected() && tLolos.isSelected()){
            tHasil.setText("lolos");
        }else{
            tHasil.setText("gagal");
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

        BGinterview = new javax.swing.ButtonGroup();
        BGpsikotes = new javax.swing.ButtonGroup();
        BGkemampuan = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tNama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tPosisi = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        iLolos = new javax.swing.JRadioButton();
        iGagal = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        pLolos = new javax.swing.JRadioButton();
        pGagal = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        tLolos = new javax.swing.JRadioButton();
        tGagal = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        tHasil = new javax.swing.JTextField();
        btSimpan = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        btHapus = new javax.swing.JButton();
        btBaru = new javax.swing.JButton();
        btKeluar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tTabel = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        tCari = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 153, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("FORM TES CALON KARYAWAN");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("INPUT TES CALON KARYAWAN"));

        jLabel2.setText("ID TES");

        jLabel3.setText("NAMA");

        jLabel4.setText("POSISI");

        tPosisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SALES", "ADMINISTRASI", "TECHNICAL SUPPORT", "KASIR", "IT", "OPT. TELEPON", "DRIVER", "OB" }));

        jLabel5.setText("INTERVIEW");

        BGinterview.add(iLolos);
        iLolos.setText("LOLOS");
        iLolos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iLolosActionPerformed(evt);
            }
        });

        BGinterview.add(iGagal);
        iGagal.setText("GAGAL");
        iGagal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iGagalActionPerformed(evt);
            }
        });

        jLabel6.setText("PSIKOTES");

        BGpsikotes.add(pLolos);
        pLolos.setText("LOLOS");
        pLolos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pLolosActionPerformed(evt);
            }
        });

        BGpsikotes.add(pGagal);
        pGagal.setText("GAGAL");
        pGagal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pGagalActionPerformed(evt);
            }
        });

        jLabel7.setText("TES KEMAMPUAN");

        BGkemampuan.add(tLolos);
        tLolos.setText("LOLOS");
        tLolos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tLolosActionPerformed(evt);
            }
        });

        BGkemampuan.add(tGagal);
        tGagal.setText("GAGAL");
        tGagal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tGagalActionPerformed(evt);
            }
        });

        jLabel8.setText("HASIL");

        tHasil.setEditable(false);

        btSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Save.png"))); // NOI18N
        btSimpan.setText("SIMPAN");
        btSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSimpanActionPerformed(evt);
            }
        });

        btEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Modify.png"))); // NOI18N
        btEdit.setText("UBAH");
        btEdit.setEnabled(false);
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });

        btHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Erase.png"))); // NOI18N
        btHapus.setText("HAPUS");
        btHapus.setEnabled(false);
        btHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHapusActionPerformed(evt);
            }
        });

        btBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Create.png"))); // NOI18N
        btBaru.setText("BARU");
        btBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBaruActionPerformed(evt);
            }
        });

        btKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Exit.png"))); // NOI18N
        btKeluar.setText("KELUAR");
        btKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKeluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tHasil, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tId, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(pLolos)
                                    .addComponent(iLolos)
                                    .addComponent(tLolos))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(iGagal)
                                    .addComponent(pGagal)
                                    .addComponent(tGagal)))
                            .addComponent(tPosisi, 0, 1, Short.MAX_VALUE)
                            .addComponent(tNama)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btHapus)
                        .addGap(18, 18, 18)
                        .addComponent(btBaru)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btKeluar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tPosisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iLolos)
                    .addComponent(iGagal)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pLolos)
                    .addComponent(pGagal)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tLolos)
                    .addComponent(tGagal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(tHasil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSimpan)
                    .addComponent(btEdit)
                    .addComponent(btHapus)
                    .addComponent(btBaru)
                    .addComponent(btKeluar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("TABEL TES CALON KARYAWAN"));

        tTabel.setModel(new javax.swing.table.DefaultTableModel(
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
        tTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tTabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tTabel);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Find.png"))); // NOI18N
        jLabel9.setText("CARI");

        tCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tCariKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(tCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 64, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(10, 10, 10)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(604, 619));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tTabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tTabelMouseClicked
        setTabel();
        btEdit.setEnabled(true);
        btHapus.setEnabled(true);
        btSimpan.setEnabled(false);
    }//GEN-LAST:event_tTabelMouseClicked

    private void btSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSimpanActionPerformed
        simpan_tes();
    }//GEN-LAST:event_btSimpanActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        edit_tes();
    }//GEN-LAST:event_btEditActionPerformed

    private void btHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHapusActionPerformed
        hapus_tes();
        baru_tes();
    }//GEN-LAST:event_btHapusActionPerformed

    private void btBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBaruActionPerformed
        baru_tes();
        btEdit.setEnabled(false);
        btHapus.setEnabled(false);
        btSimpan.setEnabled(true);
    }//GEN-LAST:event_btBaruActionPerformed

    private void btKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKeluarActionPerformed
        MenuUtama mu= new MenuUtama();
        mu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btKeluarActionPerformed

    private void tCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tCariKeyReleased
        cari_tes();
    }//GEN-LAST:event_tCariKeyReleased

    private void iLolosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iLolosActionPerformed
        hasil_tes();
    }//GEN-LAST:event_iLolosActionPerformed

    private void iGagalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iGagalActionPerformed
        hasil_tes();
    }//GEN-LAST:event_iGagalActionPerformed

    private void pLolosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pLolosActionPerformed
        hasil_tes();
    }//GEN-LAST:event_pLolosActionPerformed

    private void pGagalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pGagalActionPerformed
        hasil_tes();
    }//GEN-LAST:event_pGagalActionPerformed

    private void tLolosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tLolosActionPerformed
        hasil_tes();
    }//GEN-LAST:event_tLolosActionPerformed

    private void tGagalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tGagalActionPerformed
        hasil_tes();
    }//GEN-LAST:event_tGagalActionPerformed

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
            java.util.logging.Logger.getLogger(seleksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(seleksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(seleksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(seleksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new seleksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGinterview;
    private javax.swing.ButtonGroup BGkemampuan;
    private javax.swing.ButtonGroup BGpsikotes;
    private javax.swing.JButton btBaru;
    private javax.swing.JButton btEdit;
    private javax.swing.JButton btHapus;
    private javax.swing.JButton btKeluar;
    private javax.swing.JButton btSimpan;
    private javax.swing.JRadioButton iGagal;
    private javax.swing.JRadioButton iLolos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton pGagal;
    private javax.swing.JRadioButton pLolos;
    private javax.swing.JTextField tCari;
    private javax.swing.JRadioButton tGagal;
    private javax.swing.JTextField tHasil;
    private javax.swing.JTextField tId;
    private javax.swing.JRadioButton tLolos;
    private javax.swing.JTextField tNama;
    private javax.swing.JComboBox tPosisi;
    private javax.swing.JTable tTabel;
    // End of variables declaration//GEN-END:variables
}
