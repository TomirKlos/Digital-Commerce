package pl.klaster.digitalcommerce.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.klaster.digitalcommerce.components.service.EmailService;
import pl.klaster.ecommerce.canonicalmodel.Identifier;
import pl.klaster.ecommerce.sales.application.api.ConfirmOrderCommand;
import pl.klaster.ecommerce.sales.application.api.ConfirmOrderHandler;
import pl.klaster.ecommerce.sales.application.api.VerifyPaymentCommand;
import pl.klaster.ecommerce.sales.application.api.VerifyPaymentHandler;
import pl.klaster.ecommerce.sales.domain.order.OrderRepository;
import pl.klaster.ecommerce.sales.readmodel.offer.OfferDto;
import pl.klaster.ecommerce.sales.readmodel.offer.OfferFinder;
import pl.klaster.ecommerce.sales.readmodel.readmodel.PaymentURLs;

import java.util.Map;

@RestController
public class CheckoutController {

    @Autowired
    private OfferFinder offerMaker;

    @Autowired
    ConfirmOrderHandler confirmOrderHandler;

    @Autowired
    PaymentURLs paymentURLs;

    @Autowired
    VerifyPaymentHandler verifyPaymentCommandHandler;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    @Qualifier("emailService")
    private EmailService emailService;

    @RequestMapping("/offer")
    @ResponseBody
    public OfferDto offer() {
        return offerMaker.currentOffer();
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    ResponseEntity checkout(@RequestBody Map<String, String> payload) {

        Identifier id = Identifier.generateUUID();
        confirmOrderHandler.handle(new ConfirmOrderCommand(id, payload.get("email")));

        return ResponseEntity.ok(paymentURLs.getPaymentUrl(id));
    }

    @RequestMapping(value = "/verify-payment", method = RequestMethod.POST)
    @ResponseBody
    ResponseEntity verifyPayment(@RequestBody MultiValueMap<String,String> formData) {

        Identifier id = Identifier.generateUUID();

        VerifyPaymentCommand c = new VerifyPaymentCommand(
                formData.get("p24_session_id").get(0).toString(),
                formData.get("p24_order_id").get(0).toString(),
                formData.get("p24_sign").get(0).toString()
        );

        verifyPaymentCommandHandler.handle(c);

        emailService.sendEmailWithSerial(formData);

        return ResponseEntity.ok("");
    }



}