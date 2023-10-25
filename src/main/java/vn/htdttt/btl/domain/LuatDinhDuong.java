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
@Table(name = "tbl_luatdinhduong")
public class LuatDinhDuong {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "dt_chieucao")
    private String dtChieuCao;
    @Column(name = "dt_cannang")
    private String dtCanNang;

    @ManyToOne
    @JoinColumn(name = "muc_do_dinh_duong")
    private TheChat mucDoDinhDuong;
}
