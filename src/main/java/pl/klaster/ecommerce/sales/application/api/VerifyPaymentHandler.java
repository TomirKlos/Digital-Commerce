package pl.klaster.ecommerce.sales.application.api;

import org.springframework.stereotype.Component;
import pl.klaster.ecommerce.canonicalmodel.Identifier;
import pl.klaster.ecommerce.canonicalmodel.events.OrderConfirmed;
import pl.klaster.ecommerce.sales.application.services.payment.Przelewy24PaymentConfirmation;
import pl.klaster.ecommerce.sales.domain.order.Order;
import pl.klaster.ecommerce.sales.domain.order.OrderRepository;
import pl.klaster.ecommerce.system.EventBus;

@Component
public class VerifyPaymentHandler {

    private Przelewy24PaymentConfirmation paymentConfirmation;
    private OrderRepository orderRepository;
    private EventBus eventBus;


    public VerifyPaymentHandler(Przelewy24PaymentConfirmation paymentConfirmation, OrderRepository orderRepository, EventBus eventBus) {
        this.paymentConfirmation = paymentConfirmation;
        this.orderRepository = orderRepository;
        this.eventBus = eventBus;
    }

    public void handle(VerifyPaymentCommand command) {
        Order order = orderRepository.loadByPayment(command.getPaymentIdentifier());

        paymentConfirmation.verifyPayment(order.getPayment(), command.getCheckSum(), command.getOrderId());

        order.confirm();

        eventBus.dispatch(new OrderConfirmed(Identifier.generateUUID()));
    }
}
