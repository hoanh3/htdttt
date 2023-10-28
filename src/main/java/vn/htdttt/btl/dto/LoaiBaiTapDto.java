package vn.htdttt.btl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.htdttt.btl.domain.LoaiBaiTap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoaiBaiTapDto {
    private int loai;
    private String chucNang;

    public LoaiBaiTapDto(LoaiBaiTap loaiBaiTap) {
        this.loai = loaiBaiTap.getLoai();
        this.chucNang = loaiBaiTap.getChucNang();
    }
}
