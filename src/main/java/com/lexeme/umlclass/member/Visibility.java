package com.lexeme.umlclass.member;

public enum Visibility {
  PUBLIC("+"),
  PROTECTED("#"),
  PRIVATE("-");

  private final String symbol;

  Visibility(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }
}
