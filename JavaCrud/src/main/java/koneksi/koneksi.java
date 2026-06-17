package Koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException; // Tambahan import untuk SQLException
import javax.swing.JOptionPane;

public class koneksi {
    
    // Variabel "Connection Koneksi;" sudah dihapus karena tidak dipakai
    
    public static Connection Koneksi() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/tutorialjavahaikal", "root", "");
            
            return koneksi;
            
        } catch(ClassNotFoundException | SQLException e) { // Catch sudah dibuat spesifik
            JOptionPane.showMessageDialog(null, e);
            
            return null;
        }
    }
}