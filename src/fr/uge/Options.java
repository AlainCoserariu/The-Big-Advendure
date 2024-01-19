package fr.uge;

import java.util.List;

public class Options {
  private final String map;
  private final boolean dryRun;
  private final boolean validate;
  
  private String getMap(List<String> args) {
    if (args.contains("--level") && args.indexOf("--level") + 1 < args.size()) {
      return args.get(args.indexOf("--level") + 1);
    } else {
      return "adventure.map";
    }
  }
  
  private boolean getDryRun(List<String> args) {
    return args.contains("--dry-run");
  }
  
  private boolean getValidate(List<String> args) {
    return args.contains("--validate");
  }
  
  public Options(String args[]) {
    var arguments = List.of(args);
    
    map = getMap(arguments);
    dryRun = getDryRun(arguments);
    validate = getValidate(arguments);
  }
  
  public String map() {
    return map;
  }
  
  public boolean dryRun() {
    return dryRun;
  }
  
  public boolean validate() {
    return validate;
  }
  
}
