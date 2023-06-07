package data.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Feature {
  ACCOUNT_BALANCE_INFORMATION("Account Balance Information"),
  MONEY_WITHDRAWAL("Money Withdrawal"),
  PHONE_CREDITS_TOP_UP("Phone Credits Top-Up"),
  ELECTRICITY_BILLS_TOKEN("Electricity Bills Token"),
  ACCOUNT_MUTATION("Account Mutation/Funds Transfer"),
  MONEY_DEPOSIT("Money Deposit"),

  ;

  private String name;

  public static Feature getByOrder(int order) {
    switch (order) {
      case 0:
        return ACCOUNT_BALANCE_INFORMATION;
      case 1:
        return MONEY_WITHDRAWAL;
      case 2:
        return PHONE_CREDITS_TOP_UP;
      case 3:
        return ELECTRICITY_BILLS_TOKEN;
      case 4:
        return ACCOUNT_MUTATION;
      case 5:
        return MONEY_DEPOSIT;
      default:
        throw new IllegalArgumentException("Cannot find Feature of order " + order);
    }
  }
}
