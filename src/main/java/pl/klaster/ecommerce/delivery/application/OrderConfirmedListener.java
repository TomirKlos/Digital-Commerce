package pl.klaster.ecommerce.delivery.application;

import pl.klaster.ecommerce.canonicalmodel.events.OrderConfirmed;
import pl.klaster.ecommerce.delivery.domain.DeliveryMechanism;
import pl.klaster.ecommerce.delivery.domain.DeliverySubject;
import pl.klaster.ecommerce.system.Subscriber;

public class OrderConfirmedListener implements Subscriber<OrderConfirmed> {

    private DeliveryMechanism deliveryMechanism;

    public OrderConfirmedListener(DeliveryMechanism deliveryMechanism) {
        this.deliveryMechanism = deliveryMechanism;
    }

    @Override
    public void handle(OrderConfirmed event) {
        deliveryMechanism.handleDelivery(new DeliverySubject(event.getOrderId()));
    }
}
