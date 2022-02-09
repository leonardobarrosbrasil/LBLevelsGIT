package lb.levels.main;

import lb.levels.commands.CommandExperience;
import lb.levels.commands.CommandLevel;
import lb.levels.commands.tab.CommandTest;
import lb.levels.utils.FunctionsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainLevels extends JavaPlugin {

    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    private static MainLevels instance;

    public static MainLevels getPlugin() {
        return instance;
    }

    public FunctionsManager functions;

    public void registerCommands() {
        CommandLevel level = new CommandLevel(this, "level");
        CommandExperience exp = new CommandExperience(this, "exp");
        CommandTest test = new CommandTest(this, "leveltest");
        console.sendMessage("§aLBLevels: Comandos carregados com sucesso.");
    }

    @Override
    public void onEnable() {
        instance = this;
        functions = new FunctionsManager();
        registerCommands();
        console.sendMessage("§aLBLevels: Plugin carregado com sucesso.");
    }

    @Override
    public void onDisable() {
        console.sendMessage("§cLBLevels: Plugin descarregado com sucesso.");
    }

    public FunctionsManager getFunctions() {
        return functions;
    }
}
