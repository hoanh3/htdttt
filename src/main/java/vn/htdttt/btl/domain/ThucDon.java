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
@Table(name = "tbl_thucdon")
public class ThucDon {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "do_tuoi")
    private int doTuoi;
    @Column(name = "thuc_don_chi_tiet")
    private String thucDonChiTiet;
    @Column(name = "ghi_chu")
    private String ghiChu;

    @ManyToOne
    @JoinColumn(name = "id_the_chat")
    private TheChat theChat;

}
