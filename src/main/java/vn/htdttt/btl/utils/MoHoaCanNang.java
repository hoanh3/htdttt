package vn.htdttt.btl.utils;

import org.springframework.stereotype.Component;
import vn.htdttt.btl.dto.CanNangDto;
import vn.htdttt.btl.dto.FuzzyCanNang;

@Component
public class MoHoaCanNang {
    public FuzzyCanNang moHoaCanNang(CanNangDto canNangDto, double canNang) {
        FuzzyCanNang fuzzyCanNang = new FuzzyCanNang();

        double ratCoi = moHoaRatCoi(canNangDto, canNang);
        double coi = moHoaCoi(canNangDto, canNang);
        double trungBinh = moHoaTrungBinh(canNangDto, canNang);
        double nang = moHoaNang(canNangDto, canNang);
        double ratNang = moHoaRatNang(canNangDto, canNang);

        fuzzyCanNang.setRatNang(ratNang);
        fuzzyCanNang.setNang(nang);
        fuzzyCanNang.setTrungBinh(trungBinh);
        fuzzyCanNang.setCoi(coi);
        fuzzyCanNang.setRatCoi(ratCoi);
        return fuzzyCanNang;
    }

    private static double moHoaRatCoi(CanNangDto canNangDto, double canNang) {
        double ratCoi = 0.0;
        if (canNang <= canNangDto.getRatCoi()) {
            ratCoi = 1.0;
        } else if (canNang <= canNangDto.getCoi()) {
            ratCoi = ((canNangDto.getCoi()) - canNang)
                    / ((canNangDto.getCoi()) - canNangDto.getRatCoi());
        } else {
            ratCoi = 0.0;
        }
        return ratCoi;
    }

    private static double moHoaCoi(CanNangDto canNangDto, double canNang) {
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
        return coi;
    }

    private static double moHoaTrungBinh(CanNangDto canNangDto, double canNang) {
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
        return trungBinh;
    }

    private static double moHoaNang(CanNangDto canNangDto, double canNang) {
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
        return nang;
    }

    private static double moHoaRatNang(CanNangDto canNangDto, double canNang) {
        double ratNang = 0.0;
        if (canNang >= canNangDto.getNang() && canNang < canNangDto.getRatNang()) {
            ratNang = (canNang - (canNangDto.getNang()))
                    / (canNangDto.getRatNang() - (canNangDto.getNang()));
        } else if (canNang >= canNangDto.getRatNang()){
            ratNang = 1.0;
        }
        return ratNang;
    }
}
