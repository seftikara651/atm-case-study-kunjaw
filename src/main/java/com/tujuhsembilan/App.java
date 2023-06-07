package com.tujuhsembilan;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tujuhsembilan.logic.ATMLogic;
import com.tujuhsembilan.logic.ATMLogic.Arguments;

import data.constant.BankCompany;
import data.constant.Feature;
import data.model.ATM;
import data.model.Bank;
import data.model.Customer;
import data.repository.ATMRepo;
import data.repository.BankRepo;

import static com.tujuhsembilan.logic.ConsoleUtil.*;

public class App {

  public static void main(String[] args) {
    boolean loop = true;
    while (loop) {
      Integer selection = menuSelection(Arrays.asList(BankCompany.values()).stream()
          .map(item -> "ATM " + item.getName())
          .collect(Collectors.toList()), false);
      if (selection != null && selection > 0 && selection <= BankCompany.values().length) {
        new App(BankCompany.getByOrder(selection - 1).getName()).start();
      } else if (selection == 999999999) {
        loop = false;
      } else {
        printInvalid();
      }
    }
  }

  /// --- --- --- --- ---

  final Bank bank;
  final ATM atm;

  public App(String bankName) {
    Bank lBank = null;
    ATM lAtm = null;

    Optional<Bank> qBank = BankRepo.findBankByName(bankName);
    if (qBank.isPresent()) {
      Optional<ATM> qAtm = ATMRepo.findATMByBank(qBank.get());
      if (qAtm.isPresent()) {
        lBank = qBank.get();
        lAtm = qAtm.get();
      }
    }

    this.bank = lBank;
    this.atm = lAtm;
  }

  public void start() {
    if (bank != null && atm != null) {
      printClear();
      Customer customer = ATMLogic.login();
      if (customer != null) {
        boolean loop = true;
        while (loop) {
          boolean depositable = Arrays.asList("BNI", "Mandiri").contains(atm.getBank().getName());
          Integer selection = menuSelection(Arrays.asList(Feature.values()).stream()
              .filter(item -> depositable || !item.equals(Feature.MONEY_DEPOSIT))
              .map(Feature::getName)
              .collect(Collectors.toList()));
          if (selection != null && selection > 0 && selection <= Feature.values().length) {
            printClear();
            Arguments args = Arguments.builder()
                .atm(atm)
                .customer(customer)
                .build();
            switch (selection) {
              case 1:
                ATMLogic.accountBalanceInformation(args);
                break;
              case 2:
                ATMLogic.moneyWithdrawal(args);
                break;
              case 3:
                ATMLogic.phoneCreditsTopUp(args);
                break;
              case 4:
                ATMLogic.electricityBillsToken(args);
                break;
              case 5:
                ATMLogic.accountMutation(args);
                break;
              case 6:
                if (depositable) {
                  ATMLogic.moneyDeposit(args);
                  break;
                }
              default:
                printInvalid();
                break;
            }
            delay();

            boolean answerLoop = true;
            while (answerLoop) {
              printClear();
              System.out.println("Do another transaction? (Y=1, N=0)");
              System.out.print(" > ");
              Integer answer = nextInt();
              if (answer != null && answer >= 0 && answer <= 1) {
                loop = answer == 1;
                answerLoop = false;
              } else {
                printInvalid();
              }
            }
          } else if (selection == 0) {
            loop = false;
          } else {
            printInvalid();
          }
        }
      }
    } else {
      System.out.println("Cannot find Bank or ATM");
      delay();
    }
  }

}
