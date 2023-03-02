package UserInterFace;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class HomeUser extends javax.swing.JFrame implements Runnable {
    private Thread thread;
    private Detail detail;
    
    public HomeUser(Detail d) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        lblSoftwareName.setForeground(Color.GREEN);
        detail = new Detail(d);
        //Start();
    }

    private void Start() {
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }
    
    private void Update() {
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel(){
            ImageIcon icon = new ImageIcon("src/Image/Background2.png");
            public void paintComponent(Graphics g){

                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, this);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        lblSoftwareName = new javax.swing.JLabel();
        ButtonBanHang = new javax.swing.JButton();
        ButtonThietBi = new javax.swing.JButton();
        ButtonDonDatHang = new javax.swing.JButton();
        ButtonTimKiem = new javax.swing.JButton();
        ButtonDangXuat = new javax.swing.JButton();
        LabelTenPhanMem = new javax.swing.JLabel();
        LabelTenPhanMem1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Giao Diện Hệ Thống");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(1033, 439));

        lblSoftwareName.setFont(new java.awt.Font("Times New Roman", 0, 38)); // NOI18N
        lblSoftwareName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        ButtonBanHang.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonBanHang.setForeground(new java.awt.Color(255, 102, 0));
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

        ButtonThietBi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonThietBi.setForeground(new java.awt.Color(255, 0, 51));
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

        ButtonDonDatHang.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonDonDatHang.setForeground(new java.awt.Color(51, 153, 0));
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

        ButtonTimKiem.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ButtonTimKiem.setForeground(new java.awt.Color(204, 51, 255));
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

        ButtonDangXuat.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
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

        LabelTenPhanMem.setFont(new java.awt.Font("Arial", 1, 32)); // NOI18N
        LabelTenPhanMem.setForeground(new java.awt.Color(0, 0, 255));
        LabelTenPhanMem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelTenPhanMem.setText("- PHẦN MỀM QUẢN LÝ CỬA HÀNG BÁN THIẾT BỊ XE MÁY -");

        LabelTenPhanMem1.setFont(new java.awt.Font("Monotype Corsiva", 1, 36)); // NOI18N
        LabelTenPhanMem1.setForeground(new java.awt.Color(255, 102, 102));
        LabelTenPhanMem1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelTenPhanMem1.setText("  ~ WELCOME ~");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(LabelTenPhanMem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblSoftwareName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(975, 975, 975))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(ButtonBanHang, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(ButtonThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(ButtonDonDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(ButtonTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(ButtonDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(LabelTenPhanMem1, javax.swing.GroupLayout.PREFERRED_SIZE, 1022, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(22, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelTenPhanMem, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSoftwareName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ButtonTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonDangXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButtonBanHang, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                    .addComponent(ButtonThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonDonDatHang, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(LabelTenPhanMem1)
                .addGap(9, 9, 9))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1050, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    // Button Bán Hàng.
    private void ButtonBanHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonBanHangActionPerformed
        BanHang banhang = new BanHang(detail);
        this.setVisible(false);
        banhang.setVisible(true);
    }//GEN-LAST:event_ButtonBanHangActionPerformed

    // Button Thiết Bị.
    private void ButtonThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThietBiActionPerformed
        ThietBi thietbi = new ThietBi(detail);
        this.setVisible(false);
        thietbi.setVisible(true);
    }//GEN-LAST:event_ButtonThietBiActionPerformed

    // Button Đơn Đặt Hàng.
    private void ButtonDonDatHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDonDatHangActionPerformed
        DonDatHang dondathang = new DonDatHang(detail);
        this.setVisible(false);
        dondathang.setVisible(true);
    }//GEN-LAST:event_ButtonDonDatHangActionPerformed

    // Button Tìm Kiếm.
    private void ButtonTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTimKiemActionPerformed
        TimKiem timkiem = new TimKiem(detail);
        this.setVisible(false);
        timkiem.setVisible(true);
    }//GEN-LAST:event_ButtonTimKiemActionPerformed

    // Button Đăng Xuất.
    private void ButtonDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDangXuatActionPerformed
        int Click = JOptionPane.showConfirmDialog(null, "ĐĂNG XUẤT KHỎI TÀI KHOẢN ?", "Thông Báo",2);
        if(Click == JOptionPane.YES_OPTION) {
            DangNhap dangnhap = new DangNhap();
            this.setVisible(false);
            dangnhap.setVisible(true);
        }
    }//GEN-LAST:event_ButtonDangXuatActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail = new Detail();
                new HomeUser(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonBanHang;
    private javax.swing.JButton ButtonDangXuat;
    private javax.swing.JButton ButtonDonDatHang;
    private javax.swing.JButton ButtonThietBi;
    private javax.swing.JButton ButtonTimKiem;
    private javax.swing.JLabel LabelTenPhanMem;
    private javax.swing.JLabel LabelTenPhanMem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblSoftwareName;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
//        long FPS=80;
//        long period=1000*1000000/FPS;
//        long beginTime,sleepTime;
//        
//        beginTime=System.nanoTime();
//        while(true){
//            
//            Update();
//            
//            long deltaTime=System.nanoTime()-beginTime;
//            sleepTime=period-deltaTime;
//            try{
//                if(sleepTime>0)
//                    Thread.sleep(sleepTime/1000000);
//                else    Thread.sleep(period/2000000);
//                
//            }catch(Exception ex){
//                ex.printStackTrace();
//            }
//            beginTime=System.nanoTime();
//        }
    }
}
