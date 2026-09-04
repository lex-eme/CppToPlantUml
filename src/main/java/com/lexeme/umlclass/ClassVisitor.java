package com.lexeme.umlclass;

import com.lexeme.parser.CPP14Parser;
import com.lexeme.parser.CPP14ParserBaseVisitor;
import com.lexeme.umlclass.member.ClassMember;
import com.lexeme.umlclass.member.MemberDeclarationVisitor;
import com.lexeme.umlclass.member.Visibility;

public class ClassVisitor extends CPP14ParserBaseVisitor<Void> {
  private Visibility currentVisibility;
  private final ClassDescriptor classDescriptor = new ClassDescriptor();

  public ClassDescriptor getClassDescriptor() {
    return classDescriptor;
  }

  @Override
  public Void visitClassHead(CPP14Parser.ClassHeadContext ctx) {
    if (ctx.classKey() != null) {
      if (ctx.classKey().start.getType() == CPP14Parser.Class) {
        classDescriptor.classType = ClassType.CLASS;
        currentVisibility = Visibility.PRIVATE;
      } else if (ctx.classKey().start.getType() == CPP14Parser.Struct) {
        classDescriptor.classType = ClassType.STRUCT;
        currentVisibility = Visibility.PUBLIC;
      }
    }

    if (ctx.classHeadName() != null) {
      classDescriptor.name = ctx.classHeadName().getText();
    } else if (ctx.Identifier() != null) {
      classDescriptor.name = ctx.Identifier().getText();
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
    return null;
  }

  @Override
  public Void visitMemberDeclaration(CPP14Parser.MemberDeclarationContext ctx) {
    MemberDeclarationVisitor visitor = new MemberDeclarationVisitor(currentVisibility);
    ClassMember member = visitor.visit(ctx);
    if (member != null) {
      classDescriptor.addClassMember(member);
    }
    return null;
  }
}
