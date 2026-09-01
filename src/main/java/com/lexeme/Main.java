package com.lexeme;

import com.lexeme.parser.CPP14Lexer;
import com.lexeme.parser.CPP14Parser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
  static void main() {
    var filePath =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "AHexGridTest.h");
    // Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "AHexGrid.h");
    String fileContent;
    try {
      fileContent = Files.readString(filePath, StandardCharsets.UTF_8);
    } catch (IOException e) {
      return;
    }

    CharStream stream = CharStreams.fromString(fileContent);
    var lexer = new CPP14Lexer(stream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    var parser = new CPP14Parser(tokens);
    ParseTree tree = parser.translationUnit();

    FileVisitor visitor = new FileVisitor();
    visitor.visit(tree);
  }
}
