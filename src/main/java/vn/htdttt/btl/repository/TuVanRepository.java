package vn.htdttt.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.htdttt.btl.domain.TuVan;
import vn.htdttt.btl.dto.TheChatDto;
import vn.htdttt.btl.dto.TuVanDto;

public interface TuVanRepository extends JpaRepository<TuVan, Integer> {
    @Query("SELECT new vn.htdttt.btl.dto.TuVanDto(entity) FROM TuVan entity where entity.theChat.id = :mucdo ")
    TuVanDto getByMucDo(@Param("mucdo") int mucDo);
}
