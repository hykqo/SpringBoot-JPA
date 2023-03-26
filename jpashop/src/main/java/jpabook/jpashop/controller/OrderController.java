package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final MemberService memberService;

    @GetMapping("")
    public String createForm(Model model){
        List<Member> members = memberService.fineMembers();
        List<Item> items = itemService.findItems();
        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }

    @PostMapping("")
    public String saveOrder(@RequestParam Long memberId,
                            @RequestParam Long itemId,
                            @RequestParam int count){
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }
}
