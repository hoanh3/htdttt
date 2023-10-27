package vn.htdttt.btl.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoaiVanDong {
    PHAT_TRIEN_CHIEU_CAO(1),
    PHAT_TRIEN_CAN_NANG(2),
    GIAM_CAN(3),
    PHAT_TRIEN_TOAN_DIEN(4);

    private final int type;
}
