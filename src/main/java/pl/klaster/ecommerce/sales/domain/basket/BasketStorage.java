package pl.klaster.ecommerce.sales.domain.basket;

import org.springframework.stereotype.Component;
import pl.klaster.ecommerce.canonicalmodel.Identifier;

@Component
public interface BasketStorage {
    public Basket loadForCustomer(Identifier id);

    public void store(Identifier id, Basket basket);
}
