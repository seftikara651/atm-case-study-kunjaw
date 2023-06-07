package data.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
  private String id;

  private String account;
  private String pin;

  private String fullName;

  private BigDecimal balance;

  @Builder.Default
  private Integer invalidTries = 0;

  public void recordFailure() {
    this.invalidTries += 1;
  }

  public boolean isBlocked() {
    return this.invalidTries >= 3;
  }

  public void add(BigDecimal amount) {
    this.balance = this.balance.add(amount);
  }

  public void subtract(BigDecimal amount) {
    this.balance = this.balance.subtract(amount);
  }
}
