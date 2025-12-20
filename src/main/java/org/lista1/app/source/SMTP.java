package org.lista1.app.source;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.Multipart;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class SMTP {

    private static final Logger logger = LogManager.getLogger();
    private static final Random random = new Random();

    private final String smtpHost;
    private final String smtpPort;
    private final String fromEmail;

    // Singleton instance
    private static SMTP instance;

    // Prywatny konstruktor
    private SMTP() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/appsettings.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Nie można wczytać pliku appsettings.properties", e);
        }

        this.smtpHost = props.getProperty("mail.smtp.host");
        this.smtpPort = props.getProperty("mail.smtp.port");
        this.fromEmail = props.getProperty("mail.from");

        logger.info("SMTP skonfigurowany: host={}, port={}, from={}", smtpHost, smtpPort, fromEmail);
    }

    // Publiczna metoda zwracająca instancję singletona
    public static synchronized SMTP getInstance() {
        if (instance == null) {
            instance = new SMTP();
        }
        return instance;
    }

    public void sendOrderEmail(clientRequest order) {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(order.getCustomer().getEmail())
            );
            message.setSubject("Podsumowanie zamówienia nr " + order.getOrderID());

            StringBuilder emailContent = new StringBuilder();
            emailContent.append("<h3>Podsumowanie zamówienia</h3>");
            emailContent.append("<p><strong>Dane klienta:</strong><br>");
            emailContent.append(order.getCustomer().getFirstName()).append(" ")
                    .append(order.getCustomer().getLastName()).append("<br>");
            emailContent.append("Email: ").append(order.getCustomer().getEmail()).append("<br>");
            emailContent.append("Telefon: ").append(order.getCustomer().getPhoneNumber()).append("</p>");

            emailContent.append("<p><strong>Produkty:</strong><br>");
            for (clientRequest.Product p : order.getProducts()) {
                double price = Math.round((random.nextDouble() * 100 + 1) * 100.0) / 100.0; // losowa cena 1-100 PLN
                emailContent.append(p.getProductCode())
                        .append(" (").append(p.getQuantity()).append(" ").append(p.getUnit()).append(")")
                        .append(" : ").append(price).append(" PLN<br>");
            }
            emailContent.append("</p>");

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(emailContent.toString(), "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            logger.info("E-mail z podsumowaniem zamówienia nr {} wysłany do {}",
                    order.getOrderID(), order.getCustomer().getEmail());

        } catch (MessagingException e) {
            logger.error("Błąd wysyłki e-maila dla zamówienia nr {}: {}", order.getOrderID(), e.getMessage());
        }
    }
}
