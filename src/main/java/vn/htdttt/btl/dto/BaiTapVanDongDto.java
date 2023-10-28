package vn.htdttt.btl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.htdttt.btl.domain.BaiTapVanDong;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaiTapVanDongDto {
    private String tenBaiTap;
    private String moTa;
    private int thoiGian;

    public BaiTapVanDongDto(BaiTapVanDong baiTapVanDong) {
        this.tenBaiTap = baiTapVanDong.getTenBaiTao();
        this.moTa = baiTapVanDong.getMoTa();
        this.thoiGian = baiTapVanDong.getThoiGian();
    }
}
