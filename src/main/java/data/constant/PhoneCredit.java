package data.constant;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PhoneCredit {
  M10K(BigDecimal.valueOf(10_000), "Rp10.000,00"),
  M20K(BigDecimal.valueOf(20_000), "Rp20.000,00"),
  M50K(BigDecimal.valueOf(50_000), "Rp50.000,00"),
  M100K(BigDecimal.valueOf(100_000), "Rp100.000,00"),

  ;

  private BigDecimal value;
  private String display;

  public static PhoneCredit getByOrder(int order) {
    switch (order) {
      case 0:
        return M10K;
      case 1:
        return M20K;
      case 2:
        return M50K;
      case 3:
        return M100K;
      default:
        throw new IllegalArgumentException("Cannot find PhoneCredit of order " + order);
    }
  }
}
