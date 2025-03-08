package org.payroll.hr;

public interface Payable {

    double CalculatePay();
    String generatePayStub(String payDate);
}
