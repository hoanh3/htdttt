package vn.htdttt.btl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.htdttt.btl.commons.AnswerDto;
import vn.htdttt.btl.consts.LoaiVanDong;
import vn.htdttt.btl.consts.QuyTacVanDong;
import vn.htdttt.btl.dto.*;
import vn.htdttt.btl.repository.BaiTapVanDongRepository;
import vn.htdttt.btl.repository.CanNangRepository;
import vn.htdttt.btl.repository.ChieuCaoRepository;
import vn.htdttt.btl.repository.LoaiBaiTapRepository;
import vn.htdttt.btl.service.PhysicalActivityConsultingService;
import vn.htdttt.btl.utils.DinhDangPhanHoi;
import vn.htdttt.btl.utils.MoHoaCanNang;
import vn.htdttt.btl.utils.MoHoaChieuCao;
import vn.htdttt.btl.utils.MoHoaVanDong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhysicalActivityConsultingServiceImpl implements PhysicalActivityConsultingService {
    private final ChieuCaoRepository chieuCaoRepository;
    private final CanNangRepository canNangRepository;
    private final MoHoaChieuCao moHoaChieuCao;
    private final MoHoaCanNang moHoaCanNang;
    private final MoHoaVanDong moHoaVanDong;
    private final LoaiBaiTapRepository loaiBaiTapRepository;
    private final DinhDangPhanHoi dinhDangPhanHoi;
    private final BaiTapVanDongRepository baiTapVanDongRepository;

    @Override
    public List<AnswerDto> getResponse(InputDataDto inputDataDto) {
        int tuoi = inputDataDto.getTuoi();
        String gioiTinh = inputDataDto.getGioiTinh();
        ChieuCaoDto chieuCaoDto = chieuCaoRepository.getByTuoiAndGioiTinh(tuoi, gioiTinh);
        CanNangDto canNangDto = canNangRepository.getByTuoiAndGioiTinh(tuoi, gioiTinh);

        FuzzyChieuCao fuzzyChieuCao = moHoaChieuCao.moHoaChieuCao(chieuCaoDto, inputDataDto.getChieuCao());
        FuzzyCanNang fuzzyCanNang = moHoaCanNang.moHoaCanNang(canNangDto, inputDataDto.getCanNang());
        FuzzyVanDong fuzzyVanDong = moHoaVanDong.moHoaVanDong(fuzzyCanNang, fuzzyChieuCao);

        LoaiBaiTapDto loaiBaiTapDto = giaiMo(fuzzyVanDong);

        List<AnswerDto> result = new ArrayList<>();
        result.add(new AnswerDto(dinhDangPhanHoi.getTuVanVanDong(loaiBaiTapDto)));
        Page<BaiTapVanDongDto> baiTapVanDongDtos = baiTapVanDongRepository
                .getByDoTuoiAAndLoaiBaiTap(tuoi, loaiBaiTapDto.getLoai(), PageRequest.of(0, 3));
        baiTapVanDongDtos.forEach(baiTap -> {
            String tuVan = baiTap.getTenBaiTap() + " "
                    + baiTap.getMoTa()
                    + "<br> &#9658 Thời gian thực hiện: "
                    + baiTap.getThoiGian() + "phút.";
            result.add(new AnswerDto(tuVan));
        });
        return result;
    }

    private LoaiBaiTapDto giaiMo(FuzzyVanDong fuzzyVanDong) {
        double phatTrienChieuCao = fuzzyVanDong.getPhatTrienChieuCao();
        double phatTrienCanNang = fuzzyVanDong.getPhatTrienCanNang();
        double giamCam = fuzzyVanDong.getGiamCam();
        double phatTrienToanDien = fuzzyVanDong.getPhatTrienToanDien();
        double[] array = {phatTrienChieuCao, phatTrienCanNang, giamCam, phatTrienToanDien};
        double maxValue = Arrays.stream(array).max().getAsDouble();
        LoaiBaiTapDto loaiBaiTap = new LoaiBaiTapDto();
        double x1 = 0.0;
        double x2 = 0.0;
        double quytac1 = QuyTacVanDong.PHAT_TRIEN_CHIEU_CAO.getGiaTriVanDong();
        double quytac2 = QuyTacVanDong.PHAT_TRIEN_CAN_NANG.getGiaTriVanDong();
        double quytac3 = QuyTacVanDong.GIAM_CAN.getGiaTriVanDong();
        double quytac4 = QuyTacVanDong.PHAT_TRIEN_TOAN_DIEN.getGiaTriVanDong();
        if(maxValue == phatTrienChieuCao) {
            x1 = phatTrienChieuCao * (quytac1 - 0) + 0;
            x2 = quytac2 - phatTrienChieuCao * (quytac2 - quytac1);
        } else if (maxValue == phatTrienCanNang) {
            x1 = phatTrienCanNang * (quytac2 - quytac1) + quytac1;
            x2 = quytac3 - phatTrienCanNang * (quytac3 - quytac2);
        } else if (maxValue == giamCam) {
            x1 = giamCam * (quytac3 - quytac2) + quytac2;
            x2 = quytac4 - giamCam * (quytac4 - quytac3);
        } else if (maxValue == phatTrienToanDien) {
            x1 = phatTrienToanDien * (quytac4 - quytac3) + quytac3;
            x2 = 1 - phatTrienToanDien * (1 - quytac4);
        }
        double trungBinhMax = (x1 + x2) / 2.0;

        phatTrienChieuCao = 0.0;
        if(trungBinhMax >= 0 && trungBinhMax <= quytac1) {
            phatTrienChieuCao = (trungBinhMax - 0) / (quytac1- 0);
        } else if (trungBinhMax >= quytac1 && trungBinhMax <= quytac2) {
            phatTrienChieuCao = (quytac2 - trungBinhMax) / (quytac2 - quytac1);
        }
        phatTrienCanNang = 0.0;
        if(trungBinhMax >=quytac1 && trungBinhMax <= quytac2) {
            phatTrienCanNang = (trungBinhMax -quytac1) / (quytac2 -quytac1);
        } else if (trungBinhMax >= quytac2 && trungBinhMax <= quytac3) {
            phatTrienCanNang = (quytac3 - trungBinhMax) / (quytac3 - quytac2);
        }
        giamCam = 0.0;
        if(trungBinhMax >= quytac2 && trungBinhMax <= quytac3) {
            giamCam = (trungBinhMax - quytac2) / (quytac3 - quytac2);
        } else if (trungBinhMax >= quytac3 && trungBinhMax <= quytac4) {
            giamCam = (quytac4 - trungBinhMax) / (quytac4 - quytac3);
        }
        phatTrienToanDien = 0.0;
        if(trungBinhMax >= quytac3 && trungBinhMax <= quytac4) {
            phatTrienToanDien = (trungBinhMax - quytac3) / (quytac4 - quytac3);
        } else if (trungBinhMax >= quytac4 && trungBinhMax <= 1) {
            phatTrienToanDien = (1 - trungBinhMax) / (1 - quytac4);
        }

        double[] arrayDecision = {phatTrienChieuCao, phatTrienCanNang, giamCam, phatTrienToanDien};
        double decision = Arrays.stream(arrayDecision).max().getAsDouble();
        if(decision == phatTrienChieuCao) {
            loaiBaiTap = loaiBaiTapRepository.getByLoai(LoaiVanDong.PHAT_TRIEN_CHIEU_CAO.getType());
        } else if (decision == phatTrienCanNang) {
            loaiBaiTap = loaiBaiTapRepository.getByLoai(LoaiVanDong.PHAT_TRIEN_CAN_NANG.getType());
        } else if (decision == giamCam) {
            loaiBaiTap = loaiBaiTapRepository.getByLoai(LoaiVanDong.GIAM_CAN.getType());
        } else if (decision == phatTrienToanDien) {
            loaiBaiTap = loaiBaiTapRepository.getByLoai(LoaiVanDong.PHAT_TRIEN_TOAN_DIEN.getType());
        }
        return loaiBaiTap;
    }
}
