package pl.klaster.ecommerce.sales.application.api;

import pl.klaster.ecommerce.canonicalmodel.Identifier;

public class AddToBasketCommand {
    private Identifier productId;

    public AddToBasketCommand(Identifier productId) {
        this.productId = productId;
    }

    public Identifier getProductId() {
        return productId;
    }
}
