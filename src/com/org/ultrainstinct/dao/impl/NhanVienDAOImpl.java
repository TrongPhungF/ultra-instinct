package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.dao.NhanVienDAO;
import com.org.ultrainstinct.dao.AbstractCrudDao;
import com.org.ultrainstinct.model.NhanVien;
import com.org.ultrainstinct.utils.Constant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * NhanVienDAOImpl represents a concrete implementation of NhanVienDAO.
 * </p>
 *
 * @author MinhNgoc
 */
public class NhanVienDAOImpl extends AbstractCrudDao<NhanVien, Long> implements NhanVienDAO {

    /**
     * <p>
     * Method mapRow NhanVien
     * </p>
     *
     * @param rs ResultSet.
     * @return NhanVien.
     * @throws SQLException SQLException.
     * @author MinhNgoc.
     */
    @Override
    protected NhanVien mapRow(ResultSet rs) throws SQLException {
        return NhanVien.builder()
            .nhanVienNo(rs.getLong(Constant.NHAN_VIEN_NO))
            .maNhanVien(rs.getString("maNhanVien"))
            .tenNhanVien(rs.getString("tenNhanVien"))
            .hoNhanVien(rs.getString("tenNhanVien"))
            .matKhau(rs.getString("matKhau"))
            .soDienThoai(rs.getString("soDienThoai"))
            .email(rs.getString("email"))
            .chucVu(rs.getString("chucVu"))
            .trangThaiXoa(rs.getBoolean("trangThaiXoa"))
            .nguoiTao(rs.getString("nguoiTao"))
            .thoiGianTao(rs.getDate("nguoiTao"))
            .build();
    }

    /**
     * <p>
     * Method getTableName table NhanVien.
     * </p>
     *
     * @return String.
     * @author MinhNgoc.
     */
    @Override
    protected String getTableName() {
        return Constant.NHAN_VIEN_TABLE_NAME;
    }

    /**
     * <p>
     * Method getPrimaryKeyColumnName.
     * </p>
     *
     * @return String.
     * @author MinhNgoc.
     */
    @Override
    protected String getPrimaryKeyColumnName() {
        return Constant.NHAN_VIEN_NO;
    }

    /**
     * <p>
     * Method getEntityValues.
     * </p>
     *
     * @param entity NhanVien.
     * @return Object[].
     * @author MinhNgoc.
     */
    @Override
    protected Object[] getEntityValues(NhanVien entity) {
        return new Object[]{
            entity.getMaNhanVien(),
            entity.getTenNhanVien(),
            entity.getHoNhanVien(),
            entity.getMatKhau(),
            entity.getSoDienThoai(),
            entity.getEmail(),
            entity.getChucVu(),
            entity.isTrangThaiXoa(),
            entity.getNguoiTao(),
            entity.getThoiGianTao(),
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
            """ + Constant.NHAN_VIEN_TABLE_NAME + """
            (maNhanVien, tenNhanVien, hoNhanVien, matKhau, soDienThoai, email, chucVu, trangThaiXoa, nguoiTao, thoiGianTao) 
            values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
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
            """ + Constant.NHAN_VIEN_TABLE_NAME + """
            SET maNhanVien = ?, tenNhanVien = ?, hoNhanVien = ?, matKhau = ?, 
            soDienThoai = ?, email = ?, chucVu = ?, trangThaiXoa = ?, nguoiTao = ?, thoiGianTao = ?  
            WHERE
            """ + Constant.SAN_PHAM_NO + " = ? ";
    }

    /**
     * <p>
     * Method get max MaNhanVien.
     * </p>
     *
     * @return long.
     * @author MinhNgoc.
     */
    @Override
    public long getMaxMaNhanVien() throws SQLException {
        String sql = " SELECT COUNT(1) MAX_MA_NHANVIEN FROM " + getTableName();

        PreparedStatement stmt = AbstractCrudDao.connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getLong("MAX_MA_NHANVIEN");
        }
        return 0L;
    }
}
