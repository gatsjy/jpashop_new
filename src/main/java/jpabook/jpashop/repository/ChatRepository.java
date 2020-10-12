package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Chat;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Gatsjy
 * @since 2020-10-12
 * realize dreams myself
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */
@Repository
public class ChatRepository {

    private final Map<String, Chat> chatMap;
    private final Collection<Chat> chatRooms;

    public ChatRepository(){
        // 테스트를 위한 용도로 UUID로 채팅방 id를 지정하여, 3개의 채팅방을 생성해두었다.
        chatMap = Collections.unmodifiableMap(
                Stream.of(Chat.create("1번방"),
                          Chat.create("2번방"),
                          Chat.create("3번방"))
                       .collect(Collectors.toMap(Chat::getId, Function.identity())));

        chatRooms = Collections.unmodifiableCollection(chatMap.values());
    }

    public Chat getChat(String id){
        return chatMap.get(id);
    }

    public Collection<Chat> getChatRooms() {
        return chatMap.values();
    }
}
