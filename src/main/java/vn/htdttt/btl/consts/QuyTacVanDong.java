package vn.htdttt.btl.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuyTacVanDong {
    PHAT_TRIEN_CHIEU_CAO(0.2),
    PHAT_TRIEN_CAN_NANG(0.4),
    GIAM_CAN(0.6),
    PHAT_TRIEN_TOAN_DIEN(0.8);

    private final double giaTriVanDong;
}
