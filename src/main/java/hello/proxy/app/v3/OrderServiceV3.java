package hello.proxy.app.v3;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepository;

    public OrderServiceV3(OrderRepositoryV3 orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }

}

// v1 은 interface 를 생성하고 이를 구현
// v2 는 interface 없이
// v1, v2 는 Config 를 통해 빈 등록을 수동으로 해주었다면
// v3 는 @component 어노테이션을 포함하고 있는 @Controller ,@Service , @Repository 어노테이션으로 빈등록.
// v1,v2 에서는 새로운 Config 를 만들어 프록시를 사용해주었지만 v3 에서는 현재의 방법으로 불가능하다.
// 이를 해결하기 위한 방법이 빈 후처리기 라고 한다.