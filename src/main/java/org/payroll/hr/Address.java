package org.payroll.hr;

public class Address {
    private String street;
    private String city;
    private String state;
    private String postCode;

    public Address(String street, String city, String state, String postCode){
        this.street = street;
        this.city = city;
        this.state = state;
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return "Street: " + this.street
                +"\nCity: " + this.city
                +"\nState: " + this.state
                +"\npostCode: " + this.postCode;
    }

}
