package org.payroll.hr;

public abstract class Employee {
    private String name;
    private double payRate;
    private final int EMPLOYEE_ID;
    private Address address;
    private static int nextID = 1000;
    private static final double STARTING_PAY_RATE = 7.75;

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, Double )

    public static int getNextID() {
        int id = nextID;
        nextID++;
        return id;
    }
}

