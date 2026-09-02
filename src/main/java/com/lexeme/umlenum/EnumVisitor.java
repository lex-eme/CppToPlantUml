package com.lexeme.umlenum;

import com.lexeme.parser.CPP14Parser;
import com.lexeme.parser.CPP14ParserBaseVisitor;

public class EnumVisitor extends CPP14ParserBaseVisitor<Void> {
    private final EnumDescriptor enumDescriptor = new EnumDescriptor();

    public EnumDescriptor getEnumDescriptor() {
        return enumDescriptor;
    }

    @Override
    public Void visitEnumHead(CPP14Parser.EnumHeadContext ctx) {
        if (ctx.Identifier() != null) {
            enumDescriptor.name = ctx.Identifier().getText();
        } else {
            enumDescriptor.name = "Unknown";
        }
        return null;
    }

    @Override
    public Void visitEnumerator(CPP14Parser.EnumeratorContext ctx) {
        enumDescriptor.enumerators.add(ctx.Identifier().getText());
        return null;
    }
}
