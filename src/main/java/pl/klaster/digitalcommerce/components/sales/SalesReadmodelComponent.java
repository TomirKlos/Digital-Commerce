package pl.klaster.digitalcommerce.components.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.klaster.ecommerce.sales.domain.basket.BasketStorage;
import pl.klaster.ecommerce.sales.domain.offer.OfferMaker;
import pl.klaster.ecommerce.sales.domain.order.OrderRepository;
import pl.klaster.ecommerce.sales.domain.payment.PaymentGateway;
import pl.klaster.ecommerce.sales.readmodel.offer.OfferFinder;
import pl.klaster.ecommerce.sales.readmodel.productcatalog.ProductFinder;
import pl.klaster.ecommerce.sales.readmodel.readmodel.PaymentURLs;
import pl.klaster.ecommerce.system.SystemUserContext;

@Component
public class SalesReadmodelComponent {
    @Autowired
    BasketStorage basketStorage;

    @Autowired
    OfferMaker offerMaker;

    @Autowired
    SystemUserContext systemUserContext;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PaymentGateway paymentGateway;

    @Bean
    OfferFinder offerFinder() {
        return new OfferFinder(
            basketStorage,
            offerMaker,
            systemUserContext
        );
    }

    @Bean
    ProductFinder productFinder() {
        return new ProductFinder();
    }

    @Bean
    PaymentURLs paymentURLs() {
        return new PaymentURLs(
            paymentGateway,
            orderRepository
        );
    }
}
