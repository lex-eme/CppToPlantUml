package com.lexeme;

import com.lexeme.umlclass.ClassDescriptor;
import com.lexeme.umlclass.ClassVisitor;
import com.lexeme.parser.CPP14Parser;
import com.lexeme.parser.CPP14ParserBaseVisitor;
import com.lexeme.umlenum.EnumDescriptor;
import com.lexeme.umlenum.EnumVisitor;

public class FileVisitor extends CPP14ParserBaseVisitor<Void> {
  @Override
  public Void visitClassSpecifier(CPP14Parser.ClassSpecifierContext ctx) {
    ClassVisitor classVisitor = new ClassVisitor();
    classVisitor.visit(ctx);
    ClassDescriptor classDescriptor = classVisitor.getClassDescriptor();
    System.out.println(classDescriptor);
    visitChildren(ctx);
    return null;
  }

  @Override
  public Void visitEnumSpecifier(CPP14Parser.EnumSpecifierContext ctx) {
    EnumVisitor enumVisitor = new EnumVisitor();
    enumVisitor.visit(ctx);
    EnumDescriptor enumDescriptor = enumVisitor.getEnumDescriptor();
    System.out.println(enumDescriptor);
    visitChildren(ctx);
    return null;
  }
}
