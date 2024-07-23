package com.org.ultrainstinct.dao;

import java.sql.SQLException;
import java.util.List;

import com.org.ultrainstinct.model.SanPham;
import com.org.ultrainstinct.dto.SearchSanPhamDto;

/**
 * <p>
 * SanPhamDAO interface implements CRUDDao interface.
 * </p>
 *
 * @author MinhNgoc.
 */
public interface SanPhamDAO extends CRUDDao<SanPham, Long> {

    /**
     * <p>
     * Method get max MaSanPham.
     * </p>
     *
     * @return long.
     * @author MinhNgoc.
     */
    long getMaxMaSanPham() throws SQLException;

    /**
     * <p>
     * Method searchSanPham.
     * </p>
     *
     * @param searchSanPhamDto SearchSanPhamDto
     * @return List of SanPham.
     * @author MinhNgoc
     */
    List<SanPham> searchSanPham(SearchSanPhamDto searchSanPhamDto);
}
