package pl.klaster.ecommerce.sales.domain.order;

import pl.klaster.ecommerce.canonicalmodel.Identifier;

public interface OrderRepository {

    public void add(Order order);

    public Order load(Identifier id);

    public Order loadByPayment(Identifier id);
}
