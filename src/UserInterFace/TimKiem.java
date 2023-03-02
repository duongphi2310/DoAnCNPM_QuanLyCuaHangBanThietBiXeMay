package UserInterFace;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class TimKiem extends javax.swing.JFrame {
    private Connection connect = null;  
    private PreparedStatement pst = null;  
    private ResultSet rs = null;

    private Detail detail;
    
    private String SQLTaiKhoan    = "SELECT * FROM TaiKhoan";
    private String SQLNhanVien    = "SELECT * FROM NhanVien";
    private String SQLThietBi     = "SELECT * FROM ThietBi";
    private String SQLDonDatHang  = "SELECT * FROM DonDatHang";
    private String SQLChucVu      = "SELECT * FROM ChucVu";
    private String SQLNhaSanXuat  = "SELECT * FROM NhaSanXuat";
    private String SQLLoaiThietBi = "SELECT * FROM LoaiThietBi";
    
    
    public TimKiem(Detail d) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        detail=new Detail(d);
        connectection();
        loadData();
    }
    
    //Kết Nối SQL.
    private void connectection(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection("jdbc:sqlserver://DUONGPHI:1433;databaseName=CuaHangThietBiXeMay;user=sa;password=sa2016;encrypt=false");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadData(){
        LoadTaiKhoan(SQLTaiKhoan);
        LoadNhanVien(SQLNhanVien);
        LoadThietBi(SQLThietBi);
        LoadDonDatHang(SQLDonDatHang);
        LoadNhaSanXuat(SQLNhaSanXuat);
        LoadLoaiThietBi(SQLLoaiThietBi);
        LoadChucVu(SQLChucVu);
    }
    
    // Lấy Dữ Liệu Bảng "TaiKhoan" Và Đưa Vào Bảng Mới.
    private void LoadTaiKhoan(String sql) {
        BangTaiKhoan.removeAll();
        try {
            String [] arr = {"TÊN ĐĂNG NHẬP", "NHÂN VIÊN", "NGÀY TẠO TÀO KHOẢN"};
            DefaultTableModel model = new DefaultTableModel(arr,0);
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                Vector vector = new Vector();
                vector.add(rs.getString("TenDangNhap").trim());
                vector.add(rs.getString("HoTen").trim());
                vector.add(new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("NgayTaoTaiKhoan")));
                model.addRow(vector);
            }
            BangTaiKhoan.setModel(model);
            BangTaiKhoan.setRowHeight(25);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Lấy Dữ Liệu Bảng "NhanVien" Và Đưa Vào Bảng Mới.
    private void LoadNhanVien(String sql) {
        BangNhanVien.removeAll();
        try {
            String [] arr = {"MA NHÂN VIÊN", "HỌ TÊN", "CHỨC VỤ", "NGÀY SINH", "GIỚI TÍNH", "ĐỊA CHỈ", "SỐ ĐIỆN THOẠI", "EMAIL", "HỆ SỐ LƯƠNG", "LƯƠNG"};
            DefaultTableModel model = new DefaultTableModel(arr,0);
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
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
            BangNhanVien.setModel(model);
            BangNhanVien.setRowHeight(25);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    // Lấy Dữ Liệu Bảng "ThietBi" Và Đưa Vào Bảng Mới.
    private void LoadThietBi(String sql) {
        BangThietBi.removeAll();
        try {
            String [] arr = {"MÃ THIẾT BỊ", "THIẾT BỊ", "LOẠI THIẾT BỊ", "NHÀ SẢN XUẤT", "BẢO HÀNH", "SỐ LƯỢNG CÒN", "ĐƠN VỊ", "GIÁ TIỀN"};
            DefaultTableModel model = new DefaultTableModel(arr,0);
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                Vector vector=new Vector();
                vector.add(rs.getString("MaThietBi").trim());
                vector.add(rs.getString("TenThietBi").trim());
                vector.add(rs.getString("TenLoaiThietBi").trim());
                vector.add(rs.getString("TenNhaSanXuat").trim());
                vector.add(rs.getInt("ThoiHanBaoHanh")+" "+rs.getString("DonViThoiGian").trim());
                vector.add(rs.getInt("SoLuongCon"));
                vector.add(rs.getString("DonViTinh").trim());
                vector.add(rs.getString("GiaTien").trim());
                model.addRow(vector);
            }
            BangThietBi.setModel(model);
            BangThietBi.setRowHeight(25);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
     }
    
    // Lấy Dữ Liệu Bảng "DonDatHang" Và Đưa Vào Bảng Mới.
    public void LoadDonDatHang(String sql) {
        BangDonDatHang.removeAll();
        try {
            String [] arr = {"MÃ ĐƠN HÀNG", "KHÁCH HÀNG", "ĐỊA CHỈ", "SỐ ĐIỆN THOẠI", "THIẾT BỊ", "SỐ LƯỢNG", "ĐƠN GIÁ", "BẢO HÀNH", "THÀNH TIỀN", "NGÀY ĐẶT", "THANH TOÁN"};
            DefaultTableModel model = new DefaultTableModel(arr,0);
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                Vector vector = new Vector();
                vector.add(rs.getString("MaDonDatHang").trim());
                vector.add(rs.getString("TenKhachHang").trim());
                vector.add(rs.getString("DiaChi").trim());
                vector.add(rs.getString("SoDienThoai").trim());
                vector.add(rs.getString("TenThietBi").trim());
                vector.add(rs.getInt("SoLuong"));
                vector.add(rs.getString("DonGia").trim());
                vector.add(rs.getString("ThoiHanBaoHanh").trim());
                vector.add(rs.getString("ThanhTien").trim());
                vector.add(new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("NgayMua")));
                vector.add(rs.getString("PhuongThucThanhToan").trim());
                model.addRow(vector);
            }
            BangDonDatHang.setModel(model);
            BangDonDatHang.setRowHeight(25);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Lấy Dữ Liệu Bảng "NhaSanXuat" Và Đưa Vào Bảng Mới.
    private void LoadNhaSanXuat(String sql) {
        BangNhaSanXuat.removeAll();
        try {
            String [] arr = {"MÃ NHÀ SẢN XUẤT", "NHÀ SẢN XUẤT", "ĐỊA CHỈ", "SỐ ĐIỆN THOẠI", "EMAIL"};
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
            BangNhaSanXuat.setModel(model);
            BangNhaSanXuat.setRowHeight(25);
            BangNhaSanXuat.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Lấy Dữ Liệu Bảng "LoaiThietBi" Và Đưa Vào Bảng Mới.
    private void LoadLoaiThietBi(String sql) {
        BangLoaiThietBi.removeAll();
        try {
            String [] arr = {"MÃ LOẠI THIẾT BỊ", "LOẠI THIẾT BỊ"};
            DefaultTableModel model = new DefaultTableModel(arr,0);
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                Vector vector = new Vector();
                vector.add(rs.getString("MaLoaiThietBi").trim());
                vector.add(rs.getString("TenLoaiThietBi").trim());
                model.addRow(vector);
            }
            BangLoaiThietBi.setModel(model);
            BangLoaiThietBi.setRowHeight(25);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Lấy Dữ Liệu Bảng "ChucVu" Và Đưa Vào Bảng Mới.
    private void LoadChucVu(String sql) {
        BangChucVu.removeAll();
        try {
            String [] arr = {"MÃ CHỨC VỤ", "CHỨC VỤ", "LƯƠNG CƠ BẢN"};
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
            BangChucVu.setModel(model);
            BangChucVu.setRowHeight(25);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton8 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        ButtonTroVe = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        TextBoxTimKiemTaiKhoan = new javax.swing.JTextField();
        ButtonLamMoiTaiKhoan = new javax.swing.JButton();
        ButtonTimTaiKhoan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        BangTaiKhoan = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        ButtonLamMoiNhanVien = new javax.swing.JButton();
        ButtonTimNhanVien = new javax.swing.JButton();
        TextBoxTimKiemNhanVien = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        BangNhanVien = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        ButtonLamMoiChucVu = new javax.swing.JButton();
        ButtonTimChucVu = new javax.swing.JButton();
        TextBoxTimKiemChucVu = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        BangChucVu = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        ButtonLamMoiThietBi = new javax.swing.JButton();
        ButtonTimThietBi = new javax.swing.JButton();
        TextBoxTimKiemThietBi = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        BangThietBi = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        ButtonLamMoiLoaiThietBi = new javax.swing.JButton();
        ButonTimLoaiThietBi = new javax.swing.JButton();
        TextBoxTimKiemLoaiThietBi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        BangLoaiThietBi = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        ButtonLamMoiNhaSanXuat = new javax.swing.JButton();
        ButtonTimNhaSanXuat = new javax.swing.JButton();
        TextBoxTimKiemNhaSanXuat = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        BangNhaSanXuat = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        ButtonLamMoiDonDatHang = new javax.swing.JButton();
        ButtonTimDonDatHang = new javax.swing.JButton();
        TextBoxTimKiemDonDatHang = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        BangDonDatHang = new javax.swing.JTable();

        jButton8.setText("jButton8");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 38)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tìm Kiếm");

        ButtonTroVe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        ButtonTroVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Back.png"))); // NOI18N
        ButtonTroVe.setText("Hệ Thống");
        ButtonTroVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonTroVeActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Tìm Kiếm:");

        ButtonLamMoiTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoiTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLamMoiTaiKhoanActionPerformed(evt);
            }
        });

        ButtonTimTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Find.png"))); // NOI18N
        ButtonTimTaiKhoan.setText("Tìm");
        ButtonTimTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonTimTaiKhoanActionPerformed(evt);
            }
        });

        BangTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(BangTaiKhoan);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1055, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextBoxTimKiemTaiKhoan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonTimTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonLamMoiTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TextBoxTimKiemTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonLamMoiTaiKhoan)
                    .addComponent(ButtonTimTaiKhoan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jTabbedPane1.addTab("TÀI KHOẢN", jPanel1);

        ButtonLamMoiNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoiNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLamMoiNhanVienActionPerformed(evt);
            }
        });

        ButtonTimNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Find.png"))); // NOI18N
        ButtonTimNhanVien.setText("Tìm");
        ButtonTimNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonTimNhanVienActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Tìm Kiếm:");

        BangNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(BangNhanVien);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1055, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextBoxTimKiemNhanVien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonTimNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonLamMoiNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonLamMoiNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ButtonTimNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TextBoxTimKiemNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("NHÂN VIÊN", jPanel2);

        ButtonLamMoiChucVu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoiChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLamMoiChucVuActionPerformed(evt);
            }
        });

        ButtonTimChucVu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Find.png"))); // NOI18N
        ButtonTimChucVu.setText("Tìm");
        ButtonTimChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonTimChucVuActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Tìm Kiếm:");

        BangChucVu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(BangChucVu);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1055, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextBoxTimKiemChucVu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonTimChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonLamMoiChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ButtonTimChucVu)
                        .addComponent(TextBoxTimKiemChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addComponent(ButtonLamMoiChucVu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("CHỨC VỤ", jPanel7);

        ButtonLamMoiThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoiThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLamMoiThietBiActionPerformed(evt);
            }
        });

        ButtonTimThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Find.png"))); // NOI18N
        ButtonTimThietBi.setText("Tìm");
        ButtonTimThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonTimThietBiActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Tìm Kiếm:");

        BangThietBi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(BangThietBi);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1055, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextBoxTimKiemThietBi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonTimThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonLamMoiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ButtonTimThietBi)
                        .addComponent(TextBoxTimKiemThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(ButtonLamMoiThietBi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("THIẾT BỊ", jPanel3);

        ButtonLamMoiLoaiThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoiLoaiThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLamMoiLoaiThietBiActionPerformed(evt);
            }
        });

        ButonTimLoaiThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Find.png"))); // NOI18N
        ButonTimLoaiThietBi.setText("Tìm");
        ButonTimLoaiThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButonTimLoaiThietBiActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Tìm Kiếm:");

        BangLoaiThietBi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(BangLoaiThietBi);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1055, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextBoxTimKiemLoaiThietBi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButonTimLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonLamMoiLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonLamMoiLoaiThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ButonTimLoaiThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TextBoxTimKiemLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("LOẠI THIẾT BỊ", jPanel6);

        ButtonLamMoiNhaSanXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoiNhaSanXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLamMoiNhaSanXuatActionPerformed(evt);
            }
        });

        ButtonTimNhaSanXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Find.png"))); // NOI18N
        ButtonTimNhaSanXuat.setText("Tìm");
        ButtonTimNhaSanXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonTimNhaSanXuatActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Tìm Kiếm:");

        BangNhaSanXuat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(BangNhaSanXuat);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1055, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextBoxTimKiemNhaSanXuat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonTimNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonLamMoiNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonLamMoiNhaSanXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ButtonTimNhaSanXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TextBoxTimKiemNhaSanXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("NHÀ SẢN XUẤT", jPanel5);

        ButtonLamMoiDonDatHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoiDonDatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLamMoiDonDatHangActionPerformed(evt);
            }
        });

        ButtonTimDonDatHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Find.png"))); // NOI18N
        ButtonTimDonDatHang.setText("Tìm");
        ButtonTimDonDatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonTimDonDatHangActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Tìm Kiếm:");

        BangDonDatHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(BangDonDatHang);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1055, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextBoxTimKiemDonDatHang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonTimDonDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonLamMoiDonDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ButtonTimDonDatHang)
                        .addComponent(TextBoxTimKiemDonDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(ButtonLamMoiDonDatHang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("ĐƠN ĐẶT HÀNG", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ButtonTroVe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonTroVe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Tìm Tài Khoản.
    private void ButtonTimTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTimTaiKhoanActionPerformed
        String SQLTimTaiKhoan = "SELECT * FROM TaiKhoan WHERE"
                + " TenDangNhap       like N'%" + this.TextBoxTimKiemTaiKhoan.getText() + "%' "
                + "or HoTen           like N'%" + this.TextBoxTimKiemTaiKhoan.getText() + "%' "
                + "or NgayTaoTaiKhoan like N'%" + this.TextBoxTimKiemTaiKhoan.getText() + "%'";
        LoadTaiKhoan(SQLTimTaiKhoan);
        TextBoxTimKiemTaiKhoan.setText("");
    }//GEN-LAST:event_ButtonTimTaiKhoanActionPerformed

    // Làm Mới Tài Khoản.
    private void ButtonLamMoiTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLamMoiTaiKhoanActionPerformed
        LoadTaiKhoan(SQLTaiKhoan);
    }//GEN-LAST:event_ButtonLamMoiTaiKhoanActionPerformed

    // TÌm Thiết Bị.
    private void ButtonTimThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTimThietBiActionPerformed
        String SQLTimThietBi = "SELECT * FROM ThietBi WHERE"
                + " MaThietBi        like N'%" + this.TextBoxTimKiemThietBi.getText() + "%' "
                + "or TenLoaiThietBi like N'%" + this.TextBoxTimKiemThietBi.getText() + "%' "
                + "or TenThietBi     like N'%" + this.TextBoxTimKiemThietBi.getText() + "%' "
                + "or TenNhaSanXuat  like N'%" + this.TextBoxTimKiemThietBi.getText() + "%' "
                + "or ThoiHanBaoHanh like N'%" + this.TextBoxTimKiemThietBi.getText() + "%' "
                + "or DonViThoiGian  like N'%" + this.TextBoxTimKiemThietBi.getText() + "%' "
                + "or SoLuongCon     like N'%" + this.TextBoxTimKiemThietBi.getText() + "%' "
                + "or DonViTinh      like N'%" + this.TextBoxTimKiemThietBi.getText() + "%' "
                + "or GiaTien        like N'%" + this.TextBoxTimKiemThietBi.getText() + "%'";
        LoadThietBi(SQLTimThietBi);
        TextBoxTimKiemThietBi.setText("");
    }//GEN-LAST:event_ButtonTimThietBiActionPerformed

    // Tìm Nhân Viên.
    private void ButtonTimNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTimNhanVienActionPerformed
        String SQLTimNhanVien = "SELECT * FROM NhanVien WHERE"
                + " ChucVu        like N'%" + this.TextBoxTimKiemNhanVien.getText() + "%' "
                + "or MaNhanVien  like N'%" + this.TextBoxTimKiemNhanVien.getText() + "%' "
                + "or HoTen       like N'%" + this.TextBoxTimKiemNhanVien.getText() + "%' "
                + "or NgaySinh    like N'%" + this.TextBoxTimKiemNhanVien.getText() + "%' "
                + "or GioiTinh    like N'%" + this.TextBoxTimKiemNhanVien.getText() + "%' "
                + "or DiaChi      like N'%" + this.TextBoxTimKiemNhanVien.getText() + "%' "
                + "or SoDienThoai like N'%" + this.TextBoxTimKiemNhanVien.getText() + "%' "
                + "or Email       like N'%" + this.TextBoxTimKiemNhanVien.getText() + "%' "
                + "or HeSoLuong   like N'%" + this.TextBoxTimKiemNhanVien.getText() + "' "
                + "or LuongCoBan  like N'%" + this.TextBoxTimKiemNhanVien.getText() + "'";
        LoadNhanVien(SQLTimNhanVien);
        TextBoxTimKiemNhanVien.setText("");
    }//GEN-LAST:event_ButtonTimNhanVienActionPerformed

    // Trở Về Hệ Thống.
    private void ButtonTroVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTroVeActionPerformed
        if(this.detail.getUser().toString().toString().equals("admin")) {
            TrangChuQuanLy home = new TrangChuQuanLy(detail);
            this.setVisible(false);
            home.setVisible(true);
        }
        else {
            HomeUser home=new HomeUser(detail);
            this.setVisible(false);
            home.setVisible(true);
        }
    }//GEN-LAST:event_ButtonTroVeActionPerformed

    // Tìm Nhà Sản Xuất.
    private void ButtonTimNhaSanXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTimNhaSanXuatActionPerformed
        String SQLTimNhaSanXuat = "SELECT * FROM NhaSanXuat WHERE"
                + " MaNhaSanXuat    like N'%" + this.TextBoxTimKiemNhaSanXuat.getText() + "%' "
                + "or TenNhaSanXuat like N'%" + this.TextBoxTimKiemNhaSanXuat.getText() + "%' "
                + "or DiaChi        like N'%" + this.TextBoxTimKiemNhaSanXuat.getText() + "%' "
                + "or SoDienThoai   like N'%" + this.TextBoxTimKiemNhaSanXuat.getText() + "%' "
                + "or Email         like N'%" + this.TextBoxTimKiemNhaSanXuat.getText() + "%'";
        LoadNhaSanXuat(SQLTimNhaSanXuat);
        TextBoxTimKiemNhaSanXuat.setText("");
    }//GEN-LAST:event_ButtonTimNhaSanXuatActionPerformed

    // Tìm Chức Vụ.
    private void ButtonTimChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTimChucVuActionPerformed
        String SQLTimChucVu = "SELECT * FROM ChucVu WHERE"
                + "   MaChucVu  like N'%" + this.TextBoxTimKiemChucVu.getText() + "%' "
                + "or TenChucVu like N'%" + this.TextBoxTimKiemChucVu.getText() + "%' "
                + "or Luong     like N'%" + this.TextBoxTimKiemChucVu.getText() + "%' ";
        LoadChucVu(SQLTimChucVu);
        TextBoxTimKiemChucVu.setText("");
    }//GEN-LAST:event_ButtonTimChucVuActionPerformed

    // Tìm Loại Thiết Bị.
    private void ButonTimLoaiThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButonTimLoaiThietBiActionPerformed
        String SQLTimLoaiThietBi = "SELECT * FROM LoaiThietBi WHERE"
                + "   MaLoaiThietBi  like N'%" + this.TextBoxTimKiemLoaiThietBi.getText() + "%' "
                + "or TenLoaiThietBi like N'%" + this.TextBoxTimKiemLoaiThietBi.getText() + "%'";
        LoadLoaiThietBi(SQLTimLoaiThietBi);
        TextBoxTimKiemLoaiThietBi.setText("");
    }//GEN-LAST:event_ButonTimLoaiThietBiActionPerformed

    // Thoát Chương Trình.
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

    // Làm Mới Nhân Viên.
    private void ButtonLamMoiNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLamMoiNhanVienActionPerformed
        LoadNhanVien(SQLNhanVien);
    }//GEN-LAST:event_ButtonLamMoiNhanVienActionPerformed
    
    // Làm Mới Thiết Bị.
    private void ButtonLamMoiThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLamMoiThietBiActionPerformed
        LoadThietBi(SQLThietBi);
    }//GEN-LAST:event_ButtonLamMoiThietBiActionPerformed

    // Tìm Đơn Đặt Hàng.
    private void ButtonTimDonDatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTimDonDatHangActionPerformed
        String SQLTimDonDatHang = "SELECT * FROM DonDatHang WHERE"
                + "   MaDonDatHang        like N'%" + this.TextBoxTimKiemDonDatHang.getText() + "%' "
                + "or TenKhachHang        like N'%" + this.TextBoxTimKiemDonDatHang.getText() + "%' "
                + "or DiaChi              like N'%" + this.TextBoxTimKiemDonDatHang.getText() + "%' "
                + "or SoDienThoai         like N'%" + this.TextBoxTimKiemDonDatHang.getText() + "%' "
                + "or TenThietBi          like N'%" + this.TextBoxTimKiemDonDatHang.getText() + "%' "
                + "or SoLuong             like N'%" + this.TextBoxTimKiemDonDatHang.getText() + "%' "
                + "or DonGia              like N'%" + this.TextBoxTimKiemDonDatHang.getText() + "%' "
                + "or ThoiHanBaoHanh      like N'%" + this.TextBoxTimKiemDonDatHang.getText() + "%' "
                + "or ThanhTien           like N'%" + this.TextBoxTimKiemDonDatHang.getText() + "%' "
                + "or NgayMua             like N'%" + this.TextBoxTimKiemDonDatHang.getText() + "%' "
                + "or PhuongThucThanhToan like N'%" + this.TextBoxTimKiemDonDatHang.getText() + "%'";
        LoadDonDatHang(SQLTimDonDatHang);
        TextBoxTimKiemDonDatHang.setText("");
    }//GEN-LAST:event_ButtonTimDonDatHangActionPerformed

    private void ButtonLamMoiNhaSanXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLamMoiNhaSanXuatActionPerformed
        LoadNhaSanXuat(SQLNhaSanXuat);
    }//GEN-LAST:event_ButtonLamMoiNhaSanXuatActionPerformed

    private void ButtonLamMoiChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLamMoiChucVuActionPerformed
        LoadChucVu(SQLChucVu);
    }//GEN-LAST:event_ButtonLamMoiChucVuActionPerformed

    private void ButtonLamMoiLoaiThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLamMoiLoaiThietBiActionPerformed
        LoadLoaiThietBi(SQLLoaiThietBi);
    }//GEN-LAST:event_ButtonLamMoiLoaiThietBiActionPerformed

    private void ButtonLamMoiDonDatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLamMoiDonDatHangActionPerformed
        LoadDonDatHang(SQLDonDatHang);
    }//GEN-LAST:event_ButtonLamMoiDonDatHangActionPerformed


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail = new Detail();
                new TimKiem(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BangChucVu;
    private javax.swing.JTable BangDonDatHang;
    private javax.swing.JTable BangLoaiThietBi;
    private javax.swing.JTable BangNhaSanXuat;
    private javax.swing.JTable BangNhanVien;
    private javax.swing.JTable BangTaiKhoan;
    private javax.swing.JTable BangThietBi;
    private javax.swing.JButton ButonTimLoaiThietBi;
    private javax.swing.JButton ButtonLamMoiChucVu;
    private javax.swing.JButton ButtonLamMoiDonDatHang;
    private javax.swing.JButton ButtonLamMoiLoaiThietBi;
    private javax.swing.JButton ButtonLamMoiNhaSanXuat;
    private javax.swing.JButton ButtonLamMoiNhanVien;
    private javax.swing.JButton ButtonLamMoiTaiKhoan;
    private javax.swing.JButton ButtonLamMoiThietBi;
    private javax.swing.JButton ButtonTimChucVu;
    private javax.swing.JButton ButtonTimDonDatHang;
    private javax.swing.JButton ButtonTimNhaSanXuat;
    private javax.swing.JButton ButtonTimNhanVien;
    private javax.swing.JButton ButtonTimTaiKhoan;
    private javax.swing.JButton ButtonTimThietBi;
    private javax.swing.JButton ButtonTroVe;
    private javax.swing.JTextField TextBoxTimKiemChucVu;
    private javax.swing.JTextField TextBoxTimKiemDonDatHang;
    private javax.swing.JTextField TextBoxTimKiemLoaiThietBi;
    private javax.swing.JTextField TextBoxTimKiemNhaSanXuat;
    private javax.swing.JTextField TextBoxTimKiemNhanVien;
    private javax.swing.JTextField TextBoxTimKiemTaiKhoan;
    private javax.swing.JTextField TextBoxTimKiemThietBi;
    private javax.swing.JButton jButton8;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
