package pl.klaster.ecommerce.system.infrastructure;

import pl.klaster.ecommerce.canonicalmodel.Identifier;
import pl.klaster.ecommerce.system.SystemUser;
import pl.klaster.ecommerce.system.SystemUserContext;

public class InMemorySystemUserContext implements SystemUserContext {

    private SystemUser systemUser;

    public void authenticate(Identifier id) {
        this.systemUser = new SystemUser(id);
    }

    @Override
    public SystemUser getCurrentUser() {
        return systemUser;
    }
}
