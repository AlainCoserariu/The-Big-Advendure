package fr.uge.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Lexer {
  private static final List<Token> TOKENS = List.of(Token.values());
  private static final Pattern PATTERN = Pattern
      .compile(TOKENS.stream().map(token -> "(" + token.regex + ")").collect(Collectors.joining("|")));

  private final String text;
  private final Matcher matcher;

  public Lexer(String text) {
    this.text = Objects.requireNonNull(text);
    this.matcher = PATTERN.matcher(text);
  }

  public static List<Result> getAllTokensFromFile(Path mapFile) throws IOException {
    var text = Files.readString(mapFile);
    var lexer = new Lexer(text);
    var tokens = new ArrayList<Result>();
    Result tmpToken;
    while ((tmpToken = lexer.nextResult()) != null) {
      tokens.add(tmpToken);
    }
    
    return List.copyOf(tokens);
  }
  
  public Result nextResult() {
    var matches = matcher.find();
    if (!matches) {
      return null;
    }
    for (var group = 1; group <= matcher.groupCount(); group++) {
      var start = matcher.start(group);
      if (start != -1) {
        var end = matcher.end(group);
        var content = text.substring(start, end);
        return new Result(TOKENS.get(group - 1), content);
      }
    }
    throw new AssertionError();
  }
}