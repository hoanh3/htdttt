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
@Table(name = "tbl_tuvan")
public class TuVan {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "do_tuoi_tu")
    private int doTuoiTu;
    @Column(name = "do_tuoi_den")
    private int doTuoiDen;
    @Column(name = "thuc_pham")
    private String thucPham;
    @Column(name = "loi_khuyen")
    private String loiKhuyen;
    @Column(name = "chi_tiet")
    private String chiTiet;

    @ManyToOne
    @JoinColumn(name = "id_thechat")
    private TheChat theChat;
}
