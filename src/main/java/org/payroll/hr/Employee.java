package org.payroll.hr;

public abstract class Employee implements Payable {
    private String name;
    private double payRate;
    private final int EMPLOYEE_ID;
    private Address address;
    private static int nextID = 1000;
    private static final double STARTING_PAY_RATE = 7.75;

    public Employee(String name) {
        this.name = name;
        EMPLOYEE_ID = getNextID();
        payRate = STARTING_PAY_RATE;
    }

    public Employee(String name, double payRate) {
        this.name = name;
        this.payRate = payRate;
        EMPLOYEE_ID = getNextID();
    }

    public static int getNextID() {
        int id = nextID;
        nextID++;
        return id;
    }

    public int getEMPLOYEE_ID() {
        return this.EMPLOYEE_ID;
    }

    public double getPayRate() {
        return this.payRate;
    }

    public void displayAddress() {
        System.out.println(address.toString());
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPayRate(double payRate) {
        this.payRate = payRate;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString(){
        return "Employee ID - " + this.EMPLOYEE_ID + "\nName - " +this.name
                + "\n" + this.address.toString();
    }
}

