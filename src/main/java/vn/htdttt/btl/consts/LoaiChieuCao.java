package vn.htdttt.btl.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoaiChieuCao {
    RAT_THAP("rat_thap"),
    THAP("thap"),
    TRUNG_BINH("trung_binh"),
    CAO("cao"),
    RAT_CAO("rat_cao");
    private final String columnName;
}
