package org.payroll.hr;

public class SalariedEmployee extends Employee{
    private double salary;

    public SalariedEmployee(String name) {
        super(name);
    }

    public SalariedEmployee(String name, double salary) {
        super(name);
        this.salary = salary;
    }

    @Override
    public double calculatePay() {
        return salary - (salary * .3);
    }

    @Override
    public String generatePayStub(String payDate) {
        return "\t\t\t" + "\n\t" + "Name: " + this.getName() + " - Employee Id: " + this.getEMPLOYEE_ID() +
                "\n\t" + "Pay Date \t " + payDate +
                "\n\t" + "Gross Salary $" + this.salary +
                "\n\t" + "Net Salary $" + calculatePay() + "\n\n";
    }
}
