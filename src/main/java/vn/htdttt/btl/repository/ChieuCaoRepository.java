package vn.htdttt.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.htdttt.btl.domain.ChieuCao;
import vn.htdttt.btl.projection.ResultSet;

@Repository
public interface ChieuCaoRepository extends JpaRepository<ChieuCao, Integer> {
    @Query(value = "SELECT  " +
            " cao.rat_thap ratThap , " +
            " cao.thap thap ,  " +
            " cao.trung_binh trungBinh , " +
            " cao.cao cao , " +
            " cao.rat_cao ratCao " +
            " FROM tbl_chieucao cao  " +
            " WHERE cao.tuoi = :tuoi AND LOWER(cao.gioi_tinh) = LOWER( :gioiTinh ) ", nativeQuery = true)
    ResultSet getByTuoiAndGioiTinh(@Param("tuoi") int tuoi,
                                   @Param("gioiTinh") String gioiTinh);
}
