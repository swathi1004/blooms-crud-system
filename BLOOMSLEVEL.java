package bloomslevel; 
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.sql.*;
//
//public class BLOOMSLEVEL {
//    public static void main(String[] args) {
//        new LoginFrame();
//    }
//}
//
//class LoginFrame extends JFrame implements ActionListener {
//
//    JTextField txtUser;
//    JPasswordField txtPass;
//    JButton btnLogin;
//    Connection conn;
//
//    public LoginFrame() {
//        setTitle("Login");
//        setSize(300, 200);
//        setLayout(new GridLayout(3, 2, 10, 10));
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        add(new JLabel("Username:"));
//        txtUser = new JTextField();
//        add(txtUser);
//
//        add(new JLabel("Password:"));
//        txtPass = new JPasswordField();
//        add(txtPass);
//
//        btnLogin = new JButton("Login");
//        btnLogin.addActionListener(this);
//        add(new JLabel());
//        add(btnLogin);
//
//        connectDB();
//        setVisible(true);
//    }
//
//    void connectDB() {
//        try {
//            Class.forName("org.sqlite.JDBC");
//            conn = DriverManager.getConnection("jdbc:sqlite:javaapp.db");
//            System.out.println("Connected to DB - LoginFrame");
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Database Connection Failed: " + e);
//        }
//    }
//
//    public void actionPerformed(ActionEvent e) {
//        String username = txtUser.getText();
//        String password = String.valueOf(txtPass.getPassword());
//
//        try {
//            String query = "SELECT * FROM users WHERE uname = ? AND pwd = ?";
//            PreparedStatement pst = conn.prepareStatement(query);
//            pst.setString(1, username);
//            pst.setString(2, password);
//            ResultSet rs = pst.executeQuery();
//
//            if (rs.next()) {
//                JOptionPane.showMessageDialog(this, "Login successful!");
//                dispose();
//                new BloomLevelFrame();
//            } else {
//                JOptionPane.showMessageDialog(this, "Invalid username or password.");
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Login Error: " + ex);
//        }
//    }
//}
//
//class BloomLevelFrame extends JFrame implements ActionListener {
//
//    JLabel lblCode, lblLevel, lblDesc;
//    JTextField txtCode, txtLevel;
//    JTextArea txtDesc;
//    JButton btnAdd, btnView, btnUpdate, btnDelete;
//    Connection conn;
//
//    public BloomLevelFrame() {
//        setTitle("Bloom's Level Manager");
//        setSize(450, 450);
//        setLayout(new GridLayout(6, 2, 10, 10));
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        lblCode = new JLabel("Bloom Code:");
//        txtCode = new JTextField();
//
//        lblLevel = new JLabel("Bloom Level:");
//        txtLevel = new JTextField();
//
//        lblDesc = new JLabel("Description:");
//        txtDesc = new JTextArea(3, 20);
//        JScrollPane scrollPane = new JScrollPane(txtDesc);
//
//        btnAdd = new JButton("Add");
//        btnView = new JButton("View All");
//        btnUpdate = new JButton("Update");
//        btnDelete = new JButton("Delete");
//
//        btnAdd.addActionListener(this);
//        btnView.addActionListener(this);
//        btnUpdate.addActionListener(this);
//        btnDelete.addActionListener(this);
//
//        add(lblCode); add(txtCode);
//        add(lblLevel); add(txtLevel);
//        add(lblDesc); add(scrollPane);
//        add(btnAdd); add(btnView);
//        add(btnUpdate); add(btnDelete);
//
//        connectDB();
//        setVisible(true);
//    }
//
//    void connectDB() {
//        try {
//            Class.forName("org.sqlite.JDBC");
//            conn = DriverManager.getConnection("jdbc:sqlite:javaapp.db");
//            System.out.println("Connected to SQLite");
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "DB Connection Failed: " + e);
//        }
//    }
//
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == btnAdd) {
//            addBloom();
//        } else if (e.getSource() == btnView) {
//            viewBlooms();
//        } else if (e.getSource() == btnDelete) {
//            deleteBloom();
//        } else if (e.getSource() == btnUpdate) {
//            updateBloom();
//        }
//    }
//
//    void addBloom() {
//        try {
//            String code = txtCode.getText();
//            String level = txtLevel.getText();
//            String desc = txtDesc.getText();
//
//            if (code.isEmpty() || level.isEmpty() || desc.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "All fields are required!");
//                return;
//            }
//
//            String query = "INSERT INTO blooms_level (bloom_code, bloom_level, bloom_description) VALUES (?, ?, ?)";
//            PreparedStatement pst = conn.prepareStatement(query);
//            pst.setString(1, code);
//            pst.setString(2, level);
//            pst.setString(3, desc);
//            pst.executeUpdate();
//            pst.close();
//            conn.close();
//
//            JOptionPane.showMessageDialog(this, "Bloom's Level Added!");
//            txtCode.setText(""); txtLevel.setText(""); txtDesc.setText("");
//
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Error: " + ex);
//        }
//    }
//
//    void viewBlooms() {
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM blooms_level");
//
//            StringBuilder data = new StringBuilder();
//            while (rs.next()) {
//                data.append("ID: ").append(rs.getInt("ID"))
//                    .append(", Code: ").append(rs.getString("bloom_code"))
//                    .append(", Level: ").append(rs.getString("bloom_level"))
//                    .append(", Desc: ").append(rs.getString("bloom_description"))
//                    .append("\n");
//            }
//
//            JTextArea output = new JTextArea(data.toString());
//            output.setEditable(false);
//            JScrollPane scroll = new JScrollPane(output);
//            scroll.setPreferredSize(new Dimension(400, 200));
//            JOptionPane.showMessageDialog(this, scroll, "All Bloom Levels", JOptionPane.INFORMATION_MESSAGE);
//
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Error: " + ex);
//        }
//    }
//
//    void deleteBloom() {
//        String code = JOptionPane.showInputDialog(this, "Enter Bloom Code to Delete:");
//        if (code != null && !code.trim().isEmpty()) {
//            try {
//                String query = "DELETE FROM blooms_level WHERE bloom_code = ?";
//                PreparedStatement pst = conn.prepareStatement(query);
//                pst.setString(1, code);
//                int result = pst.executeUpdate();
//
//                if (result > 0) {
//                    JOptionPane.showMessageDialog(this, "Deleted Successfully.");
//                } else {
//                    JOptionPane.showMessageDialog(this, "No record found.");
//                }
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(this, "Error: " + ex);
//            }
//        }
//    }
//
//    void updateBloom() {
//        try {
//            String code = txtCode.getText();
//            String level = txtLevel.getText();
//            String desc = txtDesc.getText();
//
//            if (code.isEmpty() || level.isEmpty() || desc.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "All fields are required for update!");
//                return;
//            }
//
//            String query = "UPDATE blooms_level SET bloom_level = ?, bloom_description = ? WHERE bloom_code = ?";
//            PreparedStatement pst = conn.prepareStatement(query);
//            pst.setString(1, level);
//            pst.setString(2, desc);
//            pst.setString(3, code);
//            int result = pst.executeUpdate();
//
//            if (result > 0) {
//                JOptionPane.showMessageDialog(this, "Bloom's Level Updated!");
//            } else {
//                JOptionPane.showMessageDialog(this, "No matching record found.");
//            }
//
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Update Error: " + ex);
//        }
//    }
//}
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.sql.*;
//
//public class BLOOMSLEVEL {
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(LoginFrame::new);
//    }
//}
//
//class LoginFrame extends JFrame implements ActionListener {
//    JTextField txtUser;
//    JPasswordField txtPass;
//    JButton btnLogin;
//    Connection conn;
//
//    public LoginFrame() {
//        setTitle("Login");
//        setSize(300, 200);
//        setLayout(new GridLayout(3, 2, 10, 10));
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        add(new JLabel("Username:"));
//        txtUser = new JTextField();
//        add(txtUser);
//
//        add(new JLabel("Password:"));
//        txtPass = new JPasswordField();
//        add(txtPass);
//
//        btnLogin = new JButton("Login");
//        btnLogin.addActionListener(this);
//        add(new JLabel()); // Spacer
//        add(btnLogin);
//
//        connectDB();
//        setVisible(true);
//    }
//
//    void connectDB() {
//        try {
//            Class.forName("org.sqlite.JDBC");
//            conn = DriverManager.getConnection("jdbc:sqlite:javaapp.db");
//            System.out.println("Connected to DB - LoginFrame");
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Database Connection Failed: " + e);
//        }
//    }
//
//    public void actionPerformed(ActionEvent e) {
//        String username = txtUser.getText().trim();
//        String password = String.valueOf(txtPass.getPassword()).trim();
//
//        try {
//            String query = "SELECT * FROM users WHERE uname = ? AND pwd = ?";
//            PreparedStatement pst = conn.prepareStatement(query);
//            pst.setString(1, username);
//            pst.setString(2, password);
//            ResultSet rs = pst.executeQuery();
//
//            if (rs.next()) {
//                JOptionPane.showMessageDialog(this, "Login successful!");
//                dispose();
//                new BloomLevelFrame(conn); // pass connection
//            } else {
//                JOptionPane.showMessageDialog(this, "Invalid username or password.");
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Login Error: " + ex);
//        }
//    }
//}
//
//class BloomLevelFrame extends JFrame implements ActionListener {
//    JLabel lblCode, lblLevel, lblDesc;
//    JTextField txtCode, txtLevel;
//    JTextArea txtDesc;
//    JButton btnAdd, btnView, btnUpdate, btnDelete;
//    Connection conn;
//
//    public BloomLevelFrame(Connection connection) {
//        this.conn = connection;
//
//        setTitle("Bloom's Level Manager");
//        setSize(500, 500);
//        setLayout(new GridBagLayout());
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(8, 8, 8, 8);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        lblCode = new JLabel("Bloom Code:");
//        txtCode = new JTextField();
//
//        lblLevel = new JLabel("Bloom Level:");
//        txtLevel = new JTextField();
//
//        lblDesc = new JLabel("Description:");
//        txtDesc = new JTextArea(3, 20);
//        JScrollPane scrollPane = new JScrollPane(txtDesc);
//
//        btnAdd = new JButton("Add");
//        btnView = new JButton("View All");
//        btnUpdate = new JButton("Update");
//        btnDelete = new JButton("Delete");
//
//        btnAdd.addActionListener(this);
//        btnView.addActionListener(this);
//        btnUpdate.addActionListener(this);
//        btnDelete.addActionListener(this);
//
//        gbc.gridx = 0; gbc.gridy = 0; add(lblCode, gbc);
//        gbc.gridx = 1; add(txtCode, gbc);
//
//        gbc.gridx = 0; gbc.gridy = 1; add(lblLevel, gbc);
//        gbc.gridx = 1; add(txtLevel, gbc);
//
//        gbc.gridx = 0; gbc.gridy = 2; add(lblDesc, gbc);
//        gbc.gridx = 1; add(scrollPane, gbc);
//
//        gbc.gridx = 0; gbc.gridy = 3; add(btnAdd, gbc);
//        gbc.gridx = 1; add(btnView, gbc);
//
//        gbc.gridx = 0; gbc.gridy = 4; add(btnUpdate, gbc);
//        gbc.gridx = 1; add(btnDelete, gbc);
//
//        setVisible(true);
//    }
//
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == btnAdd) addBloom();
//        else if (e.getSource() == btnView) viewBlooms();
//        else if (e.getSource() == btnUpdate) updateBloom();
//        else if (e.getSource() == btnDelete) deleteBloom();
//    }
//
//    void addBloom() {
//        try {
//            String code = txtCode.getText().trim();
//            String level = txtLevel.getText().trim();
//            String desc = txtDesc.getText().trim();
//
//            if (code.isEmpty() || level.isEmpty() || desc.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "All fields are required!");
//                return;
//            }
//
//            String query = "INSERT INTO blooms_level (bloom_code, bloom_level, bloom_description) VALUES (?, ?, ?)";
//            PreparedStatement pst = conn.prepareStatement(query);
//            pst.setString(1, code);
//            pst.setString(2, level);
//            pst.setString(3, desc);
//            pst.executeUpdate();
//            pst.close();
//
//            JOptionPane.showMessageDialog(this, "Bloom's Level Added!");
//            clearFields();
//
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Error: " + ex);
//        }
//    }
//
//    void viewBlooms() {
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM blooms_level");
//
//            StringBuilder data = new StringBuilder();
//            while (rs.next()) {
//                data.append("ID: ").append(rs.getInt("ID"))
//                    .append(", Code: ").append(rs.getString("bloom_code"))
//                    .append(", Level: ").append(rs.getString("bloom_level"))
//                    .append(", Desc: ").append(rs.getString("bloom_description"))
//                    .append("\n");
//            }
//
//            JTextArea output = new JTextArea(data.toString());
//            output.setEditable(false);
//            JScrollPane scroll = new JScrollPane(output);
//            scroll.setPreferredSize(new Dimension(400, 200));
//            JOptionPane.showMessageDialog(this, scroll, "All Bloom Levels", JOptionPane.INFORMATION_MESSAGE);
//
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Error: " + ex);
//        }
//    }
//
//    void updateBloom() {
//        try {
//            String code = txtCode.getText().trim();
//            String level = txtLevel.getText().trim();
//            String desc = txtDesc.getText().trim();
//
//            if (code.isEmpty() || level.isEmpty() || desc.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "All fields are required to update!");
//                return;
//            }
//
//            String query = "UPDATE blooms_level SET bloom_level = ?, bloom_description = ? WHERE bloom_code = ?";
//            PreparedStatement pst = conn.prepareStatement(query);
//            pst.setString(1, level);
//            pst.setString(2, desc);
//            pst.setString(3, code);
//
//            int result = pst.executeUpdate();
//            pst.close();
//
//            if (result > 0) {
//                JOptionPane.showMessageDialog(this, "Updated Successfully!");
//            } else {
//                JOptionPane.showMessageDialog(this, "No record found with that code.");
//            }
//
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Update Error: " + ex);
//        }
//    }
//
//    void deleteBloom() {
//        String code = JOptionPane.showInputDialog(this, "Enter Bloom Code to Delete:");
//        if (code != null && !code.trim().isEmpty()) {
//            try {
//                String query = "DELETE FROM blooms_level WHERE bloom_code = ?";
//                PreparedStatement pst = conn.prepareStatement(query);
//                pst.setString(1, code.trim());
//
//                int result = pst.executeUpdate();
//                pst.close();
//
//                if (result > 0) {
//                    JOptionPane.showMessageDialog(this, "Deleted Successfully.");
//                } else {
//                    JOptionPane.showMessageDialog(this, "No record found.");
//                }
//
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(this, "Delete Error: " + ex);
//            }
//        }
//    }
//
//    void clearFields() {
//        txtCode.setText("");
//        txtLevel.setText("");
//        txtDesc.setText("");
//    }
//}
//

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BLOOMSLEVEL {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}

class LoginFrame extends JFrame implements ActionListener {
    JTextField txtUser;
    JPasswordField txtPass;
    JButton btnLogin;
    Connection conn;

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2, 10, 10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new JLabel("Username:"));
        txtUser = new JTextField();
        add(txtUser);

        add(new JLabel("Password:"));
        txtPass = new JPasswordField();
        add(txtPass);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(this);
        add(new JLabel()); // Spacer
        add(btnLogin);

        connectDB();
        setVisible(true);
    }

    void connectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/OneDrive/Desktop/SRMAP/Apps/javaapp.db");
            System.out.println("Connected to DB - LoginFrame");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed: " + e);
        }
    }

    public void actionPerformed(ActionEvent e) {
        String username = txtUser.getText().trim();
        String password = String.valueOf(txtPass.getPassword()).trim();

        try {
            String query = "SELECT * FROM users WHERE uname = ? AND pwd = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                new BloomLevelFrame(conn);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Login Error: " + ex);
        }
    }
}

class BloomLevelFrame extends JFrame implements ActionListener {
    JLabel lblCode, lblLevel, lblDesc;
    JTextField txtCode, txtLevel;
    JTextArea txtDesc;
    JButton btnAdd, btnView, btnUpdate, btnDelete;
    Connection conn;

    public BloomLevelFrame(Connection connection) {
        this.conn = connection;

        setTitle("Bloom's Level Manager");
        setSize(500, 500);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lblCode = new JLabel("Bloom Code:");
        txtCode = new JTextField();

        lblLevel = new JLabel("Bloom Level:");
        txtLevel = new JTextField();

        lblDesc = new JLabel("Description:");
        txtDesc = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(txtDesc);

        btnAdd = new JButton("Add");
        btnView = new JButton("View All");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");

        btnAdd.addActionListener(this);
        btnView.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);

        gbc.gridx = 0; gbc.gridy = 0; add(lblCode, gbc);
        gbc.gridx = 1; add(txtCode, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(lblLevel, gbc);
        gbc.gridx = 1; add(txtLevel, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(lblDesc, gbc);
        gbc.gridx = 1; add(scrollPane, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(btnAdd, gbc);
        gbc.gridx = 1; add(btnView, gbc);

        gbc.gridx = 0; gbc.gridy = 4; add(btnUpdate, gbc);
        gbc.gridx = 1; add(btnDelete, gbc);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) addBloom();
        else if (e.getSource() == btnView) viewBlooms();
        else if (e.getSource() == btnUpdate) updateBloom();
        else if (e.getSource() == btnDelete) deleteBloom();
    }

    void addBloom() {
        try {
            String code = txtCode.getText().trim();
            String level = txtLevel.getText().trim();
            String desc = txtDesc.getText().trim();

            if (code.isEmpty() || level.isEmpty() || desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            String query = "INSERT INTO blooms_level (bloom_code, bloom_level, bloom_description) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, code);
            pst.setString(2, level);
            pst.setString(3, desc);
            pst.executeUpdate();
            pst.close();

            JOptionPane.showMessageDialog(this, "Bloom's Level Added!");
            clearFields();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex);
        }
    }

    void viewBlooms() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM blooms_level");

            StringBuilder data = new StringBuilder();
            while (rs.next()) {
                data.append("ID: ").append(rs.getInt("ID"))
                    .append(", Code: ").append(rs.getString("bloom_code"))
                    .append(", Level: ").append(rs.getString("bloom_level"))
                    .append(", Desc: ").append(rs.getString("bloom_description"))
                    .append("\n");
            }

            JTextArea output = new JTextArea(data.toString());
            output.setEditable(false);
            JScrollPane scroll = new JScrollPane(output);
            scroll.setPreferredSize(new Dimension(400, 200));
            JOptionPane.showMessageDialog(this, scroll, "All Bloom Levels", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex);
        }
    }

    void updateBloom() {
        try {
            String code = txtCode.getText().trim();
            String level = txtLevel.getText().trim();
            String desc = txtDesc.getText().trim();

            if (code.isEmpty() || level.isEmpty() || desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required to update!");
                return;
            }

            String query = "UPDATE blooms_level SET bloom_level = ?, bloom_description = ? WHERE bloom_code = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, level);
            pst.setString(2, desc);
            pst.setString(3, code);

            int result = pst.executeUpdate();
            pst.close();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No record found with that code.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Update Error: " + ex);
        }
    }

    void deleteBloom() {
        String code = JOptionPane.showInputDialog(this, "Enter Bloom Code to Delete:");
        if (code != null && !code.trim().isEmpty()) {
            try {
                String query = "DELETE FROM blooms_level WHERE bloom_code = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, code.trim());

                int result = pst.executeUpdate();
                pst.close();

                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Deleted Successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "No record found.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Delete Error: " + ex);
            }
        }
    }

    void clearFields() {
        txtCode.setText("");
        txtLevel.setText("");
        txtDesc.setText("");
    }
}
