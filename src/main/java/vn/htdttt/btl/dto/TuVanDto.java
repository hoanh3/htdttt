package vn.htdttt.btl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.htdttt.btl.consts.TheChat;
import vn.htdttt.btl.domain.TuVan;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TuVanDto {
    private String thucPham;
    private String loiKhuyen;
    private String chiTiet;
    private String theChat;

    public TuVanDto(TuVan tuVan) {
        this.thucPham = tuVan.getThucPham();
        this.loiKhuyen = tuVan.getLoiKhuyen();
        this.chiTiet = tuVan.getChiTiet();
        this.theChat = tuVan.getTheChat().getTenTheChat();
    }
}
