package vn.htdttt.btl.utils;

import org.springframework.stereotype.Component;
import vn.htdttt.btl.dto.TheChatDto;

@Component
public class DinhDangPhanHoi {
    public String getTuVanTheChat(TheChatDto theChatDto) {
        String result = String.format("Dựa trên thông số bạn đưa ra, " +
                "chúng tôi đánh giá thể chất thuộc mức: %s! <br> Cấp độ dinh dưỡng: %d.",
                theChatDto.getTenTheChat().toUpperCase(), theChatDto.getMucDo());;
        return result;
    }
}
