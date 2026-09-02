package com.lexeme.aggregate;

public enum ClassType {
  CLASS("class"),
  STRUCT("struct");

  private final String symbol;

  ClassType(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }
}
