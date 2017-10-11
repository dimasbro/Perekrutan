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
public class masa extends javax.swing.JFrame {

    String tanggalmasuk, tanggalkeluar;
    int mBulan, kBulan;

    DB_form con;
    private Object[][] TblMasakerja = null;
    private String[] label = {"ID MASA KERJA", "NAMA", "POSISI", "JENIS KELAMIN", "ALAMAT", "TELEPON", "TEMPAT LAHIR", "TGL LAHIR", "AGAMA", "PENDIDIKAN", "STATUS", "TANGGAL MASUK", "TANGGAL KELUAR", "MASA"};

    /**
     * Creates new form masa
     */
    public masa() {
        initComponents();
        con = new DB_form();
        con.Class();
        BacaTabel();
        mId.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void BacaTabel() {
        try {
            con.ss = (Statement) con.cc.createStatement();
            String sql = "select * from masa_kerja order by id_masakerja";
            con.rs = con.ss.executeQuery(sql);
            ResultSetMetaData m = con.rs.getMetaData();
            int kolom = m.getColumnCount();
            int baris = 0;
            while (con.rs.next()) {
                baris = con.rs.getRow();
            }
            TblMasakerja = new Object[baris][kolom];
            int x = 0;
            con.rs.beforeFirst();
            while (con.rs.next()) {
                TblMasakerja[x][0] = con.rs.getString("id_masakerja");
                TblMasakerja[x][1] = con.rs.getString("nama_masakerja");
                TblMasakerja[x][2] = con.rs.getString("posisi_masakerja");
                TblMasakerja[x][3] = con.rs.getString("jenkel_masakerja");
                TblMasakerja[x][4] = con.rs.getString("alamat_masakerja");
                TblMasakerja[x][5] = con.rs.getString("telepon_masakerja");
                TblMasakerja[x][6] = con.rs.getString("tempatlahir_masakerja");
                TblMasakerja[x][7] = con.rs.getDate("tanggallahir_masakerja");
                TblMasakerja[x][8] = con.rs.getString("agama_masakerja");
                TblMasakerja[x][9] = con.rs.getString("pendidikan_masakerja");
                TblMasakerja[x][10] = con.rs.getString("status_masakerja");
                TblMasakerja[x][11] = con.rs.getDate("tanggalmasuk");
                TblMasakerja[x][12] = con.rs.getDate("tanggalkeluar");
                TblMasakerja[x][13] = con.rs.getString("masakerja") + " bulan";
                x++;
            }
            mTabel.setModel(new DefaultTableModel(TblMasakerja, label));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setTabel() {
        int row = mTabel.getSelectedRow();
        mId.setText((String) mTabel.getValueAt(row, 0));
        mNama.setText((String) mTabel.getValueAt(row, 1));
        mPosisi.setSelectedItem((String) mTabel.getValueAt(row, 2));
        if (mTabel.getValueAt(row, 3).toString().equalsIgnoreCase("pria")) {
            RBpria.setSelected(true);
        } else {
            RBwanita.setSelected(true);
        }
        mAlamat.setText((String) mTabel.getValueAt(row, 4));
        mTelepon.setText((String) mTabel.getValueAt(row, 5));
        mTempatlahir.setText((String) mTabel.getValueAt(row, 6));
        mTglLahir.setDate((Date) mTabel.getValueAt(row, 7));
        mAgama.setSelectedItem((String) mTabel.getValueAt(row, 8));
        mPendidikan.setSelectedItem((String) mTabel.getValueAt(row, 9));
        mStatus.setSelectedItem((String) mTabel.getValueAt(row, 10));
        tglMasuk.setDate((Date) mTabel.getValueAt(row, 11));
        tglKeluar.setDate((Date) mTabel.getValueAt(row, 12));
        mMasakerja.setText((String) mTabel.getValueAt(row, 13));
    }

    public final void tgl() {
        Date skrg = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        String tanggal = format.format(skrg);
    }

    public void simpan_masa() {
        String id = this.mId.getText();
        String nama = this.mNama.getText();
        String posisi = (String) this.mPosisi.getSelectedItem();
        String jenkel = "";
        if (RBpria.isSelected()) {
            jenkel = "pria";
        } else {
            jenkel = "wanita";
        }
        String alamat = this.mAlamat.getText();
        String telepon = this.mTelepon.getText();
        String tempatlahir = this.mTempatlahir.getText();
        java.util.Date tanggallahir = (java.util.Date) this.mTglLahir.getDate();
        String agama = (String) this.mAgama.getSelectedItem();
        String pendidikan = (String) this.mPendidikan.getSelectedItem();
        String status = (String) this.mStatus.getSelectedItem();
        java.util.Date tanggalmasuk = (java.util.Date) this.tglMasuk.getDate();
        java.util.Date tanggalkeluar = (java.util.Date) this.tglKeluar.getDate();
        String masa = this.mMasakerja.getText();

        try {
            String sql = "insert into masa_kerja values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement p = (PreparedStatement) con.cc.prepareStatement(sql);
            p.setString(1, id);
            p.setString(2, nama);
            p.setString(3, posisi);
            p.setString(4, jenkel);
            p.setString(5, alamat);
            p.setString(6, telepon);
            p.setString(7, tempatlahir);
            p.setDate(8, new java.sql.Date(tanggallahir.getTime()));
            p.setString(9, agama);
            p.setString(10, pendidikan);
            p.setString(11, status);
            p.setDate(12, new java.sql.Date(tanggalmasuk.getTime()));
            p.setDate(13, new java.sql.Date(tanggalkeluar.getTime()));
            p.setString(14, masa);
            p.executeUpdate();

            BacaTabel();

            JOptionPane.showMessageDialog(this, "Data berhasil di input");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void edit_masa() {
        String id = this.mId.getText();
        String nama = this.mNama.getText();
        String posisi = (String) this.mPosisi.getSelectedItem();
        String jenkel = "";
        if (RBpria.isSelected()) {
            jenkel = "pria";
        } else {
            jenkel = "wanita";
        }
        String alamat = this.mAlamat.getText();
        String telepon = this.mTelepon.getText();
        String tempatlahir = this.mTempatlahir.getText();
        java.util.Date tanggallahir = (java.util.Date) this.mTglLahir.getDate();
        String agama = (String) this.mAgama.getSelectedItem();
        String pendidikan = (String) this.mPendidikan.getSelectedItem();
        String status = (String) this.mStatus.getSelectedItem();
        java.util.Date tanggalmasuk = (java.util.Date) this.tglMasuk.getDate();
        java.util.Date tanggalkeluar = (java.util.Date) this.tglKeluar.getDate();
        String masa = this.mMasakerja.getText();

        try {
            String sql = "update masa_kerja set nama_masakerja=?, posisi_masakerja=?, jenkel_masakerja=?, alamat_masakerja=?, telepon_masakerja=?, tempatlahir_masakerja=?, tanggallahir_masakerja=?, agama_masakerja=?, pendidikan_masakerja=?, status_masakerja=?, tanggalmasuk=?, tanggalkeluar=?, masakerja=? where id_masakerja=?";
            PreparedStatement p = (PreparedStatement) con.cc.prepareStatement(sql);
            p.setString(14, id);
            p.setString(1, nama);
            p.setString(2, posisi);
            p.setString(3, jenkel);
            p.setString(4, alamat);
            p.setString(5, telepon);
            p.setString(6, tempatlahir);
            p.setDate(7, new java.sql.Date(tanggallahir.getTime()));
            p.setString(8, agama);
            p.setString(9, pendidikan);
            p.setString(10, status);
            p.setDate(11, new java.sql.Date(tanggalmasuk.getTime()));
            p.setDate(12, new java.sql.Date(tanggalkeluar.getTime()));
            p.setString(13, masa);
            p.executeUpdate();

            BacaTabel();

            JOptionPane.showMessageDialog(this, "Data berhasil di ubah");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void hapus_masa() {
        try {
            String sql = "delete from masa_kerja where id_masakerja='" + mId.getText() + "'";
            con.ss.executeUpdate(sql);
            con.ss.close();
            JOptionPane.showMessageDialog(null, "Data berhasil di hapus");
            BacaTabel();
            mId.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void baru_masa() {
        mId.setText("");
        mNama.setText("");
        mPosisi.setSelectedItem("SALES");
        BGmasa.clearSelection();
        mAlamat.setText("");
        mTelepon.setText("");
        mTempatlahir.setText("");
        mTglLahir.setDate(null);
        mAgama.setSelectedItem("ISLAM");
        mPendidikan.setSelectedItem("SD");
        mStatus.setSelectedItem("BELUM MENIKAH");
        tglMasuk.setDate(null);
        tglKeluar.setDate(null);
        mMasakerja.setText("");
    }

    private void cari_masa() {
        try {
            con.ss = con.cc.createStatement();
            String sql = "select * from masa_kerja where id_masakerja like '%" + mCari.getText() + "%' or nama_masakerja like '%" + mCari.getText() + "%'";
            con.rs = con.ss.executeQuery(sql);
            ResultSetMetaData m = con.rs.getMetaData();
            int kolom = m.getColumnCount();
            int baris = 0;
            while (con.rs.next()) {
                baris = con.rs.getRow();
            }
            TblMasakerja = new Object[baris][kolom];
            int x = 0;
            con.rs.beforeFirst();
            while (con.rs.next()) {
                TblMasakerja[x][0] = con.rs.getString("id_masakerja");
                TblMasakerja[x][1] = con.rs.getString("nama_masakerja");
                TblMasakerja[x][2] = con.rs.getString("posisi_masakerja");
                TblMasakerja[x][3] = con.rs.getString("jenkel_masakerja");
                TblMasakerja[x][4] = con.rs.getString("alamat_masakerja");
                TblMasakerja[x][5] = con.rs.getString("telepon_masakerja");
                TblMasakerja[x][6] = con.rs.getString("tempatlahir_masakerja");
                TblMasakerja[x][7] = con.rs.getDate("tanggallahir_masakerja");
                TblMasakerja[x][8] = con.rs.getString("agama_masakerja");
                TblMasakerja[x][9] = con.rs.getString("pendidikan_masakerja");
                TblMasakerja[x][10] = con.rs.getString("status_masakerja");
                TblMasakerja[x][11] = con.rs.getDate("tanggalmasuk");
                TblMasakerja[x][12] = con.rs.getDate("tanggalkeluar");
                TblMasakerja[x][13] = con.rs.getString("masakerja") + " bulan";
                x++;
            }
            mTabel.setModel(new DefaultTableModel(TblMasakerja, label));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void kurangi_tanggal() {
        int tahunM = Integer.parseInt(tanggalmasuk.substring(7, 11));
        int hariM = Integer.parseInt(tanggalmasuk.substring(0, 2));
        String bulanM = (String) tanggalmasuk.substring(3, 6);

        if (bulanM.compareTo("Jan") == 0) {
            mBulan = 1;
        } else if (bulanM.compareTo("Feb") == 0) {
            mBulan = 2;
        } else if (bulanM.compareTo("Mar") == 0) {
            mBulan = 3;
        } else if (bulanM.compareTo("Apr") == 0) {
            mBulan = 4;
        } else if (bulanM.compareTo("May") == 0) {
            mBulan = 5;
        } else if (bulanM.compareTo("Jun") == 0) {
            mBulan = 6;
        } else if (bulanM.compareTo("Jul") == 0) {
            mBulan = 7;
        } else if (bulanM.compareTo("Aug") == 0) {
            mBulan = 8;
        } else if (bulanM.compareTo("Sep") == 0) {
            mBulan = 9;
        } else if (bulanM.compareTo("Oct") == 0) {
            mBulan = 10;
        } else if (bulanM.compareTo("Nov") == 0) {
            mBulan = 11;
        } else {
            mBulan = 12;
        }

        int tahunK = Integer.parseInt(tanggalkeluar.substring(7, 11));
        int hariK = Integer.parseInt(tanggalkeluar.substring(0, 2));
        String bulanK = (String) tanggalkeluar.substring(3, 6);

        if (bulanK.compareTo("Jan") == 0) {
            kBulan = 1;
        } else if (bulanK.compareTo("Feb") == 0) {
            kBulan = 2;
        } else if (bulanK.compareTo("Mar") == 0) {
            kBulan = 3;
        } else if (bulanK.compareTo("Apr") == 0) {
            kBulan = 4;
        } else if (bulanK.compareTo("May") == 0) {
            kBulan = 5;
        } else if (bulanK.compareTo("Jun") == 0) {
            kBulan = 6;
        } else if (bulanK.compareTo("Jul") == 0) {
            kBulan = 7;
        } else if (bulanK.compareTo("Aug") == 0) {
            kBulan = 8;
        } else if (bulanK.compareTo("Sep") == 0) {
            kBulan = 9;
        } else if (bulanK.compareTo("Oct") == 0) {
            kBulan = 10;
        } else if (bulanK.compareTo("Nov") == 0) {
            kBulan = 11;
        } else {
            kBulan = 12;
        }

        int hasilHari = hariK - hariM;
        int hasilBulan = (kBulan - mBulan) * 30;
        int hasilTahun = (tahunK - tahunM) * 365;
        int totalwaktu = (hasilHari + hasilBulan + hasilTahun);
        int totalbulan = totalwaktu / 30;
        mMasakerja.setText(String.valueOf(totalbulan));
    }

    private void nama_lolos() {
        try {
            con.ss = (Statement) con.cc.createStatement();
            String sql = "select b.posisi_tes,a.jenkel_pelamar,a.alamat_pelamar,a.telepon_pelamar,a.tempat_lahir_pelamar,a.tgl_lahir_pelamar,a.agama_pelamar,a.pendidikan_pelamar,a.status_pelamar from pelamar a inner join tes_calon_karyawan b on a.nama_pelamar=b.nama_tes where b.hasil_tes='lolos' and a.nama_pelamar='" + mNama.getText() + "'";
            con.rs = con.ss.executeQuery(sql);

            while (con.rs.next()) {
                Object[] obj = new Object[9];
                obj[0] = con.rs.getString(1);
                obj[1] = con.rs.getString(2);
                obj[2] = con.rs.getString(3);
                obj[3] = con.rs.getString(4);
                obj[4] = con.rs.getString(5);
                obj[5] = con.rs.getDate(6);
                obj[6] = con.rs.getString(7);
                obj[7] = con.rs.getString(8);
                obj[8] = con.rs.getString(9);

                mPosisi.setSelectedItem(obj[0]);
                if (obj[1].equals("pria")) {
                    RBpria.setSelected(true);
                } else {
                    RBwanita.setSelected(true);
                }
                mAlamat.setText((String) obj[2]);
                mTelepon.setText((String) obj[3]);
                mTempatlahir.setText((String) obj[4]);
                mTglLahir.setDate((java.util.Date) obj[5]);
                mAgama.setSelectedItem(obj[6]);
                mPendidikan.setSelectedItem(obj[7]);
                mStatus.setSelectedItem(obj[8]);
            }
            con.rs.close();
            con.ss.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
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

        BGmasa = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        mId = new javax.swing.JTextField();
        mNama = new javax.swing.JTextField();
        mPosisi = new javax.swing.JComboBox();
        RBpria = new javax.swing.JRadioButton();
        RBwanita = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mAlamat = new javax.swing.JTextArea();
        mTelepon = new javax.swing.JTextField();
        mTempatlahir = new javax.swing.JTextField();
        mTglLahir = new com.toedter.calendar.JDateChooser();
        mAgama = new javax.swing.JComboBox();
        mPendidikan = new javax.swing.JComboBox();
        mStatus = new javax.swing.JComboBox();
        mMasakerja = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        tglMasuk = new com.toedter.calendar.JDateChooser();
        tglKeluar = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        mCari = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        mTabel = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBaru = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 153, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("FORM MASA KERJA");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("INPUT MASA KERJA"));

        jLabel2.setText("ID MASA KERJA");

        jLabel3.setText("NAMA");

        jLabel4.setText("POSISI");

        jLabel5.setText("JENIS KELAMIN");

        jLabel6.setText("ALAMAT");

        jLabel7.setText("TELEPON");

        jLabel8.setText("TEMPAT, TGL LAHIR");

        jLabel9.setText("AGAMA");

        jLabel10.setText("PENDIDIKAN");

        jLabel11.setText("STATUS");

        jLabel12.setText("TANGGAL MASUK");

        mNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mNamaActionPerformed(evt);
            }
        });

        mPosisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SALES", "ADMINISTRASI", "TECHNICAL SUPPORT", "KASIR", "IT", "OPT. TELEPON", "DRIVER", "OB" }));

        BGmasa.add(RBpria);
        RBpria.setText("PRIA");

        BGmasa.add(RBwanita);
        RBwanita.setText("WANITA");

        mAlamat.setColumns(20);
        mAlamat.setRows(5);
        jScrollPane1.setViewportView(mAlamat);

        mTelepon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                mTeleponKeyTyped(evt);
            }
        });

        mTglLahir.setDateFormatString("dd-MM-yyyy");

        mAgama.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ISLAM", "KRISTEN", "HINDU", "BUDHA" }));

        mPendidikan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SD", "SMP", "SMA", "D3", "S1" }));

        mStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BELUM MENIKAH", "MENIKAH", "DUDA", "JANDA" }));

        mMasakerja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mMasakerjaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                mMasakerjaKeyTyped(evt);
            }
        });

        jLabel14.setText("TANGGAL KELUAR");

        jLabel15.setText("MASA KERJA");

        tglMasuk.setDateFormatString("dd-MM-yyyy");
        tglMasuk.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tglMasukPropertyChange(evt);
            }
        });

        tglKeluar.setDateFormatString("dd-MM-yyyy");
        tglKeluar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tglKeluarPropertyChange(evt);
            }
        });

        jLabel16.setText("BULAN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(mTempatlahir, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mTglLahir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(mNama)
                    .addComponent(mPosisi, 0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mId, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(RBpria)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(RBwanita))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tglKeluar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tglMasuk, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mAgama, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mPendidikan, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mStatus, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(mMasakerja, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(mId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(mNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(mPosisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(RBpria)
                                .addComponent(RBwanita)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addComponent(jLabel7))
                    .addComponent(mTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mTempatlahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mTglLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mAgama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mPendidikan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(tglMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tglKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(mMasakerja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("TABEL MASA KERJA"));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Find.png"))); // NOI18N
        jLabel13.setText("CARI");

        mCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mCariKeyReleased(evt);
            }
        });

        mTabel.setModel(new javax.swing.table.DefaultTableModel(
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
        mTabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mTabelMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(mTabel);

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Save.png"))); // NOI18N
        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Modify.png"))); // NOI18N
        btnUbah.setText("UBAH");
        btnUbah.setEnabled(false);
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 20, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mCari, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnSimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnUbah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBaru)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnKeluar)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(mCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnUbah)
                    .addComponent(btnHapus)
                    .addComponent(btnBaru)
                    .addComponent(btnKeluar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1266, 562));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mTabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mTabelMouseClicked
        setTabel();
        btnUbah.setEnabled(true);
        btnHapus.setEnabled(true);
        btnSimpan.setEnabled(false);

    }//GEN-LAST:event_mTabelMouseClicked

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpan_masa();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        edit_masa();
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapus_masa();
        baru_masa();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaruActionPerformed
        baru_masa();
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
        btnSimpan.setEnabled(true);
    }//GEN-LAST:event_btnBaruActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        MenuUtama mu = new MenuUtama();
        mu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void mCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mCariKeyReleased
        cari_masa();
    }//GEN-LAST:event_mCariKeyReleased

    private void tglMasukPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tglMasukPropertyChange
        if (tglMasuk.getDate() != null) {
            SimpleDateFormat Format = new SimpleDateFormat("dd MMM yyyy");
            tanggalmasuk = Format.format(tglMasuk.getDate());
        }
    }//GEN-LAST:event_tglMasukPropertyChange

    private void tglKeluarPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tglKeluarPropertyChange
        if (tglKeluar.getDate() != null) {
            SimpleDateFormat Format = new SimpleDateFormat("dd MMM yyyy");
            tanggalkeluar = Format.format(tglKeluar.getDate());
        }
    }//GEN-LAST:event_tglKeluarPropertyChange

    private void mMasakerjaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mMasakerjaKeyReleased
        kurangi_tanggal();
    }//GEN-LAST:event_mMasakerjaKeyReleased

    private void mTeleponKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mTeleponKeyTyped
        char c = evt.getKeyChar();
        if (!((Character.isDigit(c)
                || (c == KeyEvent.VK_BACKSPACE)
                || (c == KeyEvent.VK_DELETE)))) {
            evt.consume();
        }
    }//GEN-LAST:event_mTeleponKeyTyped

    private void mMasakerjaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mMasakerjaKeyTyped
        char c = evt.getKeyChar();
        if (!((Character.isDigit(c)
                || (c == KeyEvent.VK_BACKSPACE)
                || (c == KeyEvent.VK_DELETE)))) {
            evt.consume();
        }
    }//GEN-LAST:event_mMasakerjaKeyTyped

    private void mNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mNamaActionPerformed
        nama_lolos();
    }//GEN-LAST:event_mNamaActionPerformed

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
            java.util.logging.Logger.getLogger(masa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(masa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(masa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(masa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new masa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BGmasa;
    private javax.swing.JRadioButton RBpria;
    private javax.swing.JRadioButton RBwanita;
    private javax.swing.JButton btnBaru;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUbah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JComboBox mAgama;
    private javax.swing.JTextArea mAlamat;
    private javax.swing.JTextField mCari;
    private javax.swing.JTextField mId;
    private javax.swing.JTextField mMasakerja;
    private javax.swing.JTextField mNama;
    private javax.swing.JComboBox mPendidikan;
    private javax.swing.JComboBox mPosisi;
    private javax.swing.JComboBox mStatus;
    private javax.swing.JTable mTabel;
    private javax.swing.JTextField mTelepon;
    private javax.swing.JTextField mTempatlahir;
    private com.toedter.calendar.JDateChooser mTglLahir;
    private com.toedter.calendar.JDateChooser tglKeluar;
    private com.toedter.calendar.JDateChooser tglMasuk;
    // End of variables declaration//GEN-END:variables
}
