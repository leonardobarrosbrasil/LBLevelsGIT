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

    public void registerEvents() {
        //getServer().getPluginManager().registerEvents(new OnSendCommand(), this);
        console.sendMessage("§a" + pluginName + ": Eventos carregados com sucesso.");
    }

    public FunctionsManager functions;

    public void registerCommands() {
        CommandLevel level = new CommandLevel(this, "nivel");
        CommandExperience exp = new CommandExperience(this, "experienciax");
        console.sendMessage("§a" + pluginName + ": Comandos carregados com sucesso.");
    }

    @Override
    public void onEnable() {
        instance = this;
        functions = new FunctionsManager();
        pluginName = getPlugin().getDescription().getName();
        registerEvents();
        registerCommands();
        console.sendMessage("§a" + pluginName + ": Plugin habilitado com sucesso.");
    }

    @Override
    public void onDisable() {
        console.sendMessage("§c" + pluginName + ": Plugin desabilitado com sucesso.");
    }

    public FunctionsManager getFunctions() {
        return functions;
    }
}
