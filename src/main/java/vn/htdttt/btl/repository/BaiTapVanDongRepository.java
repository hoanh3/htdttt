package vn.htdttt.btl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.htdttt.btl.domain.BaiTapVanDong;
import vn.htdttt.btl.dto.BaiTapVanDongDto;

import java.util.List;

@Repository
public interface BaiTapVanDongRepository extends JpaRepository<BaiTapVanDong, Integer> {
    @Query("SELECT new vn.htdttt.btl.dto.BaiTapVanDongDto(entity) " +
            "FROM BaiTapVanDong entity where entity.doTuoiTu <= :doTuoi and entity.doTuoiDen >= :doTuoi and entity.loaiBaiTap.id = :loaiBaiTap ")
    Page<BaiTapVanDongDto> getByDoTuoiAAndLoaiBaiTap(@Param("doTuoi") int doTuoi,
                                                     @Param("loaiBaiTap") int loaiBaiTap,
                                                     Pageable pageable);
}
