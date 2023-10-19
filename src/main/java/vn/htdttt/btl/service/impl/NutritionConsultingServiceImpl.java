package vn.htdttt.btl.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Service;
import vn.htdttt.btl.consts.Consts;
import vn.htdttt.btl.dto.*;
import vn.htdttt.btl.projection.ResultSet;
import vn.htdttt.btl.repository.ChieuCaoRepository;
import vn.htdttt.btl.service.NutritionConsultingService;

@Service
@RequiredArgsConstructor
public class NutritionConsultingServiceImpl implements NutritionConsultingService {
    private final ChieuCaoRepository chieuCaoRepository;

    @Override
    public String getResponse(InputDataDto inputDataDto) {
        ResultSet rsChieuCao = chieuCaoRepository.getByTuoiAndGioiTinh(inputDataDto.getTuoi(), inputDataDto.getGioiTinh());
        ChieuCaoDto chieuCaoDto = new ChieuCaoDto(rsChieuCao);
        FuzzyChieuCao fuzzyChieuCao = getFuzzyChieuCao(chieuCaoDto, inputDataDto.getChieuCao());
        return null;
    }

    private FuzzyChieuCao getFuzzyChieuCao(ChieuCaoDto chieuCaoDto, int chieuCao) {
        FuzzyChieuCao fuzzyChieuCao = new FuzzyChieuCao();

        float ratThap = 0.0f;
        if(chieuCao <= chieuCaoDto.getRatThap()) {
            ratThap = 1.0f;
        } else if(chieuCao <= chieuCaoDto.getThap() - Consts.saiSo) {
            ratThap = ((chieuCaoDto.getThap() - Consts.saiSo) - chieuCao) * 1.0f /
                    ((chieuCaoDto.getThap() - Consts.saiSo) - chieuCaoDto.getRatThap());
        } else {
            ratThap = 0.0f;
        }

        float thap = 0.0f;
        if(chieuCao >= chieuCaoDto.getRatThap() + Consts.saiSo && chieuCao < chieuCaoDto.getThap()) {
            thap = (chieuCao - (chieuCaoDto.getRatThap() + Consts.saiSo)) * 1.0f /
                    (chieuCaoDto.getThap() - (Consts.saiSo + chieuCaoDto.getRatThap()));
        } else if(chieuCao <= chieuCaoDto.getTrungBinh() - Consts.saiSo) {
            thap = ((chieuCaoDto.getTrungBinh() - Consts.saiSo) - chieuCao) * 1.0f /
                    ((chieuCaoDto.getTrungBinh() - Consts.saiSo)- chieuCaoDto.getThap());
        } else {
            thap = 0.0f;
        }

        float trungBinh = 0.0f;
        if(chieuCao >= chieuCaoDto.getThap() + Consts.saiSo && chieuCao < chieuCaoDto.getTrungBinh()) {
            trungBinh = (chieuCao - (chieuCaoDto.getThap() + Consts.saiSo)) * 1.0f /
                    (chieuCaoDto.getTrungBinh() - (Consts.saiSo + chieuCaoDto.getThap()));
        } else if(chieuCao <= chieuCaoDto.getCao() - Consts.saiSo) {
            trungBinh = ((chieuCaoDto.getCao() - Consts.saiSo) - chieuCao) * 1.0f /
                    ((chieuCaoDto.getCao() - Consts.saiSo)- chieuCaoDto.getTrungBinh());
        } else {
            trungBinh = 0.0f;
        }

        float cao = 0.0f;
        if(chieuCao >= chieuCaoDto.getTrungBinh() + Consts.saiSo && chieuCao < chieuCaoDto.getCao()) {
            cao = (chieuCao - (chieuCaoDto.getTrungBinh() + Consts.saiSo)) * 1.0f /
                    (chieuCaoDto.getCao() - (Consts.saiSo + chieuCaoDto.getTrungBinh()));
        } else if(chieuCao <= chieuCaoDto.getRatCao() - Consts.saiSo) {
            cao = ((chieuCaoDto.getRatCao() - Consts.saiSo) - chieuCao) * 1.0f /
                    ((chieuCaoDto.getRatCao() - Consts.saiSo)- chieuCaoDto.getCao());
        } else {
            cao = 0.0f;
        }

        float ratCao = 0.0f;
        if(chieuCao >= chieuCaoDto.getCao() + Consts.saiSo && chieuCao < chieuCaoDto.getRatCao()) {
            ratCao = (chieuCao - (chieuCaoDto.getCao() + Consts.saiSo)) * 1.0f /
                    (chieuCaoDto.getRatCao() - (Consts.saiSo + chieuCaoDto.getCao()));
        } else {
            ratCao = 1.0f;
        }
        fuzzyChieuCao.setRatCao(ratCao);
        fuzzyChieuCao.setCao(cao);
        fuzzyChieuCao.setTrungBinh(trungBinh);
        fuzzyChieuCao.setThap(thap);
        fuzzyChieuCao.setRatThap(ratThap);
        return fuzzyChieuCao;
    }

    private FuzzyCanNang getFuzzyCanNang(CanNangDto canNangDto, int canNang) {
        FuzzyCanNang fuzzyCanNang = new FuzzyCanNang();

        float ratCoi = 0.0f;
        if(canNang <= canNangDto.getRatCoi()) {
            ratCoi = 1.0f;
        } else if(canNang <= canNangDto.getCoi() - Consts.saiSo) {
            ratCoi = ((canNangDto.getCoi() - Consts.saiSo) - canNang) * 1.0f /
                    ((canNangDto.getCoi() - Consts.saiSo) - canNangDto.getRatCoi());
        } else {
            ratCoi = 0.0f;
        }

        float coi = 0.0f;
        if(canNang >= canNangDto.getRatCoi() + Consts.saiSo && canNang < canNangDto.getCoi()) {
            coi = (canNang - (canNangDto.getRatCoi() + Consts.saiSo)) * 1.0f /
                    (canNangDto.getCoi() - (Consts.saiSo + canNangDto.getRatCoi()));
        } else if(canNang <= canNangDto.getTrungBinh() - Consts.saiSo) {
            coi = ((canNangDto.getTrungBinh() - Consts.saiSo) - canNang) * 1.0f /
                    ((canNangDto.getTrungBinh() - Consts.saiSo)- canNangDto.getCoi());
        } else {
            coi = 0.0f;
        }

        float trungBinh = 0.0f;
        if(canNang >= canNangDto.getCoi() + Consts.saiSo && canNang < canNangDto.getTrungBinh()) {
            trungBinh = (canNang - (canNangDto.getCoi() + Consts.saiSo)) * 1.0f /
                    (canNangDto.getTrungBinh() - (Consts.saiSo + canNangDto.getCoi()));
        } else if(canNang <= canNangDto.getBeo() - Consts.saiSo) {
            trungBinh = ((canNangDto.getBeo() - Consts.saiSo) - canNang) * 1.0f /
                    ((canNangDto.getBeo() - Consts.saiSo)- canNangDto.getTrungBinh());
        } else {
            trungBinh = 0.0f;
        }

        float beo = 0.0f;
        if(canNang >= canNangDto.getTrungBinh() + Consts.saiSo && canNang < canNangDto.getBeo()) {
            beo = (canNang - (canNangDto.getTrungBinh() + Consts.saiSo)) * 1.0f /
                    (canNangDto.getBeo() - (Consts.saiSo + canNangDto.getTrungBinh()));
        } else if(canNang <= canNangDto.getBeoPhi() - Consts.saiSo) {
            beo = ((canNangDto.getBeoPhi() - Consts.saiSo) - canNang) * 1.0f /
                    ((canNangDto.getBeoPhi() - Consts.saiSo)- canNangDto.getBeo());
        } else {
            beo = 0.0f;
        }

        float beoPhi = 0.0f;
        if(canNang >= canNangDto.getBeo() + Consts.saiSo && canNang < canNangDto.getBeoPhi()) {
            beoPhi = (canNang - (canNangDto.getBeo() + Consts.saiSo)) * 1.0f /
                    (canNangDto.getBeoPhi() - (Consts.saiSo + canNangDto.getBeo()));
        } else {
            beoPhi = 1.0f;
        }
        fuzzyCanNang.setBeoPhi(beoPhi);
        fuzzyCanNang.setBeo(beo);
        fuzzyCanNang.setTrungBinh(trungBinh);
        fuzzyCanNang.setCoi(coi);
        fuzzyCanNang.setRatCoi(ratCoi);
        return fuzzyCanNang;
    }
}
