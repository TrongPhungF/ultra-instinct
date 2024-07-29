package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.chart.ModelChart;
import com.org.ultrainstinct.dao.SanPhamDAO;
import com.org.ultrainstinct.dao.AbstractCrudDao;
import com.org.ultrainstinct.model.SanPham;
import com.org.ultrainstinct.dto.SearchSanPhamDto;
import com.org.ultrainstinct.model.HoaDon;
import com.org.ultrainstinct.utils.Constant;
import java.beans.Statement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * SanPhamServiceImpl represents a concrete implementation of SanPhamDAO.
 * </p>
 *
 * @author MinhNgoc
 */
public class SanPhamDAOImpl extends AbstractCrudDao<SanPham, Long> implements SanPhamDAO {
    
private static final Logger LOGGER = Logger.getLogger(SanPhamDAOImpl.class.getName());
    /**
     * <p>
     * Method mapRow SanPham
     * </p>
     *
     * @param rs ResultSet
     * @return SanPham
     * @throws SQLException SQLException
     * @author MinhNgoc
     */
    @Override
    protected SanPham mapRow(ResultSet rs) throws SQLException {
        return SanPham.builder()
            .sanPhamNo(rs.getLong(Constant.SAN_PHAM_NO))
            .maSanPham(rs.getString("maSanPham"))
            .tenSanPham(rs.getString("tenSanPham"))
            .giaNiemYet(rs.getFloat("giaNiemYet"))
            .soLuongTon(rs.getInt("soLuongTon"))
            .hinh(rs.getString("hinh"))
            .loaiSanPham(rs.getString("maLoaiSanPham"))
            .build();
    }

    /**
     * <p>
     * Method getTableName table SanPham.
     * </p>
     *
     * @return String.
     * @author MinhNgoc.
     */
    @Override
    protected String getTableName() {
        return Constant.SAN_PHAM_TABLE_NAME;
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
        return Constant.SAN_PHAM_NO;
    }

    /**
     * <p>
     * Method getEntityValues.
     * </p>
     *
     * @param entity SanPham.
     * @return Object[].
     * @author MinhNgoc.
     */
    @Override
    protected Object[] getEntityValues(SanPham entity) {
        return new Object[]{
            entity.getMaSanPham(),
            entity.getLoaiSanPham(),
            entity.getTenSanPham(),
            entity.getGiaNiemYet(),
            entity.getSoLuongTon(),
            entity.getHinh(),
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
            """ + Constant.SAN_PHAM_TABLE_NAME + """
            (maSanPham, maLoaiSanPham, tenSanPham, giaNiemYet, soLuongTon, hinh) values (?,?,?,?,?,?);
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
        return "UPDATE " + Constant.SAN_PHAM_TABLE_NAME +
            " SET maSanPham = ?, maLoaiSanPham = ?, tenSanPham = ?, giaNiemYet = ?, soLuongTon = ?, hinh = ?  WHERE "
            + Constant.SAN_PHAM_NO + " = ?";
    }

    /**
     * <p>
     * Method get max maxMaSanPham.
     * </p>
     *
     * @return long maxMaSanPham.
     * @author MinhNgoc.
     */
    @Override
    public long getMaxMaSanPham() throws SQLException {
        String sql = " SELECT COUNT(1) MAX_MA_SANPHAM FROM " + getTableName();
        PreparedStatement stmt = AbstractCrudDao.connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getLong("MAX_MA_SANPHAM");
        }
        return 0L;
    }

    /**
     * <p>
     * Method searchSanPham.
     * </p>
     *
     * @param dto SearchSanPhamDto.
     * @return List of SanPham.
     * @author MinhNgoc.
     */
    @Override
    public List<SanPham> searchSanPham(SearchSanPhamDto dto) {
        StringBuilder sql = new StringBuilder("""
            SELECT SanPhamNo
                 , maSanPham
                 , maLoaiSanPham
                 , tenSanPham
                 , giaNiemYet
                 , soLuongTon
                 , hinh 
            FROM dbo.SanPham
            WHERE 1=1
            """);

        List<Object> params = new ArrayList<>();

        if (StringUtils.isNotBlank(dto.getMaSanPham())) {
            sql.append(" AND maSanPham = ? ");
            params.add(dto.getMaSanPham());
        }

        if (StringUtils.isNotBlank(dto.getTenSanPham())) {
            sql.append(" AND tenSanPham LIKE ? ");
            params.add("%" + dto.getTenSanPham() + "%");
        }

        if (StringUtils.isNotBlank(dto.getLoaiSanPham())) {
            sql.append(" AND maLoaiSanPham = ? ");
            params.add(dto.getLoaiSanPham());
        }

        if (ObjectUtils.isNotEmpty(dto.getGiaNiemYet())) {
            sql.append(" AND giaNiemYet = ? ");
            params.add(dto.getLoaiSanPham());
        }

        if (ObjectUtils.isNotEmpty(dto.getSoLuongTon())) {
            sql.append(" AND soLuongTon = ? ");
            params.add(dto.getLoaiSanPham());
        }

        if (StringUtils.isNotBlank(dto.getFreeText())) {
            sql.append("""
                AND (maSanPham = ? 
                     OR tenSanPham LIKE ? 
                     OR maLoaiSanPham = ? 
                     OR giaNiemYet = ? 
                     OR soLuongTon = ?) 
                """);
            params.add(dto.getFreeText());
            params.add("%" + dto.getFreeText() + "%");
            params.add(dto.getFreeText());
            params.add(dto.getFreeText());
            params.add(dto.getFreeText());
        }

        List<SanPham> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
   public List<SanPham> selectByKeyword(String keyword) {
        String sql = """
            SELECT * FROM SanPham 
            WHERE maSanPham LIKE ? 
            OR maLoaiSanPham LIKE ? 
            OR tenSanPham LIKE ? 
            OR giaNiemYet LIKE ?
        """;
        String wildcardKeyword = "%" + keyword + "%";

        return select(sql, wildcardKeyword, wildcardKeyword, wildcardKeyword, wildcardKeyword);
    }

    private List<SanPham> select(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
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
    @Override
    public List<SanPham> getBestSellingProducts(int limit) {
        String sql = """
        SELECT TOP (?) * FROM SanPham
        ORDER BY soLuongBan DESC
        """;
    List<SanPham> bestSellingProducts = new ArrayList<>();
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, limit);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            bestSellingProducts.add(mapRow(rs));
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to fetch best-selling products: " + e.getMessage());
    }
    return bestSellingProducts;
    }
    
    @Override
    public SanPham findByIdSP(String maSanPham) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE maSanPham = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, maSanPham);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Hoặc ném ngoại lệ nếu cần
    }
    public static List<ModelChart> fetchData() {
        List<ModelChart> data = new ArrayList<>();
        int currentYear = Year.now().getValue(); // Get the current year

        String sql = "SELECT month(hd.ngayLap) AS month, " +
                     "SUM(hdct.giaBan * hdct.soLuong) AS doanhThu, " +
                     "SUM(pnct.giaNhap * pnct.soLuong) AS chiPhi " +
                     "FROM HoaDonChiTiet hdct " +
                     "JOIN HoaDon hd ON hdct.maHoaDon = hd.maHoaDon " +
                     "JOIN SanPham sp ON hdct.maSanPham = sp.maSanPham " +
                     "LEFT JOIN PhieuNhapChiTiet pnct ON sp.maSanPham = pnct.maSanPham " +
                     "WHERE YEAR(hd.ngayLap) = ? " + // Filter by current year
                     "GROUP BY month(hd.ngayLap)";

        try (
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, currentYear); // Set the current year parameter
            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int month = rs.getInt("month");
                    double doanhThu = rs.getDouble("doanhThu");
                    double chiPhi = rs.getDouble("chiPhi");
                    double loiNhuan = doanhThu - chiPhi;
                    data.add(new ModelChart("Tháng " + month, new double[]{doanhThu, chiPhi, loiNhuan}));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
      public static List<ModelChart> fetchDataKH_DH() {
        List<ModelChart> data = new ArrayList<>();
        int currentYear = Year.now().getValue(); // Get the current year
        String sql = "SELECT MONTH(hd.ngayLap) AS month, " +
                     "COUNT(DISTINCT hd.maKhachHang) AS soLuongKhachHang, " +
                     "SUM(CASE WHEN hd.trangThai = 0 THEN 1 ELSE 0 END) AS soDonThanhCong, " +
                     "SUM(CASE WHEN hd.trangThai = 1 THEN 1 ELSE 0 END) AS soDonHuy, " +
                     "SUM(hdct.soLuong) AS soLuongSanPhamBanDuoc " +
                     "FROM HoaDon hd " +
                     "LEFT JOIN HoaDonChiTiet hdct ON hd.maHoaDon = hdct.maHoaDon " +
                     "WHERE  YEAR(hd.ngayLap) = ? " + // Filter by current year
                     "GROUP BY month(hd.ngayLap)";

        try (
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, currentYear); // Set the current year parameter
            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int month = rs.getInt("month");
                    int soLuongKhachHang = rs.getInt("soLuongKhachHang");
                    int soDonThanhCong = rs.getInt("soDonThanhCong");
                    int soDonHuy = rs.getInt("soDonHuy");
                    int soLuongSanPhamBanDuoc = rs.getInt("soLuongSanPhamBanDuoc");
                    data.add(new ModelChart("Tháng " + month, new double[]{soLuongKhachHang, soDonThanhCong, soDonHuy, soLuongSanPhamBanDuoc}));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
///////của taoooo đừng động vô
    @Override
    public HoaDon findById(String maHoaDon) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteById(String maSanPham) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(SanPham sanPham) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(SanPham sanPham) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SanPham> selectByKeywordSP(String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SanPham> selectByLoai(String loai) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
