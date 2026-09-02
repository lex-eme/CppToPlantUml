package com.lexeme.member;

public class Variable extends ClassMember {
  @Override
  public String toString() {
    return visibility + " " + name + ": " + type + variableType;
  }
}
