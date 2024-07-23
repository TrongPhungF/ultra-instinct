package com.org.ultrainstinct.dao;

import java.sql.SQLException;

import com.org.ultrainstinct.model.NhanVien;

/**
 * <p>
 * NhanVienDAO interface implements CRUDDao interface.
 * </p>
 *
 * @author MinhNgoc.
 */
public interface NhanVienDAO extends CRUDDao<NhanVien, Long> {

    /**
     * <p>
     * Method get max MaNhanVien.
     * </p>
     *
     * @return long.
     * @author MinhNgoc.
     */
    long getMaxMaNhanVien() throws SQLException;
}
