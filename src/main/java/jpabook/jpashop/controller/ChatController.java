package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Chat;
import jpabook.jpashop.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Gatsjy
 * @since 2020-10-12
 * realize dreams myself
 * Blog : https://blog.naver.com/gkswndks123
 * Github : https://github.com/gatsjy
 */
@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatRepository chatRepository;
    private final AtomicInteger seq = new AtomicInteger(0);

    @Autowired
    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @GetMapping("/rooms")
    public String rooms(Model model){
        model.addAttribute("rooms", chatRepository.getChatRooms());
        return "/chat/room-list";
    }

    @GetMapping("/rooms/{id}")
    public String room(@PathVariable String id, Model model){
        Chat chat = chatRepository.getChat(id);
        model.addAttribute("room", chat);
        model.addAttribute("member", "member" + seq.incrementAndGet());

        return "/chat/room";
    }
}
