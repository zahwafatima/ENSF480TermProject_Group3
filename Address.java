public class Address {
    
    private String street;
    private String city; 
    private String country;
    
    // Constructor
    public Address(String street, String city, String country) {
        this.street = street;
        this.city = city;
        this.country = country;
    }

    //getters and setters
    public String getStreet(){
        return street;
    }
    public void setStreet(String Street){
        this.street = Street;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}

