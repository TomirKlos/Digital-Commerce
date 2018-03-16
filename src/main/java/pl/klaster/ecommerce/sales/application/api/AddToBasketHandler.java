package pl.klaster.ecommerce.sales.application.api;

import org.springframework.stereotype.Component;
import pl.klaster.ecommerce.sales.domain.basket.Basket;
import pl.klaster.ecommerce.sales.domain.basket.BasketStorage;
import pl.klaster.ecommerce.sales.domain.productcatalog.Product;
import pl.klaster.ecommerce.sales.domain.productcatalog.ProductCatalog;
import pl.klaster.ecommerce.system.SystemUserContext;

@Component
public class AddToBasketHandler {

    private SystemUserContext systemUserContext;
    private BasketStorage basketStorage;
    private ProductCatalog productCatalog;

    public AddToBasketHandler(SystemUserContext systemUserContext, BasketStorage basketStorage, ProductCatalog productCatalog) {
        this.systemUserContext = systemUserContext;
        this.basketStorage = basketStorage;
        this.productCatalog = productCatalog;
    }

    public void handle(AddToBasketCommand command) {
        Basket basket = basketStorage.loadForCustomer(systemUserContext.getCurrentUser().getId());

        Product product = productCatalog.load(command.getProductId());

        basket.add(product);

        basketStorage.store(systemUserContext.getCurrentUser().getId(), basket);
    }
}
