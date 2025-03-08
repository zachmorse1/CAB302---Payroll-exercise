package org.payroll.hr;

public class SalariedEmployee extends Employee{
    private double salary;

    public SalariedEmployee(String name) {
        super(name);
    }

    public SalariedEmployee(String name, double payRate) {
        super(name, payRate);
    }

    @Override
    public double CalculatePay() {
        return 0;
    }

    @Override
    public String generatePayStub(String payDate) {
        return "";
    }
}
