package pl.klaster.ecommerce.sales.domain.productcatalog;

import org.junit.Assert;
import org.junit.Test;
import pl.klaster.ecommerce.canonicalmodel.Identifier;

public class ProductTest {

    @Test
    public void itVerifyStockAvailability() {
        Product inStock = new Product(Identifier.byString("lego"), 10);
        Product notInStock = new Product(Identifier.byString("lego"), 0);

        Assert.assertTrue(inStock.isInStock());
        Assert.assertFalse(notInStock.isInStock());
    }
}
