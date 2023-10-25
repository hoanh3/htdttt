package vn.htdttt.btl.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoaiCanNang {
    RAT_COI("rat_coi"),
    COI("coi"),
    TRUNG_BINH("trung_binh"),
    BEO("beo"),
    BEO_PHI("beo_phi");
    private final String columnName;
}
