package vn.htdttt.btl.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TheChat {
    SUY_DINH_DUONG_NANG(1),
    SUY_DINH_DUONG(2),
    BINH_THUONG(3),
    THUA_CAN(4),
    BEO_PHI(5);

    private final int mucDo;
}
