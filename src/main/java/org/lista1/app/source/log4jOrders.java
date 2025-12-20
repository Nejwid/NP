package org.lista1.app.source;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class log4jOrders {

    private static final Logger logger = LogManager.getLogger();
    private static final Random random = new Random();

    public static void logOrderDetails(clientRequest order) {
        logger.info("Numer zamówienia: {}", order.getOrderID());

        clientRequest.Customer customer = order.getCustomer();
        logger.info("Dane klienta: {} {} | Email: {} | Telefon: {}",
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );

        logger.info("Produkty w zamówieniu:");
        for (clientRequest.Product p : order.getProducts()) {
            double randomPrice = Math.round((random.nextDouble() * 100 + 1) * 100.0) / 100.0; // losowa cena 1-100 PLN, zaokrąglona
            logger.info("- {} ({} {}) : {} PLN",
                    p.getProductCode(),
                    p.getQuantity(),
                    p.getUnit(),
                    randomPrice
            );
        }
    }
}
