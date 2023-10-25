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
@Table(name = "tbl_thechat")
public class TheChat {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "ten_the_chat")
    private String tenTheChat;
    @Column(name = "muc_do")
    private int mucDo;
}
