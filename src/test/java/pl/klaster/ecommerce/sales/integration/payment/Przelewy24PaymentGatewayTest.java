package pl.klaster.ecommerce.sales.integration.payment;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import pl.klaster.ecommerce.canonicalmodel.Identifier;
import pl.klaster.ecommerce.sales.application.services.payment.Przelewy24PaymentGateway;
import pl.klaster.ecommerce.sales.domain.order.ClientData;
import pl.klaster.ecommerce.sales.domain.payment.Payment;
import pl.klaster.przelewy24.Przelewy24Api;
import pl.klaster.przelewy24.Przelewy24Properties;
import pl.klaster.support.http.NativeHttpClient;

public class Przelewy24PaymentGatewayTest {
    @Test
    @Ignore
    public void itAllowProceedPaymentProcess() {
        Payment p = thereIsExamplePayment();
        Przelewy24PaymentGateway gateway = new Przelewy24PaymentGateway(przelewy24Api(), "http://uek.krakow.pl", "http://uek.krakow.pl");

        String url = gateway.obtainPaymentURL(p);

        Assert.assertTrue(url.contains("https://sandbox.przelewy24.pl/trnRequest"));
        Assert.assertTrue(
                "Token does not match regex",
                url.matches("^.*[0-9A-Z]{10}-[0-9A-Z]{6}-[0-9A-Z]{6}-[0-9A-Z]{10}$")
        );
    }

    private Payment thereIsExamplePayment() {
        return new Payment(new Identifier("payment_1234"), new ClientData(new Identifier("c_1"), "noreply.uek.dev.test@gmail.com"), 123.00);
    }

    private Przelewy24Api przelewy24Api() {
        return new Przelewy24Api(
                new Przelewy24Properties(
                    System.getenv("P24_MERCHANT_ID"),
                    System.getenv("P24_MERCHANT_ID"),
                    System.getenv("P24_CRC")
                ),
                new NativeHttpClient()
        );
    }
}
