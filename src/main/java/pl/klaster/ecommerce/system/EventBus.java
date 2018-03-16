package pl.klaster.ecommerce.system;

import org.springframework.stereotype.Component;

@Component
public interface EventBus {
    public void dispatch(Object object);

    public void addEventListener(Class className, Subscriber subscriber);
}
