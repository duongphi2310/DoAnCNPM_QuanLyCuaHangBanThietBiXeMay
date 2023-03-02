package UserInterFace;

import java.awt.Color;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TaiKhoan extends javax.swing.JFrame {
    private Connection connect = null;  
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    private Boolean Add = false, Change = false;
    private String sql = "SELECT * FROM TaiKhoan";
    
    private Detail detail;

    public TaiKhoan(Detail d) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        detail=new Detail(d);
        KetNoiSQL();
        Load(sql);
        Disabled();
        LoadNhanVien();
        LabelTrangThai.setForeground(Color.red);
    }
    

    private void LoadNhanVien() {
        String sql = "SELECT * FROM NhanVien";
        ComboBoxNhanVien.removeAllItems();
        try {
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()) {
                ComboBoxNhanVien.addItem(rs.getString("HoTen").trim());
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void KetNoiSQL() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection("jdbc:sqlserver://DUONGPHI:1433;databaseName=CuaHangThietBiXeMay;user=sa;password=sa2016;encrypt=false");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void Load(String sql) {
        BangTaiKhoan.removeAll();
        try {
            String [] arr = {"TÊN ĐĂNG NHẬP", "MẬT KHẨU", "TÊN NHÂN VIÊN", "NGÀY TẠO TÀI KHOẢN"};
            DefaultTableModel model = new DefaultTableModel(arr,0);
            pst = connect.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Vector vector=new Vector();
                vector.add(rs.getString("TenDangNhap").trim());
                vector.add(rs.getString("MatKhau").trim());
                vector.add(rs.getString("HoTen").trim());
                vector.add(new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("NgayTaoTaiKhoan")));
                model.addRow(vector);
            }
            BangTaiKhoan.setModel(model);
            BangTaiKhoan.setRowHeight(25);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void Enabled(){
        TextBoxTenDangNhap.setEnabled(true);
        TextBoxMatKhau.setEnabled(true);
        ComboBoxNhanVien.setEnabled(true);
        TextBoxNgayTao.setEnabled(true);
        btn.setEnabled(true);
        LabelTrangThai.setText("TRẠNG THÁI");
    }
    
    private void Disabled(){
        TextBoxTenDangNhap.setEnabled(false);
        TextBoxMatKhau.setEnabled(false);
        btn.setEnabled(false);
        ComboBoxNhanVien.setEnabled(false);
        TextBoxNgayTao.setEnabled(false);
    }
    
    private void Refresh() {
        ComboBoxNhanVien.removeAllItems();
        Add    = false;
        Change = false;
        this.TextBoxTenDangNhap.setText("");
        this.TextBoxMatKhau.setText("");
        LoadNhanVien();
        ((JTextField)this.TextBoxNgayTao.getDateEditor().getUiComponent()).setText("");
        this.ButtonSuaTaiKhoan.setEnabled(false);
        this.ButtonThemTaiKhoan.setEnabled(true);
        this.ButtonLuuTaiKhoan.setEnabled(false);
        this.ButtonXoaTaiKhoan.setEnabled(false);
    }
    
    private void ThemTaiKhoan() {
        String sqlInsert = "INSERT INTO TaiKhoan (TenDangNhap, MatKhau, HoTen, NgayTaoTaiKhoan) VALUES(?, ?, ?, ?)";
        if(checkNull()) {
            try {
                pst = connect.prepareStatement(sqlInsert);
                String hoTen = ComboBoxNhanVien.getSelectedItem().toString();
                String sqlCheck = "SELECT * FROM TaiKhoan WHERE HoTen = ?";
                pst = connect.prepareStatement(sqlCheck);
                pst.setString(1, hoTen);
                rs = pst.executeQuery();
                if (rs.next()) {
                    LabelTrangThai.setText("ĐÃ TỒN TẠI TÀI KHOẢN CHO NHÂN VIÊN NÀY !");
                } else {
                    pst = connect.prepareStatement(sqlInsert);
                    pst.setString(1, this.TextBoxTenDangNhap.getText());
                    pst.setString(2, this.TextBoxMatKhau.getText());
                    pst.setString(3, this.ComboBoxNhanVien.getSelectedItem().toString());
                    pst.setDate(4, new java.sql.Date(TextBoxNgayTao.getDate().getTime()));
                    pst.executeUpdate();
                    Load(sql);
                    Disabled();
                    Refresh();
                    LabelTrangThai.setText("THÊM TÀI KHOẢN THÀNH CÔNG !");
                }
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void changeAccount() {
        int Click = BangTaiKhoan.getSelectedRow();
        TableModel model = BangTaiKhoan.getModel();
        
        String sqlUpdate = "UPDATE TaiKhoan SET TenDangNhap = ?, MatKhau = ?, HoTen = ?, NgayTaoTaiKhoan = ? WHERE TenDangNhap = '" + model.getValueAt(Click,0).toString().trim() + "'";
        
        if(checkNull()) {
            try {
                pst = connect.prepareStatement(sqlUpdate);
                pst.setString(1, this.TextBoxTenDangNhap.getText());
                pst.setString(2, this.TextBoxMatKhau.getText());
                pst.setString(3, this.ComboBoxNhanVien.getSelectedItem().toString());
                pst.setDate(4, new java.sql.Date(TextBoxNgayTao.getDate().getTime()));
                pst.executeUpdate();
                Disabled();
                Refresh();
                LabelTrangThai.setText("LƯU THÀNH CÔNG !");
                Load(sql);
            }
            catch(Exception ex) {
                ex.printStackTrace();  
            }
        }
    }
    
    private boolean Check() {
        boolean check = true;
        String sqlCheck = "SELECT * FROM TaiKhoan";
        try {
            pst = connect.prepareStatement(sqlCheck);
            rs = pst.executeQuery();
            while(rs.next()) {
                if(this.TextBoxTenDangNhap.getText().equals(rs.getString("TenDangNhap").toString().trim())) {
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
    private boolean checkNull() {
        boolean check = true;
        if(this.TextBoxTenDangNhap.getText().equals("")) {
            LabelTrangThai.setText("CHƯA NHẬP TÊN ĐĂNG NHẬP !");
            return false;
        }
        if(this.TextBoxMatKhau.getText().equals("")) {
            LabelTrangThai.setText("CHƯA NHẬP MẬT KHẨU !");
            return false;
        }
        if(((JTextField)TextBoxNgayTao.getDateEditor().getUiComponent()).getText().equals("")) {
            LabelTrangThai.setText("CHƯA CHỌN NGÀY TẠO TÀI KHOẢN !");
            return false;
        }
        return check;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        BangTaiKhoan = new javax.swing.JTable();
        ButtonTroVeTrangChu = new javax.swing.JButton();
        LabelTrangThai = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TextBoxTenDangNhap = new javax.swing.JTextField();
        TextBoxMatKhau = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TextBoxNgayTao = new com.toedter.calendar.JDateChooser();
        ComboBoxNhanVien = new javax.swing.JComboBox<>();
        btn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        ButtonSuaTaiKhoan = new javax.swing.JButton();
        ButtonThemTaiKhoan = new javax.swing.JButton();
        ButtonXoaTaiKhoan = new javax.swing.JButton();
        ButtonLuuTaiKhoan = new javax.swing.JButton();
        ButtonLamMoiTaiKhoan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        BangTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        BangTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BangTaiKhoanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(BangTaiKhoan);

        ButtonTroVeTrangChu.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        ButtonTroVeTrangChu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Back.png"))); // NOI18N
        ButtonTroVeTrangChu.setText(" HỆ THỐNG");
        ButtonTroVeTrangChu.setMargin(new java.awt.Insets(0, -5, 2, 0));
        ButtonTroVeTrangChu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ButtonTroVeTrangChuMouseClicked(evt);
            }
        });
        ButtonTroVeTrangChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonTroVeTrangChuActionPerformed(evt);
            }
        });

        LabelTrangThai.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        LabelTrangThai.setForeground(new java.awt.Color(255, 0, 51));
        LabelTrangThai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelTrangThai.setText("TRẠNG THÁI");
        LabelTrangThai.setFocusable(false);
        LabelTrangThai.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("CẬP NHẬT TÀI KHOẢN");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Tên Đăng Nhập:");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("Mật Khẩu:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Tên Nhân Viên:");

        TextBoxTenDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBoxTenDangNhapActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setText("Ngày Tạo Tài Khoản:");

        TextBoxNgayTao.setDateFormatString("dd/MM/yyyy");

        btn.setText("...");
        btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ComboBoxNhanVien, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(TextBoxTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TextBoxNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TextBoxMatKhau)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TextBoxTenDangNhap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TextBoxMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(ComboBoxNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(TextBoxNgayTao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        ButtonSuaTaiKhoan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonSuaTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Change.png"))); // NOI18N
        ButtonSuaTaiKhoan.setText(" SỬA");
        ButtonSuaTaiKhoan.setEnabled(false);
        ButtonSuaTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSuaTaiKhoanActionPerformed(evt);
            }
        });

        ButtonThemTaiKhoan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonThemTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Add.png"))); // NOI18N
        ButtonThemTaiKhoan.setText("THÊM");
        ButtonThemTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThemTaiKhoanActionPerformed(evt);
            }
        });

        ButtonXoaTaiKhoan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonXoaTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete.png"))); // NOI18N
        ButtonXoaTaiKhoan.setText(" XÓA");
        ButtonXoaTaiKhoan.setEnabled(false);
        ButtonXoaTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonXoaTaiKhoanActionPerformed(evt);
            }
        });

        ButtonLuuTaiKhoan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonLuuTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Save.png"))); // NOI18N
        ButtonLuuTaiKhoan.setText(" LƯU");
        ButtonLuuTaiKhoan.setEnabled(false);
        ButtonLuuTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLuuTaiKhoanActionPerformed(evt);
            }
        });

        ButtonLamMoiTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoiTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLamMoiTaiKhoanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(ButtonLamMoiTaiKhoan)
                .addGap(35, 35, 35)
                .addComponent(ButtonThemTaiKhoan)
                .addGap(35, 35, 35)
                .addComponent(ButtonSuaTaiKhoan)
                .addGap(35, 35, 35)
                .addComponent(ButtonXoaTaiKhoan)
                .addGap(35, 35, 35)
                .addComponent(ButtonLuuTaiKhoan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ButtonLamMoiTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
            .addComponent(ButtonThemTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ButtonSuaTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ButtonXoaTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ButtonLuuTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ButtonTroVeTrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(jLabel6)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(LabelTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButtonTroVeTrangChu)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(LabelTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BangTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BangTaiKhoanMouseClicked
        int Click=BangTaiKhoan.getSelectedRow();
        TableModel model=BangTaiKhoan.getModel();
        
        ComboBoxNhanVien.removeAllItems();
        
        TextBoxTenDangNhap.setText(model.getValueAt(Click,0).toString());
        TextBoxMatKhau.setText(model.getValueAt(Click,1).toString());
        ComboBoxNhanVien.addItem(model.getValueAt(Click,2).toString());
        ((JTextField)TextBoxNgayTao.getDateEditor().getUiComponent()).setText(model.getValueAt(Click,3).toString());
        
        ButtonXoaTaiKhoan.setEnabled(true);
        ButtonSuaTaiKhoan.setEnabled(true);
    }//GEN-LAST:event_BangTaiKhoanMouseClicked

    private void ButtonTroVeTrangChuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ButtonTroVeTrangChuMouseClicked
        TrangChuQuanLy login = new TrangChuQuanLy(detail);
        this.setVisible(false);
        login.setVisible(true);
    }//GEN-LAST:event_ButtonTroVeTrangChuMouseClicked

    private void ButtonThemTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThemTaiKhoanActionPerformed
        Refresh();
        Add=true;
        Enabled();
        ButtonThemTaiKhoan.setEnabled(false);
        ButtonLuuTaiKhoan.setEnabled(true);
    }//GEN-LAST:event_ButtonThemTaiKhoanActionPerformed

    private void ButtonSuaTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSuaTaiKhoanActionPerformed
        int Click=BangTaiKhoan.getSelectedRow();
        TableModel model=BangTaiKhoan.getModel();
        
        Add=false;
        Change=true;
        Enabled();
        LoadNhanVien();
        
        if(model.getValueAt(Click,0).toString().trim().equals("admin")){
            TextBoxTenDangNhap.setEnabled(false);
        }
        ButtonSuaTaiKhoan.setEnabled(false);
        ButtonXoaTaiKhoan.setEnabled(false);
        ButtonThemTaiKhoan.setEnabled(false);
        ButtonLuuTaiKhoan.setEnabled(true);
    }//GEN-LAST:event_ButtonSuaTaiKhoanActionPerformed

    private void ButtonLamMoiTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLamMoiTaiKhoanActionPerformed
        Refresh();
        Disabled();
        Load(sql);
    }//GEN-LAST:event_ButtonLamMoiTaiKhoanActionPerformed

    private void ButtonLuuTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLuuTaiKhoanActionPerformed
        if(Add == true) {
            if(Check()) {
                ThemTaiKhoan();
            }
            else
                LabelTrangThai.setText("TÊN ĐĂNG NHÂPK ĐÃ TỒN TẠI !");
        }
        else if(Change == true) {
            changeAccount();
        }
    }//GEN-LAST:event_ButtonLuuTaiKhoanActionPerformed

    private void ButtonXoaTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonXoaTaiKhoanActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "XÓA TÀI KHOẢN ?", "Thông Báo",2);
        if(Click == JOptionPane.YES_OPTION){
            if(this.TextBoxTenDangNhap.getText().equals("admin")) {
                this.LabelTrangThai.setText("Không thể xóa tài khoản của Admin");
            }
            else {
                String sqlDelete = "DELETE FROM TaiKhoan WHERE TenDangNhap = ? AND MatKhau = ? AND HoTen = ? AND NgayTaoTaiKhoan = ?";
                try{
                    pst=connect.prepareStatement(sqlDelete);
                    pst.setString(1, this.TextBoxTenDangNhap.getText());
                    pst.setString(2, this.TextBoxMatKhau.getText());
                    pst.setString(3, this.ComboBoxNhanVien.getSelectedItem().toString());
                    pst.setDate(4, new java.sql.Date(TextBoxNgayTao.getDate().getTime()));
                    pst.executeUpdate();
                    this.LabelTrangThai.setText("XÓA TÀI KHOẢN THÀNH CÔNG !");
                    Load(sql);
                    Refresh();
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_ButtonXoaTaiKhoanActionPerformed

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

    private void btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActionPerformed
        NhanVien account = new NhanVien(detail);
        this.setVisible(false);
        account.setVisible(true);
    }//GEN-LAST:event_btnActionPerformed

    private void ButtonTroVeTrangChuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTroVeTrangChuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButtonTroVeTrangChuActionPerformed

    private void TextBoxTenDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBoxTenDangNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBoxTenDangNhapActionPerformed

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
                new TaiKhoan(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BangTaiKhoan;
    private javax.swing.JButton ButtonLamMoiTaiKhoan;
    private javax.swing.JButton ButtonLuuTaiKhoan;
    private javax.swing.JButton ButtonSuaTaiKhoan;
    private javax.swing.JButton ButtonThemTaiKhoan;
    private javax.swing.JButton ButtonTroVeTrangChu;
    private javax.swing.JButton ButtonXoaTaiKhoan;
    private javax.swing.JComboBox<String> ComboBoxNhanVien;
    private javax.swing.JLabel LabelTrangThai;
    private javax.swing.JTextField TextBoxMatKhau;
    private com.toedter.calendar.JDateChooser TextBoxNgayTao;
    private javax.swing.JTextField TextBoxTenDangNhap;
    private javax.swing.JButton btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
