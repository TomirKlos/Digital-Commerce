package pl.klaster.ecommerce.sales.domain.offer;

import pl.klaster.ecommerce.sales.domain.offer.discounts.QuantityDiscount;

public class DiscountFactory {
    public static DiscountPolicy offerDiscount() {
        return staticDiscount();
    }

    public static DiscountPolicy checkoutDiscount() {
        return staticDiscount();
    }

    private static DiscountPolicy staticDiscount() {
        return new QuantityDiscount(5, 0.20);
    }
}
