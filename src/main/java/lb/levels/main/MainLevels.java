package lb.levels.main;

import lb.levels.commands.CommandExperience;
import lb.levels.commands.CommandLevel;
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

    private String pluginName;

    public FunctionsManager functions;

    public void registerCommands() {
        CommandLevel level = new CommandLevel(this, "nivel");
        CommandExperience exp = new CommandExperience(this, "exp");
        console.sendMessage("§a" + pluginName + ": Comandos carregados com sucesso.");
    }

    @Override
    public void onEnable() {
        instance = this;
        functions = new FunctionsManager();
        pluginName = getPlugin().getDescription().getName();
        registerCommands();
        console.sendMessage("§a" + pluginName + ": Plugin carregado com sucesso.");
    }

    @Override
    public void onDisable() {
        console.sendMessage("§c" + pluginName + ": Plugin descarregado com sucesso.");
    }

    public FunctionsManager getFunctions() {
        return functions;
    }
}
