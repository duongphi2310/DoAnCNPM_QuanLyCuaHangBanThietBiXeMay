package UserInterFace;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class DoanhThu extends javax.swing.JFrame {
    private Connection connect = null;  
    private PreparedStatement pst = null;  
    private ResultSet rs = null;
    
    private Detail detail;
    private String sql = "SELECT * FROM DoanhThu";
    private String date = "1/3/2023";
    
    public DoanhThu(Detail d) {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        loadDate();
        detail=new Detail(d);
        connection();
        load(sql);
        
    }

    private void loadDate(){
        try {
            DateChooserTuNgay.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(date));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        DateChooserDenNgay.setDate(new java.util.Date());
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
    
    private void load(String sql){
        dele();
        int count=0;
        long tongTien=0;
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        try{
            String [] arr = {"MÃ NHÂN VIÊN", "HỌ TÊN", "NGÀY BÁN", "GIỜ BÁN", "TỔNG TIỀN HÓA ĐƠN", "TIỀN NHẬN CỦA KHÁCH", "TIỀN DƯ CỦA KHÁCH"};
            DefaultTableModel model = new DefaultTableModel(arr,0);
            pst = connect.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()){
                Vector vector = new Vector();
                vector.add(rs.getString("MaNhanVien").trim());
                vector.add(rs.getString("HoTen").trim());
                vector.add(new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("NgayBan")));
                vector.add(rs.getString("GioBan").trim());
                vector.add(rs.getString("TongGiaTien").trim());
                vector.add(rs.getString("TienNhan").trim());
                vector.add(rs.getString("TienThua").trim());
                model.addRow(vector);
                String []s = rs.getString("TongGiaTien").trim().split("\\s");
                tongTien = convertedToNumbers(s[0])+tongTien;
                count++;
                //addRevenue(rs.getString("MaNhanVien").trim(),rs.getString("HoTen").trim(),rs.getDate("NgayBan"),rs.getString("GioBan").trim(),rs.getString("TongGiaTien").trim(),rs.getString("TienNhan").trim(),rs.getString("TienThua").trim());
            }
            BangDoanhThu.setModel(model);
            lblSoHoaDon.setText(String.valueOf(count));
            lblTongDoanhThu.setText(formatter.format(tongTien)+" "+"VND");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        in();
    }
    
    private long convertedToNumbers(String s){
        String number="";
        String []array=s.replace(","," ").split("\\s");
        for(String i:array){
            number=number.concat(i);
        }
        return Long.parseLong(number);
    }
    
    private void dele(){
            String sql = "DELETE FROM DoanhThu_CT";
            try{
                pst = connect.prepareStatement(sql);
                pst.executeUpdate();
            }
            catch(Exception ex){
               ex.printStackTrace();
        }
    }
    
    private void in(){
        String sqlPay = "INSERT INTO DoanhThu_CT (tonghoadon,tongtien,tungay,denngay) VALUES(?,?,?,?)";
        try{
            pst = connect.prepareStatement(sqlPay);
            pst.setString(1, lblSoHoaDon.getText());
            pst.setString(2,lblTongDoanhThu.getText());
            pst.setString(3, ((JTextField)DateChooserTuNgay.getDateEditor().getUiComponent()).getText().toString());
            pst.setString(4,((JTextField)DateChooserDenNgay.getDateEditor().getUiComponent()).getText().toString());
            pst.executeUpdate();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
//    private void addRevenue(String manhanvien, String name, Date date, String time,String tongtien,String tien,String tiendu){
//        String sqlPay="INSERT INTO ThongKe (EmployeeCode,Name,Date,Time,TotalMoney,Money,Surplus) VALUES(?,?,?,?,?,?,?)";
//        try{
//            pst=conn.prepareStatement(sqlPay);
//            pst.setString(1, manhanvien);
//            pst.setString(2, name);
//            pst.setDate(3,date);
//            pst.setString(4, time);
//            pst.setString(5, tongtien);
//            pst.setString(6, tien);
//            pst.setString(7, tiendu);
//            pst.executeUpdate();
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//        }
//    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ButtonTroVe = new javax.swing.JButton();
        LabelThongKeDoanhThu = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        DateChooserTuNgay = new com.toedter.calendar.JDateChooser();
        DateChooserDenNgay = new com.toedter.calendar.JDateChooser();
        ButtonThongKe = new javax.swing.JButton();
        ButtonLamMoiDoanhThu = new javax.swing.JButton();
        ButtonInThongKe = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        BangDoanhThu = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        lblSoHoaDon = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblTongDoanhThu = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        ButtonTroVe.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        ButtonTroVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Back.png"))); // NOI18N
        ButtonTroVe.setText("HỆ THỐNG");
        ButtonTroVe.setMargin(new java.awt.Insets(2, -7, 5, 2));
        ButtonTroVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonTroVeActionPerformed(evt);
            }
        });

        LabelThongKeDoanhThu.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        LabelThongKeDoanhThu.setForeground(new java.awt.Color(51, 204, 0));
        LabelThongKeDoanhThu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelThongKeDoanhThu.setText("DOANH THU");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel2.setText("Từ Ngày:");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel3.setText("Đến Ngày:");

        DateChooserTuNgay.setDateFormatString("dd/MM/yyyy");

        DateChooserDenNgay.setDateFormatString("dd/MM/yyyy");

        ButtonThongKe.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ButtonThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Revenue.png"))); // NOI18N
        ButtonThongKe.setText("THỐNG KÊ");
        ButtonThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonThongKeActionPerformed(evt);
            }
        });

        ButtonLamMoiDoanhThu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        ButtonLamMoiDoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLamMoiDoanhThuActionPerformed(evt);
            }
        });

        ButtonInThongKe.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ButtonInThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Print Sale.png"))); // NOI18N
        ButtonInThongKe.setText("IN THỐNG KÊ");
        ButtonInThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonInThongKeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DateChooserTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DateChooserDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(ButtonLamMoiDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(ButtonThongKe)
                .addGap(30, 30, 30)
                .addComponent(ButtonInThongKe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonThongKe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButtonLamMoiDoanhThu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(DateChooserTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DateChooserDenNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(ButtonInThongKe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        BangDoanhThu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(BangDoanhThu);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Tổng Số Hóa Đơn Bán Ra:");

        lblSoHoaDon.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblSoHoaDon.setText("0");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Tổng Tiền Hóa Đơn:");

        lblTongDoanhThu.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTongDoanhThu.setText("0 VND");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Cửa Hàng Bán Thiết Bị Xe Máy ABC – Motor: Số 97 Đường Man Thiện, Phường Hiệp Phú, TP. Thủ Đức, TP. Hồ Chí Minh");

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Điện thoại: 0988919701     –     Email: phisuper2310@gmail.com");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTongDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ButtonTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(195, 195, 195)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1009, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72)
                                .addComponent(LabelThongKeDoanhThu)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonTroVe, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel12)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel14)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LabelThongKeDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblSoHoaDon)
                    .addComponent(jLabel6)
                    .addComponent(lblTongDoanhThu))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonThongKeActionPerformed
        String sqlRevenue="SELECT * FROM DoanhThu WHERE NgayBan BETWEEN '"+new java.sql.Date(DateChooserTuNgay.getDate().getTime())+"' AND '"+new java.sql.Date(DateChooserDenNgay.getDate().getTime())+"'";
        load(sqlRevenue);
    }//GEN-LAST:event_ButtonThongKeActionPerformed

    private void ButtonLamMoiDoanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLamMoiDoanhThuActionPerformed
        load(sql);
        loadDate();
    }//GEN-LAST:event_ButtonLamMoiDoanhThuActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int click = JOptionPane.showConfirmDialog(null,"THOÁT CHƯƠNG TRÌNH ?","Thông Báo",2);
        if(click == JOptionPane.OK_OPTION){
            System.exit(0);
        }
        else{
            if(click == JOptionPane.CANCEL_OPTION){    
                this.setVisible(true);
            }
        }
    }//GEN-LAST:event_formWindowClosing

    private void ButtonTroVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonTroVeActionPerformed
        TrangChuQuanLy login = new TrangChuQuanLy(detail);
        this.setVisible(false);
        login.setVisible(true);
    }//GEN-LAST:event_ButtonTroVeActionPerformed

    private void ButtonInThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonInThongKeActionPerformed
        try {
            JasperReport report=JasperCompileManager.compileReport("C:\\Users\\D.Thanh Trung\\Documents\\NetBeansProjects\\Quan Ly Cua Hang Mua Ban Thiet Bi Dien Tu\\src\\UserInterFace\\ThongKe.jrxml");
            
            JasperPrint print=JasperFillManager.fillReport(report, null, connect);
            
            JasperViewer.viewReport(print,false);
        }
        catch (JRException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_ButtonInThongKeActionPerformed


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Detail detail = new Detail();
                new DoanhThu(detail).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable BangDoanhThu;
    private javax.swing.JButton ButtonInThongKe;
    private javax.swing.JButton ButtonLamMoiDoanhThu;
    private javax.swing.JButton ButtonThongKe;
    private javax.swing.JButton ButtonTroVe;
    private com.toedter.calendar.JDateChooser DateChooserDenNgay;
    private com.toedter.calendar.JDateChooser DateChooserTuNgay;
    private javax.swing.JLabel LabelThongKeDoanhThu;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSoHoaDon;
    private javax.swing.JLabel lblTongDoanhThu;
    // End of variables declaration//GEN-END:variables
}
