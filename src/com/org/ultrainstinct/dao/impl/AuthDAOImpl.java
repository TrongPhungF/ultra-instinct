package com.org.ultrainstinct.dao.impl;

import com.org.ultrainstinct.dao.AbstractCrudDao;
import com.org.ultrainstinct.dao.AuthDAO;
import com.org.ultrainstinct.dto.UserSession;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>
 * AuthDAOImpl interface implements AuthDAO interface.
 * </p>
 *
 * @author MinhNgoc.
 */
public class AuthDAOImpl implements AuthDAO {
    
    @Override
    public boolean logIn(String userName, String password) throws SQLException {
        String sql = """
                SELECT maNhanVien, matKhau, chucVu, hoNhanVien, tenNhanVien FROM dbo.NhanVien
                WHERE maNhanVien = ? AND matKhau = ?
                """;

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = AbstractCrudDao.connection.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                // Kết hợp họ và tên thành full name
                String fullName = rs.getString("hoNhanVien") + " " + rs.getString("tenNhanVien");
                // Nếu đăng nhập thành công, khởi tạo UserSession với họ tên
                UserSession.getUser(userName, password, rs.getString("chucVu"), fullName);
                return true;
            }
            return false;
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }
    }

    @Override
    public void logOut() {
        UserSession.clear();
    }

    @Override
    public boolean isLogin() {
        UserSession userSession = UserSession.getUser();
        return ObjectUtils.isNotEmpty(userSession) && userSession.isLogin();
    }

    @Override
    public boolean isManager() {
        UserSession userSession = UserSession.getUser();
        return userSession != null && userSession.isManager();
    }

    @Override
    public boolean isSales() {
        UserSession userSession = UserSession.getUser();
        return userSession != null && userSession.isSales();
    }

    @Override
    public boolean isWarehouse() {
        UserSession userSession = UserSession.getUser();
        return userSession != null && userSession.isWarehouse();
    }

   

   
    
}
