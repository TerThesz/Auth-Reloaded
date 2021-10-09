package auth.reloaded.commands;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandHandler {
  private Map<Class<? extends ExecutableCommand>, ExecutableCommand> commands = new HashMap<Class<? extends ExecutableCommand>, ExecutableCommand>();
  private static List<String> availableCommands = Arrays.asList(new String[] {"prd"});

  public static List<String> getCommands() {
    return availableCommands;
  }

  public CommandHandler() {
    CommandInitializer.initializeCommands();

    mapCommands(CommandInitializer.getCommands());
  }

  public boolean handleCommand(CommandSender sender, Command cmd, String label, String[] args) {
    Boolean executed = false;
    
    for (CommandObject command : CommandInitializer.getCommands()) {
      if (command.getName().equalsIgnoreCase(cmd.getName())) {
        commands.get(command.getCommand()).executeCommand(sender, args);

        // TODO: Check minArgs, hasToBeOnline, permissions
        
        executed = true;
      }
    }

    return executed;
  }

  private void mapCommands(List<CommandObject> commandObjects) {
    List<Class<? extends ExecutableCommand>> classes = new ArrayList<Class<? extends ExecutableCommand>>();
    for (CommandObject command : commandObjects)
      classes.add(command.getCommand());

    for (Class<? extends ExecutableCommand> clazz : classes)
      commands.put(clazz, newInstance(clazz));
  }

  public static <T> T newInstance(Class<T> clazz) {
    try {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    } catch (ReflectiveOperationException e) {
        throw new IllegalStateException("Could not invoke no-args constructor of class " + clazz, e);
    }
  }
}
