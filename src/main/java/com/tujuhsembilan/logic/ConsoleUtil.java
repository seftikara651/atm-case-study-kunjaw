package com.tujuhsembilan.logic;

import java.util.Collection;
import java.util.Scanner;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ConsoleUtil {

  public static final Scanner in = new Scanner(System.in);

  public static void printClear() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void printDivider(Character character) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 35; i++) {
      sb.append(character);
    }
    System.out.println(sb.toString());
  }

  public static void printDivider() {
    printDivider('=');
  }

  public static String nextLine() {
    return in.nextLine();
  }

  public static Integer nextInt() {
    try {
      return Integer.parseInt(nextLine());
    } catch (NumberFormatException e) {
      System.out.println("Only number input are allowed");
      return -1;
    }
  }

  public static Long nextLong() {
    try {
      return Long.parseLong(nextLine());
    } catch (NumberFormatException e) {
      System.out.println("Only number input are allowed");
      return -1L;
    }
  }

  public static void delay() {
    System.out.print("\nPress enter to continue...");
    nextLine();
  }

  public static void printInvalid() {
    System.out.println("Invalid input");
    delay();
  }

  public static Integer menuSelection(Collection<String> menus, boolean allowExit) {
    printClear();
    printDivider();
    int num = 1;
    for (String menu : menus) {
      System.out.println(" " + num + ". " + menu);
      num++;
    }
    if (allowExit) {
      printDivider('-');
      System.out.println(" 0. EXIT");
    }
    printDivider();

    System.out.print(" > ");
    return nextInt();
  }

  public static Integer menuSelection(Collection<String> menus) {
    return menuSelection(menus, true);
  }

}
