package vn.htdttt.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.htdttt.btl.domain.TheChat;
import vn.htdttt.btl.dto.TheChatDto;

@Repository
public interface TheChatRepository extends JpaRepository<TheChat, Integer> {

    @Query("SELECT new vn.htdttt.btl.dto.TheChatDto(entity) FROM TheChat entity where entity.mucDo = :mucdo ")
    TheChatDto getByMucDo(@Param("mucdo") int mucDo);
}
