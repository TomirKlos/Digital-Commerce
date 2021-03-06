package pl.klaster.ecommerce.sales.infrastructure;

import pl.klaster.ecommerce.canonicalmodel.Entity;
import pl.klaster.ecommerce.canonicalmodel.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryRepository<Item extends Entity> {

    protected  List<Item> items;

    public InMemoryRepository() {
        this.items = new ArrayList<>();
    }

    public void add(Item order) {
        items.add(order);
    }

    public Item load(Identifier id) {
        Optional<Item> item = items.stream()
                .filter((i -> i.getId().equals(id)))
                .reduce((acc, first) -> first)
                ;

        if (!item.isPresent()) {
            throw new RuntimeException("Order not found");
        }

        return item.get();
    }
}
