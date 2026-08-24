package org.example;

import com.eno.parser.CPP14Parser;
import com.eno.parser.CPP14ParserBaseListener;

public class TestListener extends CPP14ParserBaseListener {
  private String typeName = "";
  private String identifier = "";
  private AccessSpecifier accessSpecifier = AccessSpecifier.PRIVATE;
  private boolean isPointer = false;

  enum AccessSpecifier {
    PRIVATE,
    PUBLIC,
    PROTECTED
  }

  @Override
  public void enterAccessSpecifier(CPP14Parser.AccessSpecifierContext ctx) {
    if (ctx.start.getType() == CPP14Parser.Private) {
      accessSpecifier = AccessSpecifier.PRIVATE;
    } else if (ctx.start.getType() == CPP14Parser.Public) {
      accessSpecifier = AccessSpecifier.PUBLIC;
    } else if (ctx.start.getType() == CPP14Parser.Protected) {
      accessSpecifier = AccessSpecifier.PROTECTED;
    }
  }

  @Override
  public void enterMemberDeclaration(CPP14Parser.MemberDeclarationContext ctx) {
    isPointer = false;
  }

  @Override
  public void exitMemberDeclaration(CPP14Parser.MemberDeclarationContext ctx) {
    switch (accessSpecifier) {
      case PRIVATE -> System.out.println("- " + identifier + ": " + (isPointer ? "*" : "") + typeName);
      case PUBLIC -> System.out.println("+ " + identifier + ": " + (isPointer ? "*" : "") + typeName);
      case PROTECTED -> System.out.println("# " + identifier + ": " + (isPointer ? "*" : "") + typeName);
    }
  }

    @Override
  public void enterDeclarator(CPP14Parser.DeclaratorContext ctx) {
    identifier = ctx.getText();
    if (identifier.charAt(0) == '*') {
      identifier = identifier.replace("*", "");
    }
  }

  @Override
  public void enterDeclSpecifier(CPP14Parser.DeclSpecifierContext ctx) {
    typeName = ctx.getText();
  }

  @Override
  public void enterPointerOperator(CPP14Parser.PointerOperatorContext ctx) {
    isPointer = true;
  }
}
