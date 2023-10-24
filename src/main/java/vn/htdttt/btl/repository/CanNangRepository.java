package vn.htdttt.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.htdttt.btl.domain.CanNang;
import vn.htdttt.btl.projection.ResultSet;

@Repository
public interface CanNangRepository extends JpaRepository<CanNang, Integer> {
    @Query(value = "SELECT  " +
            " cn.rat_coi ratCoi, " +
            " cn.coi coi, " +
            " cn.trung_binh trungBinh, " +
            " cn.beo beo, " +
            " cn.beo_phi beoPhi " +
            "FROM tbl_cannang cn " +
            " WHERE cn.tuoi = :tuoi AND LOWER(cn.gioi_tinh) = LOWER( :gioiTinh ) ", nativeQuery = true)
    ResultSet getByTuoiAndGioiTinh(@Param("tuoi") int tuoi,
                                   @Param("gioiTinh") String gioiTinh);
}
