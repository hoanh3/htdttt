package vn.htdttt.btl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.htdttt.btl.domain.ThucDon;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThucDonDto {
    private int doTuoi;
    private String thucDonChiTiet;
    private String ghiChu;

    public ThucDonDto(ThucDon thucDon) {
        this.doTuoi = thucDon.getDoTuoi();
        this.thucDonChiTiet = thucDon.getThucDonChiTiet();
        this.ghiChu = thucDon.getGhiChu();
    }
}
