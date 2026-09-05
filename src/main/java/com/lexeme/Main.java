package com.lexeme;

import com.lexeme.parser.CPP14Lexer;
import com.lexeme.parser.CPP14Parser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
  static void main(String[] args) {
    if (args.length == 1) {
      System.out.println("Source path: " + args[0]);
      Path sourcePath = Paths.get(args[0]);
      try (Stream<Path> stream = Files.walk(sourcePath)) {
        stream.filter(file -> file.toString().endsWith(".h")).forEach(Main::processFile);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      System.out.println("Usage: java CppToPlantUml-1.0-SNAPSHOT.jar pathToYourSourceFolder");
      var filePath =
          Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "AHexGridTest.h");
      processFile(filePath);
    }
  }

  private static void processFile(Path path) {
    System.out.println("=== File under process: " + path + " ===\n");
    String fileContent;
    try {
      fileContent = Files.readString(path, StandardCharsets.UTF_8);
    } catch (IOException e) {
      return;
    }

    if (fileContent.startsWith("\uFEFF")) {
      fileContent = fileContent.substring(1);
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
