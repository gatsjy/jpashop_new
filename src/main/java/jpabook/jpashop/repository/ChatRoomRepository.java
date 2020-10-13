package jpabook.jpashop.repository;

import jpabook.jpashop.domain.ChatRoom;
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
public class ChatRoomRepository {

    private final Map<String, ChatRoom> chatMap;
    private final Collection<ChatRoom> chatRooms;

    public ChatRoomRepository(){
        // 테스트를 위한 용도로 UUID로 채팅방 id를 지정하여, 3개의 채팅방을 생성해두었다.
        chatMap = Collections.unmodifiableMap(
                Stream.of(ChatRoom.create("1번방"),
                          ChatRoom.create("2번방"),
                          ChatRoom.create("3번방"))
                       .collect(Collectors.toMap(ChatRoom::getId, Function.identity())));

        chatRooms = Collections.unmodifiableCollection(chatMap.values());
    }

    public ChatRoom getChat(String id){
        return chatMap.get(id);
    }

    public Collection<ChatRoom> getChatRooms() {
        return chatMap.values();
    }
}
