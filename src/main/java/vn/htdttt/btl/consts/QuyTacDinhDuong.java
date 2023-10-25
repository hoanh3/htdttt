package vn.htdttt.btl.consts;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuyTacDinhDuong {
    SUY_DINH_DUONG_NANG(0.15),
    SUY_DINH_DUONG(0.3),
    BINH_THUONG(0.45),
    THUA_CAN(0.6),
    BEO_PHI(0.7);

    private final double giaTriDinhDuong;
}
