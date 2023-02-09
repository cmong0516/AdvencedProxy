package hello.proxy.app.v2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequestMapping
@ResponseBody
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;

    public OrderControllerV2(OrderServiceV2 orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/v2/request")
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/v2/no-log")
    public String noLog() {
        return "no-log";
    }

}

// V2 는 Interface 가 없는 클래스들로 이루어져있다.
// AppV2Config 를 통해 수동으로 Bean 등록을 해준다.
// @RequestMapping 을 사용하는 이유는 컴포넌트 스캔 대상이 되지 않으면서 컨트롤러로 인식해야 하기 때문.