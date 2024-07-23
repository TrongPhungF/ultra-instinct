package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.dao.KhachHangDAO;
import com.org.ultrainstinct.dao.AbstractCrudDao;
import static com.org.ultrainstinct.dao.AbstractCrudDao.LOGGER;
import static com.org.ultrainstinct.dao.AbstractCrudDao.connection;
import com.org.ultrainstinct.model.KhachHang;
import com.org.ultrainstinct.utils.Constant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * <p>
 * KhachHangDAOImpl represents a concrete implementation of KhachHangDAO.
 * </p>
 *
 * @author MinhNgoc
 */
public class KhachHangDAOImpl extends AbstractCrudDao<KhachHang, Long> implements KhachHangDAO {

    /**
     * <p>
     * Method mapRow DiaChi
     * </p>
     *
     * @param rs ResultSet
     * @return DiaChi
     * @throws SQLException SQLException
     * @author MinhNgoc
     */
    @Override
    protected KhachHang mapRow(ResultSet rs) throws SQLException {
        return KhachHang.builder()
               .khachHangNo(rs.getLong(Constant.KHACH_HANG_NO))
               .maKhachHang(rs.getString("maKhachHang"))
               .tenKH(rs.getString("tenKH"))
               .soDienThoai(rs.getString("soDienThoai"))
               .email(rs.getString("email"))
               .ngayDangKy(rs.getDate("ngayDangKy"))
               .trangThaiKH(rs.getBoolean("trangThaiKH"))
               .trangThaiXoa(rs.getBoolean("trangThaiXoa"))
               .nguoiTao(rs.getString("nguoiTao"))
               .ghiChu(rs.getString("ghiChu"))
               .build();
    }

    /**
     * <p>
     * Method getTableName table DiaChi
     * </p>
     *
     * @return String DiaChi
     * @author MinhNgoc
     */
    @Override
    protected String getTableName() {
        return Constant.KHACH_HANG_TABLE_NAME;
    }

    /**
     * <p>
     * Method getPrimaryKeyColumnName
     * </p>
     *
     * @return String diaChiNo
     * @author MinhNgoc
     */
    @Override
    protected String getPrimaryKeyColumnName() {
        return Constant.KHACH_HANG_NO;
    }

    /**
     * <p>
     * Method getEntityValues.
     * </p>
     * @param entity KhachHang.
     * @return Object[].
     * @author MinhNgoc.
     */
    @Override
    protected Object[] getEntityValues(KhachHang entity) {
        return new Object[]{
            entity.getMaKhachHang(),
            entity.getTenKH(),
            entity.getSoDienThoai(),
            entity.getEmail(),
            entity.getNgayDangKy(),
            entity.getTenKH(),
            entity.isTrangThaiXoa(),
            entity.getNguoiTao(),
            entity.getGhiChu(),
        };
    }

    /**
     * <p>
     * Method getInsertQuery.
     * </p>
     * @return String
     * @author MinhNgoc.
     */
    @Override
    protected String getInsertQuery() {
        return """
               INSERT INTO
               """ + Constant.SAN_PHAM_TABLE_NAME + """
               (maKhachHang, tenKH, soDienThoai, email, ngayDangKy, trangThaiKH, trangThaiXoa, nguoiTao, ghiChu) values (?,?,?,?,?,?);
               """;
    }

    /**
     * <p>
     * Method getUpdateQuery.
     * </p>
     * @return String
     * @author MinhNgoc.
     */
    @Override
    protected String getUpdateQuery() {
        return """
               UPDATE
               """ + Constant.SAN_PHAM_TABLE_NAME + """
               SET maKhachHang = ?, tenKH = ?, soDienThoai = ?, email = ?, ngayDangKy = ?, trangThaiKH = ?, trangThaiXoa = ?, nguoiTao = ?, ghiChu = ?  WHERE
               """+ Constant.SAN_PHAM_NO +" = ? ";
    }

    /**
     * <p>
     * Method get max MaDiaChi
     * </p>
     *
     * @return long maxMaDiaChi
     * @author MinhNgoc
     */
    @Override
    public long getMaxMaSanPham() throws SQLException {
        String sql = " SELECT COUNT(1) MAX_MA_KHACHHANG FROM " + getTableName();

        PreparedStatement stmt = AbstractCrudDao.connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getLong("MAX_MA_KHACHHANG");
        }
        return 0L;
    }
    @Override
    public KhachHang findByMaKhachHang(String maKH) {
        String sql = " SELECT * FROM " + getTableName()+" WHERE maKhachHang = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, maKH);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to find entity by ma khach hang.", e);
        }
        return null;
    }
}
