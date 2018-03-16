package pl.klaster.ecommerce.sales.domain.offer.discounts;

import pl.klaster.ecommerce.sales.domain.offer.OfferItem;
import pl.klaster.ecommerce.sales.domain.offer.Discount;
import pl.klaster.ecommerce.sales.domain.offer.DiscountPolicy;

public class QuantityDiscount implements DiscountPolicy {

    private Integer itemCount;
    private Double discountValue;

    public QuantityDiscount(Integer itemCount, Double discountValue) {
        this.itemCount = itemCount;
        this.discountValue = discountValue;
    }

    @Override
    public Discount calculateDiscount(OfferItem item) {
        if (item.getQuantity() >= itemCount) {
            return new Discount("Item count", (item.getTotalCost() * discountValue));
        }

        return Discount.noDiscount();
    }
}
