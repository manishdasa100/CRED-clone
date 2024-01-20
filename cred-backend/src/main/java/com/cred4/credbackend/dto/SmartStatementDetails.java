package com.cred4.credbackend.dto;

import java.util.List;

import com.cred4.credbackend.models.TransactionByCategory;
import com.cred4.credbackend.models.TransactionByVendor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmartStatementDetails {
    
    List<TransactionByVendor> transactionsByVendor;

    List<TransactionByCategory> transactionsByCategory;
}
