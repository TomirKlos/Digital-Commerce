package pl.klaster.ecommerce.sales.infrastructure;

import pl.klaster.ecommerce.canonicalmodel.Identifier;
import pl.klaster.ecommerce.sales.domain.order.ClientData;
import pl.klaster.ecommerce.sales.domain.order.ClientInformation;

public class InMemoryClientInformation implements ClientInformation {
    @Override
    public ClientData getDataForClient(Identifier id) {
        return new ClientData(id, "email@email.pl");
    }
}
