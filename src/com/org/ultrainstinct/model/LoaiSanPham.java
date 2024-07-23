package com.org.ultrainstinct.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * LoaiSanPham class with table.
 * </p>
 *
 * @author MinhNgoc
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class LoaiSanPham implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long loaiSanPhamNo;

    private String maLoaiSanPham;

    private String tenLoai;

    private String moTa;
}