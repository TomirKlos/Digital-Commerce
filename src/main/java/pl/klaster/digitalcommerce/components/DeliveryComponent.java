package pl.klaster.digitalcommerce.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.klaster.ecommerce.canonicalmodel.events.OrderConfirmed;
import pl.klaster.ecommerce.delivery.application.OrderConfirmedListener;
import pl.klaster.ecommerce.delivery.infrastructure.DumpToFileDeliveryMechanism;
import pl.klaster.ecommerce.system.EventBus;

@Component
public class DeliveryComponent {

    @Autowired
    EventBus eventBus;

    @Bean
    private OrderConfirmedListener orderConfirmedListener() {

        OrderConfirmedListener listener = new OrderConfirmedListener(new DumpToFileDeliveryMechanism());
        eventBus.addEventListener(OrderConfirmed.class, listener);

        return listener;
    }
}
