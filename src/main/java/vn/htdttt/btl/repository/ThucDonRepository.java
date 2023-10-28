package vn.htdttt.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.htdttt.btl.domain.TheChat;
import vn.htdttt.btl.domain.ThucDon;
import vn.htdttt.btl.dto.ThucDonDto;

@Repository
public interface ThucDonRepository extends JpaRepository<ThucDon, Integer> {

    @Query("SELECT new vn.htdttt.btl.dto.ThucDonDto(entity) FROM ThucDon entity where entity.doTuoi = :doTuoi and entity.theChat.id = :theChat ")
    ThucDonDto getByDoTuoiAndIdTheChat(@Param("doTuoi") int doTuoi,
                                       @Param("theChat") int theChat);
}
