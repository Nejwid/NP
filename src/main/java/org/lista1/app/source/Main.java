package org.lista1.app.source;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

/** consider a company that imports any products upon customer request,
    anything from car parts to even custom neck lanyard with the university logo. */

public class Main {
    public static void main(String[] args) {
        apacheConfig jsonPath = new apacheConfig();
        //System.out.print(jsonLoader.getOrderRequestsDirectory());
        jsonLoader loader = new jsonLoader(jsonPath.getOrderRequestsDirectory());
        List<String> jsonRequests = loader.loadAllJsonOrders(); /** załadowane jsony z uzyciem apache*/

        ObjectMapper mapper = new ObjectMapper(); /** obiekt z klasy do mapowania danyhc z jsona do naszej klasy*/
        List<clientRequest> requests = new ArrayList<>(); /** lista na zamowienia*/
        for (String json : jsonRequests) {
            try {
                clientRequest temp = mapper.readValue(json, clientRequest.class); /** "tymczasowe" obiekty na zamowienia*/
                requests.add(temp);
            } catch (Exception e) {
                System.err.println("Błąd przy deserializacji JSON" + e.getMessage());
                e.printStackTrace();
            }
        }
        for (clientRequest request : requests) {
            System.out.println(request.getOrderID());
        }
    }
}