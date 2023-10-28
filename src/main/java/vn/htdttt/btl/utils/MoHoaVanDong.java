package vn.htdttt.btl.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.htdttt.btl.consts.LoaiCanNang;
import vn.htdttt.btl.consts.LoaiChieuCao;
import vn.htdttt.btl.consts.LoaiVanDong;
import vn.htdttt.btl.consts.TheChat;
import vn.htdttt.btl.domain.LoaiBaiTap;
import vn.htdttt.btl.domain.LuatDinhDuong;
import vn.htdttt.btl.domain.LuatVanDong;
import vn.htdttt.btl.dto.FuzzyCanNang;
import vn.htdttt.btl.dto.FuzzyChieuCao;
import vn.htdttt.btl.dto.FuzzyDinhDuong;
import vn.htdttt.btl.dto.FuzzyVanDong;
import vn.htdttt.btl.repository.LuatVanDongRepository;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MoHoaVanDong {
    private final LuatVanDongRepository luatVanDongRepository;

    public FuzzyVanDong moHoaVanDong(FuzzyCanNang fuzzyCanNang, FuzzyChieuCao fuzzyChieuCao) {
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

        FuzzyVanDong fuzzyVanDong = new FuzzyVanDong();
        for(Map.Entry<String, Double> cao : chieuCao.entrySet()) {
            String colChieuCao = cao.getKey();
            double valChieuCao = cao.getValue();
            double epsilon = 1e-10;
            if(Math.abs(valChieuCao - 0.0) > epsilon) {
                for(Map.Entry<String, Double> nang : canNang.entrySet()) {
                    String colCanNang = nang.getKey();
                    double valCanNang = nang.getValue();
                    double fuzzyValue = Math.min(valChieuCao, valCanNang);
                    LuatVanDong luatVanDong = luatVanDongRepository.getByDtCanNangAndDtChieuCao(colCanNang, colChieuCao);
                    if(luatVanDong.getLoaiBaiTap().getLoai() == LoaiVanDong.PHAT_TRIEN_CHIEU_CAO.getType()) {
                        fuzzyVanDong.setPhatTrienChieuCao(Math.max(fuzzyVanDong.getPhatTrienChieuCao(), fuzzyValue));
                    } else if (luatVanDong.getLoaiBaiTap().getLoai() == LoaiVanDong.PHAT_TRIEN_CAN_NANG.getType()) {
                        fuzzyVanDong.setPhatTrienCanNang(Math.max(fuzzyVanDong.getPhatTrienCanNang(), fuzzyValue));
                    } else if (luatVanDong.getLoaiBaiTap().getLoai() == LoaiVanDong.GIAM_CAN.getType()) {
                        fuzzyVanDong.setGiamCam(Math.max(fuzzyVanDong.getGiamCam(), fuzzyValue));
                    } else if (luatVanDong.getLoaiBaiTap().getLoai() == LoaiVanDong.PHAT_TRIEN_TOAN_DIEN.getType()) {
                        fuzzyVanDong.setPhatTrienToanDien(Math.max(fuzzyVanDong.getPhatTrienToanDien(), fuzzyValue));
                    }
                }
            }
        }
        return fuzzyVanDong;
    }
}
