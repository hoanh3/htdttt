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
public class CanNangDto {
    private double ratCoi;
    private double coi;
    private double trungBinh;
    private double beo;
    private double beoPhi;

    public CanNangDto(ResultSet resultSet) {
        this.ratCoi = resultSet.getRatCoi();
        this.coi = resultSet.getCoi();
        this.trungBinh = resultSet.getTrungBinh();
        this.beo = resultSet.getBeo();
        this.beoPhi = resultSet.getBeoPhi();
    }
}
