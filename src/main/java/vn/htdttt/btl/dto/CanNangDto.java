package vn.htdttt.btl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.htdttt.btl.domain.CanNang;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CanNangDto {
    private double ratCoi;
    private double coi;
    private double trungBinh;
    private double nang;
    private double ratNang;

    public CanNangDto(CanNang canNang) {
        this.ratCoi = canNang.getRatCoi();
        this.coi = canNang.getCoi();
        this.trungBinh = canNang.getTrungBinh();
        this.nang = canNang.getNang();
        this.ratNang = canNang.getRatNang();
    }
}
