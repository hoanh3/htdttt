package vn.htdttt.btl.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoaiCanNang {
    RAT_COI("rat_coi"),
    COI("coi"),
    TRUNG_BINH("trung_binh"),
    NANG("nang"),
    RAT_NANG("rat_nang");
    private final String columnName;
}
