package UserInterFace;

import java.awt.Color;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class NhanVien extends javax.swing.JFrame {
    private Connection connect = null;  
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    private boolean Add = false,Change = false;
    private String sql = "SELECT * FROM NhanVien";
    
    private Detail detail;
    
    public NhanVien(Detail d) {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        connection();
        detail = new Detail(d);
        Load(sql);
        Disabled();
        LoadChucVu();
        lblStatus.setForeground(Color.red);
    }

    //Kết Nối Đến SQL
    private void connection(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection("jdbc:sqlserver://DUONGPHI:1433;databaseName=CuaHangThietBiXeMay;user=sa;password=sa2016;encrypt=false");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void Enabled(){
        ComboBoxTenChucVu.setEnabled(true);
        TextBoxMaNhanVien.setEnabled(true);
        TextBoxHoTen.setEnabled(true);
        TextBoxNgaySinh.setEnabled(true);
        ComboBoxGioiTinh.setEnabled(true);
        TextBoxSoDienThoai.setEnabled(true);
        TextBoxHeSoLuong.setEnabled(true);
        TextBoxDiaChi.setEnabled(true);
        TextBoxEmail.setEnabled(true);
        btn.setEnabled(true);
        lblStatus.setText("Trạng Thái!");
    }
    
    private void Disabled(){
        ComboBoxTenChucVu.setEnabled(false);
        TextBoxMaNhanVien.setEnabled(false);
        TextBoxHoTen.setEnabled(false);
        TextBoxNgaySinh.setEnabled(false);
        ComboBoxGioiTinh.setEnabled(false);
        TextBoxSoDienThoai.setEnabled(false);
        TextBoxHeSoLuong.setEnabled(false);
        TextBoxDiaChi.setEnabled(false);
        TextBoxEmail.setEnabled(false);
        btn.setEnabled(false);
    }
    
    private void Refresh(){
        Add    = false;
        Change = false;
        TextBoxMaNhanVien.setText("");
        TextBoxHoTen.setText("");
        ((JTextField)TextBoxNgaySinh.getDateEditor().getUiComponent()).setText("");
        TextBoxSoDienThoai.setText("");
        TextBoxHeSoLuong.setText("");
        TextBoxDiaChi.setText("");
        TextBoxEmail.setText("");
        ButtonThemNhanVien.setEnabled(true);
        ButtonSuaNhanVien.setEnabled(false);
        ButtonXoaNhanVien.setEnabled(false);
        ButtonLuuNhanVien.setEnabled(false);
        LoadChucVu();
        LoadGioiTinh();
        lblStatus.setText("Trạng Thái");
    }
    
    private void LoadGioiTinh(){
        ComboBoxGioiTinh.removeAllItems();
        ComboBoxGioiTinh.addItem("Nam");
        ComboBoxGioiTinh.addItem("Nữ");
        ComboBoxGioiTinh.addItem("Khác");
    }
    
    private void Load(String sql ){
        try {
            String [] arr = {"Mã Nhân Viên","Họ Tên","Chức Vụ","Ngày Sinh","Giới Tính","Địa Chỉ","Số Điện Thoại","E-mail","Hệ Số Lương","Lương Cơ Bản"};
            DefaultTableModel model = new DefaultTableModel(arr,0);
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Vector vector = new Vector();
                vector.add(rs.getString("MaNhanVien").trim());
                vector.add(rs.getString("HoTen").trim());
                vector.add(rs.getString("ChucVu").trim());
                vector.add(new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("NgaySinh")));
                vector.add(rs.getString("GioiTinh").trim());
                vector.add(rs.getString("DiaChi").trim());
                vector.add(rs.getString("SoDienThoai").trim());
                vector.add(rs.getString("Email").trim());
                vector.add(rs.getInt("HeSoLuong"));
                vector.add(rs.getString("LuongCoBan").trim());
                model.addRow(vector);
            }
            // Hiện Dữ Liệu
            BangNhanVien.setModel(model);
            // Điều Chỉnh Kích Thước
            BangNhanVien.getColumnModel().getColumn(0).setPreferredWidth(40);
            BangNhanVien.getColumnModel().getColumn(1).setPreferredWidth(80);
            BangNhanVien.getColumnModel().getColumn(2).setPreferredWidth(15);
            BangNhanVien.getColumnModel().getColumn(3).setPreferredWidth(20);
            BangNhanVien.getColumnModel().getColumn(4).setPreferredWidth(5);
            BangNhanVien.getColumnModel().getColumn(5).setPreferredWidth(40);
            BangNhanVien.getColumnModel().getColumn(6).setPreferredWidth(20);
            BangNhanVien.getColumnModel().getColumn(7).setPreferredWidth(100);
            BangNhanVien.getColumnModel().getColumn(8).setPreferredWidth(10);
            BangNhanVien.getColumnModel().getColumn(9).setPreferredWidth(20);
            BangNhanVien.setRowHeight(25);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void LoadChucVu() {
        ComboBoxTenChucVu.removeAllItems();
        String sqlcbxPosition = "SELECT * FROM ChucVu";
        try {
            pst = connect.prepareStatement(sqlcbxPosition);
            rs = pst.executeQuery();
            while(rs.next()) {
                this.ComboBoxTenChucVu.addItem(rs.getString("TenChucVu").trim());
            }
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    
    // Check Điều Kiện
    private boolean checkNull() {
        boolean check = true;
        if(String.valueOf(this.TextBoxMaNhanVien.getText()).length() == 0) {
            lblStatus.setText("Chưa Nhập Mã Nhân Viên!");
            return false;
        }
        if(String.valueOf(this.TextBoxHoTen.getText()).length() == 0) {
            lblStatus.setText("Chưa Nhập Họ Tên Nhân Viên!");
            return false;
        }
        if(String.valueOf(((JTextField)this.TextBoxNgaySinh.getDateEditor().getUiComponent()).getText()).length() == 0) {
            lblStatus.setText("Chưa Nhập Ngày Sinh!");
            return false;
        }
        if(String.valueOf(this.TextBoxDiaChi.getText()).length() == 0) {
            lblStatus.setText("Chưa Nhập Địa Chỉ!");
            return false;
        }
        if(String.valueOf(this.TextBoxSoDienThoai.getText()).length() == 0) {
            lblStatus.setText("Chưa Nhập Số Điện Thoại!");
            return false;
        }
        if(String.valueOf(this.TextBoxEmail.getText()).length() == 0) {
            lblStatus.setText("Chưa Nhập E-Mail Của Nhân Viên!");
            return false;
        }
        if(String.valueOf(this.TextBoxHeSoLuong.getText()).length() == 0) {
            lblStatus.setText("Chưa Nhập Hệ Số Lương!");
            return false;
        }
        if(String.valueOf(this.TextBoxLuong.getText()).length() == 0) {
            lblStatus.setText("Bạn cần cập nhật lại bậc lương!");
            return false;
        }
        return check;
    }
    
    // Thêm Nhân Viên Mới
    private void ThemNhanVien() {
        if(checkNull()) {
            String sqlInsert = "INSERT INTO NhanVien (MaNhanVien, HoTen, ChucVu, NgaySinh, GioiTinh, DiaChi, SoDienThoai, Email, HeSoLuong, LuongCoBan) VALUES(?,?,?,?,?,?,?,?,?,?)";
            try {
                pst = connect.prepareStatement(sqlInsert);
                pst.setString(1, this.TextBoxMaNhanVien.getText());
                pst.setString(2, TextBoxHoTen.getText());
                pst.setString(3, (String)ComboBoxTenChucVu.getSelectedItem());
                pst.setDate(4,new java.sql.Date(this.TextBoxNgaySinh.getDate().getTime()));
                pst.setString(5, (String)ComboBoxGioiTinh.getSelectedItem());
                pst.setString(6, TextBoxDiaChi.getText());
                pst.setString(7, this.TextBoxSoDienThoai.getText());
                pst.setString(8, TextBoxEmail.getText());
                pst.setInt(9, Integer.parseInt(TextBoxHeSoLuong.getText()));
                pst.setString(10, TextBoxLuong.getText() +" "+"VND");
                pst.executeUpdate();
                Refresh();
                lblStatus.setText("Thêm Thành Công!");
                Disabled();
                Load(sql);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    private void changeEmployees(){
        int Click = BangNhanVien.getSelectedRow();
        TableModel model = BangNhanVien.getModel(); 
        if(checkNull()) {
            String sqlChange = "UPDATE NhanVien SET MaNhanVien=?, HoTen=?, ChucVu=?, NgaySinh=?, GioiTinh=?, DiaChi=?, SoDienThoai=?, Email=?, HeSoLuong=?, LuongCoBan=? WHERE MaNhanVien='"+model.getValueAt(Click,0).toString().trim()+"'";
            try {
                pst=connect.prepareStatement(sqlChange);
                pst.setString(1, this.TextBoxMaNhanVien.getText());
                pst.setString(2, TextBoxHoTen.getText());
                pst.setString(3, (String)ComboBoxTenChucVu.getSelectedItem());
                pst.setDate(4,new java.sql.Date(this.TextBoxNgaySinh.getDate().getTime()));
                pst.setString(5, (String)ComboBoxGioiTinh.getSelectedItem());
                pst.setString(6, TextBoxDiaChi.getText());
                pst.setString(7, this.TextBoxSoDienThoai.getText());
                pst.setString(8, TextBoxEmail.getText());
                pst.setInt(9, Integer.parseInt(TextBoxHeSoLuong.getText()));
                pst.setString(10, TextBoxLuong.getText() +" "+"VND");
                pst.executeUpdate();
                lblStatus.setText("Lưu Thay Đổi Thành Công!");
                Disabled();
                Refresh();
                Load(sql);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    private boolean Check(){
        boolean kq = true;
        String sqlCheck = "SELECT * FROM NhanVien";
        try{
            pst = connect.prepareStatement(sqlCheck);
            rs = pst.executeQuery();
            while(rs.next()) {
                if(this.TextBoxMaNhanVien.getText().equals(rs.getString("MaNhanVien").toString().trim())){
                    return false;
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
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

        jScrollPane1 = new javax.swing.JScrollPane();
        BangNhanVien = new javax.swing.JTable();
        ButtonTroVe = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        TextBoxMaNhanVien = new javax.swing.JTextField();
        ComboBoxTenChucVu = new javax.swing.JComboBox<>();
        TextBoxHoTen = new javax.swing.JTextField();
        ComboBoxGioiTinh = new javax.swing.JComboBox<>();
        TextBoxSoDienThoai = new javax.swing.JTextField();
        TextBoxDiaChi = new javax.swing.JTextField();
        TextBoxNgaySinh = new com.toedter.calendar.JDateChooser();
        TextBoxEmail = new javax.swing.JTextField();
        btn = new javax.swing.JButton();
        TextBoxLuong = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        TextBoxHeSoLuong = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        ButtonSuaNhanVien = new javax.swing.JButton();
        ButtonLuuNhanVien = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        ButtonXoaNhanVien = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        ButtonThemNhanVien = new javax.swing.JButton();
        ButtonLamMoiNhanVien = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        BangNhanVien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        BangNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        BangNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BangNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(BangNhanVien);

        ButtonTroVe.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        ButtonTroVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Back.png"))); // NOI18N
        ButtonTroVe.setText(" HỆ THỐNG");
        ButtonTroVe.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ButtonTroVe.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ButtonTroVe.setMargin(new java.awt.Insets(0, -9, 3, 0));
        ButtonTroVe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonTroVeMouseClicked(evt);
            }
        });
        ButtonTroVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonTroVeActionPerformed(evt);
            }
        });

        lblStatus.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 0, 0));
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatus.setText("TRẠNG THÁI");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Chức Vụ:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("Mã Nhân Viên:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Họ & Tên:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("Ngày Sinh:");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setText("Giới Tính:");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setText("Địa Chỉ:");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setText("SĐT:");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setText("E–Mail:");

        TextBoxMaNhanVien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        ComboBoxTenChucVu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ComboBoxTenChucVu.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ComboBoxTenChucVuPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        ComboBoxTenChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxTenChucVuActionPerformed(evt);
            }
        });

        TextBoxHoTen.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        ComboBoxGioiTinh.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ComboBoxGioiTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxGioiTinhActionPerformed(evt);
            }
        });

        TextBoxSoDienThoai.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TextBoxSoDienThoai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextBoxSoDienThoaiKeyReleased(evt);
            }
        });

        TextBoxDiaChi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TextBoxDiaChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBoxDiaChiActionPerformed(evt);
            }
        });

        TextBoxNgaySinh.setDateFormatString("dd/MM/yyyy");

        TextBoxEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btn.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btn.setText("....");
        btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActionPerformed(evt);
            }
        });

        TextBoxLuong.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TextBoxLuong.setEnabled(false);
        TextBoxLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBoxLuongActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setText("Lương:");

        TextBoxHeSoLuong.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TextBoxHeSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextBoxHeSoLuongKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel11.setText("Hệ Số Lương:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ComboBoxTenChucVu, 0, 103, Short.MAX_VALUE)
                    .addComponent(TextBoxMaNhanVien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TextBoxNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TextBoxHoTen))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(TextBoxDiaChi)
                        .addGap(24, 24, 24))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ComboBoxGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TextBoxEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(TextBoxSoDienThoai))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextBoxHeSoLuong))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TextBoxLuong)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(ComboBoxGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(TextBoxDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(TextBoxSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)
                                    .addComponent(TextBoxHeSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(TextBoxEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)
                                    .addComponent(TextBoxLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(TextBoxHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(TextBoxNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboBoxTenChucVu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(btn)))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(TextBoxMaNhanVien)
                                .addContainerGap())))))
        );

        ButtonSuaNhanVien.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonSuaNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png"))); // NOI18N
        ButtonSuaNhanVien.setText(" SỬA");
        ButtonSuaNhanVien.setEnabled(false);
        ButtonSuaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSuaNhanVienActionPerformed(evt);
            }
        });

        ButtonLuuNhanVien.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonLuuNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save.png"))); // NOI18N
        ButtonLuuNhanVien.setText("  LƯU");
        ButtonLuuNhanVien.setEnabled(false);
        ButtonLuuNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLuuNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        ButtonXoaNhanVien.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonXoaNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete.png"))); // NOI18N
        ButtonXoaNhanVien.setText(" XÓA");
        ButtonXoaNhanVien.setEnabled(false);
        ButtonXoaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonXoaNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 26, Short.MAX_VALUE)
                .addComponent(ButtonXoaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ButtonXoaNhanVien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        ButtonThemNhanVien.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonThemNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png"))); // NOI18N
        ButtonThemNhanVien.setText("THÊM");
        ButtonThemNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThemNhanVienActionPerformed(evt);
            }
        });

        ButtonLamMoiNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoiNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLamMoiNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ButtonLamMoiNhanVien)
                .addGap(30, 30, 30)
                .addComponent(ButtonThemNhanVien)
                .addGap(27, 27, 27))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ButtonThemNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ButtonLamMoiNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Print Sale.png"))); // NOI18N
        jButton1.setText("IN DANH SÁCH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonSuaNhanVien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(ButtonLuuNhanVien)
                .addGap(31, 31, 31)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonLuuNhanVien, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonSuaNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        jLabel12.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Cửa Hàng Bán Thiết Bị Xe Máy ABC – Motor: Số 97 Đường Man Thiện, Phường Hiệp Phú, TP. Thủ Đức, TP. Hồ Chí Minh");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("CẬP NHẬT NHÂN VIÊN");

        jLabel14.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Điện thoại: 0988919701     –     Email: phisuper2310@gmail.com");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel13)
                                .addGap(15, 15, 15))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ButtonTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(21, 21, 21))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonTroVeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonTroVeMouseClicked
        TrangChuQuanLy home = new TrangChuQuanLy(detail);
        this.setVisible(false);
        home.setVisible(true);
    }//GEN-LAST:event_ButtonTroVeMouseClicked

    private void BangNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BangNhanVienMouseClicked
        ComboBoxGioiTinh.removeAllItems();
        ComboBoxTenChucVu.removeAllItems();
        
        int Click=BangNhanVien.getSelectedRow();
        TableModel model=BangNhanVien.getModel();
        
        TextBoxMaNhanVien.setText(model.getValueAt(Click,0).toString());
        TextBoxHoTen.setText(model.getValueAt(Click,1).toString());
        ComboBoxTenChucVu.addItem(model.getValueAt(Click,2).toString());
        ((JTextField)TextBoxNgaySinh.getDateEditor().getUiComponent()).setText(model.getValueAt(Click,3).toString());
        ComboBoxGioiTinh.addItem(model.getValueAt(Click,4).toString());
        TextBoxDiaChi.setText(model.getValueAt(Click,5).toString());
        TextBoxSoDienThoai.setText(model.getValueAt(Click,6).toString());
        TextBoxEmail.setText(model.getValueAt(Click,7).toString());
        TextBoxHeSoLuong.setText(model.getValueAt(Click,8).toString());
        String []s=model.getValueAt(Click,9).toString().split("\\s");
        TextBoxLuong.setText(s[0]);
        
        ButtonXoaNhanVien.setEnabled(true);
        ButtonSuaNhanVien.setEnabled(true);
    }//GEN-LAST:event_BangNhanVienMouseClicked

    private void ButtonThemNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThemNhanVienActionPerformed
        Refresh();
        Add=true;
        ButtonThemNhanVien.setEnabled(false);
        ButtonLuuNhanVien.setEnabled(true);
        LoadGioiTinh();
        Enabled();
        LoadChucVu();
    }//GEN-LAST:event_ButtonThemNhanVienActionPerformed

    private void ButtonSuaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSuaNhanVienActionPerformed
        Add=false;
        Change=true;
        TextBoxHeSoLuong.setText("");
        TextBoxLuong.setText("");
        ButtonThemNhanVien.setEnabled(false);
        ButtonSuaNhanVien.setEnabled(false);
        ButtonXoaNhanVien.setEnabled(false);
        ButtonLuuNhanVien.setEnabled(true);
        LoadGioiTinh();
        Enabled();
        LoadChucVu();
    }//GEN-LAST:event_ButtonSuaNhanVienActionPerformed

    private void ButtonXoaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonXoaNhanVienActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Xác Nhận Xóa ?", "Thông Báo",2);
        if(Click ==JOptionPane.YES_OPTION){
            String sqlDelete="DELETE FROM NhanVien WHERE MaNhanVien=? AND HoTen=? AND SoDienThoai=? ";//
            try{
                pst=connect.prepareStatement(sqlDelete);
                pst.setString(1, String.valueOf(this.TextBoxMaNhanVien.getText()));
                pst.setString(2, TextBoxHoTen.getText());
                pst.setString(3, TextBoxSoDienThoai.getText());
                pst.executeUpdate();
                
                Disabled();
                Refresh();
                lblStatus.setText("Xóa Nhân Viên Thành Công !");
                Load(sql);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_ButtonXoaNhanVienActionPerformed

    private void ButtonLuuNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLuuNhanVienActionPerformed
        if(Add==true){
            if(Check()){
                ThemNhanVien();
            }
            else lblStatus.setText("Mã Nhân Viên Đã Tồn Tại, Vui Lòng Nhập Lại !");
        }else if(Change==true){
            changeEmployees();
        }
    }//GEN-LAST:event_ButtonLuuNhanVienActionPerformed

    private void ButtonLamMoiNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLamMoiNhanVienActionPerformed
        LoadGioiTinh();
        Refresh();
        Disabled();
        LoadChucVu();
        Load(sql);
    }//GEN-LAST:event_ButtonLamMoiNhanVienActionPerformed

    private void TextBoxHeSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextBoxHeSoLuongKeyReleased
        String sql = "SELECT * FROM ChucVu where TenChucVu=?";
        TextBoxHeSoLuong.setText(cutChar(TextBoxHeSoLuong.getText()));
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        try {
            pst=connect.prepareStatement(sql);
            pst.setString(1, this.ComboBoxTenChucVu.getSelectedItem().toString());
            rs=pst.executeQuery();
            while(rs.next()){
                if(TextBoxHeSoLuong.getText().equals("")){
                    TextBoxLuong.setText("0");
                }
                else{   
                    if(TextBoxHeSoLuong.getText().equals("0")){
                        int Level=Integer.parseInt(TextBoxHeSoLuong.getText());
                        
                        String[]s=rs.getString("Luong").trim().split("\\s");
                        
                        TextBoxLuong.setText(formatter.format((int)(convertedToNumbers(s[0])/2)));
                    }
                    else{
                        int Level=Integer.parseInt(TextBoxHeSoLuong.getText().toString());
                        String[]s=rs.getString("Luong").trim().split("\\s");
                        
                        TextBoxLuong.setText(formatter.format(Level*convertedToNumbers(s[0])));
                    }
                }   
            }
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }
    }//GEN-LAST:event_TextBoxHeSoLuongKeyReleased

    private void ComboBoxTenChucVuPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ComboBoxTenChucVuPopupMenuWillBecomeInvisible
        TextBoxHeSoLuong.setText("");
        TextBoxLuong.setText("");
    }//GEN-LAST:event_ComboBoxTenChucVuPopupMenuWillBecomeInvisible

    private void btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActionPerformed
        ThongTin data = new ThongTin(detail);
        this.setVisible(false);
        data.setVisible(true);
    }//GEN-LAST:event_btnActionPerformed

    private void TextBoxSoDienThoaiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextBoxSoDienThoaiKeyReleased
        TextBoxSoDienThoai.setText(cutChar(TextBoxSoDienThoai.getText()));
    }//GEN-LAST:event_TextBoxSoDienThoaiKeyReleased

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int click = JOptionPane.showConfirmDialog(null,"Thoát Khỏi Chương Trình?","Thông Báo",2);
        if(click == JOptionPane.OK_OPTION){
            System.exit(0);
        }
        else{
            if(click == JOptionPane.CANCEL_OPTION){    
                this.setVisible(true);
            }
        }
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            JasperReport report=JasperCompileManager.compileReport("E:\\QUANLYCUAHANGBANTHIETBIXEMAY\\Quan Ly Cua Hang Mua Ban Thiet Bi Dien Tu\\Quan Ly Cua Hang Mua Ban Thiet Bi Dien Tu\\src\\UserInterFace\\Empployees.jrxml");
            
            JasperPrint print=JasperFillManager.fillReport(report, null, connect);
            
            JasperViewer.viewReport(print,false);
        }
        catch (JRException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ButtonTroVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTroVeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonTroVeActionPerformed

    private void ComboBoxTenChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxTenChucVuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxTenChucVuActionPerformed

    private void TextBoxLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBoxLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBoxLuongActionPerformed

    private void TextBoxDiaChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBoxDiaChiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBoxDiaChiActionPerformed

    private void ComboBoxGioiTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxGioiTinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxGioiTinhActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail = new Detail();
                new NhanVien(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BangNhanVien;
    private javax.swing.JButton ButtonLamMoiNhanVien;
    private javax.swing.JButton ButtonLuuNhanVien;
    private javax.swing.JButton ButtonSuaNhanVien;
    private javax.swing.JButton ButtonThemNhanVien;
    private javax.swing.JButton ButtonTroVe;
    private javax.swing.JButton ButtonXoaNhanVien;
    private javax.swing.JComboBox<String> ComboBoxGioiTinh;
    private javax.swing.JComboBox<String> ComboBoxTenChucVu;
    private javax.swing.JTextField TextBoxDiaChi;
    private javax.swing.JTextField TextBoxEmail;
    private javax.swing.JTextField TextBoxHeSoLuong;
    private javax.swing.JTextField TextBoxHoTen;
    private javax.swing.JTextField TextBoxLuong;
    private javax.swing.JTextField TextBoxMaNhanVien;
    private com.toedter.calendar.JDateChooser TextBoxNgaySinh;
    private javax.swing.JTextField TextBoxSoDienThoai;
    private javax.swing.JButton btn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblStatus;
    // End of variables declaration//GEN-END:variables
}
