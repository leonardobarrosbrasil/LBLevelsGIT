package lb.levels.commands;

import com.sun.istack.internal.NotNull;
import lb.engines.main.MainEngines;
import lb.levels.commands.tab.CommandTab;
import lb.levels.main.MainLevels;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import sun.applet.Main;

import java.util.Objects;

public class CommandLevel implements CommandExecutor {

    public CommandLevel(MainLevels main, String command) {
        Objects.requireNonNull(main.getCommand(command)).setExecutor(this);
        Objects.requireNonNull(main.getCommand(command)).setTabCompleter(new CommandTab());
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command arg1, @NotNull String arg2, String[] args) {
        if (args.length == 0) {
            if (!sender.hasPermission("lb.admin.level")) {
                sender.sendMessage("§cVocê não tem permissão para fazer isto.");
                return true;
            }
            sender.sendMessage("§cVocê pode usar /comandos para aprender a usar o comando.");
            return true;
        }
        if (args.length == 3) {
            args3(sender, args);
            return true;
        }
        sender.sendMessage("§cArgumentos inválidos.");
        return true;
    }

    private void args3(CommandSender sender, String[] args) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        switch (args[0]) {
            case "definir":
                if (MainEngines.getPlugin().getManager().hasCache(target.getUniqueId())) {
                    if (!args[2].matches("^[0-9]*$") || Integer.parseInt(args[2]) > 1000 || Integer.parseInt(args[2]) <= 0) {
                        sender.sendMessage("§cO nível precisa ser um número válido.");
                        return;
                    }
                    int value = Integer.parseInt(args[2]);
                    MainEngines.getPlugin().getManager().getCache(target.getUniqueId()).setLevel(value);
                    sender.sendMessage("§aVocê definiu o nível de " + target.getName() + " para " + value + ".");
                } else {
                    if (!args[2].matches("^[0-9]*$") || Integer.parseInt(args[2]) > 1000 || Integer.parseInt(args[2]) <= 0) {
                        sender.sendMessage("§cO nível precisa ser um número válido.");
                        return;
                    }
                    int value = Integer.parseInt(args[2]);
                    Bukkit.getScheduler().runTaskAsynchronously(MainLevels.getPlugin(), () -> {
                        try {
                            MainEngines.getPlugin().getMySQL().setLevel(target.getUniqueId(), value);
                            sender.sendMessage("§aVocê definiu o nível de " + target.getName() + " para " + value + ".");
                        } catch (NullPointerException ex) {
                            sender.sendMessage("§cJogador não encontrado.");
                        }
                    });
                }
                break;
            case "adicionar":
                if (MainEngines.getPlugin().getManager().hasCache(target.getUniqueId())) {
                    if (!args[2].matches("^[0-9]*$") || Integer.parseInt(args[2]) > 1000 || Integer.parseInt(args[2]) <= 0) {
                        sender.sendMessage("§cO nível precisa ser um número válido.");
                        return;
                    }
                    int value = Integer.parseInt(args[2]);
                    if (MainEngines.getPlugin().getManager().getCache(target.getUniqueId()).getLevel() + value > 1000) {
                        sender.sendMessage("§cO valor somado ao nível atual do jogador é superior a 1000.");
                        return;
                    }
                    MainEngines.getPlugin().getManager().getCache(target.getUniqueId()).setLevel(MainEngines.getPlugin().getManager().getCache(target.getUniqueId()).getLevel() + value);
                    sender.sendMessage("§aVocê adicionou " + value + " níveis para " + target.getName() + ".");
                } else {
                    if (!args[2].matches("^[0-9]*$") || Integer.parseInt(args[2]) > 1000 || Integer.parseInt(args[2]) <= 0) {
                        sender.sendMessage("§cO nível precisa ser um número válido.");
                        return;
                    }
                    int value = Integer.parseInt(args[2]);
                    Bukkit.getScheduler().runTaskAsynchronously(MainLevels.getPlugin(), () -> {
                        try {
                            if (MainEngines.getPlugin().getMySQL().getData(target.getUniqueId()).getLevel() + value > 1000) {
                                sender.sendMessage("§cO valor somado ao nível atual do jogador é superior a 1000.");
                                return;
                            }
                            MainEngines.getPlugin().getMySQL().setLevel(target.getUniqueId(), MainEngines.getPlugin().getMySQL().getData(target.getUniqueId()).getLevel() + value);
                            sender.sendMessage("§aVocê adicionou " + value + " níveis para " + target.getName() + ".");
                        } catch (NullPointerException ex) {
                            sender.sendMessage("§cJogador não encontrado.");
                        }
                    });
                }
                break;
            case "remover":
                if (MainEngines.getPlugin().getManager().hasCache(target.getUniqueId())) {
                    if (!args[2].matches("^[0-9]*$") || Integer.parseInt(args[2]) > 1000 || Integer.parseInt(args[2]) <= 0) {
                        sender.sendMessage("§cO nível precisa ser um número válido.");
                        return;
                    }
                    int value = Integer.parseInt(args[2]);
                    if (MainEngines.getPlugin().getManager().getCache(target.getUniqueId()).getLevel() < value) {
                        sender.sendMessage("§cO valor é superior ao nível atual do jogador.");
                        return;
                    }
                    MainEngines.getPlugin().getManager().getCache(target.getUniqueId()).setLevel(MainEngines.getPlugin().getManager().getCache(target.getUniqueId()).getLevel() - value);
                    sender.sendMessage("§aVocê removeu " + value + " níveis de " + target.getName() + ".");
                } else {
                    if (!args[2].matches("^[0-9]*$") || Integer.parseInt(args[2]) > 1000 || Integer.parseInt(args[2]) <= 0) {
                        sender.sendMessage("§cO nível precisa ser um número válido.");
                        return;
                    }
                    int value = Integer.parseInt(args[2]);
                    Bukkit.getScheduler().runTaskAsynchronously(MainLevels.getPlugin(), () -> {
                        try {
                            if (MainEngines.getPlugin().getMySQL().getData(target.getUniqueId()).getLevel() < value) {
                                sender.sendMessage("§cO valor é superior ao nível atual do jogador.");
                                return;
                            }
                            MainEngines.getPlugin().getMySQL().setLevel(target.getUniqueId(), MainEngines.getPlugin().getMySQL().getData(target.getUniqueId()).getLevel() - value);
                            sender.sendMessage("§aVocê removeu " + value + " níveis de " + target.getName() + ".");
                        } catch (NullPointerException ex) {
                            sender.sendMessage("§cJogador não encontrado.");
                        }
                    });
                }
                break;
            default:
                sender.sendMessage("§cArgumentos inválidos.");
                break;
        }
    }
}