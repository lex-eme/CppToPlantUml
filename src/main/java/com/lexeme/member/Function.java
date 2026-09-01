package com.lexeme.member;

import java.util.ArrayList;
import java.util.List;

public class Function extends ClassMember {
  public List<ClassMember> parameters = new ArrayList<>();

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(visibility.symbol);
    stringBuilder.append(" ");
    stringBuilder.append(name);
    stringBuilder.append("(");

    for (int i = 0; i < parameters.size(); i++) {
      ClassMember parameter = parameters.get(i);
      stringBuilder.append(parameter.name);
      stringBuilder.append(": ");
      stringBuilder.append(parameter.type);
      stringBuilder.append(parameter.variableType.symbol);

      if (i + 1 < parameters.size()) {
        stringBuilder.append(", ");
      }
    }

    stringBuilder.append(")");
    stringBuilder.append(type.isEmpty() ? "" : ": ");
    stringBuilder.append(type);
    return stringBuilder.toString();
  }
}
