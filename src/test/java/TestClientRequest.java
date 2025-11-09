
import com.github.nylle.javafixture.*;
import org.lista1.app.source.clientRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestClientRequest {

    private final Fixture fixture = new Fixture();

    private String generateRandomString(int maxLength) {
        String temp = fixture.create(String.class);
        if(temp.length()>maxLength){
            return temp.substring(0, maxLength);
        }
        return temp;
    }
    private String randomUnit(){
        return Math.random() < 0.5 ? "szt" : "kg";
    }

    @Test
    void testValidRandomOrder() {
        clientRequest.Customer randomCustomer = new clientRequest.Customer(
                generateRandomString(6), // firstName
                generateRandomString(6), // lastName
                generateRandomString(10), // email
                generateRandomString(9)  // phoneNumber
        );

        int productCount = (int)(Math.random() * 12);
        List<clientRequest.Product> randomProducts = IntStream.range(0, productCount)
                .mapToObj(i -> new clientRequest.Product(
                        generateRandomString(3),            // productCode
                        Math.abs(fixture.create(Integer.class)) % 100 + 1, // quantity >= 1
                        randomUnit()             // unit
                ))
                .collect(Collectors.toList());


        clientRequest testRequest = new clientRequest(randomCustomer, randomProducts);
        System.out.println("Order ID: " + testRequest.getOrderID());
        assertDoesNotThrow(() -> testRequest.validate());
    }

}
