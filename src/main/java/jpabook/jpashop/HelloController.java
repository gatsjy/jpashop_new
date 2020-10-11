package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        // 모델이란건 데이터를 실어서 뷰로 넘길 수 있는 기능
        model.addAttribute("data","hello!!!");
        return "hello";
    }
}
