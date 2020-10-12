package jpabook.jpashop.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpabook.jpashop.domain.Chat;
import jpabook.jpashop.domain.ChatMessage;
import jpabook.jpashop.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author Gatsjy
 * @since 2020-10-12
 * realize dreams myself
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */
@Slf4j
@Profile("!stomp")
@Component
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {
    private final ObjectMapper obejctMapper;
    private final ChatRepository chatRepository;

    // session에서 메세지를 수신 했을 때 실행 된다.
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        log.info("payload : {}", payload);

        ChatMessage chatMessage = obejctMapper.readValue(payload, ChatMessage.class);
        Chat chat = chatRepository.getChat(chatMessage.getChatRoomId());

        log.info("session : {}", session);
        log.info("message : {}", message);
        log.info("chatMessage : {}", chatMessage);
        log.info("objectMapper : {}", obejctMapper);
        chat.handleMessage(session, chatMessage, obejctMapper);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}
