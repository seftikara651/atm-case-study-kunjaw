package com.tujuhsembilan.logic;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class NumberUtil {

  public static String spellNumber(Long val) {
    final String[] nom = { "Nol", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan",
        "Sepuluh", "Sebelas" };

    if (val < 0) {
      return "Negatif " + spellNumber(Math.abs(val));
    } else if (val >= 0 && val < 12) {
      return nom[val.intValue()];
    } else if (val <= 19) {
      return nom[val.intValue() % 10] + " Belas";
    } else if (val <= 99) {
      return nom[val.intValue() / 10] + " Puluh" + ((val % 10 > 0) ? (" " + nom[val.intValue() % 10]) : "");
    } else if (val <= 199) {
      return "Seratus" + ((val % 100 > 0) ? (" " + spellNumber(val % 100)) : "");
    } else if (val <= 999) {
      return nom[val.intValue() / 100] + " Ratus" + ((val % 100 > 0) ? (" " + spellNumber(val % 100)) : "");
    } else if (val <= 1_999) {
      return "Seribu" + ((val % 1_000 > 0) ? (" " + spellNumber(val % 1_000)) : "");
    } else if (val <= 999_999) {
      return spellNumber(val / 1_000) + " Ribu"
          + ((val % 1_000 > 0) ? (" " + spellNumber(val % 1_000)) : "");
    } else if (val <= 999_999_999) {
      return spellNumber(val / 1_000_000) + " Juta"
          + ((val % 1_000_000 > 0) ? (" " + spellNumber(val % 1_000_000)) : "");
    } else if (val <= 999_999_999_999L) {
      return spellNumber(val / 1_000_000_000L) + " Miliar"
          + ((val % 1_000_000_000L > 0) ? (" " + spellNumber(val % 1_000_000_000L)) : "");
    } else if (val <= 999_999_999_999_999L) {
      return spellNumber(val / 1_000_000_000_000L) + " Triliun"
          + ((val % 1_000_000_000_000L > 0) ? (" " + spellNumber(val % 1_000_000_000_000L)) : "");
    } else if (val <= 999_999_999_999_999_999L) {
      return spellNumber(val / 1_000_000_000_000_000L) + " Quadriliun"
          + ((val % 1_000_000_000_000_000L > 0) ? (" " + spellNumber(val % 1_000_000_000_000_000L))
              : "");
    } else {
      return "";
    }
  }

  public static String formatIdr(BigDecimal number) {
    return NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID")).format(number);
  }

}
