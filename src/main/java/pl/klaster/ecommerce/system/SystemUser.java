package pl.klaster.ecommerce.system;

import pl.klaster.ecommerce.canonicalmodel.Identifier;

public class SystemUser {
    private Identifier id;

    public SystemUser(Identifier id) {
        this.id = id;
    }

    public Identifier getId() {
        return id;
    }
}
