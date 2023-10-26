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
@Table(name = "tbl_cannang")
public class CanNang {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "tuoi")
    private int tuoi;
    @Column(name = "gioi_tinh")
    private String gioiTinh;
    @Column(name = "rat_coi")
    private double ratCoi;
    @Column(name = "coi")
    private double coi;
    @Column(name = "trung_binh")
    private double trungBinh;
    @Column(name = "nang")
    private double nang;
    @Column(name = "rat_nang")
    private double ratNang;
}
