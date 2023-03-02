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

public class DonDatHang extends javax.swing.JFrame {
    private static Connection connect = null; 
    private static PreparedStatement pst = null;  
    private static ResultSet rs = null;
    
    private boolean Add = false, Change = false;
    private String sql = "SELECT * FROM DonDatHang";
    
    private Detail detail;
    
    public DonDatHang(Detail d) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        connection();
        detail = new Detail(d);
        Load(sql);
        Disabled();
        LabelTrangThaiDonDatHang.setForeground(Color.red);
    }
    
    //Kết Nối SQL.
    private void connection(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect=DriverManager.getConnection("jdbc:sqlserver://DUONGPHI:1433;databaseName=CuaHangThietBiXeMay;user=sa;password=sa2016;encrypt=false");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void Enabled() {
        TextBoxMaDonDatHang.setEnabled(true);
        ComboBoxLoaiThietBi.setEnabled(true);
        TextBoxHoTenKhachHang.setEnabled(true);
        TextBoxDiaChi.setEnabled(true);
        TextBoxSoDienThoai.setEnabled(true);
        ComboBoxLoaiThietBi.setEnabled(true);
        TextBoxNgayDat.setEnabled(true);
        ComboBoxPhuongThucThanhToan.setEnabled(true);
        btnProduct.setEnabled(true);
        LabelTrangThaiDonDatHang.setText("TRẠNG THÁI");
    }
    
    private void Disabled() {
        TextBoxMaDonDatHang.setEnabled(false);
        ComboBoxLoaiThietBi.setEnabled(false);
        TextBoxHoTenKhachHang.setEnabled(false);
        TextBoxDiaChi.setEnabled(false);
        TextBoxSoDienThoai.setEnabled(false);
        ComboBoxTenThietBi.setEnabled(false);
        btnProduct.setEnabled(false);
        TextBoxSoLuong.setEnabled(false);
        TextBoxDonGia.setEnabled(false);
        TextBoxThoiHanBaoHanh.setEnabled(false);
        TextBoxThanhTien.setEnabled(false);
        TextBoxNgayDat.setEnabled(false);
        ComboBoxPhuongThucThanhToan.setEnabled(false);
    }
    
    // Đưa Dữ Liệu Từ Bảng "DonDatHang" Vào Bảng.
    public void Load(String sql) {
        BangDonDatHang.removeAll();
        try {
            String [] arr = {"MÃ ĐƠN HÀNG", "KHÁCH HÀNG", "ĐỊA CHỈ", "SỐ ĐIỆN THOẠI", "THIẾT BỊ", "SỐ LƯỢNG", "GIÁ TIỀN", "BẢO HÀNH", "THÀNH TIỀN", "NGÀY ĐẶT", "THANH TOÁN"};
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
    
    // Lấy Dữ Liệu Từ Bảng "LoaiThietBi"
    private void LoadLoaiThietBi(){
        String sql = "SELECT * FROM LoaiThietBi";
        try {
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                this.ComboBoxLoaiThietBi.addItem(rs.getString("TenLoaiThietBi").trim());
            }
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    
    // Lấy Dữ Liệu Truyền Vào Phương Thức Thanh Toán.
    private void LoadPhuongThucThanhToan() {
        ComboBoxPhuongThucThanhToan.removeAllItems();
        ComboBoxPhuongThucThanhToan.addItem("Chuyển Khoản");
        ComboBoxPhuongThucThanhToan.addItem("Tiền Mặt");
        ComboBoxPhuongThucThanhToan.addItem("Ví MOMO");
    }
    
    private void Refresh() {
        Add = false;
        Change = false;
        TextBoxHoTenKhachHang.setText("");
        TextBoxDiaChi.setText("");
        TextBoxSoDienThoai.setText("");
        TextBoxMaDonDatHang.setText("");
        TextBoxSoLuong.setText("");
        TextBoxDonGia.setText("");
        TextBoxThoiHanBaoHanh.setText("");
        TextBoxThanhTien.setText("");
        ((JTextField)TextBoxNgayDat.getDateEditor().getUiComponent()).setText("");
        ComboBoxLoaiThietBi.removeAllItems();
        ComboBoxTenThietBi.removeAllItems();
        ComboBoxPhuongThucThanhToan.removeAllItems();
        ButtonThemDonDatHang.setEnabled(true);
        ButtonXoaDonDatHang.setEnabled(false);
        ButtonSuaDonDatHang.setEnabled(false);
        ButtonLuuDonDatHang.setEnabled(false);
        Disabled();
    }
    
    private String getLatestOrderCode() {
        String latestCode = "";
        try {
            String sql = "SELECT MaDonDatHang FROM DonDatHang ORDER BY MaDonDatHang DESC LIMIT 1";
            pst = connect.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                latestCode = rs.getString("MaDonDatHang");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return latestCode;
    }

    
    // Thêm Đơn Đặt Hàng.
    private void ThemDonDatHang() {
        if(checkNull()) {
            String sqlInsert = "INSERT INTO DonDatHang (MaDonDatHang,TenKhachHang,DiaChi,SoDienThoai,TenThietBi,SoLuong,DonGia,ThoiHanBaoHanh,ThanhTien,NgayMua,PhuongThucThanhToan) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            try {
                pst = connect.prepareStatement(sqlInsert);
                pst.setString(1, this.TextBoxMaDonDatHang.getText());
                pst.setString(2, this.TextBoxHoTenKhachHang.getText());
                pst.setString(3, TextBoxDiaChi.getText());
                pst.setString(4,TextBoxSoDienThoai.getText());
                pst.setString(5, String.valueOf(this.ComboBoxTenThietBi.getSelectedItem()));
                pst.setInt(6, Integer.parseInt(this.TextBoxSoLuong.getText()));
                pst.setString(7, TextBoxDonGia.getText());
                pst.setString(8,this.TextBoxThoiHanBaoHanh.getText());
                pst.setString(9,this.TextBoxThanhTien.getText());
                pst.setDate(10, new java.sql.Date(TextBoxNgayDat.getDate().getTime()));
                pst.setString(11, String.valueOf(this.ComboBoxPhuongThucThanhToan.getSelectedItem()));
                pst.executeUpdate();
                LabelTrangThaiDonDatHang.setText("THÊM ĐƠN ĐẶT HÀNG THÀNH CÔNG !");
                Disabled();
                Refresh();
                Load(sql); 
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Sửa Đơn Đặt Hàng.
    private void SuaDonDatHang() {
        int Click = BangDonDatHang.getSelectedRow();
        TableModel model = BangDonDatHang.getModel();    
        if(checkNull()) {
            String sqlChange = "UPDATE DonDatHang SET MaDonDatHang = ?, TenKhachHang = ?,"
                    + "DiaChi = ?, SoDienThoai = ?, TenThietBi = ?, SoLuong = ?, DonGia = ?,"
                    + "ThoiHanBaoHanh = ?, ThanhTien = ?, NgayMua = ?, PhuongThucThanhToan = ? "
                    + "WHERE MaDonDatHang = '" + model.getValueAt(Click,0).toString().trim() + "'";
            try {
                pst = connect.prepareStatement(sqlChange);
                pst.setString(1, this.TextBoxMaDonDatHang.getText());
                pst.setString(2, this.TextBoxHoTenKhachHang.getText());
                pst.setString(3, TextBoxDiaChi.getText());
                pst.setString(4,TextBoxSoDienThoai.getText());
                pst.setString(5, String.valueOf(this.ComboBoxTenThietBi.getSelectedItem()));
                pst.setInt(6, Integer.parseInt(this.TextBoxSoLuong.getText()));
                pst.setString(7, TextBoxDonGia.getText());
                pst.setString(8,this.TextBoxThoiHanBaoHanh.getText());
                pst.setString(9,this.TextBoxThanhTien.getText());
                pst.setDate(10, new java.sql.Date(TextBoxNgayDat.getDate().getTime()));
                pst.setString(11, String.valueOf(this.ComboBoxPhuongThucThanhToan.getSelectedItem()));
                pst.executeUpdate();
                LabelTrangThaiDonDatHang.setText("LƯU THÀNH CÔNG !");
                Disabled();
                Refresh();
                Load(sql);
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public boolean Check() {
        boolean check = true;
        String sqlCheck = "SELECT * FROM DonDatHang";
        try {
            pst = connect.prepareStatement(sqlCheck);
            rs = pst.executeQuery();
            while(rs.next()) {
                if(this.TextBoxMaDonDatHang.getText().equals(rs.getString("MaDonDatHang").toString().trim())){
                    return false;                                           
                }
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return check;
    }
    
    // Check Điều Kiện.
    public boolean checkNull() {
        boolean check = true;
        if(String.valueOf(this.TextBoxHoTenKhachHang.getText()).length() == 0) {
            LabelTrangThaiDonDatHang.setText("CHƯA NHẬP TÊN KHÁCH HÀNG !");
            return false;
        }
        if(String.valueOf(this.TextBoxDiaChi.getText()).length() == 0) {
            LabelTrangThaiDonDatHang.setText("CHƯA NHẬP ĐỊA CHỈ CỦA KHÁCH HÀNG !");
            return false;
        }
        if(String.valueOf(this.TextBoxSoDienThoai.getText()).length() == 0) {
            LabelTrangThaiDonDatHang.setText("CHƯA NHẬP SỐ ĐIỆN THOẠI CỦA KHÁCH HÀNG !");
            return false;
        }
        if(String.valueOf(this.ComboBoxLoaiThietBi.getSelectedItem()).length() == 0) {
            LabelTrangThaiDonDatHang.setText("CHƯA CHỌN LOẠI THIẾT BỊ !");
            return false;
        }
        if(String.valueOf(this.ComboBoxTenThietBi.getSelectedItem()).length() == 0) {
            LabelTrangThaiDonDatHang.setText("CHƯA CHỌN THIẾT BỊ !");
            return false;
        }
        if(String.valueOf(this.TextBoxSoLuong.getText()).length() == 0) {
            LabelTrangThaiDonDatHang.setText("CHƯA NHẬP SỐ LƯỢNG !");
            return false;
        }
        if(String.valueOf(((JTextField)this.TextBoxNgayDat.getDateEditor().getUiComponent()).getText()).length() == 0) {
            LabelTrangThaiDonDatHang.setText("CHƯA CHỌN NGÀY ĐẶT HÀNG !");
            return false;
        }
        return check;
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
    
    private void LoadData(){
        ComboBoxLoaiThietBi.removeAllItems();
        String sql = "SELECT * FROM ThietBi where TenThietBi = ?";
        try {
            pst=connect.prepareStatement(sql);
            pst.setString(1, this.ComboBoxTenThietBi.getSelectedItem().toString());
            rs=pst.executeQuery();
            while(rs.next()){
                ComboBoxLoaiThietBi.addItem(rs.getString("TenLoaiThietBi").trim());
            }
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        BangDonDatHang = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        LabelTrangThaiDonDatHang = new javax.swing.JLabel();
        ButtonTroVe = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        TextBoxMaDonDatHang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TextBoxHoTenKhachHang = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        TextBoxDiaChi = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TextBoxSoDienThoai = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        ComboBoxLoaiThietBi = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        ComboBoxTenThietBi = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        TextBoxSoLuong = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        TextBoxDonGia = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        TextBoxThoiHanBaoHanh = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        TextBoxNgayDat = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        TextBoxThanhTien = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        ComboBoxPhuongThucThanhToan = new javax.swing.JComboBox<>();
        btnProduct = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        ButtonLamMoiDonDatHang = new javax.swing.JButton();
        ButtonThemDonDatHang = new javax.swing.JButton();
        ButtonSuaDonDatHang = new javax.swing.JButton();
        ButtonInDanhSach = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        ButtonLuuDonDatHang = new javax.swing.JButton();
        ButtonXoaDonDatHang = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        BangDonDatHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        BangDonDatHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BangDonDatHangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(BangDonDatHang);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1096, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 204));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ĐƠN ĐẶT HÀNG");

        LabelTrangThaiDonDatHang.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        LabelTrangThaiDonDatHang.setForeground(new java.awt.Color(255, 0, 0));
        LabelTrangThaiDonDatHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelTrangThaiDonDatHang.setText("TRẠNG THÁI");

        ButtonTroVe.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        ButtonTroVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Back.png"))); // NOI18N
        ButtonTroVe.setText("HỆ THỐNG");
        ButtonTroVe.setMargin(new java.awt.Insets(0, -9, 3, 0));
        ButtonTroVe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonTroVeMouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel15.setText("Mã Đơn Hàng:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel3.setText("Tên Khách Hàng:");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel4.setText("Địa Chỉ:");

        TextBoxDiaChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBoxDiaChiActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel5.setText("Số Điện Thoại:");

        TextBoxSoDienThoai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextBoxSoDienThoaiKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel6.setText("Loại Thiết Bị:");

        ComboBoxLoaiThietBi.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ComboBoxLoaiThietBiPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel7.setText("Tên Thiết Bị:");
        jLabel7.setToolTipText("");

        ComboBoxTenThietBi.setMaximumRowCount(50);
        ComboBoxTenThietBi.setEnabled(false);
        ComboBoxTenThietBi.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ComboBoxTenThietBiPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel9.setText("Số Lượng:");

        TextBoxSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBoxSoLuongActionPerformed(evt);
            }
        });
        TextBoxSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextBoxSoLuongKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel10.setText("Giá:");
        jLabel10.setToolTipText("");

        TextBoxDonGia.setEnabled(false);

        jLabel11.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel11.setText("Bảo Hành:");

        TextBoxThoiHanBaoHanh.setEnabled(false);
        TextBoxThoiHanBaoHanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBoxThoiHanBaoHanhActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel14.setText("Ngày Đặt:");

        TextBoxNgayDat.setDateFormatString("dd/MM/yyyy");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel12.setText("Thành Tiền:");

        TextBoxThanhTien.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel13.setText("Thanh Toán:");

        btnProduct.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnProduct.setText("...");
        btnProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel15)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(TextBoxHoTenKhachHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(TextBoxMaDonDatHang, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TextBoxDiaChi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(ComboBoxLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(ComboBoxTenThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(btnProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TextBoxThoiHanBaoHanh, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(8, 8, 8)
                        .addComponent(TextBoxSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TextBoxDonGia)
                            .addComponent(TextBoxSoLuong))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TextBoxThanhTien)
                    .addComponent(TextBoxNgayDat, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(ComboBoxPhuongThucThanhToan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextBoxSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextBoxMaDonDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextBoxSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextBoxNgayDat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextBoxHoTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextBoxThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboBoxLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(TextBoxDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(TextBoxDiaChi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboBoxPhuongThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboBoxTenThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextBoxThoiHanBaoHanh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ButtonLamMoiDonDatHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoiDonDatHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonLamMoiDonDatHangMouseClicked(evt);
            }
        });

        ButtonThemDonDatHang.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ButtonThemDonDatHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png"))); // NOI18N
        ButtonThemDonDatHang.setText("THÊM");
        ButtonThemDonDatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThemDonDatHangActionPerformed(evt);
            }
        });

        ButtonSuaDonDatHang.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ButtonSuaDonDatHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png"))); // NOI18N
        ButtonSuaDonDatHang.setText(" SỬA");
        ButtonSuaDonDatHang.setEnabled(false);
        ButtonSuaDonDatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSuaDonDatHangActionPerformed(evt);
            }
        });

        ButtonInDanhSach.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ButtonInDanhSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Print Sale.png"))); // NOI18N
        ButtonInDanhSach.setText(" IN DANH SÁCH");
        ButtonInDanhSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonInDanhSachActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        ButtonLuuDonDatHang.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ButtonLuuDonDatHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save.png"))); // NOI18N
        ButtonLuuDonDatHang.setText(" LƯU");
        ButtonLuuDonDatHang.setEnabled(false);
        ButtonLuuDonDatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLuuDonDatHangActionPerformed(evt);
            }
        });

        ButtonXoaDonDatHang.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ButtonXoaDonDatHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete.png"))); // NOI18N
        ButtonXoaDonDatHang.setText(" XÓA");
        ButtonXoaDonDatHang.setEnabled(false);
        ButtonXoaDonDatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonXoaDonDatHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(ButtonLamMoiDonDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(ButtonThemDonDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(ButtonSuaDonDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(ButtonXoaDonDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(ButtonLuuDonDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(124, 124, 124)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ButtonInDanhSach))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonLamMoiDonDatHang, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(ButtonThemDonDatHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                        .addComponent(ButtonSuaDonDatHang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(0, 0, 0)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ButtonXoaDonDatHang, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                .addComponent(ButtonLuuDonDatHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ButtonInDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ButtonTroVe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(LabelTrangThaiDonDatHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ButtonTroVe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelTrangThaiDonDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonTroVeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonTroVeMouseClicked
        if(this.detail.getUser().toString().toString().equals("admin")){
            TrangChuQuanLy home = new TrangChuQuanLy(detail);
            this.setVisible(false);
            home.setVisible(true);
        }
        else{
            HomeUser home = new HomeUser(detail);
            this.setVisible(false);
            home.setVisible(true);
        }
    }//GEN-LAST:event_ButtonTroVeMouseClicked

    private void ComboBoxLoaiThietBiPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ComboBoxLoaiThietBiPopupMenuWillBecomeInvisible
        ComboBoxTenThietBi.removeAllItems();
        String sql = "SELECT * FROM ThietBi where TenLoaiThietBi=?";
        try {
            pst=connect.prepareStatement(sql);
            pst.setString(1, this.ComboBoxLoaiThietBi.getSelectedItem().toString());
            rs=pst.executeQuery();
            while(rs.next()){
                this.ComboBoxTenThietBi.addItem(rs.getString("TenThietBi").trim());
            }
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }
        if(ComboBoxTenThietBi.getItemCount()==0){
            ComboBoxTenThietBi.setEnabled(false);
            TextBoxSoLuong.setEnabled(false);
            TextBoxSoLuong.setText("");
            TextBoxDonGia.setText("");
            TextBoxThoiHanBaoHanh.setText("");
            TextBoxThanhTien.setText("");
        }
        else ComboBoxTenThietBi.setEnabled(true);
    }//GEN-LAST:event_ComboBoxLoaiThietBiPopupMenuWillBecomeInvisible

    private void ComboBoxTenThietBiPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ComboBoxTenThietBiPopupMenuWillBecomeInvisible
        String sql = "SELECT * FROM ThietBi where TenThietBi=?";
        try {
            pst=connect.prepareStatement(sql);
            pst.setString(1, this.ComboBoxTenThietBi.getSelectedItem().toString());
            rs=pst.executeQuery();
            while(rs.next()){
                TextBoxSoLuong.setEnabled(true);
                TextBoxDonGia.setText(rs.getString("GiaTien").trim());
                TextBoxThoiHanBaoHanh.setText(String.valueOf(rs.getInt("ThoiHanBaoHanh"))+" "+rs.getString("DonViThoiGian").trim());
            }
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }
    }//GEN-LAST:event_ComboBoxTenThietBiPopupMenuWillBecomeInvisible

    private void TextBoxSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextBoxSoLuongKeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        TextBoxSoLuong.setText(cutChar(TextBoxSoLuong.getText()));
        if(TextBoxSoLuong.getText().equals("")){
            String []s=TextBoxDonGia.getText().split("\\s");
            TextBoxThanhTien.setText("0"+" "+s[1]);
        }
        else{
            int soluong=Integer.parseInt(TextBoxSoLuong.getText());
            
            String []s=TextBoxDonGia.getText().split("\\s");
            
            TextBoxThanhTien.setText(formatter.format(convertedToNumbers(s[0])*soluong)+" "+s[1]);
        }
    }//GEN-LAST:event_TextBoxSoLuongKeyReleased

    private void BangDonDatHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BangDonDatHangMouseClicked
        int Click=BangDonDatHang.getSelectedRow();
        TableModel model=BangDonDatHang.getModel();
        ComboBoxTenThietBi.removeAllItems();
        ComboBoxPhuongThucThanhToan.removeAllItems();
        ComboBoxLoaiThietBi.removeAllItems();
        TextBoxMaDonDatHang.setText(model.getValueAt(Click,0).toString());
        TextBoxHoTenKhachHang.setText(model.getValueAt(Click,1).toString());
        TextBoxDiaChi.setText(model.getValueAt(Click,2).toString());
        TextBoxSoDienThoai.setText(model.getValueAt(Click,3).toString());
        ComboBoxTenThietBi.addItem(model.getValueAt(Click,4).toString());
        TextBoxSoLuong.setText(model.getValueAt(Click,5).toString());
        TextBoxDonGia.setText(model.getValueAt(Click,6).toString());
        TextBoxThoiHanBaoHanh.setText(model.getValueAt(Click,7).toString());
        TextBoxThanhTien.setText(model.getValueAt(Click,8).toString());
        ((JTextField)TextBoxNgayDat.getDateEditor().getUiComponent()).setText(model.getValueAt(Click,9).toString());
        ComboBoxPhuongThucThanhToan.addItem(model.getValueAt(Click,10).toString());
        
        LoadData();
        
        ButtonSuaDonDatHang.setEnabled(true);
        ButtonXoaDonDatHang.setEnabled(true);
    }//GEN-LAST:event_BangDonDatHangMouseClicked

    private void ButtonThemDonDatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThemDonDatHangActionPerformed
        Refresh();
        Add=true;
        ButtonThemDonDatHang.setEnabled(false);
        ButtonLuuDonDatHang.setEnabled(true);
        Enabled();
        LoadLoaiThietBi();
        LoadPhuongThucThanhToan();
    }//GEN-LAST:event_ButtonThemDonDatHangActionPerformed

    private void ButtonSuaDonDatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSuaDonDatHangActionPerformed
        Change=true;
        Add=false;
        ButtonThemDonDatHang.setEnabled(false);
        ButtonXoaDonDatHang.setEnabled(false);
        ButtonSuaDonDatHang.setEnabled(false);
        ButtonLuuDonDatHang.setEnabled(true);
        Enabled();
        LoadLoaiThietBi();
        LoadPhuongThucThanhToan();
    }//GEN-LAST:event_ButtonSuaDonDatHangActionPerformed

    private void ButtonXoaDonDatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonXoaDonDatHangActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa đơn đặt hàng hay không?", "Thông Báo",2);
        if(Click ==JOptionPane.YES_OPTION){
            String sqlDelete="DELETE FROM DonDatHang WHERE SoDienThoai=? AND TenKhachHang=? AND DiaChi=?";
            try{
                pst=connect.prepareStatement(sqlDelete);
                pst.setString(1, TextBoxSoDienThoai.getText());
                pst.setString(2, this.TextBoxHoTenKhachHang.getText());
                pst.setString(3, TextBoxDiaChi.getText());
                pst.executeUpdate();
                LabelTrangThaiDonDatHang.setText("XÓA ĐƠN ĐẶT HÀNG THÀNH CÔNG !");
                Disabled();
                Refresh();
                Load(sql);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_ButtonXoaDonDatHangActionPerformed

    private void ButtonLuuDonDatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLuuDonDatHangActionPerformed
        if(Add==true){
            if(Check()){
                ThemDonDatHang();
            }
            else LabelTrangThaiDonDatHang.setText("Không thể thêm đơn đặt hàng vì mã đơn đặt hàng bạn nhập đã tồn tại");
        }else if(Change==true){
                SuaDonDatHang();
        }
    }//GEN-LAST:event_ButtonLuuDonDatHangActionPerformed

    private void ButtonLamMoiDonDatHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonLamMoiDonDatHangMouseClicked
        Refresh();
    }//GEN-LAST:event_ButtonLamMoiDonDatHangMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int lick=JOptionPane.showConfirmDialog(null,"Bạn Có Muốn Thoát Khỏi Chương Trình Hay Không?","Thông Báo",2);
        if(lick==JOptionPane.OK_OPTION){
            System.exit(0);
        }
        else{
            if(lick==JOptionPane.CANCEL_OPTION){    
                this.setVisible(true);
            }
        }
    }//GEN-LAST:event_formWindowClosing

    private void TextBoxSoDienThoaiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextBoxSoDienThoaiKeyReleased
        TextBoxSoDienThoai.setText(cutChar(TextBoxSoDienThoai.getText()));
    }//GEN-LAST:event_TextBoxSoDienThoaiKeyReleased

    private void btnProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductActionPerformed
        ThongTin data = new ThongTin(detail);;
        this.setVisible(false);
        data.setVisible(true);
    }//GEN-LAST:event_btnProductActionPerformed

    private void ButtonInDanhSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonInDanhSachActionPerformed
        try {
            JasperReport report=JasperCompileManager.compileReport("E:\\QUANLYCUAHANGBANTHIETBIXEMAY\\QUANLYCUAHANGBANTHIETBIXEMAY\\src\\UserInterFace\\Orders.jrxml");
            
            JasperPrint print=JasperFillManager.fillReport(report, null, connect);
            
            JasperViewer.viewReport(print,false);
        }
        catch (JRException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_ButtonInDanhSachActionPerformed

    private void TextBoxDiaChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBoxDiaChiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBoxDiaChiActionPerformed

    private void TextBoxSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBoxSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBoxSoLuongActionPerformed

    private void TextBoxThoiHanBaoHanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBoxThoiHanBaoHanhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBoxThoiHanBaoHanhActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DonDatHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DonDatHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DonDatHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DonDatHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail=new Detail();
                new DonDatHang(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BangDonDatHang;
    private javax.swing.JButton ButtonInDanhSach;
    private javax.swing.JButton ButtonLamMoiDonDatHang;
    private javax.swing.JButton ButtonLuuDonDatHang;
    private javax.swing.JButton ButtonSuaDonDatHang;
    private javax.swing.JButton ButtonThemDonDatHang;
    private javax.swing.JButton ButtonTroVe;
    private javax.swing.JButton ButtonXoaDonDatHang;
    private javax.swing.JComboBox<String> ComboBoxLoaiThietBi;
    private javax.swing.JComboBox<String> ComboBoxPhuongThucThanhToan;
    private javax.swing.JComboBox<String> ComboBoxTenThietBi;
    private javax.swing.JLabel LabelTrangThaiDonDatHang;
    private javax.swing.JTextField TextBoxDiaChi;
    private javax.swing.JTextField TextBoxDonGia;
    private javax.swing.JTextField TextBoxHoTenKhachHang;
    private javax.swing.JTextField TextBoxMaDonDatHang;
    private com.toedter.calendar.JDateChooser TextBoxNgayDat;
    private javax.swing.JTextField TextBoxSoDienThoai;
    private javax.swing.JTextField TextBoxSoLuong;
    private javax.swing.JTextField TextBoxThanhTien;
    private javax.swing.JTextField TextBoxThoiHanBaoHanh;
    private javax.swing.JButton btnProduct;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
