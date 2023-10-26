package vn.htdttt.btl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.htdttt.btl.commons.AnswerDto;
import vn.htdttt.btl.consts.LoaiCanNang;
import vn.htdttt.btl.consts.LoaiChieuCao;
import vn.htdttt.btl.consts.QuyTacDinhDuong;
import vn.htdttt.btl.consts.TheChat;
import vn.htdttt.btl.domain.LuatDinhDuong;
import vn.htdttt.btl.dto.*;
import vn.htdttt.btl.repository.*;
import vn.htdttt.btl.service.NutritionConsultingService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class NutritionConsultingServiceImpl implements NutritionConsultingService {
    private final ChieuCaoRepository chieuCaoRepository;
    private final CanNangRepository canNangRepository;
    private final LuatDinhDuongRepository luatDinhDuongRepository;
    private final TheChatRepository theChatRepository;
    private final TuVanRepository tuVanRepository;

    @Override
    public List<AnswerDto> getResponse(InputDataDto inputDataDto) {
        int tuoi = inputDataDto.getTuoi();
        String gioiTinh = inputDataDto.getGioiTinh();
        ChieuCaoDto chieuCaoDto = chieuCaoRepository.getByTuoiAndGioiTinh(tuoi, gioiTinh);
        CanNangDto canNangDto = canNangRepository.getByTuoiAndGioiTinh(tuoi, gioiTinh);
        FuzzyChieuCao fuzzyChieuCao = getFuzzyChieuCao(chieuCaoDto, inputDataDto.getChieuCao());
        FuzzyCanNang fuzzyCanNang = getFuzzyCanNang(canNangDto, inputDataDto.getCanNang());
        FuzzyDinhDuong fuzzyDinhDuong = getFuzzyDinhDuong(fuzzyCanNang, fuzzyChieuCao);
        TheChatDto theChatDto = giaiMo(fuzzyDinhDuong);
        List<AnswerDto> result = new ArrayList<>();
        result.add(new AnswerDto(getTuVanTheChat(theChatDto)));
        TuVanDto tuVanDto = tuVanRepository.getByMucDo(theChatDto.getMucDo());
        result.add(new AnswerDto(tuVanDto.getLoiKhuyen()));
        result.add(new AnswerDto(tuVanDto.getThucPham()));
        result.add(new AnswerDto(tuVanDto.getChiTiet()));
        return result;
    }

    private String getTuVanTheChat(TheChatDto theChatDto) {
        String result = String.format("Dựa trên thông số bạn đưa ra, chúng tôi đánh giá thể chất thuộc mức: %s! <br> Cấp độ: %d.", theChatDto.getTenTheChat().toUpperCase(), theChatDto.getMucDo());;
        return result;
    }

    private TheChatDto giaiMo(FuzzyDinhDuong fuzzyDinhDuong) {
        double muc1 = fuzzyDinhDuong.getMuc1();
        double muc2 = fuzzyDinhDuong.getMuc2();
        double muc3 = fuzzyDinhDuong.getMuc3();
        double muc4 = fuzzyDinhDuong.getMuc4();
        double muc5 = fuzzyDinhDuong.getMuc5();
        double[] array = {muc1, muc2, muc3, muc4, muc5};
        double maxValue = Arrays.stream(array).max().getAsDouble();
        TheChatDto theChatDto = new TheChatDto();
        double x1 = 0.0;
        double x2 = 0.0;
        double quytac1 = QuyTacDinhDuong.SUY_DINH_DUONG_NANG.getGiaTriDinhDuong();
        double quytac2 = QuyTacDinhDuong.SUY_DINH_DUONG.getGiaTriDinhDuong();
        double quytac3 = QuyTacDinhDuong.BINH_THUONG.getGiaTriDinhDuong();
        double quytac4 = QuyTacDinhDuong.THUA_CAN.getGiaTriDinhDuong();
        double quytac5 = QuyTacDinhDuong.BEO_PHI.getGiaTriDinhDuong();
        if(maxValue == muc1) {
            x1 = muc1 * (quytac1 - 0) + 0;
            x2 = quytac2 - muc1 * (quytac2 - quytac1);
        } else if (maxValue == muc2) {
            x1 = muc2 * (quytac2 - quytac1) + quytac1;
            x2 = quytac3 - muc2 * (quytac3 - quytac2);
        } else if (maxValue == muc3) {
            x1 = muc3 * (quytac3 - quytac2) + quytac2;
            x2 = quytac4 - muc3 * (quytac4 - quytac3);
        } else if (maxValue == muc4) {
            x1 = muc4 * (quytac4 - quytac3) + quytac3;
            x2 = quytac5 - muc4 * (quytac5 - quytac4);
        } else if (maxValue == muc5) {
            x1 = muc5 * (quytac5 - quytac4) + quytac4;
            x2 = 1 - muc5 * (1 - quytac5);
        }
        double trungBinhMax = (x1 + x2) / 2.0;
        muc1 = 0.0;
        if(trungBinhMax >= 0 && trungBinhMax <= quytac1) {
            muc1 = (trungBinhMax - 0) / (quytac1- 0);
        } else if (trungBinhMax >= quytac1 && trungBinhMax <= quytac2) {
            muc1 = (quytac2 - trungBinhMax) / (quytac2 - quytac1);
        }

        muc2 = 0.0;
        if(trungBinhMax >=quytac1 && trungBinhMax <= quytac2) {
            muc2 = (trungBinhMax -quytac1) / (quytac2 -quytac1);
        } else if (trungBinhMax >= quytac2 && trungBinhMax <= quytac3) {
            muc2 = (quytac3 - trungBinhMax) / (quytac3 - quytac2);
        }
        muc3 = 0.0;
        if(trungBinhMax >= quytac2 && trungBinhMax <= quytac3) {
            muc3 = (trungBinhMax - quytac2) / (quytac3 - quytac2);
        } else if (trungBinhMax >= quytac3 && trungBinhMax <= quytac4) {
            muc3 = (quytac4 - trungBinhMax) / (quytac4 - quytac3);
        }
        muc4 = 0.0;
        if(trungBinhMax >= quytac3 && trungBinhMax <= quytac4) {
            muc4 = (trungBinhMax - quytac3) / (quytac4 - quytac3);
        } else if (trungBinhMax >= quytac4 && trungBinhMax <= quytac5) {
            muc4 = (quytac5 - trungBinhMax) / (quytac5 - quytac4);
        }
        muc5 = 0.0;
        if(trungBinhMax >= quytac4 && trungBinhMax <= quytac5) {
            muc5 = (trungBinhMax - quytac4) / (quytac5 - quytac4);
        } else if (trungBinhMax >= quytac5 && trungBinhMax <= 1) {
            muc5 = (1 - trungBinhMax) / (1 - quytac5);
        }
        double[] arrayDecision = {muc1, muc2, muc3, muc4, muc5};
        double decision = Arrays.stream(arrayDecision).max().getAsDouble();
        if(decision == muc1) {
            theChatDto = theChatRepository.getByMucDo(TheChat.SUY_DINH_DUONG_NANG.getMucDo());
        } else if (decision == muc2) {
            theChatDto = theChatRepository.getByMucDo(TheChat.SUY_DINH_DUONG.getMucDo());
        } else if (decision == muc3) {
            theChatDto = theChatRepository.getByMucDo(TheChat.BINH_THUONG.getMucDo());
        } else if (decision == muc4) {
            theChatDto = theChatRepository.getByMucDo(TheChat.THUA_CAN.getMucDo());
        } else if (decision == muc5) {
            theChatDto = theChatRepository.getByMucDo(TheChat.BEO_PHI.getMucDo());
        }
        return theChatDto;
    }

    private FuzzyDinhDuong getFuzzyDinhDuong(FuzzyCanNang fuzzyCanNang, FuzzyChieuCao fuzzyChieuCao) {
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

    private FuzzyChieuCao getFuzzyChieuCao(ChieuCaoDto chieuCaoDto, double chieuCao) {
        FuzzyChieuCao fuzzyChieuCao = new FuzzyChieuCao();

        double ratThap = 0.0;
        if (chieuCao <= chieuCaoDto.getRatThap()) {
            ratThap = 1.0;
        } else if (chieuCao <= chieuCaoDto.getThap()) {
            ratThap = ((chieuCaoDto.getThap()) - chieuCao)
                    / ((chieuCaoDto.getThap()) - chieuCaoDto.getRatThap());
        } else {
            ratThap = 0.0;
        }

        double thap = 0.0;
        if (chieuCao >= chieuCaoDto.getRatThap() && chieuCao < chieuCaoDto.getThap()) {
            thap = (chieuCao - (chieuCaoDto.getRatThap()))
                    / (chieuCaoDto.getThap() - (chieuCaoDto.getRatThap()));
        } else if (chieuCao <= chieuCaoDto.getTrungBinh() && chieuCao >= chieuCaoDto.getThap()) {
            thap = ((chieuCaoDto.getTrungBinh()) - chieuCao)
                    / ((chieuCaoDto.getTrungBinh()) - chieuCaoDto.getThap());
        } else {
            thap = 0.0;
        }

        double trungBinh = 0.0;
        if (chieuCao >= chieuCaoDto.getThap() && chieuCao < chieuCaoDto.getTrungBinh()) {
            trungBinh = (chieuCao - (chieuCaoDto.getThap()))
                    / (chieuCaoDto.getTrungBinh() - (chieuCaoDto.getThap()));
        } else if (chieuCao <= chieuCaoDto.getCao() && chieuCao >= chieuCaoDto.getTrungBinh()) {
            trungBinh = ((chieuCaoDto.getCao()) - chieuCao)
                    / ((chieuCaoDto.getCao()) - chieuCaoDto.getTrungBinh());
        } else {
            trungBinh = 0.0;
        }

        double cao = 0.0;
        if (chieuCao >= chieuCaoDto.getTrungBinh() && chieuCao < chieuCaoDto.getCao()) {
            cao = (chieuCao - (chieuCaoDto.getTrungBinh()))
                    / (chieuCaoDto.getCao() - (chieuCaoDto.getTrungBinh()));
        } else if (chieuCao <= chieuCaoDto.getRatCao() && chieuCao >= chieuCaoDto.getCao()) {
            cao = ((chieuCaoDto.getRatCao()) - chieuCao)
                    / ((chieuCaoDto.getRatCao()) - chieuCaoDto.getCao());
        } else {
            cao = 0.0;
        }

        double ratCao = 0.0;
        if (chieuCao >= chieuCaoDto.getCao() && chieuCao < chieuCaoDto.getRatCao()) {
            ratCao = (chieuCao - (chieuCaoDto.getCao()))
                    / (chieuCaoDto.getRatCao() - (chieuCaoDto.getCao()));
        } else if(chieuCao >= chieuCaoDto.getRatCao()){
            ratCao = 1.0;
        }
        fuzzyChieuCao.setRatCao(ratCao);
        fuzzyChieuCao.setCao(cao);
        fuzzyChieuCao.setTrungBinh(trungBinh);
        fuzzyChieuCao.setThap(thap);
        fuzzyChieuCao.setRatThap(ratThap);
        return fuzzyChieuCao;
    }

    private FuzzyCanNang getFuzzyCanNang(CanNangDto canNangDto, double canNang) {
        FuzzyCanNang fuzzyCanNang = new FuzzyCanNang();

        double ratCoi = 0.0;
        if (canNang <= canNangDto.getRatCoi()) {
            ratCoi = 1.0;
        } else if (canNang <= canNangDto.getCoi()) {
            ratCoi = ((canNangDto.getCoi()) - canNang)
                    / ((canNangDto.getCoi()) - canNangDto.getRatCoi());
        } else {
            ratCoi = 0.0;
        }

        double coi = 0.0;
        if (canNang >= canNangDto.getRatCoi() && canNang < canNangDto.getCoi()) {
            coi = (canNang - (canNangDto.getRatCoi()))
                    / (canNangDto.getCoi() - (canNangDto.getRatCoi()));
        } else if (canNang <= canNangDto.getTrungBinh() && canNang >= canNangDto.getCoi()) {
            coi = ((canNangDto.getTrungBinh()) - canNang)
                    / ((canNangDto.getTrungBinh()) - canNangDto.getCoi());
        } else {
            coi = 0.0;
        }

        double trungBinh = 0.0;
        if (canNang >= canNangDto.getCoi() && canNang < canNangDto.getTrungBinh()) {
            trungBinh = (canNang - (canNangDto.getCoi()))
                    / (canNangDto.getTrungBinh() - (canNangDto.getCoi()));
        } else if (canNang <= canNangDto.getNang() && canNang >= canNangDto.getTrungBinh()) {
            trungBinh = ((canNangDto.getNang()) - canNang)
                    / ((canNangDto.getNang()) - canNangDto.getTrungBinh());
        } else {
            trungBinh = 0.0;
        }

        double nang = 0.0;
        if (canNang >= canNangDto.getTrungBinh() && canNang < canNangDto.getNang()) {
            nang = (canNang - (canNangDto.getTrungBinh()))
                    / (canNangDto.getNang() - (canNangDto.getTrungBinh()));
        } else if (canNang <= canNangDto.getRatNang() && canNang >= canNangDto.getNang()) {
            nang = ((canNangDto.getRatNang()) - canNang)
                    / ((canNangDto.getRatNang()) - canNangDto.getNang());
        } else {
            nang = 0.0;
        }

        double ratNang = 0.0;
        if (canNang >= canNangDto.getNang() && canNang < canNangDto.getRatNang()) {
            ratNang = (canNang - (canNangDto.getNang()))
                    / (canNangDto.getRatNang() - (canNangDto.getNang()));
        } else if (canNang >= canNangDto.getRatNang()){
            ratNang = 1.0;
        }
        fuzzyCanNang.setRatNang(ratNang);
        fuzzyCanNang.setNang(nang);
        fuzzyCanNang.setTrungBinh(trungBinh);
        fuzzyCanNang.setCoi(coi);
        fuzzyCanNang.setRatCoi(ratCoi);
        return fuzzyCanNang;
    }
}
