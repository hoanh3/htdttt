package vn.htdttt.btl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.htdttt.btl.dto.AnswerDto;
import vn.htdttt.btl.dto.InputDataDto;
import vn.htdttt.btl.dto.ResponseDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/predict")
@RequiredArgsConstructor
public class NutritionConsultingController {
    private final ObjectMapper objectMapper;

    @CrossOrigin(origins = "http://127.0.0.1:5500", methods = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.DELETE })
    @PostMapping()
    public ResponseEntity<ResponseDto> getResponse(@RequestBody InputDataDto inputDataDto) {
        AnswerDto answer = new AnswerDto("hello");
        AnswerDto answerDto = new AnswerDto("chieu cao");
        List<AnswerDto> answerDtoList = new ArrayList<>();
        answerDtoList.add(answerDto);
        answerDtoList.add(answer);
        ResponseDto responseDto = new ResponseDto(answerDtoList);
        return ResponseEntity.ok(responseDto);
    }
}
