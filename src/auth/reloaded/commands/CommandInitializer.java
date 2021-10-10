package auth.reloaded.commands;

import java.util.List;

import com.google.common.collect.ImmutableList;

import auth.reloaded.commands.auth.Login;
import auth.reloaded.commands.auth.Register;
// import auth.reloaded.commands.auth.RegisterOther;

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
  
    /* CommandObject register_other = CommandObject.builder()
      .name("register-other")
      .minArgs(3)
      .command(RegisterOther.class)
      .register(); */

    CommandObject login = CommandObject.builder()
      .name("login")
      .minArgs(1)
      .command(Login.class)
      .register();

    commands = ImmutableList.of(register,/* register_other,*/ login);
  }
}
