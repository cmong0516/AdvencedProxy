package hello.proxy.app.v1;

public class OrderControllerV1Impl implements OrderControllerV1 {

    private final OrderServiceV1 orderService;

    public OrderControllerV1Impl(OrderServiceV1 orderService) {
        this.orderService = orderService;
    }

    @Override
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @Override
    public String noLog() {
        return "no-log";
    }
}


// V1 은 Interface 를 만들고 구현체를 만든다.
// AppV1Config 를 통해 Bean 수동 등록.
// Interface 에서 @RequestMapping 과 메서드에 @GetMapping 어노테이션을 사용