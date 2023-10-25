package vn.htdttt.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.htdttt.btl.domain.LuatDinhDuong;

@Repository
public interface LuatDinhDuongRepository extends JpaRepository<LuatDinhDuong, Integer> {
    LuatDinhDuong getByDtCanNangAndDtChieuCao(String dtCanNang, String dtChieuCao);

}
