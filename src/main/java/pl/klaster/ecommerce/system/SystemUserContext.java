package pl.klaster.ecommerce.system;

import org.springframework.stereotype.Component;

@Component
public interface SystemUserContext {
    public SystemUser getCurrentUser();
}
