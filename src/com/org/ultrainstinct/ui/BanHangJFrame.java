package com.org.ultrainstinct.ui;

import org.apache.commons.lang3.StringUtils;

import com.org.ultrainstinct.dao.KhachHangDAO;
import com.org.ultrainstinct.dao.SanPhamDAO;
import com.org.ultrainstinct.dao.impl.KhachHangDAOImpl;
import com.org.ultrainstinct.dao.impl.SanPhamDAOImpl;
import com.org.ultrainstinct.model.HoaDonChiTiet;
import com.org.ultrainstinct.model.KhachHang;
import com.org.ultrainstinct.model.SanPham;
import com.org.ultrainstinct.utils.Constant;
import com.org.ultrainstinct.utils.DateUtil;
import com.org.ultrainstinct.utils.MessageDialog;
import com.org.ultrainstinct.utils.NumberUtil;
import com.org.ultrainstinct.utils.StringUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang3.ObjectUtils;

public class BanHangJFrame extends javax.swing.JPanel {

    private final SanPhamDAO sanPhamDao;
    private final KhachHangDAO khachHangDAO;

    public BanHangJFrame() {
        sanPhamDao = new SanPhamDAOImpl();
        khachHangDAO = new KhachHangDAOImpl();
        initComponents();
        initSanPhamList();
        initTimeCurrent();
    }

    public final void initSanPhamList() {
        List<SanPham> sanPhamList = sanPhamDao.findAll();
        // Create a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"STT", "Mã sản phẩm", "Tên sản phẩm", "Giá", "Số lượng"},
                0
        );
        // Populate the model with data from sanPhamList
        for (int i = 0; i < sanPhamList.size(); i++) {
            SanPham sp = sanPhamList.get(i);
            model.addRow(new Object[]{i + 1, sp.getMaSanPham(), sp.getTenSanPham(), NumberUtil.withBigDecimalFloat(sp.getGiaNiemYet(), 9), sp.getSoLuongTon()});
        }

        // Set the model to the table
        sanPhamTable.setModel(model);

        // Add the table to the scroll pane
        jScrollPane1.setViewportView(sanPhamTable);

    }

    public final void searchSanPham(String sanPhamTextSearch) {
        List<SanPham> sanPhamList = sanPhamDao.findAll();
        // Create a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"STT", "Mã sản phẩm", "Tên sản phẩm", "Giá", "Số lượng"},
                0
        );
        // Populate the model with data from sanPhamList
        for (int i = 0; i < sanPhamList.size(); i++) {
            SanPham sp = sanPhamList.get(i);
            model.addRow(new Object[]{i + 1, sp.getMaSanPham(), sp.getTenSanPham(), sp.getGiaNiemYet(), sp.getSoLuongTon()});
        }

        // Set the model to the table
        sanPhamTable.setModel(model);

        // Add the table to the scroll pane
        jScrollPane1.setViewportView(sanPhamTable);
    }

    public final void findByMaKhachHang(String maKhachHang) {
        KhachHang khachHang = khachHangDAO.findByMaKhachHang(maKhachHang);
        if (ObjectUtils.isNotEmpty(khachHang)) {
            txtMaKhachHang.setText(khachHang.getMaKhachHang());
            txtTenKhachHang.setText(khachHang.getTenKH());
        }

    }

    private void updateSecondTableSanPham(Object[] rowData) {
        DefaultTableModel model = (DefaultTableModel) tableSanPhamListChoose.getModel();
        int count = model.getRowCount();
        rowData[0] = count + 1;
        model.addRow(rowData);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sanPhamTable = new javax.swing.JTable();
        lblTongTien = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableSanPhamListChoose = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jtextKhachHangSearch = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lbDateCurrent = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaKhachHang = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtTenKhachHang = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jBtnKhachHangSeach = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtTimKiem1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("jLabel4");

        setBackground(new java.awt.Color(245, 245, 245));

        sanPhamTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã sản phẩm", "Tên sản phẩm", "Giá ", "Số lượng"
            }
        ));
        sanPhamTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sanPhamTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(sanPhamTable);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com/org/ultrainstinct/ui/Bundle"); // NOI18N
        lblTongTien.setText(bundle.getString("BanHangJFrame.jLabel14.text")); // NOI18N

        tableSanPhamListChoose.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên sản phẩm", "Số lượng", "Giá", "T. Tiền"
            }
        ));
        jScrollPane3.setViewportView(tableSanPhamListChoose);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel8.setText(bundle.getString("BanHangJFrame.jLabel8.text")); // NOI18N

        jLabel1.setText(bundle.getString("BanHangJFrame.jLabel1.text")); // NOI18N

        jButton4.setBackground(new java.awt.Color(51, 204, 0));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText(bundle.getString("BanHangJFrame.jButton4.text")); // NOI18N
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jtextKhachHangSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtextKhachHangSearchActionPerformed(evt);
            }
        });

        jLabel2.setText(bundle.getString("BanHangJFrame.jLabel2.text")); // NOI18N

        lbDateCurrent.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbDateCurrent.setText(bundle.getString("BanHangJFrame.jLabel9.text")); // NOI18N

        jLabel3.setText(bundle.getString("BanHangJFrame.jLabel3.text")); // NOI18N

        txtMaKhachHang.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText(bundle.getString("BanHangJFrame.jLabel11.text")); // NOI18N

        jButton3.setText(bundle.getString("BanHangJFrame.jButton3.text")); // NOI18N

        jBtnKhachHangSeach.setText(bundle.getString("BanHangJFrame.jBtnKhachHangSeach.text")); // NOI18N
        jBtnKhachHangSeach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBtnKhachHangSeachMouseClicked(evt);
            }
        });
        jBtnKhachHangSeach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnKhachHangSeachActionPerformed(evt);
            }
        });

        jLabel12.setText(bundle.getString("BanHangJFrame.jLabel12.text")); // NOI18N

        txtTimKiem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiem1ActionPerformed(evt);
            }
        });
        txtTimKiem1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiem1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimKiem1KeyTyped(evt);
            }
        });

        jLabel13.setText(bundle.getString("BanHangJFrame.jLabel13.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTimKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(248, 248, 248)
                                        .addComponent(lblTongTien))
                                    .addComponent(jLabel2)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(34, 34, 34)
                                                .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jtextKhachHangSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jBtnKhachHangSeach, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton3)))
                                        .addComponent(jButton4))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(163, 163, 163)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(40, 40, 40)
                                                .addComponent(lbDateCurrent)))))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(lbDateCurrent)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(txtTimKiem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtextKhachHangSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3)
                        .addComponent(jBtnKhachHangSeach)))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTongTien)
                    .addComponent(jLabel13))
                .addGap(26, 26, 26)
                .addComponent(jButton4)
                .addContainerGap(69, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void sanPhamTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sanPhamTableMouseClicked
        int selectedRow = sanPhamTable.getSelectedRow();
        if (selectedRow != -1) {
            // Fetch data from the selected row
            Object[] rowData = new Object[5];
            for (int i = 0; i < sanPhamTable.getColumnCount(); i++) {
                rowData[i] = sanPhamTable.getValueAt(selectedRow, i);
            }
            Float price = rowData[3] != null ? (Float) rowData[3] : 0;
            Integer quantity = rowData[4] != null ? (Integer) rowData[4] : 0;

            Float total = price * quantity;

            Object[] rowDataCal = new Object[5];

            rowDataCal[1] = rowData[2];
            rowDataCal[2] = rowData[4];
            rowDataCal[3] = rowData[3];
            rowDataCal[4] = total;
            updateSecondTableSanPham(rowDataCal);
            String txtTotalHoaDon = lblTongTien.getText();
            Double totalHoaDon = Double.valueOf(txtTotalHoaDon);
            totalHoaDon += total;
            lblTongTien.setText(totalHoaDon.toString());

        }
    }//GEN-LAST:event_sanPhamTableMouseClicked

    private boolean isKhachHangIsEmty() {
        boolean isCheckKhachHangIsEmty = Boolean.FALSE;
        if (StringUtils.isBlank(txtMaKhachHang.getText())) {
            MessageDialog.alertError(this, "Vui lòng không để trống mã khách hàng !!!");
            isCheckKhachHangIsEmty = Boolean.TRUE;
        }

        if (isCheckKhachHangIsEmty && StringUtils.isBlank(txtTenKhachHang.getText())) {
            MessageDialog.alertError(this, "Vui lòng không để trống tên khách hàng !!!");
            isCheckKhachHangIsEmty = Boolean.TRUE;
        }
        return isCheckKhachHangIsEmty;
    }

    private boolean isSanPhamIsEmty() {
        boolean isSanPhamIsEmty = Boolean.FALSE;
        if (tableSanPhamListChoose.getRowCount() == 0) {
            MessageDialog.alertError(this, "Vui lòng chọn sản phẩm !!!");
            isSanPhamIsEmty = Boolean.TRUE;
        }
        return isSanPhamIsEmty;
    }

    private void initTimeCurrent() {
        lbDateCurrent.setText(DateUtil.localDateToString(LocalDate.now(), DateUtil.YYYY_MM_DD));
//        lblTimeCurrent.setText(DateUtil.localDateTimeToString(LocalDateTime.now(), DateUtil.HH_MM_SS));
//        
//        Timer timer = new Timer(2000, (e) -> {
//            lblTimeCurrent.setText(DateUtil.localDateTimeToString(LocalDateTime.now(), DateUtil.HH_MM_SS));
//        });
//        timer.start();
    }

    private void getAllDataFromTableSanPhamListChoose(String maHoaDon) {
    DefaultTableModel model = (DefaultTableModel) tableSanPhamListChoose.getModel();
    int rowCount = model.getRowCount();
    List<Object[]> rowDataList = new ArrayList<>();
    
    for (int i = 0; i < rowCount; i++) {
        Object[] rowData = new Object[model.getColumnCount()];
        for (int j = 0; j < model.getColumnCount(); j++) {
            rowData[j] = model.getValueAt(i, j);
        }
        HoaDonChiTiet
                .builder()
                .maHDCT(StringUtil.genCode(Constant.CODE_HD))
                .giaBan(1)
                .soLuong(rowCount)
                .maHoaDon(maHoaDon)
//                .maSanPham()
                .build();
        rowDataList.add(rowData);
    }
    
    // Process rowDataList as needed
    // For example, you can print it
    for (Object[] row : rowDataList) {
        System.out.println(Arrays.toString(row));
    }
}
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jtextKhachHangSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtextKhachHangSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtextKhachHangSearchActionPerformed

    private void jBtnKhachHangSeachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBtnKhachHangSeachMouseClicked
        String seachTextKhachHang = jtextKhachHangSearch.getText();
        if (StringUtils.isNotBlank(seachTextKhachHang)) {
            findByMaKhachHang(seachTextKhachHang);
        }
    }//GEN-LAST:event_jBtnKhachHangSeachMouseClicked

    private void jBtnKhachHangSeachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnKhachHangSeachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnKhachHangSeachActionPerformed

    private void txtTimKiem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem1ActionPerformed

    private void txtTimKiem1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem1KeyReleased

    private void txtTimKiem1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiem1KeyTyped

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        if (!isKhachHangIsEmty() && !isSanPhamIsEmty()) {

        }
    }//GEN-LAST:event_jButton4MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnKhachHangSeach;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jtextKhachHangSearch;
    private javax.swing.JLabel lbDateCurrent;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTable sanPhamTable;
    private javax.swing.JTable tableSanPhamListChoose;
    private javax.swing.JTextField txtMaKhachHang;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTimKiem1;
    // End of variables declaration//GEN-END:variables

}
