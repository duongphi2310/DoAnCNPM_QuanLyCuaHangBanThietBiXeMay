package UserInterFace;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class ThietBi extends javax.swing.JFrame {
    private Connection connect = null;  
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    private String sql = "SELECT * FROM ThietBi";
    private boolean Add=false,Change=false;
    
    private Detail detail;
    
    public ThietBi(Detail d) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        connection();
        detail=new Detail(d);
        Disabled();
        Load(sql);
        LabelTrangThai.setForeground(Color.red);
    }
    
    // Kết Nối SQL.
    private void connection(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection("jdbc:sqlserver://DUONGPHI:1433;databaseName=CuaHangThietBiXeMay;user=sa;password=sa2016;encrypt=false");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Tải Dữ Liệu "ThietBi" từ SQL Vào Bảng.
    private void Load(String sql) {
        BangThietBi.removeAll();
        try {
            String [] array = {"MÃ THIẾT BỊ", "TÊN THIẾT BỊ", "LOẠI THIẾT BỊ", "NHÀ SẢN XUẤT", "THỜI HẠN BẢO HÀNH", "SỐ LƯỢNG CÒN", "ĐƠN VỊ", "GIÁ", "HÌNH ẢNH"};
            DefaultTableModel model = new DefaultTableModel(array,0);
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                Vector vector = new Vector();
                vector.add(rs.getString("MaThietBi").trim());
                vector.add(rs.getString("TenThietBi").trim());
                vector.add(rs.getString("TenLoaiThietBi").trim());
                vector.add(rs.getString("TenNhaSanXuat").trim());
                vector.add(rs.getInt("ThoiHanBaoHanh")+" "+rs.getString("DonViThoiGian").trim());
                vector.add(rs.getInt("SoLuongCon"));
                vector.add(rs.getString("DonViTinh").trim());
                vector.add(rs.getString("GiaTien").trim());
                vector.add(rs.getString("HinhAnh").trim());
                model.addRow(vector);
            }
            BangThietBi.setModel(model);
            BangThietBi.getColumnModel().getColumn(0).setPreferredWidth(0);
            BangThietBi.getColumnModel().getColumn(1).setPreferredWidth(300);
            BangThietBi.getColumnModel().getColumn(2).setPreferredWidth(0);
            BangThietBi.getColumnModel().getColumn(3).setPreferredWidth(0);
            BangThietBi.getColumnModel().getColumn(4).setPreferredWidth(0);
            BangThietBi.getColumnModel().getColumn(5).setPreferredWidth(0);
            BangThietBi.getColumnModel().getColumn(6).setPreferredWidth(0);
            BangThietBi.getColumnModel().getColumn(7).setPreferredWidth(0);
            BangThietBi.setRowHeight(25);
            
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
     }
    
    // Tải Dữ Liệu "LoaiThietBi" từ SQL, lấy thông tin của "TenLoaiThietBi"
    public void LoadLoaiThietBi() {
        ComboBoxTenLoaiThietBi.removeAllItems();
        String sql = "SELECT * FROM LoaiThietBi";
        try{
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                this.ComboBoxTenLoaiThietBi.addItem(rs.getString("TenLoaiThietBi").trim());
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Tải Dữ Liệu "NhaSanXuat" từ SQL, lấy thông tin của "TenNhaSanXuat"
    public void LoadNhaSanXuat() {
        ComboBoxTenNhaSanXuat.removeAllItems();
        String sql = "SELECT * FROM NhaSanXuat";
        try {
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                this.ComboBoxTenNhaSanXuat.addItem(rs.getString("TenNhaSanXuat").trim());
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Tải Dữ Liệu "DonViThoiGian" từ SQL.
    private void LoadDonViThoiGian(){
        ComboBoxDonViThoiGian.removeAllItems();
        this.ComboBoxDonViThoiGian.addItem("Ngày");
        this.ComboBoxDonViThoiGian.addItem("Tháng");
        this.ComboBoxDonViThoiGian.addItem("Năm");
    }
    
    private void Enabled(){
        ButtonLoaiThietBi.setEnabled(true);
        ButtonNhaSanXuat.setEnabled(true);
        TextBoxMaThietBi.setEnabled(true);
        TextBoxTenThietBi.setEnabled(true);
        TextBoxThoiHanBaoHanh.setEnabled(true);
        TextBoxSoLuongCon.setEnabled(true);
        TextBoxDonViTinh.setEnabled(true);
        TextBoxGiaTien.setEnabled(true);
        ComboBoxTenLoaiThietBi.setEnabled(true);
        ComboBoxTenNhaSanXuat.setEnabled(true);
        ComboBoxDonViThoiGian.setEnabled(true);
        ButtonThemHinhAnh.setEnabled(true);
        ComboBoxTenLoaiThietBi.setEnabled(true);
        ComboBoxTenNhaSanXuat.setEnabled(true);
        ComboBoxDonViThoiGian.setEnabled(true);
        ButtonThemHinhAnh.setEnabled(true);
        LabelTrangThai.setText("TRẠNG THÁI");
    }
     
    public void Disabled(){
        ButtonLoaiThietBi.setEnabled(false);
        ButtonNhaSanXuat.setEnabled(false);
        TextBoxMaThietBi.setEnabled(false);
        TextBoxTenThietBi.setEnabled(false);
        TextBoxThoiHanBaoHanh.setEnabled(false);
        TextBoxSoLuongCon.setEnabled(false);
        TextBoxDonViTinh.setEnabled(false);
        TextBoxGiaTien.setEnabled(false);
        ComboBoxTenLoaiThietBi.setEnabled(false);
        ComboBoxTenNhaSanXuat.setEnabled(false);
        ComboBoxDonViThoiGian.setEnabled(false);
        ButtonThemHinhAnh.setEnabled(false);
        TextBoxHinhAnh.setEnabled(false);
    }
    
    public void Refresh(){
        TextBoxMaThietBi.setText("");
        TextBoxTenThietBi.setText("");
        TextBoxThoiHanBaoHanh.setText("");
        TextBoxSoLuongCon.setText("");
        TextBoxDonViTinh.setText("");
        TextBoxGiaTien.setText("");
        ButtonThemThietBi.setEnabled(true);
        ButtonSuaThietBi.setEnabled(false);
        ButtonXoaThietBi.setEnabled(false);
        ButtonLuuThietBi.setEnabled(false);
        Add    = false;
        Change = false;
        HinhAnh.setIcon(null);
        TextBoxHinhAnh.setText("");
        Disabled();
    }
    
    // Check Điều Kiện.
    public boolean checkNull() {
        boolean check = true;
        if(String.valueOf(this.TextBoxMaThietBi.getText()).length() == 0) {
            LabelTrangThai.setText("CHƯA NHẬP MÃ THIẾT BỊ !");
            return false;
        }
        if(String.valueOf(this.TextBoxTenThietBi.getText()).length() == 0) {
            LabelTrangThai.setText("CHƯA NHẬP TÊN THIẾT BỊ !");
            return false;
        }
        if(String.valueOf(this.TextBoxThoiHanBaoHanh.getText()).length() == 0){
            LabelTrangThai.setText("CHƯA NHẬP THỜI HẠN BẢO HÀNH !");
            return false;
        }
        if(String.valueOf(this.TextBoxSoLuongCon.getText()).length() == 0) {
            LabelTrangThai.setText("CHƯA NHẬP SỐ LƯỢNG THIẾT BỊ CÒN TRONG KHO !");
            return false;
        }
        if(String.valueOf(this.TextBoxDonViTinh.getText()).length() == 0) {
            LabelTrangThai.setText("CHƯA NHẬP ĐƠN VỊ TÍNH !");
            return false;
        }
        if(String.valueOf(this.TextBoxGiaTien.getText()).length() == 0) {
            LabelTrangThai.setText("CHƯA NHẬP GIÁ THIẾT BỊ !");
            return false;
        }
        if(String.valueOf(this.TextBoxHinhAnh.getText()).length() == 0) {
            LabelTrangThai.setText("CHƯA CÓ ẢNH SẢN PHẨM !");
            return false;
        }
        return check;
    }
    
    public boolean Check() {
        boolean check = true;
        String sqlCheck = "SELECT * FROM ThietBi";
        try {
            PreparedStatement pstCheck = connect.prepareStatement(sqlCheck);
            ResultSet rsCheck = pstCheck.executeQuery();
            while(rsCheck.next()) {
                if(this.TextBoxMaThietBi.getText().equals(rsCheck.getString("MaThietBi").toString().trim())){
                    return false;
                }
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return check;
    }
    
    // Thêm Thiết Bị.
    private void ThemThietBi() {
        if(checkNull()) {
            String sqlInsert = "INSERT INTO ThietBi (MaThietBi, TenThietBi, TenLoaiThietBi, TenNhaSanXuat, ThoiHanBaoHanh, DonViThoiGian, SoLuongCon, DonViTinh, GiaTien, HinhAnh) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                pst = connect.prepareStatement(sqlInsert);
                pst.setString(1, (String)TextBoxMaThietBi.getText());
                pst.setString(2, (String)TextBoxTenThietBi.getText());
                pst.setString(3, String.valueOf(this.ComboBoxTenLoaiThietBi.getSelectedItem()));
                pst.setString(4,String.valueOf(ComboBoxTenNhaSanXuat.getSelectedItem()));
                pst.setInt(5,Integer.parseInt(TextBoxThoiHanBaoHanh.getText()));
                pst.setString(6, String.valueOf(this.ComboBoxDonViThoiGian.getSelectedItem()));
                pst.setInt(7, Integer.parseInt(TextBoxSoLuongCon.getText()));
                pst.setString(8, String.valueOf(this.TextBoxDonViTinh.getText()));
                pst.setString(9, TextBoxGiaTien.getText()+" "+"VND");
                pst.setString(10, String.valueOf(this.TextBoxHinhAnh.getText()));
                pst.executeUpdate();
                LabelTrangThai.setText("THÊM THIẾT BỊ THÀNH CÔNG !");
                Disabled();
                Refresh();
                Load(sql);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    // Sửa Thiết Bị.
    private void SuaThietBi() {
        int Click = BangThietBi.getSelectedRow();
        TableModel model = BangThietBi.getModel();
        if(checkNull()) {
            String sqlChange = "UPDATE ThietBi SET MaThietBi = ?, TenThietBi = ?, TenLoaiThietBi = ?, TenNhaSanXuat = ?, ThoiHanBaoHanh = ?,DonViThoiGian = ?, SoLuongCon = ?, DonViTinh = ?, GiaTIen = ?, HinhAnh = ? WHERE MaThietBi = '" + model.getValueAt(Click,0).toString().trim() + "'";
            try {
                pst = connect.prepareStatement(sqlChange);
                pst.setString(1, TextBoxMaThietBi.getText());
                pst.setString(2, TextBoxTenThietBi.getText());
                pst.setString(3, String.valueOf(ComboBoxTenLoaiThietBi.getSelectedItem()));
                pst.setString(4,String.valueOf(ComboBoxTenNhaSanXuat.getSelectedItem()));
                pst.setInt(5,Integer.parseInt(TextBoxThoiHanBaoHanh.getText()));
                pst.setString(6, String.valueOf(ComboBoxDonViThoiGian.getSelectedItem()));
                pst.setInt(7, Integer.parseInt(TextBoxSoLuongCon.getText()));
                pst.setString(8, TextBoxDonViTinh.getText());
                pst.setString(9, TextBoxGiaTien.getText()+" "+"VND");
                pst.setString(10, TextBoxHinhAnh.getText());
                pst.executeUpdate();
                LabelTrangThai.setText("LƯU THÀNH CÔNG !");
                Disabled();
                Refresh();
                Load(sql);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        BangThietBi = new javax.swing.JTable();
        LabelTrangThai = new javax.swing.JLabel();
        btnBackHome = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        HinhAnh = new javax.swing.JLabel();
        ButtonThemHinhAnh = new javax.swing.JButton();
        TextBoxHinhAnh = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        TextBoxMaThietBi = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ComboBoxTenLoaiThietBi = new javax.swing.JComboBox<>();
        ButtonLoaiThietBi = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        TextBoxTenThietBi = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ComboBoxTenNhaSanXuat = new javax.swing.JComboBox<>();
        ButtonNhaSanXuat = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        TextBoxThoiHanBaoHanh = new javax.swing.JTextField();
        ComboBoxDonViThoiGian = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        TextBoxSoLuongCon = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        TextBoxDonViTinh = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        TextBoxGiaTien = new javax.swing.JTextField();
        LabelHinhAnh = new javax.swing.JLabel();
        LabelCapNhatThietBi = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        ButtonLamMoiThietBi = new javax.swing.JButton();
        ButtonThemThietBi = new javax.swing.JButton();
        ButtonSuaThietBi = new javax.swing.JButton();
        ButtonXoaThietBi = new javax.swing.JButton();
        ButtonLuuThietBi = new javax.swing.JButton();
        ButtonInThietBi = new javax.swing.JButton();
        lblRun1 = new javax.swing.JLabel();
        lblRun2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        BangThietBi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        BangThietBi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Loại linh kiện", "Tên linh kiện", "Nhà sản xuất", "Thời gian bảo hành", "Số lượng còn", "Đơn vị", "Giá"
            }
        ));
        BangThietBi.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        BangThietBi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BangThietBiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(BangThietBi);

        LabelTrangThai.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        LabelTrangThai.setForeground(new java.awt.Color(255, 0, 0));
        LabelTrangThai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelTrangThai.setText("TRẠNG THÁI");
        LabelTrangThai.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnBackHome.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnBackHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Back.png"))); // NOI18N
        btnBackHome.setText(" HỆ THỐNG");
        btnBackHome.setMargin(new java.awt.Insets(2, -5, 2, 0));
        btnBackHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBackHomeMouseClicked(evt);
            }
        });
        btnBackHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackHomeActionPerformed(evt);
            }
        });

        HinhAnh.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        HinhAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        HinhAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ButtonThemHinhAnh.setFont(new java.awt.Font("Arial", 3, 10)); // NOI18N
        ButtonThemHinhAnh.setText("Thêm Hình Ảnh");
        ButtonThemHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonThemHinhAnhMouseClicked(evt);
            }
        });
        ButtonThemHinhAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThemHinhAnhActionPerformed(evt);
            }
        });

        TextBoxHinhAnh.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Mã Thiết Bị:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("Loại Thiết Bị:");

        ButtonLoaiThietBi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonLoaiThietBi.setText("...");
        ButtonLoaiThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLoaiThietBiActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Tên Thiết Bị:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("Nhà Sản Xuất:");

        ButtonNhaSanXuat.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonNhaSanXuat.setText("...");
        ButtonNhaSanXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonNhaSanXuatActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setText("Bảo Hành:");

        TextBoxThoiHanBaoHanh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextBoxThoiHanBaoHanhKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setText("Số Lượng Còn:");

        TextBoxSoLuongCon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextBoxSoLuongConKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setText("Đơn Vị Tính:");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setText("Giá Tiền:");

        TextBoxGiaTien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextBoxGiaTienKeyReleased(evt);
            }
        });

        LabelHinhAnh.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        LabelHinhAnh.setText("Hình Ảnh");

        LabelCapNhatThietBi.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        LabelCapNhatThietBi.setForeground(new java.awt.Color(0, 102, 204));
        LabelCapNhatThietBi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelCapNhatThietBi.setText("CẬP NHẬT THIẾT BỊ");

        ButtonLamMoiThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoiThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLamMoiThietBiActionPerformed(evt);
            }
        });

        ButtonThemThietBi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonThemThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png"))); // NOI18N
        ButtonThemThietBi.setText("THÊM");
        ButtonThemThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThemThietBiActionPerformed(evt);
            }
        });

        ButtonSuaThietBi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonSuaThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png"))); // NOI18N
        ButtonSuaThietBi.setText(" SỬA");
        ButtonSuaThietBi.setEnabled(false);
        ButtonSuaThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSuaThietBiActionPerformed(evt);
            }
        });

        ButtonXoaThietBi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonXoaThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete.png"))); // NOI18N
        ButtonXoaThietBi.setText(" XÓA");
        ButtonXoaThietBi.setEnabled(false);
        ButtonXoaThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonXoaThietBiActionPerformed(evt);
            }
        });

        ButtonLuuThietBi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonLuuThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save.png"))); // NOI18N
        ButtonLuuThietBi.setText(" LƯU");
        ButtonLuuThietBi.setEnabled(false);
        ButtonLuuThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLuuThietBiActionPerformed(evt);
            }
        });

        ButtonInThietBi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonInThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Print Sale.png"))); // NOI18N
        ButtonInThietBi.setText(" IN DANH SÁCH");
        ButtonInThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonInThietBiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(ButtonLamMoiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(ButtonThemThietBi)
                .addGap(40, 40, 40)
                .addComponent(ButtonSuaThietBi)
                .addGap(42, 42, 42)
                .addComponent(ButtonXoaThietBi)
                .addGap(40, 40, 40)
                .addComponent(ButtonLuuThietBi)
                .addGap(42, 42, 42)
                .addComponent(ButtonInThietBi)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(ButtonThemThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ButtonLamMoiThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addComponent(ButtonSuaThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ButtonXoaThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ButtonLuuThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(ButtonInThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(HinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(13, 13, 13)
                                        .addComponent(ComboBoxTenLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ButtonLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ComboBoxTenNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ButtonNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(TextBoxMaThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(TextBoxTenThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(TextBoxThoiHanBaoHanh, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ComboBoxDonViThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(TextBoxSoLuongCon)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(ButtonThemHinhAnh)
                                .addGap(18, 18, 18)
                                .addComponent(TextBoxHinhAnh)))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TextBoxDonViTinh, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                            .addComponent(TextBoxGiaTien)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(LabelHinhAnh))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(130, 130, 130)
                        .addComponent(LabelCapNhatThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(TextBoxTenThietBi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TextBoxThoiHanBaoHanh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextBoxDonViTinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(ComboBoxDonViThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7))
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TextBoxMaThietBi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ComboBoxTenLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextBoxSoLuongCon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ButtonLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ComboBoxTenNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ButtonNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)
                                .addComponent(jLabel8)
                                .addComponent(TextBoxGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextBoxHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ButtonThemHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(HinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelCapNhatThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelHinhAnh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        lblRun1.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        lblRun1.setForeground(new java.awt.Color(255, 0, 0));
        lblRun1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRun1.setText("Cửa Hàng Bán Thiết Bị Xe Máy ABC – Motor: Số 97 Đường Man Thiện, Phường Hiệp Phú, TP. Thủ Đức, TP. Hồ Chí Minh");

        lblRun2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        lblRun2.setForeground(new java.awt.Color(255, 0, 0));
        lblRun2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRun2.setText("Điện thoại: 0988919701     –     Email: phisuper2310@gmail.com");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnBackHome, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(202, 202, 202)
                                        .addComponent(lblRun1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(384, 384, 384)
                                        .addComponent(lblRun2))))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBackHome, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblRun1)
                        .addGap(8, 8, 8)
                        .addComponent(lblRun2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Lấy Giá Trị Từ Table Đưa Ngược Tên TextBox.
    private void BangThietBiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BangThietBiMouseClicked
        int Click = BangThietBi.getSelectedRow();
        TableModel model = BangThietBi.getModel();
        ComboBoxTenNhaSanXuat.removeAllItems();
        ComboBoxTenLoaiThietBi.removeAllItems();
        ComboBoxDonViThoiGian.removeAllItems();
        TextBoxMaThietBi.setText(model.getValueAt(Click,0).toString());
        TextBoxTenThietBi.setText(model.getValueAt(Click,1).toString());
        ComboBoxTenLoaiThietBi.addItem(model.getValueAt(Click,2).toString());
        ComboBoxTenNhaSanXuat.addItem(model.getValueAt(Click,3).toString());
        String []s = model.getValueAt(Click,4).toString().trim().split("\\s");
        TextBoxThoiHanBaoHanh.setText(s[0]);
        ComboBoxDonViThoiGian.addItem(s[1]);
        TextBoxSoLuongCon.setText(model.getValueAt(Click,5).toString());
        TextBoxDonViTinh.setText(model.getValueAt(Click,6).toString());
        String []s1 = model.getValueAt(Click,7).toString().split("\\s");
        TextBoxGiaTien.setText(s1[0]);
        TextBoxHinhAnh.setText(model.getValueAt(Click,8).toString());
        ImageIcon icon = new ImageIcon(model.getValueAt(Click,8).toString());
        HinhAnh.setIcon(icon);
        ButtonSuaThietBi.setEnabled(true);
        ButtonXoaThietBi.setEnabled(true);
    }//GEN-LAST:event_BangThietBiMouseClicked

    private void ButtonThemThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThemThietBiActionPerformed
        Refresh();
        Add = true;
        Enabled();
        LoadLoaiThietBi();
        LoadNhaSanXuat();
        LoadDonViThoiGian();
        ButtonThemThietBi.setEnabled(false);
        ButtonLuuThietBi.setEnabled(true);
    }//GEN-LAST:event_ButtonThemThietBiActionPerformed

    private void ButtonThemHinhAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonThemHinhAnhMouseClicked
        JFileChooser open = new JFileChooser();
        open.setDialogTitle("CHỌN HÌNH ẢNH SẢM PHẨM !");
        int file = open.showOpenDialog(null);
        if(file == JFileChooser.APPROVE_OPTION){
            TextBoxHinhAnh.setText(open.getCurrentDirectory().toString()+"\\"+open.getSelectedFile().getName());
            ImageIcon icon = new ImageIcon(TextBoxHinhAnh.getText());
            HinhAnh.setIcon(icon);
        }
        else LabelTrangThai.setText("CHỌN HÌNH ẢNH SẢM PHẨM !");
    }//GEN-LAST:event_ButtonThemHinhAnhMouseClicked

    private void ButtonSuaThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSuaThietBiActionPerformed
        Add    = false;
        Change = true;
        Enabled();
        LoadLoaiThietBi();
        LoadNhaSanXuat();
        LoadDonViThoiGian();
        ButtonThemThietBi.setEnabled(false);
        ButtonSuaThietBi.setEnabled(false);
        ButtonXoaThietBi.setEnabled(false);
        ButtonLuuThietBi.setEnabled(true);
    }//GEN-LAST:event_ButtonSuaThietBiActionPerformed

    private void ButtonXoaThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonXoaThietBiActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "XÓA THIẾT BỊ ?", "Thông Báo",2);
        if(Click == JOptionPane.YES_OPTION){
            String sqlDelete = "DELETE FROM ThietBi WHERE MaThietBi = ? AND TenThietBi = ?";
            try{
                pst = connect.prepareStatement(sqlDelete);
                pst.setString(1, (String)TextBoxMaThietBi.getText());
                pst.setString(2, TextBoxTenThietBi.getText());
                pst.executeUpdate();
                LabelTrangThai.setText("XÓA THIẾT BỊ THÀNH CÔNG !");
                Disabled();
                Refresh();
                Load(sql);
            }
            catch(Exception ex) {
                    ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_ButtonXoaThietBiActionPerformed

    private void ButtonLuuThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLuuThietBiActionPerformed
        if(Add == true) {
            if(Check()) {
                ThemThietBi();
            }
            else LabelTrangThai.setText("THIẾT BỊ ĐÃ TỒN TẠI !");
        } else if(Change == true){
            SuaThietBi();
        }
    }//GEN-LAST:event_ButtonLuuThietBiActionPerformed

    private void btnBackHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackHomeMouseClicked
        if(this.detail.getUser().toString().toString().equals("admin")) {
            TrangChuQuanLy home = new TrangChuQuanLy(detail);
            this.setVisible(false);
            home.setVisible(true);
        }
        else {
            HomeUser home = new HomeUser(detail);
            this.setVisible(false);
            home.setVisible(true);
        }
    }//GEN-LAST:event_btnBackHomeMouseClicked

    private void ButtonLamMoiThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLamMoiThietBiActionPerformed
        Refresh();
    }//GEN-LAST:event_ButtonLamMoiThietBiActionPerformed

    private void TextBoxGiaTienKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextBoxGiaTienKeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        
        TextBoxGiaTien.setText(cutChar(TextBoxGiaTien.getText()));
        if(TextBoxGiaTien.getText().equals("")){
            return;
        }
        else{
            TextBoxGiaTien.setText(formatter.format(convertedToNumbers(TextBoxGiaTien.getText())));
        }
    }//GEN-LAST:event_TextBoxGiaTienKeyReleased

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int click = JOptionPane.showConfirmDialog(null,"THOÁT CHƯƠNG TRÌNH ?","Thông Báo",2);
        if(click == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
        else {
            if(click == JOptionPane.CANCEL_OPTION) {    
                this.setVisible(true);
            }
        }
    }//GEN-LAST:event_formWindowClosing

    private void TextBoxThoiHanBaoHanhKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextBoxThoiHanBaoHanhKeyReleased
        TextBoxThoiHanBaoHanh.setText(cutChar(TextBoxThoiHanBaoHanh.getText()));
    }//GEN-LAST:event_TextBoxThoiHanBaoHanhKeyReleased

    private void TextBoxSoLuongConKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextBoxSoLuongConKeyReleased
        TextBoxSoLuongCon.setText(cutChar(TextBoxSoLuongCon.getText()));
    }//GEN-LAST:event_TextBoxSoLuongConKeyReleased

    private void ButtonLoaiThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLoaiThietBiActionPerformed
        if(this.detail.getUser().toString().toString().equals("admin")){
            ThongTin data = new ThongTin(detail);
            this.setVisible(false);
            data.setVisible(true);
        }
    }//GEN-LAST:event_ButtonLoaiThietBiActionPerformed

    private void ButtonNhaSanXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNhaSanXuatActionPerformed
        if(this.detail.getUser().toString().toString().equals("admin")){
            ThongTin data = new ThongTin(detail);
            this.setVisible(false);
            data.setVisible(true);
        }
    }//GEN-LAST:event_ButtonNhaSanXuatActionPerformed

    private void ButtonInThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonInThietBiActionPerformed
        try {
            JasperReport report=JasperCompileManager.compileReport("C:\\Users\\D.Thanh Trung\\Documents\\NetBeansProjects\\Quan Ly Cua Hang Mua Ban Thiet Bi Dien Tu\\src\\UserInterFace\\Menu.jrxml");
            
            JasperPrint print=JasperFillManager.fillReport(report, null, connect);
            
            JasperViewer.viewReport(print,false);
        }
        catch (JRException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_ButtonInThietBiActionPerformed

    private void btnBackHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackHomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackHomeActionPerformed

    private void ButtonThemHinhAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThemHinhAnhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonThemHinhAnhActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ThietBi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThietBi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThietBi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThietBi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail=new Detail();
                new ThietBi(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BangThietBi;
    private javax.swing.JButton ButtonInThietBi;
    private javax.swing.JButton ButtonLamMoiThietBi;
    private javax.swing.JButton ButtonLoaiThietBi;
    private javax.swing.JButton ButtonLuuThietBi;
    private javax.swing.JButton ButtonNhaSanXuat;
    private javax.swing.JButton ButtonSuaThietBi;
    private javax.swing.JButton ButtonThemHinhAnh;
    private javax.swing.JButton ButtonThemThietBi;
    private javax.swing.JButton ButtonXoaThietBi;
    private javax.swing.JComboBox<String> ComboBoxDonViThoiGian;
    private javax.swing.JComboBox<String> ComboBoxTenLoaiThietBi;
    private javax.swing.JComboBox<String> ComboBoxTenNhaSanXuat;
    private javax.swing.JLabel HinhAnh;
    private javax.swing.JLabel LabelCapNhatThietBi;
    private javax.swing.JLabel LabelHinhAnh;
    private javax.swing.JLabel LabelTrangThai;
    private javax.swing.JTextField TextBoxDonViTinh;
    private javax.swing.JTextField TextBoxGiaTien;
    private javax.swing.JTextField TextBoxHinhAnh;
    private javax.swing.JTextField TextBoxMaThietBi;
    private javax.swing.JTextField TextBoxSoLuongCon;
    private javax.swing.JTextField TextBoxTenThietBi;
    private javax.swing.JTextField TextBoxThoiHanBaoHanh;
    private javax.swing.JButton btnBackHome;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRun1;
    private javax.swing.JLabel lblRun2;
    // End of variables declaration//GEN-END:variables
}
