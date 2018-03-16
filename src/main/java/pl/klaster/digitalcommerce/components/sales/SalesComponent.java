package pl.klaster.digitalcommerce.components.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import pl.klaster.ecommerce.sales.application.api.AddToBasketHandler;
import pl.klaster.ecommerce.sales.application.api.ConfirmOrderHandler;
import pl.klaster.ecommerce.sales.application.api.VerifyPaymentHandler;
import pl.klaster.ecommerce.sales.application.services.payment.Przelewy24PaymentConfirmation;
import pl.klaster.ecommerce.sales.domain.basket.BasketStorage;
import pl.klaster.ecommerce.sales.domain.offer.OfferMaker;
import pl.klaster.ecommerce.sales.domain.order.OrderFactory;
import pl.klaster.ecommerce.sales.domain.order.OrderRepository;
import pl.klaster.ecommerce.sales.domain.payment.PaymentGateway;
import pl.klaster.ecommerce.sales.domain.productcatalog.ProductCatalog;
import pl.klaster.ecommerce.system.EventBus;
import pl.klaster.ecommerce.system.SystemUserContext;

@Component
public class SalesComponent {

    @Autowired SystemUserContext systemUserContext;

    @Autowired
    private BasketStorage basketStorage;

    @Autowired
    private ProductCatalog productCatalog;

    @Autowired
    private OfferMaker offerMaker;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentGateway paymentGateway;

    @Autowired
    private Przelewy24PaymentConfirmation paymentConfirmation;

    @Autowired
    private EventBus eventBus;

    @Bean
    private VerifyPaymentHandler verifyPaymentHandler() {
        return new VerifyPaymentHandler(
                paymentConfirmation,
                orderRepository,
                eventBus
        );
    }

    @Bean
    private AddToBasketHandler addToBasketHandler() {
        return new AddToBasketHandler(
            systemUserContext,
            basketStorage,
            productCatalog
        );
    }

    @Bean
    private ConfirmOrderHandler confirmOrderHandler() {
        return new ConfirmOrderHandler(
            orderRepository,
                offerMaker,
                basketStorage,
                systemUserContext,
                new OrderFactory(paymentGateway)
        );
    }

    @Bean
    private OfferMaker getOfferMaker() {
        return new OfferMaker();
    }
}
