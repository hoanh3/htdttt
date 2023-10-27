package vn.htdttt.btl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.htdttt.btl.commons.AnswerDto;
import vn.htdttt.btl.commons.ResponseDto;
import vn.htdttt.btl.dto.InputDataDto;
import vn.htdttt.btl.service.NutritionConsultingService;

import java.util.List;

@RestController
@RequestMapping("/predict/van-dong")
@RequiredArgsConstructor
public class PhysicalActivityConsultingController {
    private final NutritionConsultingService nutritionConsultingService;

    @CrossOrigin(origins = "http://127.0.0.1:5500", methods = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.DELETE })
    @PostMapping()
    public ResponseEntity<ResponseDto> getResponse(@RequestBody InputDataDto inputDataDto) throws JsonProcessingException {
        List<AnswerDto> answerDtoList = nutritionConsultingService.getResponse(inputDataDto);
        ResponseDto responseDto = new ResponseDto(answerDtoList);
        return ResponseEntity.ok(responseDto);
    }
}
