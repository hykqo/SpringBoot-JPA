package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 * */
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    //엔티티를 그대로 반환하면 안된다.
    //1.json으로 변환할때 양방향관계에서 계속 연관 객체를 탐색하므로 LOOP에 빠져 버린다. -> @JsonIgnore를 이용해서 탐색 제외하면 된다.
    //2.lazy(지연로딩)설정으로 해놓을 경우 jpa는 해당 객체에 proxy객체를 넣어놓는데,
    // 객체를 그대로 반환할 경우 jacson 라이브러리가 해당 객체를 호출시 프록시 객체가 반환되어버려서 오류를 발생시킨다. (ByteBuddy) -> hibernate5Module을 이용하여 지연로딩 시점을 변경할 수 있다.
    //어찌되었던 이러한 사유로 인해 객체를 그대로 반환하면 안된다.
    @GetMapping("v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
//        for(Order order : all) {
//            order.getMember().getName(); //lazy 강제 초기화.
//            order.getDelivery().getAddress(); //lazy 강제 초기화.
//        }
        return all;
    }



}
