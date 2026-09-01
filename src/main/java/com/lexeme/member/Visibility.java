package com.lexeme.member;

public enum Visibility {
  PUBLIC("+"),
  PROTECTED("#"),
  PRIVATE("-");

  public final String symbol;

  Visibility(String symbol) {
    this.symbol = symbol;
  }
}
