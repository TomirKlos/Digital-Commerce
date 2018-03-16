package pl.klaster.ecommerce.sales.domain.productcatalog;

import org.springframework.stereotype.Repository;
import pl.klaster.ecommerce.canonicalmodel.Identifier;

@Repository
public interface ProductCatalog {
    public Product load(Identifier id);

    public void add(Product product);
}
