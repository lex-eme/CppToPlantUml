package com.lexeme;

import com.lexeme.member.ClassDescriptor;
import com.lexeme.member.ClassMember;
import com.lexeme.member.Visibility;
import com.lexeme.parser.CPP14Parser;
import com.lexeme.parser.CPP14ParserBaseVisitor;

public class FileVisitor extends CPP14ParserBaseVisitor<Void> {
  private Visibility currentVisibility = Visibility.PRIVATE;
  private final ClassDescriptor currentClass = new ClassDescriptor();

  @Override
  public Void visitTranslationUnit(CPP14Parser.TranslationUnitContext ctx) {
    visitChildren(ctx);
    System.out.println(currentClass);
    return null;
  }

  @Override
  public Void visitClassHead(CPP14Parser.ClassHeadContext ctx) {
    visitChildren(ctx);
    currentVisibility = Visibility.PRIVATE;

    if (ctx.classHeadName() != null) {
      currentClass.name = ctx.classHeadName().getText();
    }

    return null;
  }

  @Override
  public Void visitAccessSpecifier(CPP14Parser.AccessSpecifierContext ctx) {
    if (ctx.start.getType() == CPP14Parser.Private) {
      currentVisibility = Visibility.PRIVATE;
    } else if (ctx.start.getType() == CPP14Parser.Public) {
      currentVisibility = Visibility.PUBLIC;
    } else if (ctx.start.getType() == CPP14Parser.Protected) {
      currentVisibility = Visibility.PROTECTED;
    }
    return super.visitAccessSpecifier(ctx);
  }

  @Override
  public Void visitMemberDeclaration(CPP14Parser.MemberDeclarationContext ctx) {
    MemberDeclarationVisitor visitor = new MemberDeclarationVisitor();
    ClassMember member = visitor.visit(ctx);
    if (member != null) {
      member.visibility = currentVisibility;
      currentClass.addClassMember(member);
    }
    return null;
  }
}
