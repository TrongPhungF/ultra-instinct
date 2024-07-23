package com.org.ultrainstinct.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * PhieuNhapChiTiet class with table.
 * </p>
 *
 * @author MinhNgoc
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class PhieuNhapChiTiet implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long PNCTNo;

    private String maPNCT;

    private float giaNhap;

    private int soLuong;

    private String maSanPham;

    private String maNhaCungCap;

    private String maNhapKho;
}
