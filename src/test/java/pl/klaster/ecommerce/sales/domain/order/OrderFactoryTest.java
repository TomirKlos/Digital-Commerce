package pl.klaster.ecommerce.sales.domain.order;

import org.junit.Assert;
import org.junit.Test;
import pl.klaster.ecommerce.canonicalmodel.Identifier;
import pl.klaster.ecommerce.sales.domain.offer.Offer;
import pl.klaster.ecommerce.sales.domain.payment.NullPaymentGateway;

import java.util.ArrayList;

public class OrderFactoryTest {

    @Test
    public void itVerifyIfThereAreItems() {
        OrderFactory orderFactory = new OrderFactory(new NullPaymentGateway());

        Offer offer = new Offer(new ArrayList<>());

        try {
            Order order = orderFactory.create(new Identifier("order_1"), offer, exampleClientData());
            Assert.fail("Not allow to create order without items");
        } catch (OrderMustContainsItemsException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void itCalculate() {
        OrderFactory orderFactory = new OrderFactory(new NullPaymentGateway());

        Offer offer = new Offer(new ArrayList<>());

        try {
            Order order = orderFactory.create(new Identifier("order_1"), offer, exampleClientData());
            Assert.fail("Not allow to create order without items");
        } catch (OrderMustContainsItemsException e) {
            Assert.assertTrue(true);
        }
    }

    private ClientData exampleClientData() {
        ClientData client = new ClientData(new Identifier("jakub"), "jakub.kanclerz@gmail.com");

        return client;
    }
}
