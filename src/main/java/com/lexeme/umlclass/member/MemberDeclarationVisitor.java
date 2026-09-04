package com.lexeme.umlclass.member;

import com.lexeme.parser.CPP14Parser;
import com.lexeme.parser.CPP14ParserBaseVisitor;

public class MemberDeclarationVisitor extends CPP14ParserBaseVisitor<ClassMember> {
  private Function currentFunction;
  private final Visibility visibility;

  public MemberDeclarationVisitor(Visibility visibility) {
    this.visibility = visibility;
  }

  @Override
  public ClassMember visitMemberDeclaration(CPP14Parser.MemberDeclarationContext ctx) {
    ClassMember member = null;

    if (ctx.memberDeclaratorList() != null) {
      member = visit(ctx.memberDeclaratorList());
    }

    if (member != null && ctx.declSpecifierSeq() != null) {
      for (var declSpec : ctx.declSpecifierSeq().declSpecifier()) {
        if (declSpec.typeSpecifier() != null) {
          member.type = declSpec.typeSpecifier().getText();
        }
      }
    }

    return member;
  }

  @Override
  public ClassMember visitPointerDeclarator(CPP14Parser.PointerDeclaratorContext ctx) {
    ClassMember member = visit(ctx.noPointerDeclarator());

    if (ctx.pointerOperator().isEmpty()) {
      member.variableType = VariableType.NONE;
    } else {
      if (ctx.pointerOperator(0).start.getType() == CPP14Parser.And) {
        member.variableType = VariableType.REFERENCE;
      } else if (ctx.pointerOperator(0).start.getType() == CPP14Parser.Star) {
        member.variableType = VariableType.POINTER;
      }
    }
    return member;
  }

  @Override
  public ClassMember visitNoPointerDeclarator(CPP14Parser.NoPointerDeclaratorContext ctx) {
    if (ctx.declaratorId() != null) {
      Variable variable = new Variable();
      variable.name = ctx.getText();
      variable.visibility = visibility;
      return variable;
    }

    if (ctx.parametersAndQualifiers() != null) {
      currentFunction = new Function();
      currentFunction.visibility = visibility;
      currentFunction.name = ctx.noPointerDeclarator().getText();
      visit(ctx.parametersAndQualifiers());
      return currentFunction;
    }

    if (ctx.noPointerDeclarator() != null) {
      return visit(ctx.noPointerDeclarator());
    }

    return null;
  }

  @Override
  public ClassMember visitMemberDeclarator(CPP14Parser.MemberDeclaratorContext ctx) {
    if (ctx.declarator() != null) {
      return visit(ctx.declarator());
    }

    return null;
  }

  @Override
  public ClassMember visitParameterDeclarationList(
      CPP14Parser.ParameterDeclarationListContext ctx) {
    for (var paramDecl : ctx.parameterDeclaration()) {
      ClassMember parameter = visit(paramDecl.declarator());
      if (parameter != null && paramDecl.declSpecifierSeq() != null) {
        for (var declSpec : paramDecl.declSpecifierSeq().declSpecifier()) {
          if (declSpec.typeSpecifier() != null) {
            parameter.type = declSpec.typeSpecifier().getText();
          }
        }
      }
      currentFunction.parameters.add(parameter);
    }

    return null;
  }
}
