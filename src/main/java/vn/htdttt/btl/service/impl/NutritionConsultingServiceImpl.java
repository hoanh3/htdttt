package vn.htdttt.btl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.htdttt.btl.commons.AnswerDto;
import vn.htdttt.btl.consts.QuyTacDinhDuong;
import vn.htdttt.btl.consts.TheChat;
import vn.htdttt.btl.dto.*;
import vn.htdttt.btl.repository.*;
import vn.htdttt.btl.service.NutritionConsultingService;
import vn.htdttt.btl.utils.DinhDangPhanHoi;
import vn.htdttt.btl.utils.MoHoaCanNang;
import vn.htdttt.btl.utils.MoHoaChieuCao;
import vn.htdttt.btl.utils.MoHoaDinhDuong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NutritionConsultingServiceImpl implements NutritionConsultingService {
    private final ChieuCaoRepository chieuCaoRepository;
    private final CanNangRepository canNangRepository;
    private final TheChatRepository theChatRepository;
    private final TuVanRepository tuVanRepository;
    private final MoHoaChieuCao moHoaChieuCao;
    private final MoHoaCanNang moHoaCanNang;
    private final MoHoaDinhDuong moHoaDinhDuong;
    private final DinhDangPhanHoi dinhDangPhanHoi;
    private final ThucDonRepository thucDonRepository;

    @Override
    public List<AnswerDto> getResponse(InputDataDto inputDataDto) {
        int tuoi = inputDataDto.getTuoi();
        String gioiTinh = inputDataDto.getGioiTinh();
        ChieuCaoDto chieuCaoDto = chieuCaoRepository.getByTuoiAndGioiTinh(tuoi, gioiTinh);
        CanNangDto canNangDto = canNangRepository.getByTuoiAndGioiTinh(tuoi, gioiTinh);

        FuzzyChieuCao fuzzyChieuCao = moHoaChieuCao.moHoaChieuCao(chieuCaoDto, inputDataDto.getChieuCao());
        FuzzyCanNang fuzzyCanNang = moHoaCanNang.moHoaCanNang(canNangDto, inputDataDto.getCanNang());
        FuzzyDinhDuong fuzzyDinhDuong = moHoaDinhDuong.moHoaDinhDuong(fuzzyCanNang, fuzzyChieuCao);

        TheChatDto theChatDto = giaiMo(fuzzyDinhDuong);

        List<AnswerDto> result = new ArrayList<>();
        result.add(new AnswerDto(dinhDangPhanHoi.getTuVanTheChat(theChatDto)));
        TuVanDto tuVanDto = tuVanRepository.getByMucDoAndTuoi(theChatDto.getMucDo(), tuoi);
        result.add(new AnswerDto(tuVanDto.getLoiKhuyen()));
        result.add(new AnswerDto(tuVanDto.getThucPham()));
        result.add(new AnswerDto(tuVanDto.getChiTiet()));
        ThucDonDto thucDonDto = thucDonRepository.getByDoTuoiAndIdTheChat(tuoi, theChatDto.getMucDo());
        if (!ObjectUtils.isEmpty(thucDonDto)) {
            String thucDon = thucDonDto.getThucDonChiTiet() + " " + thucDonDto.getGhiChu();
            result.add(new AnswerDto(thucDon));
        }
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
}
