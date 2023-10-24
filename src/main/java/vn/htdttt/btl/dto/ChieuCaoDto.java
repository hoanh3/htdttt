package vn.htdttt.btl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.htdttt.btl.projection.ResultSet;

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

    public ChieuCaoDto(ResultSet resultSet) {
        this.ratThap = resultSet.getRatThap();
        this.thap = resultSet.getThap();
        this.trungBinh = resultSet.getTrungBinh();
        this.cao = resultSet.getCao();
        this.ratCao = resultSet.getRatCao();
    }
}
