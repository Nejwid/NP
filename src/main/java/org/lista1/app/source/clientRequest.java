package org.lista1.app.source;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** this class contains client info and ordered products*/

public class clientRequest {
    private final Customer customer;
    private final List<Product> products;
    private String orderID;
    @JsonCreator
    public clientRequest(
            @JsonProperty("customer") Customer customer,
            @JsonProperty("products") List<Product> products){
            /**Jackson fasterxml mapuje to w pustą listę a potem inicjuje kolejny obiekt na każde pole
            // można to zrobić bo klasy wewnętrzne są static
            // jesli byłoby to pole "static" jakiejś klasy to byłaby wspolna dla wszystkich "clientRequest"
            // ale jeśli Product to static klasa wewnętrzna to oznacza tylko tyle że można tworzyć nowe Producty bez wczesniejszego tworzenia clientRequest
            // ostatecznie Jackson tworzy instancje "clientRequest" z gotową juz listą i klientem*/
        this.customer = customer;
        this.products = products;
        this.orderID = "";
    }
    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    static class Customer{ /** klasa jest static bo wtedy moze istniec bez instancji klasy zewnetrznej*/
        private final String firstName, lastName,email, phoneNumber;
        @JsonCreator
                /**mapowanie danych z pliku json do pól*/
        Customer(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName, @JsonProperty("email") String email, @JsonProperty("phoneNumber") String phoneNumber ){
            this.email = email;
            this.lastName = lastName;
            this.firstName = firstName;
            this.phoneNumber = phoneNumber;
        }
        public String getFirstName() {
            return firstName;
        }
        public String getLastName() {
            return lastName;
        }
        public String getEmail() {
            return email;
        }
        public String getPhoneNumber() {
            return phoneNumber;
        }
    }

    static class Product {
        private final String unit, productCode;
        private final int quantity;

        @JsonCreator
        Product(@JsonProperty("productCode") String procuctCode, @JsonProperty("quantity") int quantity, @JsonProperty("unit") String unit) {
            this.unit = unit;
            this.productCode = procuctCode;
            this.quantity = quantity;
        }

        String getUnit() {
            return this.unit;
        }

        String getProductCode() {
            return this.productCode;
        }

        int getQuantity() {
            return this.quantity;
        }
    }

    private String generateID(){
        String id = "";

        id += customer.getFirstName() + "-";
        id += customer.getLastName() + "-";
        id += customer.getPhoneNumber() + "-";
        id += customer.getEmail();

        for (Product p : this.products) {
            id += "-" + p.getProductCode();
            id += "-" + p.getQuantity();
            id += "-" + p.getUnit();
        }

        return id.replaceAll("\\s+", "");
    }

    String getOrderID(){
        if(!this.orderID.isEmpty()){
            return this.orderID;
        }
        this.orderID = generateID();
        return this.orderID;
    }

    //zadanie 4.3
    public void validate() throws IllegalArgumentException {
        if (customer.getFirstName().isBlank() || customer.getLastName().isBlank()) {
            throw new IllegalArgumentException("incorrect name/last name");
        }
        if(products.size()<1 || products.size()>9){
            throw new IllegalArgumentException("invalid products count");
        }

        for (Product p : products) {
            int quantity = p.getQuantity();

            if (quantity < 1) {
                throw new IllegalArgumentException("incorrect quantity");
            }
        }
    }
}
