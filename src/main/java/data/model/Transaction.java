package data.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import data.constant.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
  private String id;

  private LocalDateTime timestamp;

  private Customer customer;
  private TransactionType type;

  private BigDecimal expense;
}
