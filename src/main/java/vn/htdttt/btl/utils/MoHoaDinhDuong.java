package vn.htdttt.btl.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.htdttt.btl.consts.LoaiCanNang;
import vn.htdttt.btl.consts.LoaiChieuCao;
import vn.htdttt.btl.consts.TheChat;
import vn.htdttt.btl.domain.LuatDinhDuong;
import vn.htdttt.btl.dto.FuzzyCanNang;
import vn.htdttt.btl.dto.FuzzyChieuCao;
import vn.htdttt.btl.dto.FuzzyDinhDuong;
import vn.htdttt.btl.repository.LuatDinhDuongRepository;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MoHoaDinhDuong {
    private final LuatDinhDuongRepository luatDinhDuongRepository;

    public FuzzyDinhDuong moHoaDinhDuong(FuzzyCanNang fuzzyCanNang, FuzzyChieuCao fuzzyChieuCao) {
        HashMap<String, Double> chieuCao = new HashMap<>();
        chieuCao.put(LoaiChieuCao.RAT_THAP.getColumnName(), fuzzyChieuCao.getRatThap());
        chieuCao.put(LoaiChieuCao.THAP.getColumnName(), fuzzyChieuCao.getThap());
        chieuCao.put(LoaiChieuCao.TRUNG_BINH.getColumnName(), fuzzyChieuCao.getTrungBinh());
        chieuCao.put(LoaiChieuCao.CAO.getColumnName(), fuzzyChieuCao.getCao());
        chieuCao.put(LoaiChieuCao.RAT_CAO.getColumnName(), fuzzyChieuCao.getRatCao());

        HashMap<String, Double> canNang = new HashMap<>();
        canNang.put(LoaiCanNang.RAT_COI.getColumnName(), fuzzyCanNang.getRatCoi());
        canNang.put(LoaiCanNang.COI.getColumnName(), fuzzyCanNang.getCoi());
        canNang.put(LoaiCanNang.TRUNG_BINH.getColumnName(), fuzzyCanNang.getTrungBinh());
        canNang.put(LoaiCanNang.NANG.getColumnName(), fuzzyCanNang.getNang());
        canNang.put(LoaiCanNang.RAT_NANG.getColumnName(), fuzzyCanNang.getRatNang());

        FuzzyDinhDuong fuzzyDinhDuong = new FuzzyDinhDuong();
        for(Map.Entry<String, Double> cao : chieuCao.entrySet()) {
            String colChieuCao = cao.getKey();
            double valChieuCao = cao.getValue();
            double epsilon = 1e-10;
            if(Math.abs(valChieuCao - 0.0) > epsilon) {
                for(Map.Entry<String, Double> nang : canNang.entrySet()) {
                    String colCanNang = nang.getKey();
                    double valCanNang = nang.getValue();
                    double fuzzyValue = Math.min(valChieuCao, valCanNang);
                    LuatDinhDuong luatDinhDuong = luatDinhDuongRepository.getByDtCanNangAndDtChieuCao(colCanNang, colChieuCao);
                    if(luatDinhDuong.getMucDoDinhDuong().getMucDo() == TheChat.SUY_DINH_DUONG_NANG.getMucDo()) {
                        fuzzyDinhDuong.setMuc1(Math.max(fuzzyDinhDuong.getMuc1(), fuzzyValue));
                    } else if (luatDinhDuong.getMucDoDinhDuong().getMucDo() == TheChat.SUY_DINH_DUONG.getMucDo()) {
                        fuzzyDinhDuong.setMuc2(Math.max(fuzzyDinhDuong.getMuc2(), fuzzyValue));
                    } else if (luatDinhDuong.getMucDoDinhDuong().getMucDo() == TheChat.BINH_THUONG.getMucDo()) {
                        fuzzyDinhDuong.setMuc3(Math.max(fuzzyDinhDuong.getMuc3(), fuzzyValue));
                    } else if (luatDinhDuong.getMucDoDinhDuong().getMucDo() == TheChat.THUA_CAN.getMucDo()) {
                        fuzzyDinhDuong.setMuc4(Math.max(fuzzyDinhDuong.getMuc4(), fuzzyValue));
                    } else if (luatDinhDuong.getMucDoDinhDuong().getMucDo() == TheChat.BEO_PHI.getMucDo()) {
                        fuzzyDinhDuong.setMuc5(Math.max(fuzzyDinhDuong.getMuc5(), fuzzyValue));
                    }
                }
            }
        }
        return fuzzyDinhDuong;
    }
}
