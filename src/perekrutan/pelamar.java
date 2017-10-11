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
public class pelamar extends javax.swing.JFrame {

    DB_form con;
    private Object[][] TblPelamar = null;
    private String[] label = {"ID PELAMAR", "NAMA", "JENIS KELAMIN", "ALAMAT", "TELEPON", "TEMPAT LAHIR", "TANGGAL LAHIR", "AGAMA", "PENDIDIKAN", "STATUS", "KEAHLIAN", "PENGALAMAN", "POSISI", "TANGGAL PELAMAR"};

    /**
     * Creates new form pelamar
     */
    public pelamar() {
        initComponents();
        con = new DB_form();
        con.Class();
        BacaTabel();
        pId.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void BacaTabel() {
        try {
            con.ss = (Statement) con.cc.createStatement();
            String sql = "select * from pelamar order by id_pelamar";
            con.rs = con.ss.executeQuery(sql);
            ResultSetMetaData m = con.rs.getMetaData();
            int kolom = m.getColumnCount();
            int baris = 0;
            while (con.rs.next()) {
                baris = con.rs.getRow();
            }
            TblPelamar = new Object[baris][kolom];
            int x = 0;
            con.rs.beforeFirst();
            while (con.rs.next()) {
                TblPelamar[x][0] = con.rs.getString("id_pelamar");
                TblPelamar[x][1] = con.rs.getString("nama_pelamar");
                TblPelamar[x][2] = con.rs.getString("jenkel_pelamar");
                TblPelamar[x][3] = con.rs.getString("alamat_pelamar");
                TblPelamar[x][4] = con.rs.getString("telepon_pelamar");
                TblPelamar[x][5] = con.rs.getString("tempat_lahir_pelamar");
                TblPelamar[x][6] = con.rs.getDate("tgl_lahir_pelamar");
                TblPelamar[x][7] = con.rs.getString("agama_pelamar");
                TblPelamar[x][8] = con.rs.getString("pendidikan_pelamar");
                TblPelamar[x][9] = con.rs.getString("status_pelamar");
                TblPelamar[x][10] = con.rs.getString("keahlian_pelamar");
                TblPelamar[x][11] = con.rs.getString("pengalaman_pelamar");
                TblPelamar[x][12] = con.rs.getString("posisi_pelamar");
                TblPelamar[x][13] = con.rs.getDate("tgl_pelamar");
                x++;
            }
            pTabel.setModel(new DefaultTableModel(TblPelamar, label));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setTabel() {
        int row = pTabel.getSelectedRow();
        pId.setText((String) pTabel.getValueAt(row, 0));
        pNama.setText((String) pTabel.getValueAt(row, 1));
        if (pTabel.getValueAt(row, 2).toString().equalsIgnoreCase("pria")) {
            RBpria.setSelected(true);
        } else {
            RBwanita.setSelected(true);
        }
        pAlamat.setText((String) pTabel.getValueAt(row, 3));
        pTelepon.setText((String) pTabel.getValueAt(row, 4));
        pTempatLahir.setText((String) pTabel.getValueAt(row, 5));
        pTglLahir.setDate((Date) pTabel.getValueAt(row, 6));
        pAgama.setSelectedItem((String) pTabel.getValueAt(row, 7));
        pPendidikan.setSelectedItem((String) pTabel.getValueAt(row, 8));
        pStatus.setSelectedItem((String) pTabel.getValueAt(row, 9));
        pKeahlian.setText((String) pTabel.getValueAt(row, 10));
        pPengalaman.setText((String) pTabel.getValueAt(row, 11));
        pPosisi.setSelectedItem((String) pTabel.getValueAt(row, 12));
        pTglPelamar.setDate((Date) pTabel.getValueAt(row, 13));
    }

    public final void tgl() {
        Date skrg = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd MM yyy");
        String tanggal = format.format(skrg);
    }

    public void simpan_pelamar() {
        String id = this.pId.getText();
        String nama = this.pNama.getText();
        String jenkel = "";
        if (RBpria.isSelected()) {
            jenkel = "pria";
        } else {
            jenkel = "wanita";
        }
        String alamat = this.pAlamat.getText();
        String telepon = this.pTelepon.getText();
        String tempatlahir = this.pTempatLahir.getText();
        java.util.Date tanggallahir = (java.util.Date) this.pTglLahir.getDate();
        String agama = (String) this.pAgama.getSelectedItem();
        String pendidikan = (String) this.pPendidikan.getSelectedItem();
        String status = (String) this.pStatus.getSelectedItem();
        String keahlian = this.pKeahlian.getText();
        String pengalaman = this.pPengalaman.getText();
        String posisi = (String) this.pPosisi.getSelectedItem();
        java.util.Date tanggalpelamar = (java.util.Date) this.pTglPelamar.getDate();

        try {
            String sql = "insert into pelamar values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement p = (PreparedStatement) con.cc.prepareStatement(sql);
            p.setString(1, id);
            p.setString(2, nama);
            p.setString(3, jenkel);
            p.setString(4, alamat);
            p.setString(5, telepon);
            p.setString(6, tempatlahir);
            p.setDate(7, new java.sql.Date(tanggallahir.getTime()));
            p.setString(8, agama);
            p.setString(9, pendidikan);
            p.setString(10, status);
            p.setString(11, keahlian);
            p.setString(12, pengalaman);
            p.setString(13, posisi);
            p.setDate(14, new java.sql.Date(tanggalpelamar.getTime()));
            p.executeUpdate();

            BacaTabel();

            JOptionPane.showMessageDialog(this, "Data berhasil di input");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void edit_pelamar() {
        String id = this.pId.getText();
        String nama = this.pNama.getText();
        String jenkel = "";
        if (RBpria.isSelected()) {
            jenkel = "pria";
        } else {
            jenkel = "wanita";
        }
        String alamat = this.pAlamat.getText();
        String telepon = this.pTelepon.getText();
        String tempatlahir = this.pTempatLahir.getText();
        java.util.Date tanggallahir = (java.util.Date) this.pTglLahir.getDate();
        String agama = (String) this.pAgama.getSelectedItem();
        String pendidikan = (String) this.pPendidikan.getSelectedItem();
        String status = (String) this.pStatus.getSelectedItem();
        String keahlian = this.pKeahlian.getText();
        String pengalaman = this.pPengalaman.getText();
        String posisi = (String) this.pPosisi.getSelectedItem();
        java.util.Date tanggalpelamar = (java.util.Date) this.pTglPelamar.getDate();

        try {
            String sql = "update pelamar set nama_pelamar=?, jenkel_pelamar=?, alamat_pelamar=?, telepon_pelamar=?, tempat_lahir_pelamar=?, tgl_lahir_pelamar=?, agama_pelamar=?, pendidikan_pelamar=?, status_pelamar=?, keahlian_pelamar=?, pengalaman_pelamar=?, posisi_pelamar=?, tgl_pelamar=? where id_pelamar=?";
            PreparedStatement p = (PreparedStatement) con.cc.prepareStatement(sql);
            p.setString(14, id);
            p.setString(1, nama);
            p.setString(2, jenkel);
            p.setString(3, alamat);
            p.setString(4, telepon);
            p.setString(5, tempatlahir);
            p.setDate(6, new java.sql.Date(tanggallahir.getTime()));
            p.setString(7, agama);
            p.setString(8, pendidikan);
            p.setString(9, status);
            p.setString(10, keahlian);
            p.setString(11, pengalaman);
            p.setString(12, posisi);
            p.setDate(13, new java.sql.Date(tanggalpelamar.getTime()));
            p.executeUpdate();

            BacaTabel();

            JOptionPane.showMessageDialog(this, "Data berhasil di ubah");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void hapus_pelamar() {
        try {
            String sql = "delete from pelamar where id_pelamar='" + pId.getText() + "'";
            con.ss.executeUpdate(sql);
            con.ss.close();
            JOptionPane.showMessageDialog(null, "Data berhasil di hapus");
            BacaTabel();
            pId.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void baru_pelamar() {
        pId.setText("");
        pNama.setText("");
        BGpelamar.clearSelection();
        pAlamat.setText("");
        pTelepon.setText("");
        pTempatLahir.setText("");
        pTglLahir.setDate(null);
        pAgama.setSelectedItem("ISLAM");
        pPendidikan.setSelectedItem("SD");
        pStatus.setSelectedItem("BELUM MENIKAH");
        pKeahlian.setText("");
        pPengalaman.setText("");
        pPosisi.setSelectedItem("SALES");
        pTglPelamar.setDate(null);
    }

    private void cari_pelamar() {
        try {
            con.ss = con.cc.createStatement();
            String sql = "select * from pelamar where id_pelamar like '%" + pCari.getText() + "%' or nama_pelamar like '%" + pCari.getText() + "%'";
            con.rs = con.ss.executeQuery(sql);
            ResultSetMetaData m = con.rs.getMetaData();
            int kolom = m.getColumnCount();
            int baris = 0;
            while (con.rs.next()) {
                baris = con.rs.getRow();
            }
            TblPelamar = new Object[baris][kolom];
            int x = 0;
            con.rs.beforeFirst();
            while (con.rs.next()) {
                TblPelamar[x][0] = con.rs.getString("id_pelamar");
                TblPelamar[x][1] = con.rs.getString("nama_pelamar");
                TblPelamar[x][2] = con.rs.getString("jenkel_pelamar");
                TblPelamar[x][3] = con.rs.getString("alamat_pelamar");
                TblPelamar[x][4] = con.rs.getString("telepon_pelamar");
                TblPelamar[x][5] = con.rs.getString("tempat_lahir_pelamar");
                TblPelamar[x][6] = con.rs.getString("tgl_lahir_pelamar");
                TblPelamar[x][7] = con.rs.getString("agama_pelamar");
                TblPelamar[x][8] = con.rs.getString("pendidikan_pelamar");
                TblPelamar[x][9] = con.rs.getString("status_pelamar");
                TblPelamar[x][10] = con.rs.getString("keahlian_pelamar");
                TblPelamar[x][11] = con.rs.getString("pengalaman_pelamar");
                TblPelamar[x][12] = con.rs.getString("posisi_pelamar");
                TblPelamar[x][13] = con.rs.getString("tgl_pelamar");
                x++;
            }
            pTabel.setModel(new DefaultTableModel(TblPelamar, label));
        } catch (Exception e) {
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

        BGpelamar = new javax.swing.ButtonGroup();
        jScrollPane4 = new javax.swing.JScrollPane();
        pAlamat2 = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        pAlamat3 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pNama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        RBpria = new javax.swing.JRadioButton();
        RBwanita = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        pAlamat = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        pTelepon = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        pTempatLahir = new javax.swing.JTextField();
        pTglLahir = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        pAgama = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        pPendidikan = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        pStatus = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        pKeahlian = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pPengalaman = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        pPosisi = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        pTglPelamar = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pCari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        pTabel = new javax.swing.JTable();
        bSimpan = new javax.swing.JButton();
        bEdit = new javax.swing.JButton();
        bHapus = new javax.swing.JButton();
        bBaru = new javax.swing.JButton();
        bKeluar = new javax.swing.JButton();

        pAlamat2.setColumns(20);
        pAlamat2.setRows(5);
        jScrollPane4.setViewportView(pAlamat2);

        pAlamat3.setColumns(20);
        pAlamat3.setRows(5);
        jScrollPane5.setViewportView(pAlamat3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 153, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("FORM DATA PELAMAR");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("INPUT DATA PELAMAR"));

        jLabel2.setText("ID PELAMAR");

        jLabel3.setText("NAMA");

        jLabel4.setText("JENIS KELAMIN");

        BGpelamar.add(RBpria);
        RBpria.setText("PRIA");

        BGpelamar.add(RBwanita);
        RBwanita.setText("WANITA");

        jLabel5.setText("ALAMAT");

        pAlamat.setColumns(20);
        pAlamat.setRows(5);
        jScrollPane3.setViewportView(pAlamat);

        jLabel6.setText("TELEPON");

        pTelepon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pTeleponKeyTyped(evt);
            }
        });

        jLabel7.setText("TEMPAT, TGL LAHIR");

        pTglLahir.setDateFormatString("dd-MM-yyyy");

        jLabel8.setText("AGAMA");

        pAgama.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ISLAM", "KRISTEN", "HINDU", "BUDHA" }));

        jLabel9.setText("PENDIDIKAN");

        pPendidikan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SD", "SMP", "SMA", "D3", "S1" }));

        jLabel13.setText("STATUS");

        pStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BELUM MENIKAH", "MENIKAH", "DUDA", "JANDA" }));

        jLabel15.setText("KEAHLIAN");

        pKeahlian.setColumns(20);
        pKeahlian.setRows(5);
        jScrollPane6.setViewportView(pKeahlian);

        jLabel14.setText("PENGALAMAN KERJA");

        pPengalaman.setColumns(20);
        pPengalaman.setRows(5);
        jScrollPane2.setViewportView(pPengalaman);

        jLabel10.setText("POSISI");

        pPosisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SALES", "ADMINISTRASI", "TECHNICAL SUPPORT", "KASIR", "IT", "OPT. TELEPON", "DRIVER", "OB" }));

        jLabel11.setText("TANGGAL PELAMAR");

        pTglPelamar.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(RBpria)
                                        .addGap(18, 18, 18)
                                        .addComponent(RBwanita))
                                    .addComponent(pNama, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pId, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(pTempatLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pTglLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(pAgama, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(135, 135, 135))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pPosisi, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pTglPelamar, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(pPendidikan, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pStatus, javax.swing.GroupLayout.Alignment.LEADING, 0, 137, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(pId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(pNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(RBpria)
                    .addComponent(RBwanita))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(pTempatLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pTglLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(pAgama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(pPendidikan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(pStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pPosisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(pTglPelamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("TABEL PELAMAR"));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Find.png"))); // NOI18N
        jLabel12.setText("CARI");

        pCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pCariKeyReleased(evt);
            }
        });

        pTabel.setModel(new javax.swing.table.DefaultTableModel(
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
        pTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pTabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(pTabel);

        bSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Save.png"))); // NOI18N
        bSimpan.setText("SIMPAN");
        bSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSimpanActionPerformed(evt);
            }
        });

        bEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Modify.png"))); // NOI18N
        bEdit.setText("UBAH");
        bEdit.setEnabled(false);
        bEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditActionPerformed(evt);
            }
        });

        bHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Erase.png"))); // NOI18N
        bHapus.setText("HAPUS");
        bHapus.setEnabled(false);
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

        bBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Create.png"))); // NOI18N
        bBaru.setText("BARU");
        bBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBaruActionPerformed(evt);
            }
        });

        bKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Exit.png"))); // NOI18N
        bKeluar.setText("KELUAR");
        bKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bKeluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(pCari, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(bSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bHapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bBaru)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bKeluar)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(pCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bSimpan)
                    .addComponent(bEdit)
                    .addComponent(bHapus)
                    .addComponent(bBaru)
                    .addComponent(bKeluar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(86, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(1338, 630));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void pTabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pTabelMouseClicked
        setTabel();
        bEdit.setEnabled(true);
        bHapus.setEnabled(true);
        bSimpan.setEnabled(false);
    }//GEN-LAST:event_pTabelMouseClicked

    private void bSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSimpanActionPerformed
        simpan_pelamar();
    }//GEN-LAST:event_bSimpanActionPerformed

    private void bEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditActionPerformed
        edit_pelamar();
    }//GEN-LAST:event_bEditActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        hapus_pelamar();
        baru_pelamar();
    }//GEN-LAST:event_bHapusActionPerformed

    private void bBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBaruActionPerformed
        baru_pelamar();
        bSimpan.setEnabled(true);
        bEdit.setEnabled(false);
        bHapus.setEnabled(false);
    }//GEN-LAST:event_bBaruActionPerformed

    private void bKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bKeluarActionPerformed
        MenuUtama mu = new MenuUtama();
        mu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_bKeluarActionPerformed

    private void pCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pCariKeyReleased
        cari_pelamar();
    }//GEN-LAST:event_pCariKeyReleased

    private void pTeleponKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pTeleponKeyTyped
        char c = evt.getKeyChar();
        if (!((Character.isDigit(c)
                || (c == KeyEvent.VK_BACKSPACE)
                || (c == KeyEvent.VK_DELETE)))) {
            evt.consume();
        }
    }//GEN-LAST:event_pTeleponKeyTyped

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
            java.util.logging.Logger.getLogger(pelamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pelamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pelamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pelamar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pelamar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGpelamar;
    private javax.swing.JRadioButton RBpria;
    private javax.swing.JRadioButton RBwanita;
    private javax.swing.JButton bBaru;
    private javax.swing.JButton bEdit;
    private javax.swing.JButton bHapus;
    private javax.swing.JButton bKeluar;
    private javax.swing.JButton bSimpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JComboBox pAgama;
    private javax.swing.JTextArea pAlamat;
    private javax.swing.JTextArea pAlamat2;
    private javax.swing.JTextArea pAlamat3;
    private javax.swing.JTextField pCari;
    private javax.swing.JTextField pId;
    private javax.swing.JTextArea pKeahlian;
    private javax.swing.JTextField pNama;
    private javax.swing.JComboBox pPendidikan;
    private javax.swing.JTextArea pPengalaman;
    private javax.swing.JComboBox pPosisi;
    private javax.swing.JComboBox pStatus;
    private javax.swing.JTable pTabel;
    private javax.swing.JTextField pTelepon;
    private javax.swing.JTextField pTempatLahir;
    private com.toedter.calendar.JDateChooser pTglLahir;
    private com.toedter.calendar.JDateChooser pTglPelamar;
    // End of variables declaration//GEN-END:variables
}
