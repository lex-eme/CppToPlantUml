package com.lexeme.member;

public class Variable extends ClassMember {
  @Override
  public String toString() {
    return visibility.symbol + " " + name + ": " + type + variableType.symbol;
  }
}
