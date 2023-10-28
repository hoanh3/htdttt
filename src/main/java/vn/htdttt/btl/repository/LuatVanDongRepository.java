package vn.htdttt.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.htdttt.btl.domain.LuatVanDong;

@Repository
public interface LuatVanDongRepository extends JpaRepository<LuatVanDong, Integer> {
    LuatVanDong getByDtCanNangAndDtChieuCao(String dtCanNang, String dtChieuCao);
}
