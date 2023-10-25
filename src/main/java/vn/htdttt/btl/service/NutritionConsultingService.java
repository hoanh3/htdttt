package vn.htdttt.btl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import vn.htdttt.btl.dto.AnswerDto;
import vn.htdttt.btl.dto.InputDataDto;

import java.util.List;

public interface NutritionConsultingService {
    List<AnswerDto> getResponse(InputDataDto inputDataDto) throws JsonProcessingException;
}
