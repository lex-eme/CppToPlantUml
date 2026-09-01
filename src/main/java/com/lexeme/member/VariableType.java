package com.lexeme.member;

public enum VariableType {
  POINTER("*"),
  REFERENCE("&"),
  NONE("");

  public final String symbol;

  VariableType(String symbol) {
    this.symbol = symbol;
  }
}
