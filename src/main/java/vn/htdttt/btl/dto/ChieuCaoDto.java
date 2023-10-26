package vn.htdttt.btl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.htdttt.btl.domain.ChieuCao;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChieuCaoDto {
    private double ratThap;
    private double thap;
    private double trungBinh;
    private double cao;
    private double ratCao;

    public ChieuCaoDto(ChieuCao chieuCao) {
        this.ratThap = chieuCao.getRatThap();
        this.thap = chieuCao.getThap();
        this.trungBinh = chieuCao.getTrungBinh();
        this.cao = chieuCao.getCao();
        this.ratCao = chieuCao.getRatCao();
    }
}
