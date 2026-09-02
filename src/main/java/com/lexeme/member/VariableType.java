package com.lexeme.member;

public enum VariableType {
  POINTER("*"),
  REFERENCE("&"),
  NONE("");

  private final String symbol;

  VariableType(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }
}
