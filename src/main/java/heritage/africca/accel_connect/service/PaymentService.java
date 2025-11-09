package heritage.africca.accel_connect.service;

import heritage.africca.accel_connect.entity.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<Payment> getAllPayments();
    Optional<Payment> getPaymentById(Long id);
    Payment savePayment(Payment payment);
    Payment updatePayment(Long id, Payment payment);
    void deletePayment(Long id);

}
