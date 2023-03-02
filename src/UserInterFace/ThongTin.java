package UserInterFace;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ThongTin extends javax.swing.JFrame {
    private Connection connect = null;  
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    private Detail detail;
    private boolean Add    = false;
    private boolean Change = false;
    
    String sql1 = "SELECT * FROM ChucVu";
    String sql2 = "SELECT * FROM NhaSanXuat";
    String sql3 = "SELECT * FROM LoaiThietBi";
    
    public ThongTin(Detail d) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        detail = new Detail(d);
        LabelTrangThaiChucVu.setForeground(Color.red);
        connection();
        LoadChucVu(sql1);
        LoadNhaSanXuat(sql2);
        LoadLoaiThietBi(sql3);
        DisabledPosition();
        DisabledClassify();
        DisabledProducer();
    }

    // Kết Nối SQL.
    private void connection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection("jdbc:sqlserver://DUONGPHI:1433;databaseName=CuaHangThietBiXeMay;user=sa;password=sa2016;encrypt=false");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Tải Dữ Liệu "ChucVu" từ SQL Vào Bảng.
    private void LoadChucVu(String sql) {
        BangChucVu.removeAll();
        try {
            String [] arr = {"Mã Chức Vụ", "Tên Chức Vụ", "Lương Cơ Bản"};
            DefaultTableModel model = new DefaultTableModel(arr,0);
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                Vector vector = new Vector();
                vector.add(rs.getString("MaChucVu").trim());
                vector.add(rs.getString("TenChucVu").trim());
                vector.add(rs.getString("Luong").trim());
                model.addRow(vector);
            }
            // Hiện Dữ Liệu
            BangChucVu.setModel(model);
            // Điều Chỉnh Kích Thước
            BangChucVu.setRowHeight(25);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Tải Dữ Liệu "NhaSanXuat" từ SQL Vào Bảng.
    private void LoadNhaSanXuat(String sql) {
        BangNhaSanXuat.removeAll();
        try {
            String [] arr = {"Mã NSX","Tên NSX","Địa Chỉ", "Số Điện Thoại", "Email"};
            DefaultTableModel model = new DefaultTableModel(arr,0);
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                Vector vector = new Vector();
                vector.add(rs.getString("MaNhaSanXuat").trim());
                vector.add(rs.getString("TenNhaSanXuat").trim());
                vector.add(rs.getString("DiaChi").trim());
                vector.add(rs.getString("SoDienThoai").trim());
                vector.add(rs.getString("Email").trim());
                model.addRow(vector);
            }
            // Hiện Dữ LIệu
            BangNhaSanXuat.setModel(model);
            // Điều Chỉnh Kích Thước
            BangNhaSanXuat.getColumnModel().getColumn(0).setPreferredWidth(0);
            BangNhaSanXuat.getColumnModel().getColumn(1).setPreferredWidth(0);
            BangNhaSanXuat.getColumnModel().getColumn(2).setPreferredWidth(350);
            BangNhaSanXuat.getColumnModel().getColumn(3).setPreferredWidth(0);
            BangNhaSanXuat.getColumnModel().getColumn(4).setPreferredWidth(0);
            BangNhaSanXuat.setRowHeight(25);  
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //Tải Dữ Liệu "LoaiThietBi" từ SQL Vào Bảng.
    private void LoadLoaiThietBi(String sql){
        BangLoaiThietBi.removeAll();
        try{
            String [] arr = {"Mã Loại Thiết Bị", "Tên Loại Thiết Bị"};
            DefaultTableModel model = new DefaultTableModel(arr,0);
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                Vector vector = new Vector();
                vector.add(rs.getString("MaLoaiThietBi").trim());
                vector.add(rs.getString("TenLoaiThietBi").trim());
                model.addRow(vector);
            }
            // Hiện Dữ Liệu
            BangLoaiThietBi.setModel(model);
            // Điều Chỉnh Kích Thước
            BangLoaiThietBi.setRowHeight(25);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Quay Về Màn Hình Trang Chủ.
    private void backHome() {
        TrangChuQuanLy home = new TrangChuQuanLy(detail);
        this.setVisible(false);
        home.setVisible(true);
    }
    
    private void EnabledPosition(){
        TextBoxMaChucVu.setEnabled(true);
        TextBoxTenChucVu.setEnabled(true);
        TextBoxLuong.setEnabled(true);
        LabelTrangThaiChucVu.setText("TRẠNG THÁI");
    }
    
    private void EnabledProducer(){
        TextBoxMaNhaSanXuat.setEnabled(true);
        TextBoxTenNhaSanXuat.setEnabled(true);
        TextBoxDiaChi.setEnabled(true);
        TextBoxSoDienThoai.setEnabled(true);
        TextBoxEmail.setEnabled(true);
        LabelTrangThaiNhaSanXuat.setText("TRẠNG THÁI");
    }
    
    private void EnabledClassify(){
        TextBoxMaLoaiThietBi.setEnabled(true);
        TextBoxTenLoaiThietBi.setEnabled(true);
        LabelTrangThaiLoaiThietBi.setText("TRẠNG THÁI");
    }
    
    private void DisabledPosition(){
        TextBoxMaChucVu.setEnabled(false);
        TextBoxTenChucVu.setEnabled(false);
        TextBoxLuong.setEnabled(false);
    }
    
    private void DisabledProducer(){
        TextBoxMaNhaSanXuat.setEnabled(false);
        TextBoxTenNhaSanXuat.setEnabled(false);
        TextBoxDiaChi.setEnabled(false);
        TextBoxSoDienThoai.setEnabled(false);
        TextBoxEmail.setEnabled(false);
    }
    
    private void DisabledClassify(){
        TextBoxMaLoaiThietBi.setEnabled(false);
        TextBoxTenLoaiThietBi.setEnabled(false);
    }
    
    private void Refresh(){
        Change = false;
        Add = false;
        TextBoxMaChucVu.setText("");
        TextBoxTenChucVu.setText("");
        TextBoxLuong.setText("");
        TextBoxMaNhaSanXuat.setText("");
        TextBoxTenNhaSanXuat.setText("");
        TextBoxDiaChi.setText("");
        TextBoxSoDienThoai.setText("");
        TextBoxEmail.setText("");
        TextBoxMaLoaiThietBi.setText("");
        TextBoxTenLoaiThietBi.setText("");
        ButtonThemChucVu.setEnabled(true);
        ButtonSuaChucVu.setEnabled(false);
        ButtonXoaChucVu.setEnabled(false);
        ButtonLuuChucVu.setEnabled(false);
        ButtonThemNhaSanXuat.setEnabled(true);
        ButtonSuaNhaSanXuat.setEnabled(false);
        ButtonXoaNhaSanXuat.setEnabled(false);
        ButtonLuuNhaSanXuat.setEnabled(false);
        ButtonThemLoaiThietBi.setEnabled(true);
        ButtonSuaLoaiThietBi.setEnabled(false);
        ButtonXoaLoaiThietBi.setEnabled(false);
        ButtonLuuLoaiThietBi.setEnabled(false);
        DisabledClassify();
        DisabledPosition();
        DisabledProducer();
    }
    
    private boolean CheckPosition() {
        boolean check = true;
        String sqlCheck = "SELECT * FROM ChucVu";
        try {
            pst = connect.prepareStatement(sqlCheck);
            rs = pst.executeQuery();
            while(rs.next()){
                if(this.TextBoxMaChucVu.getText().equals(rs.getString("MaChucVu").toString().trim())) {
                    return false;
                }
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return check;
    }
    
    private boolean CheckProducer() {
        boolean check = true;
        String sqlCheck = "SELECT * FROM NhaSanXuat";
        try {
            pst = connect.prepareStatement(sqlCheck);
            rs = pst.executeQuery();
            while(rs.next()) {
                if(this.TextBoxMaNhaSanXuat.getText().equals(rs.getString("MaNhaSanXuat").toString().trim())) {
                    return false;
                }
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return check;
    }
    
    private boolean CheckClassify() {
        boolean check = true;
        String sqlCheck = "SELECT * FROM LoaiThietBi";
        try {
            pst = connect.prepareStatement(sqlCheck);
            rs = pst.executeQuery();
            while(rs.next()){
                if(this.TextBoxMaLoaiThietBi.getText().equals(rs.getString("MaLoaiThietBi").toString().trim())) {
                    return false;
                }
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return check;
    }
    
    // Check Điều Kiện Nhập "ChucVu".
    private boolean checkNullPosition() {
        boolean check = true;
        if(String.valueOf(this.TextBoxMaChucVu.getText()).length() == 0) {
            LabelTrangThaiChucVu.setText("CHƯA NHẬP MÃ CHỨC VỤ !");
            return false;
        }
        if(String.valueOf(this.TextBoxTenChucVu.getText()).length() == 0) {
            LabelTrangThaiChucVu.setText("CHƯA NHẬP TÊN CHỨC VỤ !");
            return false;
        }
        if(String.valueOf(this.TextBoxLuong.getText()).length() == 0) {
            LabelTrangThaiChucVu.setText("CHƯA NHẬP LƯƠNG CƠ BẢN !");
            return false;
        }
        return check;
    }
    
    // Check Điều Kiện Nhập "NhaSanXuat".
    private boolean checkNullProducer(){
        boolean check = true;
        if(String.valueOf(this.TextBoxMaNhaSanXuat.getText()).length() == 0) {
            LabelTrangThaiNhaSanXuat.setText("CHƯA NHẬP MÃ NHÀ SẢN XUẤT !");
            return false;
        }
        if(String.valueOf(this.TextBoxTenNhaSanXuat.getText()).length() == 0) {
            LabelTrangThaiNhaSanXuat.setText("CHƯA NHẬP TÊN NHÀ SẢN XUẤT  !");
            return false;        
        }
        if(String.valueOf(this.TextBoxDiaChi.getText()).length() == 0) {
            LabelTrangThaiNhaSanXuat.setText("CHƯA NHẬP ĐỊA CHỈ !");
            return false;
        }
        if(String.valueOf(this.TextBoxSoDienThoai.getText()).length() == 0) {
            LabelTrangThaiNhaSanXuat.setText("CHƯA NHẬP SỐ ĐIỆN THOẠI !");
            return false;
        }
        if(String.valueOf(this.TextBoxEmail.getText()).length() == 0) {
            LabelTrangThaiNhaSanXuat.setText("CHƯA NHẬP E-MAIL !");
            return false;
        }
        return check;
    }
    
    //Check Điều Kiện Nhập "LoaiThietBi".
    private boolean checkNullClassify() {
        boolean check = true;
        if(String.valueOf(this.TextBoxMaLoaiThietBi.getText()).length() == 0) {
            LabelTrangThaiLoaiThietBi.setText("CHƯA NHẬP MÃ LOẠI THIẾT BỊ !");
            return false;
        }
        if(String.valueOf(this.TextBoxTenLoaiThietBi.getText()).length() == 0) {
            LabelTrangThaiLoaiThietBi.setText("CHƯA NHẬP TÊN LOẠI THIẾT BỊ !");
            return false;
        }   
        return check;
    }
    
    // Thêm Chức Vụ.
    private void addPosition() {
        if(checkNullPosition()) {
            String sqlInsert = "INSERT INTO ChucVu (MaChucVu, TenChucVu, Luong) VALUES(?, ?, ?)";
            try {
                pst = connect.prepareStatement(sqlInsert);
                pst.setString(1, TextBoxMaChucVu.getText());
                pst.setString(2, TextBoxTenChucVu.getText());
                pst.setString(3, TextBoxLuong.getText()+" "+"VND");
                pst.executeUpdate();
                LabelTrangThaiChucVu.setText("THÊM CHỨC VỤ THÀNH CÔNG !");
                DisabledPosition();
                Refresh();
                LoadChucVu(sql1);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    // Thêm Nhà Sản Xuất.
    private void addProducer() {
        if(checkNullProducer()) {
            String sqlInsert = "INSERT INTO NhaSanXuat (MaNhaSanXuat, TenNhaSanXuat, DiaChi, SoDienThoai, Email) VALUES(?, ?, ?, ?, ?)";
            try {
                pst = connect.prepareStatement(sqlInsert);
                pst.setString(1, TextBoxMaNhaSanXuat.getText());
                pst.setString(2, TextBoxTenNhaSanXuat.getText());
                pst.setString(3, TextBoxDiaChi.getText());
                pst.setString(4, TextBoxSoDienThoai.getText());
                pst.setString(5, TextBoxEmail.getText());
                pst.executeUpdate();
                LabelTrangThaiNhaSanXuat.setText("THÊM NHÀ SẢN XUẤT THÀNH CÔNG !");
                DisabledProducer();
                Refresh();
                LoadNhaSanXuat(sql2);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    // Thêm Loại Thiết Bị.
    private void addClassify() {
        if(checkNullClassify()) {
            String sqlInsert = "INSERT INTO LoaiThietBi (MaLoaiThietBi, TenLoaiThietBi) VALUES(?, ?)";
            try {
                pst = connect.prepareStatement(sqlInsert);
                pst.setString(1, TextBoxMaLoaiThietBi.getText());
                pst.setString(2, TextBoxTenLoaiThietBi.getText());
                pst.executeUpdate();
                LabelTrangThaiLoaiThietBi.setText("THÊM LOẠI THIẾT BỊ THÀNH CÔNG !");
                DisabledClassify();
                Refresh();
                LoadLoaiThietBi(sql3);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    // Sửa Chức Vụ.
    private void changedPosition() {
        int Click = BangChucVu.getSelectedRow();
        TableModel model = BangChucVu.getModel();
        if(checkNullPosition()) {
            String sqlChange = "UPDATE ChucVu SET MaChucVu=?, TenChucVu=?, Luong=? WHERE MaChucVu ='" + model.getValueAt(Click,0).toString().trim()+"'";
            try {
                pst = connect.prepareStatement(sqlChange);
                pst.setString(1, TextBoxMaChucVu.getText());
                pst.setString(2,TextBoxTenChucVu.getText() );
                pst.setString(3,TextBoxLuong.getText()+" "+"VND");
                pst.executeUpdate();
                LabelTrangThaiChucVu.setText("LƯU THÀNH CÔNG !");
                DisabledPosition();
                Refresh();
                LoadChucVu(sql1);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    // Sửa Loại Thiết Bị.
    private void changedClassify() {
        int Click = BangLoaiThietBi.getSelectedRow();
        TableModel model = BangLoaiThietBi.getModel();
        if(checkNullClassify()) {
            String sqlChange = "UPDATE LoaiThietBi SET MaLoaiThietBi = ?, TenLoaiThietBi = ? WHERE MaLoaiThietBi = '" + model.getValueAt(Click,0).toString().trim()+"'";;
            try {
                pst = connect.prepareStatement(sqlChange);
                pst.setString(1, TextBoxMaLoaiThietBi.getText());
                pst.setString(2,TextBoxTenLoaiThietBi.getText() );
                pst.executeUpdate();
                LabelTrangThaiLoaiThietBi.setText("LƯU THÀNH CÔNG !");
                DisabledClassify();
                Refresh();
                LoadLoaiThietBi(sql3);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    // Sửa Nhà Sản Xuất.
    private void changedProducer() {
        int Click = BangNhaSanXuat.getSelectedRow();
        TableModel model = BangNhaSanXuat.getModel();
        if(checkNullProducer()) {
            String sqlChange = "UPDATE NhaSanXuat SET MaNhaSanXuat = ?, TenNhaSanXuat = ?, DiaChi = ?, SoDienThoai = ?,Email = ? WHERE MaNhaSanXuat = '" + model.getValueAt(Click,0).toString().trim()+"'";;
            try {
                pst = connect.prepareStatement(sqlChange);
                pst.setString(1, TextBoxMaNhaSanXuat.getText());
                pst.setString(2, TextBoxTenNhaSanXuat.getText());
                pst.setString(3, TextBoxDiaChi.getText());
                pst.setString(4, TextBoxSoDienThoai.getText());
                pst.setString(5, TextBoxEmail.getText());
                pst.executeUpdate();
                LabelTrangThaiNhaSanXuat.setText("LƯU THÀNH CÔNG !");
                DisabledProducer();
                Refresh();
                LoadNhaSanXuat(sql2);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private double convertedToNumbers(String s){
        String number="";
        String []array=s.replace(","," ").split("\\s");
        for(String i:array){
            number=number.concat(i);
        }
        return Double.parseDouble(number);
    }
    
    private String cutChar(String arry){
        return arry.replaceAll("\\D+","");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        BangChucVu = new javax.swing.JTable();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        TextBoxTenChucVu = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        TextBoxLuong = new javax.swing.JTextField();
        TextBoxMaChucVu = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        ButtonLamMoiChucVu = new javax.swing.JButton();
        ButtonXoaChucVu = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        ButtonSuaChucVu = new javax.swing.JButton();
        ButtonThemChucVu = new javax.swing.JButton();
        ButtonLuuChucVu = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        LabelTrangThaiChucVu = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        BangNhaSanXuat = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        ButtonLamMoiNhaSanXuat = new javax.swing.JButton();
        ButtonThemNhaSanXuat = new javax.swing.JButton();
        ButtonSuaNhaSanXuat = new javax.swing.JButton();
        ButtonLuuNhaSanXuat = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        ButtonXoaNhaSanXuat = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        TextBoxMaNhaSanXuat = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        TextBoxTenNhaSanXuat = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        TextBoxDiaChi = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        TextBoxSoDienThoai = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        TextBoxEmail = new javax.swing.JTextField();
        lblStatus1 = new javax.swing.JLabel();
        LabelTrangThaiNhaSanXuat = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        BangLoaiThietBi = new javax.swing.JTable();
        TextBoxMaLoaiThietBi = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        TextBoxTenLoaiThietBi = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        ButtonLamMoiLoaiThietBi = new javax.swing.JButton();
        ButtonThemLoaiThietBi = new javax.swing.JButton();
        ButtonLuuLoaiThietBi = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        ButtonSuaLoaiThietBi = new javax.swing.JButton();
        ButtonXoaLoaiThietBi = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        LabelTrangThaiLoaiThietBi = new javax.swing.JLabel();
        ButtonTroVe = new javax.swing.JButton();
        lblRun1 = new javax.swing.JLabel();
        lblRun2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        BangChucVu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        BangChucVu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BangChucVuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(BangChucVu);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 67, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 67, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        TextBoxTenChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBoxTenChucVuActionPerformed(evt);
            }
        });

        TextBoxLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBoxLuongActionPerformed(evt);
            }
        });
        TextBoxLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextBoxLuongKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TextBoxLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addGap(209, 209, 209))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TextBoxLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        TextBoxMaChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBoxMaChucVuActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("Mã Chức Vụ:");

        ButtonLamMoiChucVu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoiChucVu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonLamMoiChucVuMouseClicked(evt);
            }
        });

        ButtonXoaChucVu.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonXoaChucVu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete.png"))); // NOI18N
        ButtonXoaChucVu.setText(" XÓA");
        ButtonXoaChucVu.setEnabled(false);
        ButtonXoaChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonXoaChucVuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        ButtonSuaChucVu.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonSuaChucVu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png"))); // NOI18N
        ButtonSuaChucVu.setText(" SỬA");
        ButtonSuaChucVu.setEnabled(false);
        ButtonSuaChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSuaChucVuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addComponent(ButtonSuaChucVu)
                .addGap(30, 30, 30))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ButtonSuaChucVu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        ButtonThemChucVu.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonThemChucVu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png"))); // NOI18N
        ButtonThemChucVu.setText("THÊM");
        ButtonThemChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThemChucVuActionPerformed(evt);
            }
        });

        ButtonLuuChucVu.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonLuuChucVu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save.png"))); // NOI18N
        ButtonLuuChucVu.setText(" LƯU");
        ButtonLuuChucVu.setEnabled(false);
        ButtonLuuChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLuuChucVuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(ButtonLamMoiChucVu)
                .addGap(30, 30, 30)
                .addComponent(ButtonThemChucVu)
                .addGap(30, 30, 30)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(ButtonXoaChucVu)
                .addGap(30, 30, 30)
                .addComponent(ButtonLuuChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(244, 244, 244)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(ButtonLamMoiChucVu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ButtonThemChucVu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ButtonXoaChucVu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ButtonLuuChucVu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setText("Tên Chức Vụ:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setText("Lương Cơ Bản:");

        LabelTrangThaiChucVu.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelTrangThaiChucVu.setForeground(new java.awt.Color(255, 0, 0));
        LabelTrangThaiChucVu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelTrangThaiChucVu.setText("TRẠNG THÁI");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelTrangThaiChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 1019, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TextBoxMaChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TextBoxTenChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1019, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextBoxMaChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextBoxTenChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelTrangThaiChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Chức Vụ", jPanel1);

        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 444));

        BangNhaSanXuat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        BangNhaSanXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BangNhaSanXuatMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(BangNhaSanXuat);

        ButtonLamMoiNhaSanXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoiNhaSanXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonLamMoiNhaSanXuatMouseClicked(evt);
            }
        });

        ButtonThemNhaSanXuat.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonThemNhaSanXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png"))); // NOI18N
        ButtonThemNhaSanXuat.setText("THÊM");
        ButtonThemNhaSanXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThemNhaSanXuatActionPerformed(evt);
            }
        });

        ButtonSuaNhaSanXuat.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonSuaNhaSanXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png"))); // NOI18N
        ButtonSuaNhaSanXuat.setText(" SỬA");
        ButtonSuaNhaSanXuat.setEnabled(false);
        ButtonSuaNhaSanXuat.setPreferredSize(new java.awt.Dimension(109, 41));
        ButtonSuaNhaSanXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSuaNhaSanXuatActionPerformed(evt);
            }
        });

        ButtonLuuNhaSanXuat.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonLuuNhaSanXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save.png"))); // NOI18N
        ButtonLuuNhaSanXuat.setText(" LƯU");
        ButtonLuuNhaSanXuat.setEnabled(false);
        ButtonLuuNhaSanXuat.setPreferredSize(new java.awt.Dimension(109, 41));
        ButtonLuuNhaSanXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLuuNhaSanXuatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );

        ButtonXoaNhaSanXuat.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonXoaNhaSanXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete.png"))); // NOI18N
        ButtonXoaNhaSanXuat.setText(" XÓA");
        ButtonXoaNhaSanXuat.setEnabled(false);
        ButtonXoaNhaSanXuat.setPreferredSize(new java.awt.Dimension(109, 41));
        ButtonXoaNhaSanXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonXoaNhaSanXuatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(ButtonLamMoiNhaSanXuat)
                .addGap(29, 29, 29)
                .addComponent(ButtonThemNhaSanXuat)
                .addGap(29, 29, 29)
                .addComponent(ButtonSuaNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(ButtonXoaNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(ButtonLuuNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(366, 366, 366)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(ButtonXoaNhaSanXuat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ButtonSuaNhaSanXuat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)))
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ButtonLamMoiNhaSanXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ButtonThemNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(ButtonLuuNhaSanXuat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setText("Mã Nhà Sản Xuất:");

        TextBoxMaNhaSanXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBoxMaNhaSanXuatActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setText("Tên Nhà Sản Xuất:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setText("Địa Chỉ:");

        TextBoxDiaChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBoxDiaChiActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setText("Số Điện Thoại:");

        TextBoxSoDienThoai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextBoxSoDienThoaiKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel11.setText("E-mail:");

        lblStatus1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblStatus1.setForeground(new java.awt.Color(255, 0, 0));
        lblStatus1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatus1.setText("Trạng Thái");

        LabelTrangThaiNhaSanXuat.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelTrangThaiNhaSanXuat.setForeground(new java.awt.Color(255, 0, 0));
        LabelTrangThaiNhaSanXuat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelTrangThaiNhaSanXuat.setText("TRẠNG THÁI");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(9, 9, 9)
                                        .addComponent(TextBoxMaNhaSanXuat))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(TextBoxDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(TextBoxTenNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(18, 18, 18)
                                        .addComponent(TextBoxSoDienThoai)))
                                .addGap(30, 30, 30)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(TextBoxEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addComponent(lblStatus1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelTrangThaiNhaSanXuat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TextBoxMaNhaSanXuat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(TextBoxTenNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(TextBoxDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextBoxSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextBoxEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelTrangThaiNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(lblStatus1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel7.getAccessibleContext().setAccessibleDescription("");

        jTabbedPane1.addTab("Nhà Sản Xuất", jPanel2);

        BangLoaiThietBi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        BangLoaiThietBi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BangLoaiThietBiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(BangLoaiThietBi);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jLabel13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel13.setText("Tên Loại Thiết Bị:");

        ButtonLamMoiLoaiThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoiLoaiThietBi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonLamMoiLoaiThietBiMouseClicked(evt);
            }
        });

        ButtonThemLoaiThietBi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonThemLoaiThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png"))); // NOI18N
        ButtonThemLoaiThietBi.setText("THÊM");
        ButtonThemLoaiThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThemLoaiThietBiActionPerformed(evt);
            }
        });

        ButtonLuuLoaiThietBi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonLuuLoaiThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save.png"))); // NOI18N
        ButtonLuuLoaiThietBi.setText(" LƯU");
        ButtonLuuLoaiThietBi.setEnabled(false);
        ButtonLuuLoaiThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLuuLoaiThietBiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        ButtonSuaLoaiThietBi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonSuaLoaiThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png"))); // NOI18N
        ButtonSuaLoaiThietBi.setText(" SỬA");
        ButtonSuaLoaiThietBi.setEnabled(false);
        ButtonSuaLoaiThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSuaLoaiThietBiActionPerformed(evt);
            }
        });

        ButtonXoaLoaiThietBi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonXoaLoaiThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete.png"))); // NOI18N
        ButtonXoaLoaiThietBi.setText(" XÓA");
        ButtonXoaLoaiThietBi.setEnabled(false);
        ButtonXoaLoaiThietBi.setPreferredSize(new java.awt.Dimension(103, 41));
        ButtonXoaLoaiThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonXoaLoaiThietBiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ButtonLamMoiLoaiThietBi)
                .addGap(30, 30, 30)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonThemLoaiThietBi))
                .addGap(30, 30, 30)
                .addComponent(ButtonSuaLoaiThietBi)
                .addGap(30, 30, 30)
                .addComponent(ButtonXoaLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(ButtonLuuLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(0, 2, Short.MAX_VALUE)
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(57, 57, 57))
                    .addComponent(ButtonXoaLoaiThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonSuaLoaiThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonThemLoaiThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonLamMoiLoaiThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonLuuLoaiThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jLabel12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel12.setText("Mã Loại Thiết Bị:");

        LabelTrangThaiLoaiThietBi.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelTrangThaiLoaiThietBi.setForeground(new java.awt.Color(255, 0, 0));
        LabelTrangThaiLoaiThietBi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelTrangThaiLoaiThietBi.setText("TRẠNG THÁI");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelTrangThaiLoaiThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TextBoxMaLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TextBoxTenLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(228, 228, 228)
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 175, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(TextBoxTenLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextBoxMaLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(12, 12, 12)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LabelTrangThaiLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jLabel12.getAccessibleContext().setAccessibleDescription("");

        jTabbedPane1.addTab("Loại Thiết Bị", jPanel3);

        ButtonTroVe.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        ButtonTroVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Back.png"))); // NOI18N
        ButtonTroVe.setText("HỆ THỐNG");
        ButtonTroVe.setMargin(new java.awt.Insets(2, -7, 5, 2));
        ButtonTroVe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonTroVeMouseClicked(evt);
            }
        });

        lblRun1.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lblRun1.setForeground(new java.awt.Color(255, 0, 0));
        lblRun1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRun1.setText("Cửa Hàng Bán Thiết Bị Xe Máy ABC - Motor: Số 97 Đường Man Thiện, Phường Hiệp Phú, TP. Thủ Đức, TP. Hồ Chí Minh");

        lblRun2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        lblRun2.setForeground(new java.awt.Color(255, 0, 0));
        lblRun2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRun2.setText("Điện thoại: 0988919701     -     Email: phisuper2310@gmail.com");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ButtonTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(lblRun1))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 225, Short.MAX_VALUE)
                                .addComponent(lblRun2)
                                .addGap(169, 169, 169))))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1040, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonTroVe)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblRun1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRun2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonTroVeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonTroVeMouseClicked
        backHome();
    }//GEN-LAST:event_ButtonTroVeMouseClicked

    // Thoát Chương Trình.
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int click = JOptionPane.showConfirmDialog(null,"THOÁT KHỎI CHƯƠNG TRÌNH ?","Thông Báo",2);
        if(click == JOptionPane.OK_OPTION){
            System.exit(0);
        }
        else {
            if(click == JOptionPane.CANCEL_OPTION){    
                this.setVisible(true);
            }
        }
    }//GEN-LAST:event_formWindowClosing

    // Xóa Loại Thiết Bị.
    private void ButtonXoaLoaiThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonXoaLoaiThietBiActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "XÓA LOẠI THIẾT BỊ ?", "Thông Báo",2);
        if(Click == JOptionPane.YES_OPTION) {
            String sqlDelete = "DELETE FROM LoaiThietBi WHERE MaLoaiThietBi = ? AND TenLoaiThietBi = ?";
            try{
                pst = connect.prepareStatement(sqlDelete);
                pst.setString(1, TextBoxMaLoaiThietBi.getText());
                pst.setString(2,TextBoxTenLoaiThietBi.getText() );
                pst.executeUpdate();
                LabelTrangThaiLoaiThietBi.setText("XÓA LOẠI THIẾT BỊ THÀNH CÔNG !");
                DisabledClassify();
                Refresh();
                LoadLoaiThietBi(sql3);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_ButtonXoaLoaiThietBiActionPerformed

    // Sửa Loại Thiết Bị.
    private void ButtonSuaLoaiThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSuaLoaiThietBiActionPerformed
        Add = false;
        Change = true;
        ButtonThemLoaiThietBi.setEnabled(false);
        ButtonSuaLoaiThietBi.setEnabled(false);
        ButtonXoaLoaiThietBi.setEnabled(false);
        ButtonLuuLoaiThietBi.setEnabled(true);
        EnabledClassify();
    }//GEN-LAST:event_ButtonSuaLoaiThietBiActionPerformed

    private void ButtonLuuLoaiThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLuuLoaiThietBiActionPerformed
        if(Add == true)
        if(CheckClassify())
        addClassify();
        else
        LabelTrangThaiLoaiThietBi.setText("MÃ LOẠI THIẾT BỊ ĐÃ TỒN TẠI !");
        else {
            if(Change == true)
            changedClassify();
        }
    }//GEN-LAST:event_ButtonLuuLoaiThietBiActionPerformed

    private void ButtonThemLoaiThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThemLoaiThietBiActionPerformed
        Refresh();
        Add = true;
        ButtonThemLoaiThietBi.setEnabled(false);
        ButtonLuuLoaiThietBi.setEnabled(true);
        EnabledClassify();
    }//GEN-LAST:event_ButtonThemLoaiThietBiActionPerformed

    private void ButtonLamMoiLoaiThietBiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonLamMoiLoaiThietBiMouseClicked
        Refresh();
    }//GEN-LAST:event_ButtonLamMoiLoaiThietBiMouseClicked

    private void BangLoaiThietBiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BangLoaiThietBiMouseClicked
        int Click=BangLoaiThietBi.getSelectedRow();
        TableModel model=BangLoaiThietBi.getModel();

        TextBoxMaLoaiThietBi.setText(model.getValueAt(Click,0).toString());
        TextBoxTenLoaiThietBi.setText(model.getValueAt(Click,1).toString());

        ButtonSuaLoaiThietBi.setEnabled(true);
        ButtonXoaLoaiThietBi.setEnabled(true);
    }//GEN-LAST:event_BangLoaiThietBiMouseClicked

    private void ButtonThemChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThemChucVuActionPerformed
        Refresh();
        Add=true;
        ButtonThemChucVu.setEnabled(false);
        ButtonLuuChucVu.setEnabled(true);
        EnabledPosition();
    }//GEN-LAST:event_ButtonThemChucVuActionPerformed

    private void ButtonLuuChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLuuChucVuActionPerformed
        if(Add == true)
        if(CheckPosition())
        addPosition();
        else    
            LabelTrangThaiChucVu.setText("MÃ CHỨC VỤ ĐÃ TỒN TẠI!");
        else {
            if(Change == true)
            changedPosition();
        }
    }//GEN-LAST:event_ButtonLuuChucVuActionPerformed

    private void ButtonXoaChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonXoaChucVuActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "XÓA CHỨC VỤ ?", "Thông Báo",2);
        if(Click == JOptionPane.YES_OPTION) {
            String sqlDelete = "DELETE FROM ChucVu WHERE MaChucVu = ? AND TenChucVu = ? AND Luong = ?";
            try {
                pst=connect.prepareStatement(sqlDelete);
                pst.setString(1, TextBoxMaChucVu.getText());
                pst.setString(2,TextBoxTenChucVu.getText() );
                pst.setString(3,TextBoxLuong.getText()+" "+"VND");
                pst.executeUpdate();
                LabelTrangThaiChucVu.setText("XÓA CHỨC VỤ THÀNH CÔNG !");
                DisabledPosition();
                Refresh();
                LoadChucVu(sql1);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_ButtonXoaChucVuActionPerformed

    private void ButtonSuaChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSuaChucVuActionPerformed
        Add=false;
        Change=true;
        ButtonThemChucVu.setEnabled(false);
        ButtonSuaChucVu.setEnabled(false);
        ButtonXoaChucVu.setEnabled(false);
        ButtonLuuChucVu.setEnabled(true);
        EnabledPosition();
    }//GEN-LAST:event_ButtonSuaChucVuActionPerformed

    private void ButtonLamMoiChucVuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonLamMoiChucVuMouseClicked
        Refresh();
    }//GEN-LAST:event_ButtonLamMoiChucVuMouseClicked

    private void TextBoxMaChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBoxMaChucVuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBoxMaChucVuActionPerformed

    private void TextBoxLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextBoxLuongKeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        TextBoxLuong.setText(cutChar(TextBoxLuong.getText()));
        if(TextBoxLuong.getText().equals("")){
            return;
        }
        else{
            TextBoxLuong.setText(formatter.format(convertedToNumbers(TextBoxLuong.getText())));
        }
    }//GEN-LAST:event_TextBoxLuongKeyReleased

    private void TextBoxLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBoxLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBoxLuongActionPerformed

    private void TextBoxTenChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBoxTenChucVuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBoxTenChucVuActionPerformed

    private void BangChucVuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BangChucVuMouseClicked
        int Click = BangChucVu.getSelectedRow();
        TableModel model = BangChucVu.getModel();

        TextBoxMaChucVu.setText(model.getValueAt(Click,0).toString());
        TextBoxTenChucVu.setText(model.getValueAt(Click,1).toString());
        String []s=model.getValueAt(Click,2).toString().split("\\s");
        TextBoxLuong.setText(s[0]);

        ButtonSuaChucVu.setEnabled(true);
        ButtonXoaChucVu.setEnabled(true);
    }//GEN-LAST:event_BangChucVuMouseClicked

    private void TextBoxSoDienThoaiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextBoxSoDienThoaiKeyReleased
        TextBoxSoDienThoai.setText(cutChar(TextBoxSoDienThoai.getText()));
    }//GEN-LAST:event_TextBoxSoDienThoaiKeyReleased

    private void TextBoxDiaChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBoxDiaChiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBoxDiaChiActionPerformed

    private void TextBoxMaNhaSanXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBoxMaNhaSanXuatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBoxMaNhaSanXuatActionPerformed

    private void ButtonXoaNhaSanXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonXoaNhaSanXuatActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "XÓA NHÀ SẢN XUẤT ?", "Thông Báo",2);
        if(Click ==JOptionPane.YES_OPTION){
            String sqlDelete="DELETE FROM NhaSanXuat WHERE MaNhaSanXuat = ? AND TenNhaSanXuat = ? AND DiaChi = ? AND SoDienThoai = ? AND Email = ?";
            try {
                pst = connect.prepareStatement(sqlDelete);
                pst.setString(1, TextBoxMaNhaSanXuat.getText());
                pst.setString(2, TextBoxTenNhaSanXuat.getText());
                pst.setString(3, TextBoxDiaChi.getText());
                pst.setString(4, TextBoxSoDienThoai.getText());
                pst.setString(5, TextBoxEmail.getText());
                pst.executeUpdate();
                LabelTrangThaiNhaSanXuat.setText("XÓA NHÀ SẢN XUẤT THÀNH CÔNG !");
                DisabledProducer();
                Refresh();
                LoadNhaSanXuat(sql2);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_ButtonXoaNhaSanXuatActionPerformed

    private void ButtonLuuNhaSanXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLuuNhaSanXuatActionPerformed
        if(Add == true)
        if(CheckProducer())
        addProducer();
        else    
            LabelTrangThaiNhaSanXuat.setText("MÃ NHÀ SẢN XUẤT ĐÃ TỒN TẠI !");
        else {
            if(Change == true)
            changedProducer();
        }
    }//GEN-LAST:event_ButtonLuuNhaSanXuatActionPerformed

    private void ButtonSuaNhaSanXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSuaNhaSanXuatActionPerformed
        Add=false;
        Change=true;
        ButtonThemNhaSanXuat.setEnabled(false);
        ButtonSuaNhaSanXuat.setEnabled(false);
        ButtonXoaNhaSanXuat.setEnabled(false);
        ButtonLuuNhaSanXuat.setEnabled(true);
        EnabledProducer();
    }//GEN-LAST:event_ButtonSuaNhaSanXuatActionPerformed

    private void ButtonThemNhaSanXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThemNhaSanXuatActionPerformed
        Refresh();
        Add=true;
        ButtonThemNhaSanXuat.setEnabled(false);
        ButtonLuuNhaSanXuat.setEnabled(true);
        EnabledProducer();
    }//GEN-LAST:event_ButtonThemNhaSanXuatActionPerformed

    private void ButtonLamMoiNhaSanXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonLamMoiNhaSanXuatMouseClicked
        Refresh();
        LoadNhaSanXuat(sql2);
    }//GEN-LAST:event_ButtonLamMoiNhaSanXuatMouseClicked

    private void BangNhaSanXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BangNhaSanXuatMouseClicked
        int Click=BangNhaSanXuat.getSelectedRow();
        TableModel model=BangNhaSanXuat.getModel();

        TextBoxMaNhaSanXuat.setText(model.getValueAt(Click,0).toString());
        TextBoxTenNhaSanXuat.setText(model.getValueAt(Click,1).toString());
        TextBoxDiaChi.setText(model.getValueAt(Click,2).toString());
        TextBoxSoDienThoai.setText(model.getValueAt(Click,3).toString());
        TextBoxEmail.setText(model.getValueAt(Click,4).toString());

        ButtonSuaNhaSanXuat.setEnabled(true);
        ButtonXoaNhaSanXuat.setEnabled(true);
    }//GEN-LAST:event_BangNhaSanXuatMouseClicked

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ThongTin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongTin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongTin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongTin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail=new Detail();
                new ThongTin(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BangChucVu;
    private javax.swing.JTable BangLoaiThietBi;
    private javax.swing.JTable BangNhaSanXuat;
    private javax.swing.JButton ButtonLamMoiChucVu;
    private javax.swing.JButton ButtonLamMoiLoaiThietBi;
    private javax.swing.JButton ButtonLamMoiNhaSanXuat;
    private javax.swing.JButton ButtonLuuChucVu;
    private javax.swing.JButton ButtonLuuLoaiThietBi;
    private javax.swing.JButton ButtonLuuNhaSanXuat;
    private javax.swing.JButton ButtonSuaChucVu;
    private javax.swing.JButton ButtonSuaLoaiThietBi;
    private javax.swing.JButton ButtonSuaNhaSanXuat;
    private javax.swing.JButton ButtonThemChucVu;
    private javax.swing.JButton ButtonThemLoaiThietBi;
    private javax.swing.JButton ButtonThemNhaSanXuat;
    private javax.swing.JButton ButtonTroVe;
    private javax.swing.JButton ButtonXoaChucVu;
    private javax.swing.JButton ButtonXoaLoaiThietBi;
    private javax.swing.JButton ButtonXoaNhaSanXuat;
    private javax.swing.JLabel LabelTrangThaiChucVu;
    private javax.swing.JLabel LabelTrangThaiLoaiThietBi;
    private javax.swing.JLabel LabelTrangThaiNhaSanXuat;
    private javax.swing.JTextField TextBoxDiaChi;
    private javax.swing.JTextField TextBoxEmail;
    private javax.swing.JTextField TextBoxLuong;
    private javax.swing.JTextField TextBoxMaChucVu;
    private javax.swing.JTextField TextBoxMaLoaiThietBi;
    private javax.swing.JTextField TextBoxMaNhaSanXuat;
    private javax.swing.JTextField TextBoxSoDienThoai;
    private javax.swing.JTextField TextBoxTenChucVu;
    private javax.swing.JTextField TextBoxTenLoaiThietBi;
    private javax.swing.JTextField TextBoxTenNhaSanXuat;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblRun1;
    private javax.swing.JLabel lblRun2;
    private javax.swing.JLabel lblStatus1;
    // End of variables declaration//GEN-END:variables
}
