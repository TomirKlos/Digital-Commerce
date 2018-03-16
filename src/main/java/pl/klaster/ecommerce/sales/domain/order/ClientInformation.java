package pl.klaster.ecommerce.sales.domain.order;

import pl.klaster.ecommerce.canonicalmodel.Identifier;

public interface ClientInformation {
    public ClientData getDataForClient(Identifier id);
}
