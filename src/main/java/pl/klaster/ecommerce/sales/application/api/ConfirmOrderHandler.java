package pl.klaster.ecommerce.sales.application.api;

import org.springframework.stereotype.Component;
import pl.klaster.ecommerce.canonicalmodel.Identifier;
import pl.klaster.ecommerce.sales.domain.order.*;
import pl.klaster.ecommerce.sales.domain.basket.Basket;
import pl.klaster.ecommerce.sales.domain.basket.BasketStorage;
import pl.klaster.ecommerce.sales.domain.offer.Offer;
import pl.klaster.ecommerce.sales.domain.offer.OfferMaker;
import pl.klaster.ecommerce.system.SystemUserContext;
@Component
public class ConfirmOrderHandler {

    private OrderRepository orderRepository;
    private OfferMaker offerMaker;
    private BasketStorage basketStorage;
    private SystemUserContext systemUserContext;
    private OrderFactory orderFactory;

    public ConfirmOrderHandler(OrderRepository orderRepository, OfferMaker offerMaker, BasketStorage basketStorage, SystemUserContext systemUserContext, OrderFactory orderFactory) {
        this.orderRepository = orderRepository;
        this.offerMaker = offerMaker;
        this.basketStorage = basketStorage;
        this.systemUserContext = systemUserContext;
        this.orderFactory = orderFactory;
    }

    public void handle(ConfirmOrderCommand command) {

        Basket basket = basketStorage.loadForCustomer(systemUserContext.getCurrentUser().getId());

        Offer offer = offerMaker.calculateOffer(basket.getReservedProducts());

        Order order = orderFactory.create(command.getOrderId(), offer, new ClientData(Identifier.generateUUID(), command.getDeliveryEmail()));

        orderRepository.add(order);

        basket.clear();
    }
}
