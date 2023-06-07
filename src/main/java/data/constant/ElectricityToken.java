package data.constant;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ElectricityToken {
  M50K(BigDecimal.valueOf(50_000), "Rp50.000,00"),
  M100K(BigDecimal.valueOf(100_000), "Rp100.000,00"),
  M200K(BigDecimal.valueOf(200_000), "Rp200.000,00"),
  M500K(BigDecimal.valueOf(500_000), "Rp500.000,00"),

  ;

  private BigDecimal value;
  private String display;

  public static ElectricityToken getByOrder(int order) {
    switch (order) {
      case 0:
        return M50K;
      case 1:
        return M100K;
      case 2:
        return M200K;
      case 3:
        return M500K;
      default:
        throw new IllegalArgumentException("Cannot find ElectricityToken of order " + order);
    }
  }
}
