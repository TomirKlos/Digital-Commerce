package pl.klaster.ecommerce.sales.readmodel.readmodel;

import pl.klaster.ecommerce.canonicalmodel.Identifier;
import pl.klaster.ecommerce.sales.domain.order.Order;
import pl.klaster.ecommerce.sales.domain.order.OrderRepository;
import pl.klaster.ecommerce.sales.domain.payment.PaymentGateway;

public class PaymentURLs {
    private PaymentGateway paymentGateway;
    private OrderRepository orderRepository;

    public PaymentURLs(PaymentGateway paymentGateway, OrderRepository orderRepository) {
        this.paymentGateway = paymentGateway;
        this.orderRepository = orderRepository;
    }

    public String getPaymentUrl(Identifier orderId) {
        Order o = orderRepository.load(orderId);

        return paymentGateway.obtainPaymentURL(o.getPayment().getPaymentToken());
    }
}
