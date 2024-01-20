package com.cred4.credbackend.repository;

import com.cred4.credbackend.dto.PaymentRecordDetails;

public interface PaymentRepositoryService {
    
    void addPaymentRecord(PaymentRecordDetails paymentRecordDetails);

    void deletePaymentRecord(String paymentId);

    PaymentRecordDetails getPaymentRecord(String paymentId);

}
