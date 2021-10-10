package auth.reloaded.commands;

import java.util.List;

import com.google.common.collect.ImmutableList;

import auth.reloaded.commands.auth.Register;

public class CommandInitializer {
  private static List<CommandObject> commands;

  public static List<CommandObject> getCommands() {
    return commands;
  }
  
  public static void initializeCommands() {
    CommandObject register = CommandObject.builder()
      .name("register")
      .minArgs(2)
      .command(Register.class)
      .register();

    commands = ImmutableList.of(register);
  }
}
