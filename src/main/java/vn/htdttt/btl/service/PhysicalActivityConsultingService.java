package vn.htdttt.btl.service;

import vn.htdttt.btl.commons.AnswerDto;
import vn.htdttt.btl.dto.InputDataDto;

import java.util.List;

public interface PhysicalActivityConsultingService {
    List<AnswerDto> getResponse(InputDataDto inputDataDto);
}
