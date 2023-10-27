package vn.htdttt.btl.utils;

import org.springframework.stereotype.Component;
import vn.htdttt.btl.dto.ChieuCaoDto;
import vn.htdttt.btl.dto.FuzzyChieuCao;

@Component
public class MoHoaChieuCao {
    public FuzzyChieuCao moHoaChieuCao(ChieuCaoDto chieuCaoDto, double chieuCao) {
        FuzzyChieuCao fuzzyChieuCao = new FuzzyChieuCao();

        double ratThap = moHoaRatThap(chieuCaoDto, chieuCao);
        double thap = moHoaThap(chieuCaoDto, chieuCao);
        double trungBinh = moHoaTrungBinh(chieuCaoDto, chieuCao);
        double cao = moHoaCao(chieuCaoDto, chieuCao);
        double ratCao = moHoaRatCao(chieuCaoDto, chieuCao);

        fuzzyChieuCao.setRatCao(ratCao);
        fuzzyChieuCao.setCao(cao);
        fuzzyChieuCao.setTrungBinh(trungBinh);
        fuzzyChieuCao.setThap(thap);
        fuzzyChieuCao.setRatThap(ratThap);
        return fuzzyChieuCao;
    }

    private static double moHoaRatThap(ChieuCaoDto chieuCaoDto, double chieuCao) {
        double ratThap = 0.0;
        if (chieuCao <= chieuCaoDto.getRatThap()) {
            ratThap = 1.0;
        } else if (chieuCao <= chieuCaoDto.getThap()) {
            ratThap = ((chieuCaoDto.getThap()) - chieuCao)
                    / ((chieuCaoDto.getThap()) - chieuCaoDto.getRatThap());
        } else {
            ratThap = 0.0;
        }
        return ratThap;
    }

    private static double moHoaThap(ChieuCaoDto chieuCaoDto, double chieuCao) {
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
        return thap;
    }

    private static double moHoaTrungBinh(ChieuCaoDto chieuCaoDto, double chieuCao) {
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
        return trungBinh;
    }

    private static double moHoaCao(ChieuCaoDto chieuCaoDto, double chieuCao) {
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
        return cao;
    }

    private static double moHoaRatCao(ChieuCaoDto chieuCaoDto, double chieuCao) {
        double ratCao = 0.0;
        if (chieuCao >= chieuCaoDto.getCao() && chieuCao < chieuCaoDto.getRatCao()) {
            ratCao = (chieuCao - (chieuCaoDto.getCao()))
                    / (chieuCaoDto.getRatCao() - (chieuCaoDto.getCao()));
        } else if(chieuCao >= chieuCaoDto.getRatCao()){
            ratCao = 1.0;
        }
        return ratCao;
    }
}
