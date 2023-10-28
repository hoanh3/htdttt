package vn.htdttt.btl.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_baitapvandong")
public class BaiTapVanDong {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "do_tuoi_tu")
    private int doTuoiTu;
    @Column(name = "do_tuoi_den")
    private int doTuoiDen;
    @Column(name = "ten_bai_tap")
    private String tenBaiTao;
    @Column(name = "mo_ta")
    private String moTa;
    @Column(name = "thoi_gian")
    private int thoiGian;
    @ManyToOne
    @JoinColumn(name = "id_loaibaitap")
    private LoaiBaiTap loaiBaiTap;
}
