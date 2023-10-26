package vn.htdttt.btl.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.htdttt.btl.commons.AnswerDto;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ResponseDto {
    private List<AnswerDto> answer;
}
