package com.placement.assistant.dto.response;
import lombok.*;
@Data @AllArgsConstructor @NoArgsConstructor
public class MessageResponse {
    private String message;
    private boolean success;
    public static MessageResponse success(String m){return new MessageResponse(m,true);}
    public static MessageResponse error(String m){return new MessageResponse(m,false);}
}
