package vn.htdttt.btl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InputDataDto {
    private double chieuCao;
    private double canNang;
    private int tuoi;
    private String gioiTinh;
}
