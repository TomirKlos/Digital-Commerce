package pl.klaster.ecommerce.canonicalmodel.events;

import pl.klaster.ecommerce.canonicalmodel.Identifier;

public class OrderConfirmed {
    private Identifier orderId;

    public OrderConfirmed(Identifier orderId) {
        this.orderId = orderId;
    }

    public Identifier getOrderId() {
        return orderId;
    }
}
