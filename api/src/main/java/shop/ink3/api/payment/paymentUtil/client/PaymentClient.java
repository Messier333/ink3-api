package shop.ink3.api.payment.paymentUtil.client;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "paymentClient", url = "https://api.tosspayments.com/v1/payments")
public interface PaymentClient {

    @PostMapping("/confirm")
    String confirmPayment(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody Map<String, Object> body
    );

    @PostMapping("/{paymentKey}/cancel")
    String cancelPayment(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable String paymentKey,
            @RequestBody Map<String, Object> body
    );
}