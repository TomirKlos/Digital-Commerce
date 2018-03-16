package pl.klaster.ecommerce.sales.infrastructure;

import org.springframework.stereotype.Repository;
import pl.klaster.ecommerce.canonicalmodel.Identifier;
import pl.klaster.ecommerce.sales.domain.order.Order;
import pl.klaster.ecommerce.sales.domain.order.OrderRepository;

import java.util.Optional;

@Repository
public class InMemoryOrderRepository extends InMemoryRepository<Order> implements OrderRepository {
    @Override
    public Order loadByPayment(Identifier id) {

        Optional<Order> order = items.stream()
                .filter(o -> o.getPayment().getId().equals(id))
                .reduce((acc, first) -> first)
        ;

        if (!order.isPresent()) {
            throw new RuntimeException("Order not found");
        }

        return order.get();
    }
}
