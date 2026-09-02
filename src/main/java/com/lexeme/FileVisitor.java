package com.lexeme;

import com.lexeme.member.ClassDescriptor;
import com.lexeme.member.ClassMember;
import com.lexeme.member.ClassType;
import com.lexeme.member.Visibility;
import com.lexeme.parser.CPP14Parser;
import com.lexeme.parser.CPP14ParserBaseVisitor;
import java.util.ArrayList;
import java.util.List;

public class FileVisitor extends CPP14ParserBaseVisitor<Void> {
  private Visibility currentVisibility = Visibility.PRIVATE;
  private final List<ClassDescriptor> classDescriptorList = new ArrayList<>();
  private int classIndex = 0;

  @Override
  public Void visitTranslationUnit(CPP14Parser.TranslationUnitContext ctx) {
    classIndex = -1;
    visitChildren(ctx);
    for (ClassDescriptor classDescriptor : classDescriptorList) {
      System.out.println(classDescriptor);
    }
    return null;
  }

  @Override
  public Void visitClassSpecifier(CPP14Parser.ClassSpecifierContext ctx) {
    classDescriptorList.add(new ClassDescriptor());
    classIndex += 1;
    visitChildren(ctx);
    return null;
  }

  @Override
  public Void visitClassHead(CPP14Parser.ClassHeadContext ctx) {
    if (ctx.classHeadName() != null) {
      classDescriptorList.get(classIndex).name = ctx.classHeadName().getText();
    }

    visitChildren(ctx);
    currentVisibility = Visibility.PRIVATE;

    return null;
  }

  @Override
  public Void visitClassKey(CPP14Parser.ClassKeyContext ctx) {
    if (ctx.Class() != null) {
      classDescriptorList.get(classIndex).classType = ClassType.CLASS;
    } else if (ctx.Struct() != null) {
      classDescriptorList.get(classIndex).classType = ClassType.STRUCT;
    }
    return null;
  }

  @Override
  public Void visitElaboratedTypeSpecifier(CPP14Parser.ElaboratedTypeSpecifierContext ctx) {
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
      classDescriptorList.get(classIndex).addClassMember(member);
    }
    return null;
  }
}
