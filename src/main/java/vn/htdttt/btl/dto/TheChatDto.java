package vn.htdttt.btl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.htdttt.btl.domain.TheChat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TheChatDto {
    private String tenTheChat;
    private int mucDo;

    public TheChatDto(TheChat theChat) {
        this.tenTheChat = theChat.getTenTheChat();
        this.mucDo = theChat.getMucDo();
    }
}
