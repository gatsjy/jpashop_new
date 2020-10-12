package jpabook.jpashop.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Gatsjy
 * @since 2020-10-12
 * realize dreams myself
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */
@Getter
public class Chat {
    // 채팅방은 id, name, session으로 구성된다. WebSocketSession은 spring에서 WebSocket Connection이 맺어진 세션을 가리킨다.
    // 해당 session을 통해서 메세지를 보낼 수 있다.
    private String id;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    // 채팅방 생성
    public static Chat create(@NonNull String name){
        Chat chat = new Chat();
        chat.id = UUID.randomUUID().toString();
        chat.name = name;
        return chat;
    }

    public void handleMessage(WebSocketSession session, ChatMessage chatMessage, ObjectMapper objectMapper){
        if(chatMessage.getType() == MessageType.JOIN){
            join(session);
            chatMessage.setMessage(chatMessage.getWriter() + "님이 채팅방에 입장했습니다.");
        }
        send(chatMessage, objectMapper);
    }

    private <T> void send(T messageObejct, ObjectMapper objectMapper) {
        try {
            TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageObejct));
            sessions.parallelStream().forEach( session -> {
                try{
                    session.sendMessage(message);
                } catch (Exception e){
                    synchronized (sessions){
                        sessions.remove(session);
                    }
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void join(WebSocketSession session){
        sessions.add(session);
    }
}
