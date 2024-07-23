package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.dao.SanPhamDAO;
import com.org.ultrainstinct.dao.AbstractCrudDao;
import com.org.ultrainstinct.model.SanPham;
import com.org.ultrainstinct.dto.SearchSanPhamDto;
import com.org.ultrainstinct.utils.Constant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * SanPhamServiceImpl represents a concrete implementation of SanPhamDAO.
 * </p>
 *
 * @author MinhNgoc
 */
public class SanPhamDAOImpl extends AbstractCrudDao<SanPham, Long> implements SanPhamDAO {

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
}
