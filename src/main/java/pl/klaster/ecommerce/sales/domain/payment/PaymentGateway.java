package pl.klaster.ecommerce.sales.domain.payment;

public interface PaymentGateway {
    String obtainPaymentURL(Payment payment);

    String obtainPaymentToken(Payment payment);

    String obtainPaymentURL(String token);
}
