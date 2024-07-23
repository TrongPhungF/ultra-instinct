package com.org.ultrainstinct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * SearchSanPhamRequest relate to SanPham table in database.
 * </p>
 */
@Builder
@AllArgsConstructor
@Data
public class UserSession {

  private static UserSession user;

    private String userName;
    private String password;
    private String role;
    private String fullName;

    // Hằng số cho các vai trò
    public static final String ROLE_MANAGER = "Quản trị viên";
    public static final String ROLE_SALES = "Nhân viên bán hàng";
    public static final String ROLE_WAREHOUSE = "Nhân viên kho";

    // Hàm khởi tạo private để ngăn chặn tạo thể hiện bên ngoài
    private UserSession() {
    }

    // Phương thức tĩnh để trả về thể hiện duy nhất của lớp
    public static UserSession getUser() {
        if (user == null) {
            user = new UserSession();
        }
        return user;
    }

    public static UserSession getUser(String userName, String password, String role, String fullName) {
        if (user == null) {
            user = new UserSession();
        }
        user.setUserName(userName);
        user.setPassword(password);
        user.setRole(role);
        user.setFullName(fullName); // Set giá trị fullName
        return user;
    }

    public boolean isLogin() {
        return this.userName != null && !this.userName.isEmpty();
    }

    public boolean isManager() {
        return isLogin() && this.role.equals(ROLE_MANAGER);
    }

    public boolean isSales() {
        return isLogin() && this.role.equals(ROLE_SALES);
    }

    public boolean isWarehouse() {
        return isLogin() && this.role.equals(ROLE_WAREHOUSE);
    }

    public static void clear() {
        user = null;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
