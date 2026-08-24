package org.example;

import com.eno.parser.CPP14Lexer;
import com.eno.parser.CPP14Parser;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CodePointBuffer;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Main {
  static void main() {
    var filePath =
        Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "AHexGrid.h");
    String fileContent;
    try {
      fileContent = Files.readString(filePath, StandardCharsets.UTF_8);
    } catch (IOException e) {
      return;
    }

    CodePointBuffer buffer = CodePointBuffer.withBytes(ByteBuffer.wrap(fileContent.getBytes()));
    CharStream stream = CodePointCharStream.fromBuffer(buffer);
    var lexer = new CPP14Lexer(stream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    var parser = new CPP14Parser(tokens);
    ParseTree tree = parser.translationUnit();
    TestListener listener = new TestListener();
    ParseTreeWalker walker = new ParseTreeWalker();
    walker.walk(listener, tree);
  }
}
