package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.dao.AbstractCrudDao;
import com.org.ultrainstinct.dao.NhapKhoDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.org.ultrainstinct.model.NhapKho;
import com.org.ultrainstinct.model.PhieuNhapChiTiet;
import com.org.ultrainstinct.utils.Constant;

/**
 * <p>
 * NhapKhoDAOImpl represents a concrete implementation of NhapKhoDAO.
 * </p>
 *
 * @author MinhNgoc.
 */
public class NhapKhoDAOImpl extends AbstractCrudDao<NhapKho, Long> implements NhapKhoDAO {

    /**
     * <p>
     * Method mapRow NhapKho.
     * </p>
     *
     * @param rs ResultSet.
     * @return NhapKho.
     * @throws SQLException SQLException.
     * @author MinhNgoc.
     */
    @Override
    protected NhapKho mapRow(ResultSet rs) throws SQLException {
        return NhapKho
               .builder()
               .nhapKhoNo(rs.getLong(Constant.NHAP_KHO_NO))
               .maNhapKho(rs.getString("maNhapKho"))
               .maNhanVien(rs.getString("maNhanVien"))
               .ngayNhap(rs.getDate("ngayNhap"))
               .trangThai(rs.getBoolean("trangThai"))
               .build();
    }

    /**
     * <p>
     * Method getTableName table NhapKho.
     * </p>
     *
     * @return String.
     * @author MinhNgoc.
     */
    @Override
    protected String getTableName() {
        return Constant.NHAP_KHO_TABLE_NAME;
    }

    /**
     * <p>
     * Method getPrimaryKeyColumnName
     * </p>
     *
     * @return String.
     * @author MinhNgoc
     */
    @Override
    protected String getPrimaryKeyColumnName() {
        return Constant.NHAP_KHO_NO;
    }

    /**
     * <p>
     * Method getEntityValues.
     * </p>
     * 
     * @param entity NhapKho.
     * @return Object[].
     * @author MinhNgoc.
     */
    @Override
    protected Object[] getEntityValues(NhapKho entity) {
        return new Object[] {
            entity.getMaNhapKho(),
            entity.getMaNhanVien(),
            entity.getNgayNhap(),
            entity.isTrangThai()
        };
    }

    /**
     * <p>
     * Method getInsertQuery.
     * </p>
     * 
     * @return String
     * @author MinhNgoc.
     */
    @Override
    protected String getInsertQuery() {
        return """
                INSERT INTO
                """ + Constant.NHAP_KHO_TABLE_NAME + """
                (maNhapKho, maNhanVien, ngayNhap, trangThai)
                values (?,?,?,?);
                """;
    }

    /**
     * <p>
     * Method getUpdateQuery.
     * </p>
     * 
     * @return String
     * @author MinhNgoc.
     */
    @Override
    protected String getUpdateQuery() {
        return """
                UPDATE
                """ + Constant.NHAP_KHO_TABLE_NAME +
                " SET maNhapKho = ?, maNhanVien = ?, ngayNhap = ?, trangThai = ? WHERE "
                + Constant.NHAP_KHO_NO + " = ?";
    }

    /**
     * <p>
     * Method get max maxMaNhapKho.
     * </p>
     *
     * @return long maxMaNhapKho.
     * @author MinhNgoc.
     */
    @Override
    public long getMaxMaNhapKho() throws SQLException {
        String sql = " SELECT COUNT(1) MAX_MA_NHAPKHO FROM " + getTableName();

        PreparedStatement stmt = AbstractCrudDao.connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getLong("MAX_MA_NHAPKHO");
        }
        return 0L;
    }

    @Override
    public void save(NhapKho nhapKho, List<PhieuNhapChiTiet> phieuNhapChiTietList) throws SQLException {
        String insertPhieuNhapChiTietSql = """
        INSERT INTO PhieuNhapChiTiet(maPNCT, maSanPham, maNhapKho, maNhaCungCap, giaNhap, soLuong)
        VALUES (?, ?, ?, ?, ?, ?);
        """;

        try (PreparedStatement chiTietStmt = connection.prepareStatement(insertPhieuNhapChiTietSql)) {

            // Insert into HoaDon table
            this.save(nhapKho);

            // Insert into HoaDonChiTiet table
            for (PhieuNhapChiTiet chiTiet : phieuNhapChiTietList) {
                chiTietStmt.setString(1, chiTiet.getMaPNCT());
                chiTietStmt.setString(2, chiTiet.getMaSanPham());
                chiTietStmt.setString(3, chiTiet.getMaNhapKho());
                chiTietStmt.setString(4, chiTiet.getMaNhaCungCap());
                chiTietStmt.setFloat(5, chiTiet.getGiaNhap());
                chiTietStmt.setInt(6, chiTiet.getSoLuong());
                chiTietStmt.addBatch();
            }
            chiTietStmt.executeBatch();
        } catch (SQLException e) {
            throw new SQLException("Failed to create invoice and details", e);
        }
    }
}
