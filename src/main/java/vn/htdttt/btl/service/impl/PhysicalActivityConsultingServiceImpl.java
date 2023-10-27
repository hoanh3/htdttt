package vn.htdttt.btl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.htdttt.btl.repository.CanNangRepository;
import vn.htdttt.btl.repository.ChieuCaoRepository;

@Service
@RequiredArgsConstructor
public class PhysicalActivityConsultingServiceImpl {
    private final ChieuCaoRepository chieuCaoRepository;
    private final CanNangRepository canNangRepository;
}
