package pl.klaster.digitalcommerce.components.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import pl.klaster.ecommerce.canonicalmodel.Identifier;
import pl.klaster.ecommerce.sales.domain.order.OrderItem;
import pl.klaster.ecommerce.sales.domain.order.OrderRepository;
import pl.klaster.ecommerce.sales.readmodel.productcatalog.ProductFinder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;

@Service("emailService")
public class EmailService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductFinder productFinder;

    private JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        mailSender.send(email);
    }

    public void sendEmailWithSerial(MultiValueMap<String,String> formData){

        String sessionId = formData.get("p24_session_id").get(0).toString();
        if(isConfirmed(sessionId)) {
            String email = getEmail(sessionId);
            Collection<OrderItem> boughtItems = getOrderedItemsBySessionId(sessionId);

            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(email);
            registrationEmail.setSubject("Sklep UEK");
            registrationEmail.setText(generateSerialForBoughtProducts(boughtItems));
            registrationEmail.setFrom(System.getenv("SPRING_MAIL_SENDER"));

            sendEmail(registrationEmail);
        }
    }

    public String getEmail(String sessionId){
        return orderRepository.loadByPayment(Identifier.byString(sessionId)).getPayment().getClientData().getEmail();
    }

    public boolean isConfirmed(String sessionId){
        return orderRepository.loadByPayment(Identifier.byString(sessionId)).getPayment().isConfirmed();
    }

    public Collection<OrderItem> getOrderedItemsBySessionId(String sessionId){
        return orderRepository.loadByPayment(Identifier.byString(sessionId)).getItems();
    }

    public String generateSerialForBoughtProducts(Collection<OrderItem> boughtItems){
        String tekst="Witaj, dziękujemy za zakup w naszym sklepie :) \n \n Oto lista Twoich zakupów:";

        for(OrderItem item:boughtItems){
            tekst = tekst + "\n\n" +productFinder.findById(item.getProductId().toString()).getName();
            for(int i=0;i<item.getQuantity();i++){
                String fakeSerial = (item.getProductId().toString() + i + System.currentTimeMillis()*Math.random()*100+50);
                tekst = tekst + "\n " + (Base64.getEncoder().encodeToString(fakeSerial.getBytes(StandardCharsets.UTF_8))).substring(0, 25);
            }
        }
        return tekst;
    }
}
