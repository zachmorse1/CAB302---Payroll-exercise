package org.payroll.hr;

public interface Payable {

    double calculatePay();
    String generatePayStub(String payDate);
}
