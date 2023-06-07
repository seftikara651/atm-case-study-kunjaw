package com.tujuhsembilan.logic;

import data.constant.BankCompany;
import data.constant.CurrencyNominal;
import data.constant.ElectricityToken;
import data.constant.PhoneCredit;
import data.constant.TransactionType;
import data.model.ATM;
import data.model.Bank;
import data.model.Customer;
import data.model.Transaction;
import data.repository.BankRepo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.tujuhsembilan.logic.ConsoleUtil.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ATMLogic {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static final class Arguments {
    private ATM atm;
    private Customer customer;
  }

  public static Customer login() {
    System.out.println("Please input your account number");
    System.out.print(" > ");
    String accountNumber = nextLine();

    Optional<Customer> qCustomer = BankRepo.findAllCustomers().stream()
        .filter(item -> accountNumber.equals(item.getAccount()))
        .findAny();
    if (qCustomer.isPresent()) {
      Customer customer = qCustomer.get();

      while (!customer.isBlocked()) {
        printClear();
        System.out.println("Please input your pin");
        System.out.print(" > ");
        String pin = nextLine();

        if (pin.equals(customer.getPin())) {
          return customer;
        } else {
          customer.recordFailure();
          System.out.println("Invalid pin");
          delay();
        }
      }

      if (customer.isBlocked()) {
        printClear();
        System.out.println("Account is blocked");
        delay();
      }
    } else {
      System.out.println("Account not found");
      delay();
    }

    return null;
  }

  public static void chargeCrissCross(Arguments args) {
    Optional<Bank> qBank = BankRepo.findBankByCustomer(args.customer);
    if (qBank.isPresent() && !qBank.get().equals(args.atm.getBank())) {
      args.customer.subtract(BigDecimal.valueOf(2_500));
      printDivider();
      System.out.println("This transaction is additionally charged Rp2.500,00");
      printDivider();
      System.out.println();
    }
  }

  public static void accountBalanceInformation(Arguments args) {
    chargeCrissCross(args);

    printDivider();
    System.out.println("Your current balance is");
    printDivider('-');
    System.out.println(NumberUtil.formatIdr(args.customer.getBalance()));
    System.out.println(NumberUtil.spellNumber(args.customer.getBalance().longValue()) + " Rupiah");
    printDivider();
  }

  public static void moneyWithdrawal(Arguments args) {
    System.out.println("Please input withdraw amount");
    System.out.print(" > ");
    BigDecimal withdrawAmount = BigDecimal.valueOf(nextLong());

    if (withdrawAmount.longValue() % 10_000 == 0) {
      if (args.atm.getBalance().compareTo(withdrawAmount) >= 0) {
        if (withdrawAmount.compareTo(args.atm.getBank().getMaxExpensePerWithdrawal()) <= 0) {
          Optional<BigDecimal> qDailySum = args.atm.getBank().findAllTransactionsByAccount(args.customer.getAccount())
              .stream()
              .filter(item -> ChronoUnit.HOURS.between(item.getTimestamp(), LocalDateTime.now()) <= 24)
              .filter(item -> item.getType().equals(TransactionType.WITHDRAWAL))
              .map(Transaction::getExpense)
              .reduce((a, b) -> a.add(b));
          if (qDailySum.isEmpty()
              || qDailySum.get().add(withdrawAmount).compareTo(args.atm.getBank().getMaxExpensePerUserDaily()) <= 0) {
            if (withdrawAmount.compareTo(args.customer.getBalance().subtract(BigDecimal.valueOf(10_000))) <= 0) {
              args.atm.getBank().getTransactions().add(Transaction.builder()
                  .timestamp(LocalDateTime.now())
                  .customer(args.customer)
                  .expense(withdrawAmount)
                  .type(TransactionType.WITHDRAWAL)
                  .build());
              args.atm.subtract(withdrawAmount);
              args.customer.subtract(withdrawAmount);

              printClear();
              printDivider();
              System.out.println("Withdraw amount");
              printDivider('-');
              System.out.println(NumberUtil.formatIdr(withdrawAmount));
              System.out.println(NumberUtil.spellNumber(withdrawAmount.longValue()) + " Rupiah");
              printDivider('-');
              System.out.println("Please take");
              for (CurrencyNominal amount : Arrays.asList(CurrencyNominal.values())) {
                int num = 0;
                while (withdrawAmount.subtract(amount.getValue()).compareTo(BigDecimal.valueOf(0)) >= 0) {
                  num++;
                  withdrawAmount = withdrawAmount.subtract(amount.getValue());
                }
                if (num > 0)
                  System.out.println(num + "x " + amount.getDisplay());
              }
              accountBalanceInformation(args);
            } else {
              System.out.println("You do not have sufficient balance");
            }
          } else {
            BigDecimal remaining = args.atm.getBank().getMaxExpensePerUserDaily().subtract(qDailySum.get());
            if (remaining.compareTo(BigDecimal.valueOf(0)) <= 0) {
              System.out.println("Your daily expense have reached the limit of Rp5.000.000,00");
            } else {
              System.out.println("You may only expend another " + NumberUtil.formatIdr(remaining));
            }
          }
        } else {
          System.out.println("Cannot withdraw more than Rp2.500.000,00 at once");
        }
      } else {
        System.out.println("This ATM currently doesn't have sufficient balance");
      }
    } else {
      System.out.println("You can only deposit amount that is multiple of Rp10.000,00");
    }
  }

  public static void phoneCreditsTopUp(Arguments args) {
    String phoneNumber = null;
    PhoneCredit nominal = null;

    boolean valid;

    valid = false;
    while (!valid) {
      printClear();
      System.out.println("Please input phone number (exclude 0)");
      System.out.print(" > ");
      phoneNumber = nextLine();
      if (phoneNumber.length() >= 3 && phoneNumber.length() <= 13) {
        valid = true;
      } else {
        printInvalid();
      }
    }

    valid = false;
    while (!valid) {
      Integer selection = menuSelection(Arrays.asList(PhoneCredit.values()).stream()
          .map(PhoneCredit::getDisplay)
          .collect(Collectors.toList()), false);
      if (selection != null && selection > 0 && selection <= PhoneCredit.values().length) {
        nominal = PhoneCredit.getByOrder(selection - 1);

        if (nominal.getValue().compareTo(args.customer.getBalance().subtract(BigDecimal.valueOf(10_000))) <= 0) {
          args.customer.subtract(nominal.getValue());
        } else {
          System.out.println("You do not have sufficient balance");
          return;
        }

        valid = true;
      } else {
        printInvalid();
      }
    }

    printClear();
    printDivider();
    System.out.println("Your phone number: " + phoneNumber);
    System.out.println("Bought credit nominal: " + nominal.getDisplay());
    accountBalanceInformation(args);
  }

  public static void electricityBillsToken(Arguments args) {
    String bill = null;
    ElectricityToken nominal = null;

    boolean valid;

    printClear();
    System.out.println("Please input bill number");
    System.out.print(" > ");
    bill = nextLine();

    valid = false;
    while (!valid) {
      Integer selection = menuSelection(Arrays.asList(ElectricityToken.values()).stream()
          .map(ElectricityToken::getDisplay)
          .collect(Collectors.toList()), false);
      if (selection != null && selection > 0 && selection <= ElectricityToken.values().length) {
        nominal = ElectricityToken.getByOrder(selection - 1);

        if (nominal.getValue().compareTo(args.customer.getBalance().subtract(BigDecimal.valueOf(10_000))) <= 0) {
          args.customer.subtract(nominal.getValue());
        } else {
          System.out.println("You do not have sufficient balance");
          return;
        }

        valid = true;
      } else {
        printInvalid();
      }
    }

    printClear();
    printDivider();
    System.out.println("Your bill number: " + bill);
    System.out.println(
        "Your token: " + UUID.randomUUID().toString() + "_" +
            DigestUtils.sha256Hex(String.valueOf(System.currentTimeMillis())).substring(0, 8) + "_" +
            nominal.getValue());
    System.out.println("Bought credit nominal: " + nominal.getDisplay());
    accountBalanceInformation(args);
  }

  public static void accountMutation(Arguments args) {
    Bank targetBank = null;
    while (targetBank == null) {
      Integer selection = menuSelection(Arrays.asList(BankCompany.values()).stream()
          .map(item -> "ATM " + item.getName())
          .collect(Collectors.toList()), false);
      if (selection != null && selection > 0 && selection <= BankCompany.values().length) {
        targetBank = BankRepo.findBankByName(BankCompany.getByOrder(selection - 1).getName()).orElse(null);
        if (targetBank == null) {
          System.out.println("Bank doesn't exists");
        }
      } else {
        printInvalid();
      }
    }

    printClear();
    System.out.println("Please input target account number");
    System.out.print(" > ");
    String targetAccountNumber = nextLine();

    Optional<Customer> qCustomer = targetBank.findCustomerByAccount(targetAccountNumber);
    if (qCustomer.isPresent()) {
      Customer targetCustomer = qCustomer.get();

      printClear();
      System.out.println("Please input transfer amount (to " + targetCustomer.getAccount() + " a/n "
          + targetCustomer.getFullName() + ")");
      System.out.print(" > ");
      BigDecimal transferAmount = BigDecimal.valueOf(nextLong());

      if (transferAmount.compareTo(args.customer.getBalance().subtract(BigDecimal.valueOf(10_000))) <= 0) {
        targetCustomer.add(transferAmount);
        args.customer.subtract(transferAmount);

        printClear();
        printDivider();
        System.out.println("Target Bank: " + targetBank.getName());
        System.out.println("Target Account: " + targetCustomer.getAccount() + " a/n " + targetCustomer.getFullName());
        printDivider();
        System.out.println("Transfered amount");
        printDivider('-');
        System.out.println(NumberUtil.formatIdr(transferAmount));
        System.out.println(NumberUtil.spellNumber(transferAmount.longValue()) + " Rupiah");
        accountBalanceInformation(args);
      } else {
        System.out.println("You do not have sufficient balance");
      }
    } else {
      System.out.println("Account cannot be found on selected bank");
    }
  }

  public static void moneyDeposit(Arguments args) {
    System.out.println("Please input deposit amount");
    System.out.print(" > ");
    BigDecimal deposit = BigDecimal.valueOf(nextLong());

    if (deposit.longValue() % 10_000 == 0) {
      args.customer.add(deposit);

      printClear();
      printDivider();
      System.out.println("Deposited amount");
      printDivider('-');
      System.out.println(NumberUtil.formatIdr(deposit));
      System.out.println(NumberUtil.spellNumber(deposit.longValue()) + " Rupiah");
      accountBalanceInformation(args);
    } else {
      System.out.println("You can only deposit amount that is multiple of Rp10.000,00");
    }
  }

}
