package pl.klaster.ecommerce.delivery.domain;

public interface DeliveryMechanism {
    public void handleDelivery(DeliverySubject deliverySubject);
}
