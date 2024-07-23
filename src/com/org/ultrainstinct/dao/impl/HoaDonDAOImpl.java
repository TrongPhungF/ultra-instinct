package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.dao.AbstractCrudDao;
import com.org.ultrainstinct.dao.HoaDonDAO;
import com.org.ultrainstinct.model.HoaDon;
import com.org.ultrainstinct.model.HoaDonChiTiet;
import com.org.ultrainstinct.utils.Constant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * HoaDonDAOImpl represents a concrete implementation of HoaDonDAO.
 * </p>
 */
public class HoaDonDAOImpl extends AbstractCrudDao<HoaDon, Long> implements HoaDonDAO {

    private static final Logger LOGGER = Logger.getLogger(HoaDonDAOImpl.class.getName());

    /**
     * <p>
     * Method mapRow HoaDon.
     * </p>
     *
     * @param rs ResultSet.
     * @return HoaDon.
     * @throws SQLException SQLException.
     */
    @Override
    protected HoaDon mapRow(ResultSet rs) throws SQLException {
        return HoaDon
               .builder()
               .hoaDonNo(rs.getLong(Constant.HOA_DON_NO)) // Sử dụng hằng số đúng cho cột
               .maHoaDon(rs.getString("maHoaDon"))
               .maKhachHang(rs.getString("maKhachHang"))
               .maNhanVien(rs.getString("maNhanVien"))
               .ngayLap(rs.getDate("ngayLap"))
               .trangThai(rs.getBoolean("trangThai"))
               .build();
    }

    @Override
    protected String getTableName() {
        return Constant.HOA_DON_TABLE_NAME; // Sử dụng hằng số đúng cho tên bảng
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return Constant.HOA_DON_NO; // Sử dụng hằng số đúng cho cột khóa chính
    }

    @Override
    public Object[] getEntityValues(HoaDon entity) {
        return new Object[] {
            entity.getMaHoaDon(),
            entity.getMaKhachHang(),
            entity.getMaNhanVien(),
            entity.getNgayLap(),
            entity.isTrangThai()
        };
    }

    @Override
    protected String getInsertQuery() {
        return """
                INSERT INTO """ + Constant.HOA_DON_TABLE_NAME + """
                (maHoaDon, maKhachHang, maNhanVien, ngayLap, trangThai)
                values (?,?,?,?,?);
                """;
    }

    @Override
    protected String getUpdateQuery() {
        return """
                UPDATE """ + Constant.HOA_DON_TABLE_NAME + """
                SET maHoaDon = ?, maKhachHang = ?, maNhanVien = ?, ngayLap = ?, trangThai = ?
                WHERE """ + Constant.HOA_DON_NO + " = ?";
    }

    @Override
    public long getMaxMaSanPham() throws SQLException {
        String sql = "SELECT COUNT(1) AS MAX_MA_HOADON FROM " + getTableName();

        try (PreparedStatement stmt = AbstractCrudDao.connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("MAX_MA_HOADON");
            }
        }
        return 0L;
    }

    @Override
    public void save(HoaDon hoaDon, List<HoaDonChiTiet> hoaDonChiTietList) throws SQLException {
        String insertHoaDonChiTietSql = """
        INSERT INTO HoaDonChiTiet(maHDCT, maHoaDon, maSanPham, giaBan, soLuong, ghiChu)
        VALUES (?, ?, ?, ?, ?, ?);
        """;

        try (PreparedStatement hoaDonStmt = connection.prepareStatement(getInsertQuery());
                PreparedStatement chiTietStmt = connection.prepareStatement(insertHoaDonChiTietSql)) {

            // Insert into HoaDon table
            hoaDonStmt.setString(1, hoaDon.getMaHoaDon());
            hoaDonStmt.setString(2, hoaDon.getMaKhachHang());
            hoaDonStmt.setString(3, hoaDon.getMaNhanVien());
            hoaDonStmt.setDate(4, new java.sql.Date(hoaDon.getNgayLap().getTime()));
            hoaDonStmt.setBoolean(5, hoaDon.isTrangThai());
            hoaDonStmt.executeUpdate();

            // Insert into HoaDonChiTiet table
            for (HoaDonChiTiet chiTiet : hoaDonChiTietList) {
                chiTietStmt.setString(1, chiTiet.getMaHDCT());
                chiTietStmt.setString(2, chiTiet.getMaHoaDon());
                chiTietStmt.setString(3, chiTiet.getMaSanPham());
                chiTietStmt.setDouble(4, chiTiet.getGiaBan());
                chiTietStmt.setInt(5, chiTiet.getSoLuong());
                chiTietStmt.setString(6, chiTiet.getGhiChu());
                chiTietStmt.addBatch();
            }
            chiTietStmt.executeBatch();
        } catch (SQLException e) {
            throw new SQLException("Failed to create invoice and details", e);
        }
    }
 
   @Override
   public List<HoaDon> selectByKeyword(String keyword) {
    String sql = """
        SELECT * FROM HoaDon 
        WHERE maHoaDon LIKE ? 
        OR maKhachHang LIKE ? 
        OR maNhanVien LIKE ? 
        """;
    String wildcardKeyword = "%" + keyword + "%";
    
    return select(sql, wildcardKeyword, wildcardKeyword, wildcardKeyword);
}


    private List<HoaDon> select(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to execute query.", e);
        }
        return list;
    }
    
public List<HoaDon> selectByDate(Date startDate) {
    String sql = "SELECT * FROM HoaDon WHERE ngayLap = ?";
    List<Object> params = new ArrayList<>();
    params.add(new java.sql.Date(startDate.getTime()));

    return select(sql, params.toArray());
}

public List<HoaDon> selectByTrangThai(Boolean hoanThanh, Boolean daHuy) {
    StringBuilder sql = new StringBuilder("SELECT * FROM HoaDon WHERE 1=1");
    List<Object> params = new ArrayList<>();

    if (hoanThanh != null && hoanThanh) {
        sql.append(" AND trangThai = 0");
    }

    if (daHuy != null && daHuy) {
        sql.append(" AND trangThai = 1");
    }

    return select(sql.toString(), params.toArray());
}


public List<HoaDon> selectByNguoiBan(String maNhanVien) {
    List<HoaDon> list = new ArrayList<>();
    String sql = "SELECT * FROM HoaDon WHERE maNhanVien = ?";

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, maNhanVien);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaHoaDon(rs.getString("maHoaDon"));
            hoaDon.setMaKhachHang(rs.getString("maKhachHang"));
            hoaDon.setMaNhanVien(rs.getString("maNhanVien"));
            hoaDon.setNgayLap(rs.getDate("ngayLap"));
            hoaDon.setTrangThai(rs.getBoolean("trangThai")); // Assuming trangThai is a boolean
            list.add(hoaDon);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

public Map<String, String> getSalesAndManagerPeople() {
    Map<String, String> people = new HashMap<>();
    String sql = "SELECT maNhanVien, tenNhanVien, hoNhanVien FROM NhanVien WHERE chucVu LIKE ? OR chucVu LIKE ?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, "%Quản trị viên%");
        stmt.setString(2, "%Nhân viên bán hàng%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String fullName = rs.getString("hoNhanVien") + " " + rs.getString("tenNhanVien");
            people.put(fullName, rs.getString("maNhanVien"));
        }
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Failed to get sales and manager people.", e);
    }
    return people;
}




}