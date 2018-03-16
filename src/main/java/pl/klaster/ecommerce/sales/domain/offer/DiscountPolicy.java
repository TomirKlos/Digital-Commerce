package pl.klaster.ecommerce.sales.domain.offer;

public interface DiscountPolicy {
    public Discount calculateDiscount(OfferItem item);
}
