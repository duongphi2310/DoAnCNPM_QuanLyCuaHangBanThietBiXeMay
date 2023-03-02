package UserInterFace;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.*;

class BanHang extends javax.swing.JFrame implements Runnable {
    private Connection    connect = null;  
    private PreparedStatement pst = null;  
    private ResultSet          rs = null;
    
    private boolean Add = false, Change = false, Pay = false;
    private String sql = "SELECT * FROM HoaDon";
    
    private Thread thread;
    private Detail detail;

    public BanHang(Detail d) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        detail=new Detail(d);
        LabelTrangThai.setForeground(Color.red);
        setData();
        
        KetNoiSQL();
        Pays();
        Start();
        Load(sql);
        checkBill();
    }

    // Kết Nối SQL.
    private void KetNoiSQL() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect=DriverManager.getConnection("jdbc:sqlserver://DUONGPHI:1433;databaseName=CuaHangThietBiXeMay;user=sa;password=sa2016;encrypt=false");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void setData() {
        Disabled();
        LabelTenNhanVienBanHang.setText(detail.getName());
        LabelNgay1.setText(String.valueOf(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())));
    }
    
    private void Pays(){
        LabelTongTien.setText("0 VND");
        String sqlPay = "SELECT * FROM HoaDon";
        try{
            pst=connect.prepareStatement(sqlPay);
            rs=pst.executeQuery();
            while(rs.next()){
                String []s1=rs.getString("ThanhTien").toString().trim().split("\\s");
                String []s2=LabelTongTien.getText().split("\\s");
                double totalMoney = convertedToNumbers(s1[0])+ convertedToNumbers(s2[0]);
                DecimalFormat formatter = new DecimalFormat("###,###,###");
                LabelTongTien.setText(formatter.format(totalMoney)+" "+s1[1]);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void Start(){
        if(thread==null){
            thread= new Thread(this);
            thread.start();
        }
    }
    
    private void Update(){
        LabelGio1.setText(String.valueOf(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date())));
    }
    
    private void Enabled(){
        ComboBoxLoaiThietBi.setEnabled(true);
    }
    
    private void Disabled(){
        ComboBoxLoaiThietBi.setEnabled(false);
        TextBoxSoLuong.setEnabled(false);
        ComboBoxTenThietBi.setEnabled(false);
    }
    
    private void Refresh(){
        Add=false;
        Change=false;
        Pay=false;
        ComboBoxMaThietBi.setText("");
        TextBoxDonGia.setText("");
        TextBoxSoLuong.setText("");
        TextBoxThanhTien.setText("");
        TextBoxTienNhan.setText("");
        LabelTienThua.setText("0 VND");
        ComboBoxTenThietBi.removeAllItems();
        ComboBoxLoaiThietBi.removeAllItems();
        ButtonSuaThietBi.setEnabled(false);
        ButtonXoaThietBi.setEnabled(false);
        ButtonLuuThietBi.setEnabled(false);
        ButtonInHoaDon.setEnabled(false);
        Disabled();
    }
    
    private void checkBill(){
        if(BangHoaDon.getRowCount()==0){
            LabelTongTien.setText("0 VND");
            TextBoxTienNhan.setText("");
            LabelTienThua.setText("0 VND");
            ButtonThanhToan.setEnabled(false);
            TextBoxTienNhan.setEnabled(false);
        }
        else {
            ButtonThanhToan.setEnabled(true);
            TextBoxTienNhan.setEnabled(true);
        }
    }
    
    private boolean Check() {
        boolean kq=true;
        String sqlCheck="SELECT * FROM HoaDon";
        try{
            PreparedStatement pstCheck=connect.prepareStatement(sqlCheck);
            ResultSet rsCheck=pstCheck.executeQuery();
            while(rsCheck.next()){
                if(this.ComboBoxMaThietBi.getText().equals(rsCheck.getString("MaThietBi").toString().trim())){
                    return false;                                           
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return kq;
    }

    private boolean checkNull(){
        boolean check = true;
        if(String.valueOf(this.ComboBoxMaThietBi.getText()).length() == 0){
            LabelTrangThai.setText("CHƯA CHỌN THIẾT BỊ !");
            return false;
        }
        else if(String.valueOf(this.TextBoxSoLuong.getText()).length() == 0){
                LabelTrangThai.setText("CHƯA NHẬP SỐ LƯỢNG !");
                return false;
        }
        return check;
    }
    
    private void successful(){
        ButtonLuuThietBi.setEnabled(false);
        ButtonThemThietBi.setEnabled(true);
        ButtonHoaDonMoi.setEnabled(true);
        ButtonSuaThietBi.setEnabled(false);
        ButtonXoaThietBi.setEnabled(false);
    }
    
    private void deleteInformation(){
        String sqlDelete="DELETE FROM HoaDon_TT";
            try{
                pst=connect.prepareStatement(sqlDelete);
                pst.executeUpdate();
            }
            catch(Exception ex){
               ex.printStackTrace();
        }
    }
    
    private void addProduct() {
        if(checkNull()){
            String sqlInsert="INSERT INTO HoaDon (MaThietBi, TenThietBi, SoLuong, ThanhTien) VALUES(?,?,?,?)";
            try{
                pst=connect.prepareStatement(sqlInsert);
                pst.setString(1, String.valueOf(ComboBoxMaThietBi.getText()));
                pst.setString(2, String.valueOf(ComboBoxTenThietBi.getSelectedItem()));
                pst.setInt(3, Integer.parseInt(TextBoxSoLuong.getText()));
                pst.setString(4, TextBoxThanhTien.getText());
                pst.executeUpdate();
                LabelTrangThai.setText("THÊM THIẾT BỊ THÀNH CÔNG !");
                Disabled();
                successful();
                Load(sql);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void LoadClassify(){
        String sql = "SELECT * FROM LoaiThietBi";
        try {
            pst=connect.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                this.ComboBoxLoaiThietBi.addItem(rs.getString("TenLoaiThietBi").trim());
            }
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    
    private void changeProduct() {
        int Click=BangHoaDon.getSelectedRow();
        TableModel model=BangHoaDon.getModel();

        String sqlChange="UPDATE HoaDon SET SoLuong = ?, ThanhTien = ? WHERE MaThietBi = '"+model.getValueAt(Click,0).toString().trim()+"'";
        try{
            pst=connect.prepareStatement(sqlChange);
            pst.setInt(1, Integer.parseInt(this.TextBoxSoLuong.getText()));
            pst.setString(2, TextBoxThanhTien.getText());
            pst.executeUpdate();
            Disabled();
            successful();
            LabelTrangThai.setText("LƯU THÀNH CÔNG!");
            Load(sql);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void Load(String sql) {
        BangHoaDon.removeAll();
        try{
            String [] arr = {"MÃ THIẾT BỊ", "TÊN THIẾT BỊ", "SỐ LƯỢNG", "GIÁ TIỀN"};
            DefaultTableModel model = new DefaultTableModel(arr,0);
            pst = connect.prepareStatement(sql);
            rs  = pst.executeQuery();
            while(rs.next()) {
                Vector vector = new Vector();
                vector.add(rs.getString("MaThietBi").trim());
                vector.add(rs.getString("TenThietBi").trim());
                vector.add(rs.getInt("SoLuong"));
                vector.add(rs.getString("ThanhTien").trim());
                model.addRow(vector);
            }
            BangHoaDon.setModel(model);
            BangHoaDon.getColumnModel().getColumn(0).setPreferredWidth(10);
            BangHoaDon.getColumnModel().getColumn(1).setPreferredWidth(400);
            BangHoaDon.getColumnModel().getColumn(2).setPreferredWidth(0);
            BangHoaDon.getColumnModel().getColumn(3).setPreferredWidth(20);
            BangHoaDon.setRowHeight(25);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void consistency(){
        String sqlBill="SELECT * FROM HoaDon";
        try{
            PreparedStatement pstBill=connect.prepareStatement(sqlBill);
            ResultSet rsBill=pstBill.executeQuery();
            
            while(rsBill.next()){
                
                try{
                    String sqlTemp="SELECT * FROM ThietBi WHERE MaThietBi ='"+rsBill.getString("MaThietBi")+"'";
                    PreparedStatement pstTemp=connect.prepareStatement(sqlTemp);
                    ResultSet rsTemp=pstTemp.executeQuery();
                    
                    if(rsTemp.next()){
                        
                        String sqlUpdate="UPDATE ThietBi SET SoLuongCon = ? WHERE MaThietBi = '"+rsBill.getString("MaThietBi").trim()+"'";
                        try{
                            pst=connect.prepareStatement(sqlUpdate);
                            pst.setInt(1, rsTemp.getInt("SoLuongCon")-rsBill.getInt("SoLuong"));
                            pst.executeUpdate();
                        }
                        catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }  
    }
    
    // Kiếm Tra Số Lượng Thiết Bị.
    private void KiemTraSoLuongCon() {
        String sqlCheck = "SELECT SoLuongCon FROM ThietBi WHERE MaThietBi = '" + ComboBoxMaThietBi.getText() + "'";
        try {
            pst = connect.prepareCall(sqlCheck);
            rs  = pst.executeQuery();
            while(rs.next()) {
                if(rs.getInt("SoLuongCon") == 0) {
                    LabelTrangThai.setText("THIẾT BỊ NÀY ĐÃ HẾT HÀNG !");
                    ButtonLuuThietBi.setEnabled(false);
                    TextBoxSoLuong.setEnabled(false);
                }
                else {
                    LabelTrangThai.setText("CÒN" + rs.getInt("SoLuongCon") + " THIẾT BỊ !");
                    ButtonLuuThietBi.setEnabled(true);
                    TextBoxSoLuong.setEnabled(true);
                }
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
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
    
    private void addRevenue(){
        String sqlo = "SELECT MaNhanVien FROM NhanVien WHERE HoTen = ?";
        String sqlPay="INSERT INTO DoanhThu (MaNhanVien, HoTen, NgayBan, GioBan, TongGiaTien, TienNhan, TienThua) VALUES(?,?,?,?,?,?,?)";
        String []s=LabelTongTien.getText().split("\\s");
        try{
            PreparedStatement ps = connect.prepareStatement(sqlo);
            ps.setString(1, LabelTenNhanVienBanHang.getText());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String maNhanVien = rs.getString("MaNhanVien");
                pst=connect.prepareStatement(sqlPay);
                pst.setString(1, maNhanVien);
                pst.setString(2, LabelTenNhanVienBanHang.getText());
                pst.setDate(3,new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(LabelNgay1.getText()).getTime()));
                pst.setString(4, LabelGio1.getText());
                pst.setString(5, LabelTongTien.getText());
                pst.setString(6, TextBoxTienNhan.getText()+" "+s[1]);
                pst.setString(7, LabelTienThua.getText());
                pst.executeUpdate();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private String cutChar(String arry){
        return arry.replaceAll("\\D+","");
    }
    
    private void loadPriceandClassify(String s){
        String sql = "SELECT * FROM ThietBi where MaThietBi=?";
        try {
            pst=connect.prepareStatement(sql);
            pst.setString(1,String.valueOf(s ));
            rs=pst.executeQuery();
            while(rs.next()){
                ComboBoxLoaiThietBi.addItem(rs.getString("TenLoaiThietBi").trim());
                TextBoxDonGia.setText(rs.getString("GiaTien").trim());
            }
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        BangHoaDon = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        LabelLoaiThietBi = new javax.swing.JLabel();
        LabelTenThietBi = new javax.swing.JLabel();
        LabelSoLuong = new javax.swing.JLabel();
        LabelThanhTien = new javax.swing.JLabel();
        ComboBoxLoaiThietBi = new javax.swing.JComboBox<>();
        ComboBoxTenThietBi = new javax.swing.JComboBox<>();
        TextBoxThanhTien = new javax.swing.JTextField();
        TextBoxSoLuong = new javax.swing.JTextField();
        LabelMaThietBi = new javax.swing.JLabel();
        ComboBoxMaThietBi = new javax.swing.JTextField();
        LabelDonGia = new javax.swing.JLabel();
        TextBoxDonGia = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        ButtonThemThietBi = new javax.swing.JButton();
        ButtonXoaThietBi = new javax.swing.JButton();
        ButtonLuuThietBi = new javax.swing.JButton();
        ButtonSuaThietBi = new javax.swing.JButton();
        ButtonHoaDonMoi = new javax.swing.JButton();
        ButtonInHoaDon = new javax.swing.JButton();
        ButtonThanhToan = new javax.swing.JButton();
        ButtonLamMoi = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        LabelTenNhanVienBanHang = new javax.swing.JLabel();
        LabelNgay1 = new javax.swing.JLabel();
        LabelGio1 = new javax.swing.JLabel();
        LabelGio = new javax.swing.JLabel();
        LabelNgay = new javax.swing.JLabel();
        LabelTongTienHoaDon = new javax.swing.JLabel();
        LabelTongTien = new javax.swing.JLabel();
        LabelTienNhan = new javax.swing.JLabel();
        TextBoxTienNhan = new javax.swing.JTextField();
        LabelTienDu = new javax.swing.JLabel();
        LabelTienThua = new javax.swing.JLabel();
        LabelBanHang = new javax.swing.JLabel();
        ButtonTroVe = new javax.swing.JButton();
        LabelTrangThai = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        BangHoaDon.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        BangHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        BangHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BangHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(BangHoaDon);

        LabelLoaiThietBi.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelLoaiThietBi.setText("Loại Thiết Bị:");

        LabelTenThietBi.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelTenThietBi.setText("Tên Thiết Bị:");

        LabelSoLuong.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelSoLuong.setText("Số Lượng:");

        LabelThanhTien.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelThanhTien.setText("Thành Tiền:");

        ComboBoxLoaiThietBi.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ComboBoxLoaiThietBi.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ComboBoxLoaiThietBiPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        ComboBoxTenThietBi.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
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

        TextBoxThanhTien.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TextBoxThanhTien.setEnabled(false);

        TextBoxSoLuong.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TextBoxSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextBoxSoLuongKeyReleased(evt);
            }
        });

        LabelMaThietBi.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelMaThietBi.setText("Mã Thiết Bị:");

        ComboBoxMaThietBi.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ComboBoxMaThietBi.setEnabled(false);

        LabelDonGia.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelDonGia.setText("Đơn Giá:");

        TextBoxDonGia.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        TextBoxDonGia.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LabelLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelMaThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ComboBoxMaThietBi)
                    .addComponent(ComboBoxLoaiThietBi, 0, 170, Short.MAX_VALUE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LabelSoLuong)
                    .addComponent(LabelTenThietBi))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(TextBoxSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(202, 202, 202)
                        .addComponent(LabelThanhTien))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(ComboBoxTenThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelDonGia)))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TextBoxDonGia, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addComponent(TextBoxThanhTien))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboBoxLoaiThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelTenThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextBoxDonGia, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(ComboBoxTenThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(LabelMaThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                        .addComponent(ComboBoxMaThietBi, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(LabelThanhTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TextBoxSoLuong, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LabelSoLuong, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)))
                    .addComponent(TextBoxThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        ButtonThemThietBi.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ButtonThemThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png"))); // NOI18N
        ButtonThemThietBi.setText("THÊM THIẾT BỊ");
        ButtonThemThietBi.setEnabled(false);
        ButtonThemThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThemThietBiActionPerformed(evt);
            }
        });

        ButtonXoaThietBi.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ButtonXoaThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete.png"))); // NOI18N
        ButtonXoaThietBi.setText(" XÓA THIẾT BỊ");
        ButtonXoaThietBi.setEnabled(false);
        ButtonXoaThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonXoaThietBiActionPerformed(evt);
            }
        });

        ButtonLuuThietBi.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ButtonLuuThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save.png"))); // NOI18N
        ButtonLuuThietBi.setText(" LƯU");
        ButtonLuuThietBi.setEnabled(false);
        ButtonLuuThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLuuThietBiActionPerformed(evt);
            }
        });

        ButtonSuaThietBi.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ButtonSuaThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png"))); // NOI18N
        ButtonSuaThietBi.setText(" SỬA THIẾT BỊ");
        ButtonSuaThietBi.setEnabled(false);
        ButtonSuaThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSuaThietBiActionPerformed(evt);
            }
        });

        ButtonHoaDonMoi.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ButtonHoaDonMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/New.png"))); // NOI18N
        ButtonHoaDonMoi.setText(" HÓA ĐƠN MỚI");
        ButtonHoaDonMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonHoaDonMoiActionPerformed(evt);
            }
        });

        ButtonInHoaDon.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ButtonInHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Print Sale.png"))); // NOI18N
        ButtonInHoaDon.setText("XUẤT HÓA ĐƠN");
        ButtonInHoaDon.setEnabled(false);
        ButtonInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonInHoaDonActionPerformed(evt);
            }
        });

        ButtonThanhToan.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ButtonThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Pay.png"))); // NOI18N
        ButtonThanhToan.setText(" THANH TOÁN");
        ButtonThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThanhToanActionPerformed(evt);
            }
        });

        ButtonLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLamMoiActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        LabelTenNhanVienBanHang.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelTenNhanVienBanHang.setText("Tên");

        LabelNgay1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelNgay1.setText("Date");

        LabelGio1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelGio1.setText("Time");

        LabelGio.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelGio.setText("GIỜ:");

        LabelNgay.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        LabelNgay.setText("NGÀY:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelTenNhanVienBanHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(LabelNgay)
                            .addComponent(LabelGio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelGio1, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(LabelNgay1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelTenNhanVienBanHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelNgay1)
                    .addComponent(LabelNgay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelGio1)
                    .addComponent(LabelGio))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonThemThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonLamMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonHoaDonMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonSuaThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonXoaThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonInHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonLuuThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(ButtonSuaThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ButtonHoaDonMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(ButtonThemThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ButtonLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(ButtonXoaThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ButtonThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(ButtonLuuThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ButtonInHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        LabelTongTienHoaDon.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        LabelTongTienHoaDon.setText("Tổng Tiền Hóa Đơn:");

        LabelTongTien.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        LabelTongTien.setText("0 VND");

        LabelTienNhan.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        LabelTienNhan.setText("Tiền Nhận Của Khách Hàng :");

        TextBoxTienNhan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        TextBoxTienNhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextBoxTienNhanKeyReleased(evt);
            }
        });

        LabelTienDu.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        LabelTienDu.setText("Tiền Dư Của Khách:");

        LabelTienThua.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        LabelTienThua.setText("0 VND");

        LabelBanHang.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        LabelBanHang.setForeground(new java.awt.Color(255, 0, 153));
        LabelBanHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelBanHang.setText("BÁN HÀNG");

        ButtonTroVe.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        ButtonTroVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Back.png"))); // NOI18N
        ButtonTroVe.setText("HỆ THỐNG");
        ButtonTroVe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonTroVeMouseClicked(evt);
            }
        });

        LabelTrangThai.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        LabelTrangThai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelTrangThai.setText("TRẠNG THÁI");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LabelTrangThai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LabelTongTienHoaDon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelTienNhan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TextBoxTienNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(LabelTienDu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(ButtonTroVe)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelBanHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelBanHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelTongTienHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextBoxTienNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelTienNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelTienDu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(LabelTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonTroVeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonTroVeMouseClicked
        if(this.detail.getUser().toString().toString().equals("admin")) {
            TrangChuQuanLy trangchu = new TrangChuQuanLy(detail);
            this.setVisible(false);
            trangchu.setVisible(true);
        }
        else {
            HomeUser trangchu = new HomeUser(detail);
            this.setVisible(false);
            trangchu.setVisible(true);
        }
    }//GEN-LAST:event_ButtonTroVeMouseClicked

    private void ButtonThemThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThemThietBiActionPerformed
        Refresh();
        Add = true;
        ButtonThemThietBi.setEnabled(false);
        ButtonLuuThietBi.setEnabled(true);
        Enabled();
        LoadClassify();
    }//GEN-LAST:event_ButtonThemThietBiActionPerformed

    private void ButtonHoaDonMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonHoaDonMoiActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "TẠO HÓA ĐƠN BÁN HÀNG MỚI ?", "Thông Báo",2);
        if(Click ==JOptionPane.YES_OPTION){
            String sqlDelete= "DELETE FROM HoaDon";
            try{
                pst=connect.prepareStatement(sqlDelete);
                pst.executeUpdate();
                this.LabelTrangThai.setText("ĐÃ TẠO HÓA ĐƠN BÁN HÀNG MỚI !");
                Load(sql);
                checkBill();
                Refresh();
                ButtonThemThietBi.setEnabled(true);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_ButtonHoaDonMoiActionPerformed

    private void BangHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BangHoaDonMouseClicked
        ComboBoxLoaiThietBi.removeAllItems();
        ComboBoxTenThietBi.removeAllItems();
        
        int Click=BangHoaDon.getSelectedRow();
        TableModel model=BangHoaDon.getModel();
        
        ComboBoxMaThietBi.setText(model.getValueAt(Click,0).toString());
        ComboBoxTenThietBi.addItem(model.getValueAt(Click,1).toString());
        TextBoxSoLuong.setText(model.getValueAt(Click,2).toString());
        TextBoxThanhTien.setText(model.getValueAt(Click,3).toString());
        
        loadPriceandClassify(model.getValueAt(Click,0).toString());

        ButtonSuaThietBi.setEnabled(true);
        ButtonXoaThietBi.setEnabled(true);
    }//GEN-LAST:event_BangHoaDonMouseClicked

    private void ButtonLuuThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLuuThietBiActionPerformed
        if(Add==true){
            if(Check()){
                addProduct();
            }
            else LabelTrangThai.setText("Sản phẩm đã tồn tại trong hóa đơn");
        }else if(Change==true){
            changeProduct();
        }
        checkBill();
        Pays();
    }//GEN-LAST:event_ButtonLuuThietBiActionPerformed

    private void ComboBoxLoaiThietBiPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ComboBoxLoaiThietBiPopupMenuWillBecomeInvisible
        ComboBoxTenThietBi.removeAllItems();
        String sql = "SELECT * FROM ThietBi where TenLoaiThietBi = ?";
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
            ComboBoxMaThietBi.setText("");
            TextBoxDonGia.setText("");
            TextBoxSoLuong.setText("");
            TextBoxThanhTien.setText("");
        }
        else ComboBoxTenThietBi.setEnabled(true);
    }//GEN-LAST:event_ComboBoxLoaiThietBiPopupMenuWillBecomeInvisible

    private void TextBoxSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextBoxSoLuongKeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        TextBoxSoLuong.setText(cutChar(TextBoxSoLuong.getText()));
        if(TextBoxSoLuong.getText().equals("")) {
            String []s = TextBoxDonGia.getText().split("\\s");
            TextBoxThanhTien.setText("0" + " " + s[1]);
        }
        else { 
            String sqlCheck = "SELECT SoLuongCon FROM ThietBi WHERE MaThietBi = '" + ComboBoxMaThietBi.getText() + "'";
            try {
            pst = connect.prepareStatement(sqlCheck);
            rs  = pst.executeQuery();         
                while(rs.next()) {
                    if((rs.getInt("SoLuongCon") - Integer.parseInt(TextBoxSoLuong.getText())) < 0) {
                        String []s = TextBoxDonGia.getText().split("\\s");
                        TextBoxThanhTien.setText("0"+" "+s[1]);
                        LabelTrangThai.setText("SỐ LƯỢNG NHẬP LỚN HƠN SỐ LƯỢNG HÀNG TRONG KHO !");
                        ButtonLuuThietBi.setEnabled(false);
                    }
                    else {
                        int soluong = Integer.parseInt(TextBoxSoLuong.getText().toString());
                        String []s = TextBoxDonGia.getText().split("\\s");
                        TextBoxThanhTien.setText(formatter.format(convertedToNumbers(s[0]) * soluong) + " " + s[1]);
                        LabelTrangThai.setText("SỐ LƯỢNG HỢP LỆ !");
                        ButtonLuuThietBi.setEnabled(true);
                    }
                }
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_TextBoxSoLuongKeyReleased

    private void ComboBoxTenThietBiPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ComboBoxTenThietBiPopupMenuWillBecomeInvisible
        String sql = "SELECT * FROM ThietBi where TenThietBi = ?";
        try {
            pst = connect.prepareStatement(sql);
            pst.setString(1, this.ComboBoxTenThietBi.getSelectedItem().toString());
            rs = pst.executeQuery();
            while(rs.next()) {
                ComboBoxMaThietBi.setText(rs.getString("MaThietBi").trim());
                TextBoxDonGia.setText(rs.getString("GiaTien").trim());
                TextBoxSoLuong.setEnabled(true);
            }
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }
        KiemTraSoLuongCon();
    }//GEN-LAST:event_ComboBoxTenThietBiPopupMenuWillBecomeInvisible

    private void ButtonSuaThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSuaThietBiActionPerformed
        Add=false;
        Change=true;
        ButtonThemThietBi.setEnabled(false);
        ButtonSuaThietBi.setEnabled(false);
        ButtonXoaThietBi.setEnabled(false);
        ButtonLuuThietBi.setEnabled(true);
        TextBoxSoLuong.setEnabled(true);
    }//GEN-LAST:event_ButtonSuaThietBiActionPerformed

    // Xóa Thiết Bị Khỏi Hóa Đơn.
    private void ButtonXoaThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonXoaThietBiActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "XÓA THIẾT BỊ KHỎI HÓA ĐƠN ?", "Thông Báo",2);
        if(Click == JOptionPane.YES_OPTION) {
            String sqlDelete = "DELETE FROM HoaDon WHERE MaThietBi = ?";
            try {
                pst = connect.prepareStatement(sqlDelete);
                pst.setString(1, String.valueOf(ComboBoxMaThietBi.getText()));
                pst.executeUpdate();
                this.LabelTrangThai.setText("XÓA THIẾT BỊ THÀNH CÔNG !");
                Refresh();
                Load(sql);
                successful();
                checkBill();
                Pays();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_ButtonXoaThietBiActionPerformed

    private void ButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLamMoiActionPerformed
        Refresh();
    }//GEN-LAST:event_ButtonLamMoiActionPerformed

    private void ButtonThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThanhToanActionPerformed
        deleteInformation();
        if(Pay==true){
            String []s=LabelTongTien.getText().split("\\s");
            String sqlPay="INSERT INTO HoaDon_TT (HoTen, NgayBan, GioBan, TongGiaTien, TienNhan, TienThua) VALUES(?,?,?,?,?,?)";
            try{
                pst=connect.prepareStatement(sqlPay);
                pst.setString(1, LabelTenNhanVienBanHang.getText());
                pst.setString(2, LabelNgay1.getText());
                pst.setString(3, LabelGio1.getText());
                pst.setString(4, LabelTongTien.getText());
                pst.setString(5, TextBoxTienNhan.getText()+" "+s[1]);
                pst.setString(6, LabelTienThua.getText());
                pst.executeUpdate();
                LabelTrangThai.setText("Thực hiện thanh toán thành công!");
                addRevenue();
                Disabled();
                successful();
                consistency();
                
                ButtonInHoaDon.setEnabled(true);
                ButtonThemThietBi.setEnabled(false);
                ButtonThanhToan.setEnabled(false);
                TextBoxTienNhan.setEnabled(false);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        else if(Pay==false){
            JOptionPane.showMessageDialog(null, "Bạn cần nhập số tiền khách hàng thanh toán !");
        }
    }//GEN-LAST:event_ButtonThanhToanActionPerformed

    private void TextBoxTienNhanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextBoxTienNhanKeyReleased
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        if(TextBoxTienNhan.getText().equals("")){
            String []s=LabelTongTien.getText().split("\\s");
            LabelTienThua.setText("0"+" "+s[1]);
        }
        else{
            TextBoxTienNhan.setText(formatter.format(convertedToNumbers(TextBoxTienNhan.getText())));
            
            String s1=TextBoxTienNhan.getText();
            String[] s2=LabelTongTien.getText().split("\\s");
            
            if((convertedToNumbers(s1)-convertedToNumbers(s2[0]))>=0){
                LabelTienThua.setText(formatter.format((convertedToNumbers(s1)-convertedToNumbers(s2[0])))+" "+s2[1]);
                LabelTrangThai.setText("Số tiền khách hàng đưa đã hợp lệ!");
                Pay=true;
            }
            else {
                
                LabelTienThua.setText(formatter.format((convertedToNumbers(s1)-convertedToNumbers(s2[0])))+" "+s2[1]);
                LabelTrangThai.setText("Số tiền khách hàng đưa nhỏ hơn tổng tiền mua hàng trong hóa đơn!");
                Pay=false;
            }
        }
    }//GEN-LAST:event_TextBoxTienNhanKeyReleased

    private void ButtonInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonInHoaDonActionPerformed
        
        try {
            JasperReport report=JasperCompileManager.compileReport("C:\\Users\\D.Thanh Trung\\Documents\\NetBeansProjects\\Quan Ly Cua Hang Mua Ban Thiet Bi Dien Tu\\src\\UserInterFace\\Bill.jrxml");
            
            JasperPrint print=JasperFillManager.fillReport(report, null, connect);
            
            JasperViewer.viewReport(print,false);
        }
        catch (JRException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_ButtonInHoaDonActionPerformed

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


    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail=new Detail();
                new BanHang(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BangHoaDon;
    private javax.swing.JButton ButtonHoaDonMoi;
    private javax.swing.JButton ButtonInHoaDon;
    private javax.swing.JButton ButtonLamMoi;
    private javax.swing.JButton ButtonLuuThietBi;
    private javax.swing.JButton ButtonSuaThietBi;
    private javax.swing.JButton ButtonThanhToan;
    private javax.swing.JButton ButtonThemThietBi;
    private javax.swing.JButton ButtonTroVe;
    private javax.swing.JButton ButtonXoaThietBi;
    private javax.swing.JComboBox<String> ComboBoxLoaiThietBi;
    private javax.swing.JTextField ComboBoxMaThietBi;
    private javax.swing.JComboBox<String> ComboBoxTenThietBi;
    private javax.swing.JLabel LabelBanHang;
    private javax.swing.JLabel LabelDonGia;
    private javax.swing.JLabel LabelGio;
    private javax.swing.JLabel LabelGio1;
    private javax.swing.JLabel LabelLoaiThietBi;
    private javax.swing.JLabel LabelMaThietBi;
    private javax.swing.JLabel LabelNgay;
    private javax.swing.JLabel LabelNgay1;
    private javax.swing.JLabel LabelSoLuong;
    private javax.swing.JLabel LabelTenNhanVienBanHang;
    private javax.swing.JLabel LabelTenThietBi;
    private javax.swing.JLabel LabelThanhTien;
    private javax.swing.JLabel LabelTienDu;
    private javax.swing.JLabel LabelTienNhan;
    private javax.swing.JLabel LabelTienThua;
    private javax.swing.JLabel LabelTongTien;
    private javax.swing.JLabel LabelTongTienHoaDon;
    private javax.swing.JLabel LabelTrangThai;
    private javax.swing.JTextField TextBoxDonGia;
    private javax.swing.JTextField TextBoxSoLuong;
    private javax.swing.JTextField TextBoxThanhTien;
    private javax.swing.JTextField TextBoxTienNhan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        while(true){
        Update();  
            try{
                Thread.sleep(1);  
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
