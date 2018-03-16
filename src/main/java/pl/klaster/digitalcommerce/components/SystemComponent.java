package pl.klaster.digitalcommerce.components;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pl.klaster.ecommerce.canonicalmodel.Identifier;
import pl.klaster.ecommerce.system.EventBus;
import pl.klaster.ecommerce.system.SystemUserContext;
import pl.klaster.ecommerce.system.infrastructure.InMemorySystemUserContext;
import pl.klaster.ecommerce.system.infrastructure.InternalEventBus;

@Component
public class SystemComponent {


    @Bean
    private SystemUserContext getSystemUserContext() {
        InMemorySystemUserContext systemUserContext = new InMemorySystemUserContext();
        systemUserContext.authenticate(new Identifier("user_1"));

        return systemUserContext;
    }

    @Bean
    private EventBus eventBus() {
        EventBus eventBus = new InternalEventBus();

        return eventBus;
    }
}


