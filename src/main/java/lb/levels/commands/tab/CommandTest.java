package lb.levels.commands.tab;

import lb.engines.main.MainEngines;
import lb.engines.utils.LBManager;
import lb.engines.utils.LBPlayer;
import lb.levels.main.MainLevels;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandTest implements CommandExecutor {

    public CommandTest(MainLevels main, String command) {
        Objects.requireNonNull(main.getCommand(command)).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (args.length == 0) {
            Player player = (Player) sender;
            MainEngines plugin = MainEngines.getPlugin();
            LBManager manager = plugin.getManager();
            LBPlayer cache = manager.getCache(player.getUniqueId());
            sender.sendMessage("§c" + cache.getLevel());
            return true;
        }
        sender.sendMessage("§cArgumentos inválidos.");
        return true;
    }
}