package vn.htdttt.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.htdttt.btl.domain.ChieuCao;
import vn.htdttt.btl.dto.ChieuCaoDto;

@Repository
public interface ChieuCaoRepository extends JpaRepository<ChieuCao, Integer> {
    @Query("SELECT new vn.htdttt.btl.dto.ChieuCaoDto(entity) FROM ChieuCao entity where entity.tuoi = :tuoi and lower(entity.gioiTinh) = lower( :gioiTinh )")
    ChieuCaoDto getByTuoiAndGioiTinh(@Param("tuoi") int tuoi, @Param("gioiTinh") String gioiTinh);
}
