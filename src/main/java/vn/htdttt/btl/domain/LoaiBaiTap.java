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
@Table(name = "tbl_loaibaitap")
public class LoaiBaiTap {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "loai")
    private int loai;
    @Column(name = "chuc_nang")
    private String chucNang;
}
