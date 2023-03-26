package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /*주문*/
    public Long order(Long memberId, Long itemId, int count){

        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장 - 연관객체들을 [cascadeType.All] 로 저장하였기 때문에, order의 연관된 연관객체들도 전부 영속화 해준다.
        //cascade는 주인이 1개만 있을 경우(privage Owner) 와 생명주기를 주인과 연관관계를 같이 가져가야 하는 경우에 사용하면 된다.(2가지를 전부 충족해야 함.)
        orderRepository.save(order);

        return order.getId();
    }

    /*취소*/
    public void cancelOrder(Long orderId){
        //엔티티 조회
        Order order = orderRepository.findOne(orderId);

        //주문취소
        order.cancel();
    }

    /*검색*/
//    public List<Order> findOrders(Ordersearch ordersearch){
//        return orderRepository.findAll(orderSearch);
//    }
}
