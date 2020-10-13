package jpabook.jpashop.controller;

import jpabook.jpashop.domain.ChatRoom;
import jpabook.jpashop.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Gatsjy
 * @since 2020-10-12
 * realize dreams myself
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */
@Controller
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final AtomicInteger seq = new AtomicInteger(0);

    @Autowired
    public ChatRoomController(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @GetMapping("/chats")
    public String rooms(Model model){
        model.addAttribute("rooms", chatRoomRepository.getChatRooms());
        return "/chat/room-list";
    }

    @GetMapping("/chats/{id}")
    public String room(@PathVariable String id, Model model){
        ChatRoom chatRoom = chatRoomRepository.getChat(id);
        model.addAttribute("room", chatRoom);
        model.addAttribute("member", "member" + seq.incrementAndGet());

        return "/chats/room";
    }
}
