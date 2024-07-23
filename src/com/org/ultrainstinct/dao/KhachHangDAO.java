package com.org.ultrainstinct.dao;

import java.sql.SQLException;

import com.org.ultrainstinct.model.KhachHang;

/**
 * <p>
 * KhachHangDAO interface implements CRUDDao interface.
 * </p>
 *
 * @author MinhNgoc.
 */
public interface KhachHangDAO extends CRUDDao<KhachHang, Long> {

    /**
     * <p>
     * Method get max MaKhachHang.
     * </p>
     *
     * @return long.
     * @author MinhNgoc.
     */
    long getMaxMaSanPham() throws SQLException;
     KhachHang findByMaKhachHang(String maKH);
}
