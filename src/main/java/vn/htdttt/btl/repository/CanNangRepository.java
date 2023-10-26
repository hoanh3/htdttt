package vn.htdttt.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.htdttt.btl.domain.CanNang;
import vn.htdttt.btl.dto.CanNangDto;

@Repository
public interface CanNangRepository extends JpaRepository<CanNang, Integer> {
    @Query("SELECT new vn.htdttt.btl.dto.CanNangDto(entity) FROM CanNang entity where entity.tuoi = :tuoi and lower(entity.gioiTinh) = lower( :gioiTinh )")
    CanNangDto getByTuoiAndGioiTinh(@Param("tuoi") int tuoi, @Param("gioiTinh") String gioiTinh);
}
