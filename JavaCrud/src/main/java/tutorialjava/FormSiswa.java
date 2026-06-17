package tutorialjava;

import Koneksi.koneksi;
import static Koneksi.koneksi.Koneksi;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormSiswa extends JFrame {

    // Deklarasi Komponen
    private JLabel lblTitle, lblNis, lblNama, lblJurusan, lblJk, lblAlamat;
    private JTextField txtNis, txtNama;
    private JComboBox<String> cbJurusan, cbJk;
    private JTextArea txtAlamat;
    private JScrollPane scrollAlamat, scrollTable;
    private JButton btnSimpan, btnHapus, btnUpdate, btnReset;
    private JTable tableSiswa;
    private DefaultTableModel tableModel;
    
    // Variabel tambahan untuk menampung NIS lama saat tabel diklik
    private String oldNis = ""; 

    public FormSiswa() {
        // Pengaturan dasar JFrame
        setTitle("Form Siswa");
        setSize(850, 500); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(null); 

        // --- 1. JUDUL ---
        lblTitle = new JLabel("Form Siswa");
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 26)); 
        lblTitle.setBounds(50, 20, 200, 40);
        add(lblTitle);

        // --- 2. INPUT SEBELAH KIRI ---
        lblNis = new JLabel("NIS");
        lblNis.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNis.setBounds(20, 80, 70, 25);
        add(lblNis);
        txtNis = new JTextField();
        txtNis.setBounds(100, 80, 240, 25);
        add(txtNis);

        lblNama = new JLabel("Nama");
        lblNama.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNama.setBounds(20, 115, 70, 25);
        add(lblNama);
        txtNama = new JTextField();
        txtNama.setBounds(100, 115, 240, 25);
        add(txtNama);

        lblJurusan = new JLabel("Jurusan");
        lblJurusan.setHorizontalAlignment(SwingConstants.RIGHT);
        lblJurusan.setBounds(20, 150, 70, 25);
        add(lblJurusan);
        String[] jurusan = {"Rekayasa Perangkat Lunak", "Teknik Komputer Jaringan", "Multimedia"};
        cbJurusan = new JComboBox<>(jurusan);
        cbJurusan.setBounds(100, 150, 240, 25);
        cbJurusan.setBackground(Color.WHITE);
        add(cbJurusan);

        lblJk = new JLabel("JK");
        lblJk.setHorizontalAlignment(SwingConstants.RIGHT);
        lblJk.setBounds(20, 185, 70, 25);
        add(lblJk);
        String[] jk = {"Laki-laki", "Perempuan"};
        cbJk = new JComboBox<>(jk);
        cbJk.setBounds(100, 185, 240, 25);
        cbJk.setBackground(Color.WHITE);
        add(cbJk);

        lblAlamat = new JLabel("Alamat");
        lblAlamat.setHorizontalAlignment(SwingConstants.RIGHT);
        lblAlamat.setBounds(20, 220, 70, 25);
        add(lblAlamat);
        txtAlamat = new JTextArea();
        scrollAlamat = new JScrollPane(txtAlamat);
        scrollAlamat.setBounds(100, 220, 240, 100); 
        add(scrollAlamat);

        // --- 3. TOMBOL (BUTTONS) ---
        btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(100, 335, 115, 25);
        add(btnSimpan);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(225, 335, 115, 25);
        add(btnHapus);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(100, 370, 115, 25);
        add(btnUpdate);

        btnReset = new JButton("Reset");
        btnReset.setBounds(225, 370, 115, 25);
        add(btnReset);

        // --- 4. TABEL SEBELAH KANAN ---
        String[] kolom = {"NIS", "Nama", "Jurusan", "JK", "Alamat"};
        tableModel = new DefaultTableModel(kolom, 0);
        tableSiswa = new JTable(tableModel);
        tableSiswa.setRowHeight(25);
        scrollTable = new JScrollPane(tableSiswa);
        scrollTable.setBounds(360, 80, 450, 315); 
        add(scrollTable);

        tampilData();

        // --- 5. LOGIKA TOMBOL (CRUD) ---
        
        // TOMBOL SIMPAN
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection cn = koneksi.Koneksi(); 
                    String sql = "INSERT INTO students (nis, nama, jurusan, jk, alamat) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    
                    pst.setString(1, txtNis.getText());
                    pst.setString(2, txtNama.getText());
                    pst.setString(3, cbJurusan.getSelectedItem().toString());
                    pst.setString(4, cbJk.getSelectedItem().toString());
                    pst.setString(5, txtAlamat.getText());
                    
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                    tampilData(); 
                    resetForm();  
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Gagal Menyimpan: " + ex.getMessage());
                }
            }
        });

        // TOMBOL RESET
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        // KLIK TABEL
        tableSiswa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tableSiswa.getSelectedRow();
                
                // Ambil NIS lama dari tabel dan simpan ke variabel oldNis
                oldNis = tableModel.getValueAt(baris, 0).toString();
                
                txtNis.setText(oldNis);
                txtNama.setText(tableModel.getValueAt(baris, 1).toString());
                cbJurusan.setSelectedItem(tableModel.getValueAt(baris, 2).toString());
                cbJk.setSelectedItem(tableModel.getValueAt(baris, 3).toString());
                txtAlamat.setText(tableModel.getValueAt(baris, 4).toString());
                
                // SEKARANG DIBUAT TRUE: Supaya kotak NIS tetap bisa diketik/diedit
                txtNis.setEditable(true); 
            }
        });
        
        // TOMBOL UPDATE
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection cn = koneksi.Koneksi();
                    
                    // QUERY DIUBAH: 'nis' ikut di-SET baru, dicari berdasarkan NIS LAMA
                    String sql = "UPDATE students SET nis=?, nama=?, jurusan=?, jk=?, alamat=? WHERE nis=?";
                    PreparedStatement pst = cn.prepareStatement(sql);
                    
                    pst.setString(1, txtNis.getText()); // NIS Baru yang diketik
                    pst.setString(2, txtNama.getText());
                    pst.setString(3, cbJurusan.getSelectedItem().toString());
                    pst.setString(4, cbJk.getSelectedItem().toString());
                    pst.setString(5, txtAlamat.getText());
                    pst.setString(6, oldNis); // Menggunakan NIS Lama sebagai patokan WHERE
                    
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Diupdate");
                    tampilData();
                    resetForm();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Gagal Update: " + ex.getMessage());
                }
            }
        });

        // TOMBOL HAPUS
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int jawab = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (jawab == JOptionPane.YES_OPTION) {
                        Connection cn = koneksi.Koneksi();
                        String sql = "DELETE FROM students WHERE nis=?";
                        PreparedStatement pst = cn.prepareStatement(sql);
                        pst.setString(1, txtNis.getText());
                        
                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                        tampilData();
                        resetForm();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Gagal Menghapus: " + ex.getMessage());
                }
            }
        });
    }

    // --- METHOD TAMPIL DATA ---
    public void tampilData() {
        tableModel.setRowCount(0); 
        try {
            Connection cn = koneksi.Koneksi();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");
            
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("nis"),
                    rs.getString("nama"),
                    rs.getString("jurusan"),
                    rs.getString("jk"),
                    rs.getString("alamat")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error menampilkan data: " + e.getMessage());
        }
    }

    // --- METHOD RESET FORM ---
    public void resetForm() {
        txtNis.setText("");
        txtNama.setText("");
        cbJurusan.setSelectedIndex(0);
        cbJk.setSelectedIndex(0);
        txtAlamat.setText("");
        txtNis.setEditable(true); 
        oldNis = ""; // Kosongkan kembali variabel NIS lama
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormSiswa().setVisible(true);
            }
        });
    }
}