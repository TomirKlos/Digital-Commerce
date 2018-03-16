package pl.klaster.ecommerce.sales.domain.order;

import pl.klaster.ecommerce.canonicalmodel.Identifier;

public class ClientData {
    private Identifier id;
    private String email;

    public ClientData(Identifier id, String email) {
        this.id = id;
        this.email = email;
    }

    public Identifier getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
