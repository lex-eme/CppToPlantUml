package com.lexeme.member;

import java.util.ArrayList;
import java.util.List;

public class ClassDescriptor {
  public String name;
  public ClassType classType = ClassType.CLASS;
  public List<Variable> variables = new ArrayList<>();
  public List<Function> functions = new ArrayList<>();

  public void addClassMember(ClassMember member) {
    if (member instanceof Variable) {
      variables.add((Variable) member);
    } else if (member instanceof Function) {
      functions.add((Function) member);
    }
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(classType);
    stringBuilder.append(" ");
    stringBuilder.append(name);

    if (classType == ClassType.STRUCT) {
      stringBuilder.append(" <<struct>>");
    }

    stringBuilder.append(" {\n");

    for (var variable : variables) {
      stringBuilder.append(variable);
      stringBuilder.append("\n");
    }

    stringBuilder.append("\n");

    for (var function : functions) {
      stringBuilder.append(function);
      stringBuilder.append("\n");
    }

    stringBuilder.append("}\n");

    return stringBuilder.toString();
  }
}
