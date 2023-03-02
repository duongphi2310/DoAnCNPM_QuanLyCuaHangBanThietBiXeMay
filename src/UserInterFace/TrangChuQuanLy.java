package UserInterFace;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TrangChuQuanLy extends javax.swing.JFrame implements Runnable {
    private Detail detail;
    private Thread thread;
    
    public TrangChuQuanLy(Detail d) {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        LabelTenPhanMem.setForeground(Color.RED);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        detail=new Detail(d);
    }

    private void Start(){
        if(thread==null){
            thread= new Thread(this);
            thread.start();
        }
    }
    
    private void Update(){
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel(){
            ImageIcon icon = new ImageIcon("src/Image/Background.png");
            public void paintComponent(Graphics g){

                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, this);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        LabelTenPhanMem = new javax.swing.JLabel();
        ButtonNhanVien = new javax.swing.JButton();
        ButtonThongKe = new javax.swing.JButton();
        ButtonThietBi = new javax.swing.JButton();
        ButtonThongTin = new javax.swing.JButton();
        ButtonTimKiem = new javax.swing.JButton();
        ButtonTaiKhoan = new javax.swing.JButton();
        ButtonDonDatHang = new javax.swing.JButton();
        ButtonDangXuat = new javax.swing.JButton();
        ButtonBanHang = new javax.swing.JButton();
        LabelTenPhanMem1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Giao Diện Hệ Thống");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        LabelTenPhanMem.setFont(new java.awt.Font("Arial", 1, 32)); // NOI18N
        LabelTenPhanMem.setForeground(new java.awt.Color(255, 0, 0));
        LabelTenPhanMem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelTenPhanMem.setText("– PHẦN MỀM QUẢN LÝ CỬA HÀNG BÁN THIẾT BỊ XE MÁY –");

        ButtonNhanVien.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonNhanVien.setForeground(new java.awt.Color(102, 0, 102));
        ButtonNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Account.png"))); // NOI18N
        ButtonNhanVien.setText("NHÂN VIÊN");
        ButtonNhanVien.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ButtonNhanVien.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        ButtonNhanVien.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ButtonNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonNhanVienActionPerformed(evt);
            }
        });

        ButtonThongKe.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonThongKe.setForeground(new java.awt.Color(204, 0, 102));
        ButtonThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Payroll.png"))); // NOI18N
        ButtonThongKe.setText("THỐNG KÊ");
        ButtonThongKe.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ButtonThongKe.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        ButtonThongKe.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ButtonThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThongKeActionPerformed(evt);
            }
        });

        ButtonThietBi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonThietBi.setForeground(new java.awt.Color(0, 153, 102));
        ButtonThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Product.png"))); // NOI18N
        ButtonThietBi.setText("THIẾT BỊ");
        ButtonThietBi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ButtonThietBi.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        ButtonThietBi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ButtonThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThietBiActionPerformed(evt);
            }
        });

        ButtonThongTin.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonThongTin.setForeground(new java.awt.Color(255, 0, 0));
        ButtonThongTin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Home_Data.png"))); // NOI18N
        ButtonThongTin.setText("THÔNG TIN");
        ButtonThongTin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ButtonThongTin.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        ButtonThongTin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ButtonThongTin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThongTinActionPerformed(evt);
            }
        });

        ButtonTimKiem.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonTimKiem.setForeground(new java.awt.Color(102, 204, 0));
        ButtonTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Search.png"))); // NOI18N
        ButtonTimKiem.setText("TÌM KIẾM");
        ButtonTimKiem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ButtonTimKiem.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        ButtonTimKiem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ButtonTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonTimKiemActionPerformed(evt);
            }
        });

        ButtonTaiKhoan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonTaiKhoan.setForeground(new java.awt.Color(0, 51, 204));
        ButtonTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Home_Account.png"))); // NOI18N
        ButtonTaiKhoan.setText("TÀI KHOẢN");
        ButtonTaiKhoan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ButtonTaiKhoan.setPreferredSize(new java.awt.Dimension(139, 139));
        ButtonTaiKhoan.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        ButtonTaiKhoan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ButtonTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonTaiKhoanActionPerformed(evt);
            }
        });

        ButtonDonDatHang.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonDonDatHang.setForeground(new java.awt.Color(255, 153, 0));
        ButtonDonDatHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Cart.png"))); // NOI18N
        ButtonDonDatHang.setText("ĐƠN ĐẶT HÀNG");
        ButtonDonDatHang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ButtonDonDatHang.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        ButtonDonDatHang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ButtonDonDatHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDonDatHangActionPerformed(evt);
            }
        });

        ButtonDangXuat.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonDangXuat.setForeground(new java.awt.Color(0, 204, 102));
        ButtonDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/LogOut.png"))); // NOI18N
        ButtonDangXuat.setText("ĐĂNG XUẤT");
        ButtonDangXuat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ButtonDangXuat.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        ButtonDangXuat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ButtonDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDangXuatActionPerformed(evt);
            }
        });

        ButtonBanHang.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonBanHang.setForeground(new java.awt.Color(0, 153, 204));
        ButtonBanHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Sale.png"))); // NOI18N
        ButtonBanHang.setText("BÁN HÀNG");
        ButtonBanHang.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ButtonBanHang.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        ButtonBanHang.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ButtonBanHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonBanHangActionPerformed(evt);
            }
        });

        LabelTenPhanMem1.setFont(new java.awt.Font("Monotype Corsiva", 1, 36)); // NOI18N
        LabelTenPhanMem1.setForeground(new java.awt.Color(255, 102, 102));
        LabelTenPhanMem1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelTenPhanMem1.setText("  ~ WELCOME ~");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LabelTenPhanMem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LabelTenPhanMem1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(ButtonTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(ButtonNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(ButtonThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(ButtonThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(ButtonDonDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(ButtonThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(ButtonTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(ButtonBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(ButtonDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelTenPhanMem, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(ButtonThietBi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonDonDatHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonTaiKhoan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(ButtonThongTin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(LabelTenPhanMem1)
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void ButtonDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDangXuatActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất tài khoản khỏi hệ thống hay không?", "Thông Báo",2);
        if(Click ==JOptionPane.YES_OPTION){
            DangNhap login = new DangNhap();
            this.setVisible(false);
            login.setVisible(true);
        }
    }//GEN-LAST:event_ButtonDangXuatActionPerformed

    private void ButtonTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTaiKhoanActionPerformed
        TaiKhoan account = new TaiKhoan(detail);
        this.setVisible(false);
        account.setVisible(true);
    }//GEN-LAST:event_ButtonTaiKhoanActionPerformed

    private void ButtonTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTimKiemActionPerformed
        TimKiem find = new TimKiem(detail);
        this.setVisible(false);
        find.setVisible(true);
    }//GEN-LAST:event_ButtonTimKiemActionPerformed

    private void ButtonThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThongKeActionPerformed
        DoanhThu payroll=new DoanhThu(detail);
        this.setVisible(false);
        payroll.setVisible(true);
    }//GEN-LAST:event_ButtonThongKeActionPerformed

    private void ButtonNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonNhanVienActionPerformed
        NhanVien account = new NhanVien(detail);
        this.setVisible(false);
        account.setVisible(true);
    }//GEN-LAST:event_ButtonNhanVienActionPerformed

    private void ButtonThongTinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThongTinActionPerformed
        ThongTin data = new ThongTin(detail);
        this.setVisible(false);
        data.setVisible(true);
    }//GEN-LAST:event_ButtonThongTinActionPerformed

    private void ButtonThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThietBiActionPerformed
        ThietBi product = new ThietBi(detail);
        this.setVisible(false);
        product.setVisible(true);
    }//GEN-LAST:event_ButtonThietBiActionPerformed

    private void ButtonDonDatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDonDatHangActionPerformed
        DonDatHang orderForm = new DonDatHang(detail);
        this.setVisible(false);
        orderForm.setVisible(true);
    }//GEN-LAST:event_ButtonDonDatHangActionPerformed

    private void ButtonBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonBanHangActionPerformed
        BanHang sale=new BanHang(detail);
        this.setVisible(false);
        sale.setVisible(true);
    }//GEN-LAST:event_ButtonBanHangActionPerformed


    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TrangChuQuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrangChuQuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrangChuQuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrangChuQuanLy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail=new Detail();
                new TrangChuQuanLy(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonBanHang;
    private javax.swing.JButton ButtonDangXuat;
    private javax.swing.JButton ButtonDonDatHang;
    private javax.swing.JButton ButtonNhanVien;
    private javax.swing.JButton ButtonTaiKhoan;
    private javax.swing.JButton ButtonThietBi;
    private javax.swing.JButton ButtonThongKe;
    private javax.swing.JButton ButtonThongTin;
    private javax.swing.JButton ButtonTimKiem;
    private javax.swing.JLabel LabelTenPhanMem;
    private javax.swing.JLabel LabelTenPhanMem1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        long FPS=80;
        long period=1000*1000000/FPS;
        long beginTime,sleepTime;
        
        beginTime=System.nanoTime();
        while(true){
            
            Update();
            
            long deltaTime=System.nanoTime()-beginTime;
            sleepTime=period-deltaTime;
            try{
                if(sleepTime>0)
                    Thread.sleep(sleepTime/1000000);
                else    Thread.sleep(period/2000000);
                
            }catch(Exception ex){
                ex.printStackTrace();
            }
            beginTime=System.nanoTime();
        }
    }
}
