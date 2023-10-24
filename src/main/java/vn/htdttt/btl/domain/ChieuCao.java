package vn.htdttt.btl.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_chieucao")
public class ChieuCao {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "tuoi")
    private int tuoi;
    @Column(name = "gioi_tinh")
    private String gioiTinh;
    @Column(name = "rat_thap")
    private double ratThap;
    @Column(name = "thap")
    private double thap;
    @Column(name = "trung_binh")
    private double trungBinh;
    @Column(name = "cao")
    private double cao;
    @Column(name = "rat_cao")
    private double ratCao;
}
