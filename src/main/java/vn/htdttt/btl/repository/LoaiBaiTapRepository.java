package vn.htdttt.btl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.htdttt.btl.domain.LoaiBaiTap;
import vn.htdttt.btl.dto.LoaiBaiTapDto;

@Repository
public interface LoaiBaiTapRepository extends JpaRepository<LoaiBaiTap, Integer> {
    LoaiBaiTapDto getByLoai(int loai);
}
