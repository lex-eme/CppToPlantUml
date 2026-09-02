package com.lexeme.aggregate.member;

public abstract class ClassMember {
  public Visibility visibility = Visibility.PRIVATE;
  public String name = "";
  public String type = "";
  public VariableType variableType = VariableType.NONE;
}
