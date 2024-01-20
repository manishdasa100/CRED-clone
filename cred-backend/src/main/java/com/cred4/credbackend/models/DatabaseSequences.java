package com.cred4.credbackend.models;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Database_sequences")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DatabaseSequences {
    
    @Id
    private String Id;

    private String seqData;
}
