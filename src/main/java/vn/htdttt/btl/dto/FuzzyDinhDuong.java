package vn.htdttt.btl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuzzyDinhDuong {
    private double muc1;
    private double muc2;
    private double muc3;
    private double muc4;
    private double muc5;

    public FuzzyDinhDuong() {
        this.muc1 = 0.0;
        this.muc2 = 0.0;
        this.muc3 = 0.0;
        this.muc4 = 0.0;
        this.muc5 = 0.0;
    }
}
