package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    //Model에 싫어서 뷰로 데이터를 넘길 수 있다.
    @GetMapping("hello")
    public String hello(Model model){
        //data라는 키에 hello!!라는 글자를 담아서 넘긴다.
        model.addAttribute("data","hello!!");
        //view네임을 명시한다
        return "hello";
    }
}
