package pl.klaster.ecommerce.functional;

import org.junit.Assert;
import org.junit.Test;

import pl.klaster.ecommerce.canonicalmodel.Identifier;
import pl.klaster.ecommerce.sales.domain.basket.Basket;
import pl.klaster.ecommerce.sales.domain.productcatalog.Product;
import pl.klaster.ecommerce.sales.application.api.AddToBasketCommand;
import pl.klaster.ecommerce.sales.application.api.AddToBasketHandler;
import pl.klaster.ecommerce.sales.infrastructure.InMemoryBasketStorage;
import pl.klaster.ecommerce.sales.infrastructure.InMemoryProductCatalog;
import pl.klaster.ecommerce.system.infrastructure.InMemorySystemUserContext;

public class CollectProductsTest {

    private final InMemoryProductCatalog productCatalog;
    private final AddToBasketHandler addToBasketHandler;
    private final InMemoryBasketStorage basketStorage;
    private InMemorySystemUserContext systemUserContext;

    public CollectProductsTest() {
        this.systemUserContext = new InMemorySystemUserContext();
        this.productCatalog = new InMemoryProductCatalog();
        this.basketStorage = new InMemoryBasketStorage();
        this.addToBasketHandler = new AddToBasketHandler(
            systemUserContext,
            basketStorage,
            productCatalog
        );
    }

    @Test
    public void itAllowToSelectProduct() {
        iAmGuestBuyerIdentifiedWith(new Identifier("customer_1"));
        thereIsProductIdentifiedWith(new Identifier("p1"));
        thereIsProductIdentifiedWith(new Identifier("p2"));

        chooseProduct(new Identifier("p1"));
        chooseProduct(new Identifier("p2"));

        shoppingListForCustomerContainsProduct(new Identifier("customer_1"), new Identifier("p1"));
        shoppingListForCustomerContainsProduct(new Identifier("customer_1"), new Identifier("p2"));
    }

    private void shoppingListForCustomerContainsProduct(Identifier customer, Identifier product) {
        Basket basket = this.basketStorage.loadForCustomer(customer);

        Assert.assertFalse("Basket should contains products", basket.getReservedProducts().isEmpty());

        basket.getReservedProducts()
            .stream()
            .filter(basketItem -> basketItem.getId().equals(product))
            .forEach(basketItem -> Assert.assertTrue(basketItem.getId().equals(product)))
        ;
    }

    private void chooseProduct(Identifier p1) {
        addToBasketHandler.handle(new AddToBasketCommand(p1));
    }

    private void thereIsProductIdentifiedWith(Identifier p) {
        productCatalog.add(new Product(p, 10));
    }

    private void iAmGuestBuyerIdentifiedWith(Identifier id) {
        systemUserContext.authenticate(id);
    }
}
