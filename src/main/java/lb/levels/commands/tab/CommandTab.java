package lb.levels.commands.tab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class CommandTab implements TabCompleter {

    private final List<String> args1 = Arrays.asList("set", "give", "remove");
    private final ArrayList<String> players = new ArrayList<>();

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command arg1, @NotNull String arg2, String[] args) {
        final List<String> completions = new ArrayList<>();
        if (!(sender instanceof Player)) {
            return completions;
        }
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], args1, completions);
        }
        if (args.length == 2) {
            for (String string : args1) {
                if (args[0].equalsIgnoreCase(string)) {
                    players.clear();
                    Bukkit.getOnlinePlayers().forEach(p -> {
                        players.add(p.getName());
                    });
                    StringUtil.copyPartialMatches(args[1], players, completions);
                }
            }
        }
        Collections.sort(completions);
        return completions;
    }
}
