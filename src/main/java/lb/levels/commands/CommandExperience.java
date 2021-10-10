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

import java.util.Objects;

public class CommandExperience implements CommandExecutor {

    public CommandExperience(MainLevels main, String command) {
        Objects.requireNonNull(main.getCommand(command)).setExecutor(this);
        Objects.requireNonNull(main.getCommand(command)).setTabCompleter(new CommandTab());
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command arg1, @NotNull String arg2, String[] args) {
        if (args.length == 0) {
            if (!sender.hasPermission("lb.admin.exp")) {
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
                    if (!args[2].matches("^[0-9]*$") || Integer.parseInt(args[2]) > 100000 || Integer.parseInt(args[2]) <= 0) {
                        sender.sendMessage("§cA experiência precisa ser um número válido.");
                        return;
                    }
                    MainEngines.getPlugin().getManager().getCache(target.getUniqueId()).setExp(Integer.parseInt(args[2]));
                    MainEngines.getPlugin().getFunctions().isElegible(target.getUniqueId());
                    sender.sendMessage("§aVocê definiu a experiência de " + target.getName() + " para " + args[2] + ".");
                } else {
                    if (!args[2].matches("^[0-9]*$") || Integer.parseInt(args[2]) > 100000 || Integer.parseInt(args[2]) <= 0) {
                        sender.sendMessage("§cA experiência precisa ser um número válido.");
                        return;
                    }
                    Bukkit.getScheduler().runTaskAsynchronously(MainLevels.getPlugin(), () -> {
                        try {
                            MainEngines.getPlugin().getMysql().setExp(target.getUniqueId(), Integer.parseInt(args[2]));
                            sender.sendMessage("§aVocê definiu a experiência de " + target.getName() + " para " + args[2] + ".");
                        } catch (NullPointerException ex) {
                            sender.sendMessage("§cJogador não encontrado.");
                        }
                    });
                    MainEngines.getPlugin().getFunctions().isElegible(target.getUniqueId());
                }
                break;
            case "adicionar":
                if (MainEngines.getPlugin().getManager().hasCache(target.getUniqueId())) {
                    if (!args[2].matches("^[0-9]*$") || Integer.parseInt(args[2]) > 100000 || Integer.parseInt(args[2]) <= 0) {
                        sender.sendMessage("§cA experiência precisa ser um número válido.");
                        return;
                    }
                    int value = Integer.parseInt(args[2]);
                    if (MainEngines.getPlugin().getManager().getCache(target.getUniqueId()).getExp() + value > 100000) {
                        sender.sendMessage("§cO valor somado a experiência atual do jogador é superior a 100000.");
                        return;
                    }
                    MainEngines.getPlugin().getManager().getCache(target.getUniqueId()).setExp((int) (MainEngines.getPlugin().getManager().getCache(target.getUniqueId()).getExp() + value));
                    MainEngines.getPlugin().getFunctions().isElegible(target.getUniqueId());
                    sender.sendMessage("§aVocê adicionou " + args[2] + " de experiência para " + target.getName() + ".");
                } else {
                    if (!args[2].matches("^[0-9]*$") || Integer.parseInt(args[2]) > 100000 || Integer.parseInt(args[2]) <= 0) {
                        sender.sendMessage("§cA experiência precisa ser um número válido.");
                        return;
                    }
                    int value = Integer.parseInt(args[2]);
                    Bukkit.getScheduler().runTaskAsynchronously(MainLevels.getPlugin(), () -> {
                        try {
                            if (MainEngines.getPlugin().getMysql().getData(target.getUniqueId()).getExp() + value > 100000) {
                                sender.sendMessage("§cO valor somado a experiência atual do jogador é superior a 100000.");
                                return;
                            }
                            MainEngines.getPlugin().getMysql().setExp(target.getUniqueId(), MainEngines.getPlugin().getMysql().getData(target.getUniqueId()).getExp() + value);
                            sender.sendMessage("§aVocê adicionou " + value + " de experiência(s) para " + target.getName() + ".");
                        } catch (NullPointerException ex) {
                            sender.sendMessage("§cJogador não encontrado.");
                        }
                    });
                    MainEngines.getPlugin().getFunctions().isElegible(target.getUniqueId());
                }
                break;
            case "remover":
                if (MainEngines.getPlugin().getManager().hasCache(target.getUniqueId())) {
                    if (!args[2].matches("^[0-9]*$") || Integer.parseInt(args[2]) > 100000 || Integer.parseInt(args[2]) <= 0) {
                        sender.sendMessage("§cA experiência precisa ser um número válido.");
                        return;
                    }
                    int value = Integer.parseInt(args[2]);
                    if (MainEngines.getPlugin().getManager().getCache(target.getUniqueId()).getExp() < value) {
                        sender.sendMessage("§cO valor é superior a experiência atual do jogador.");
                        return;
                    }
                    MainEngines.getPlugin().getManager().getCache(target.getUniqueId()).setExp(MainEngines.getPlugin().getManager().getCache(target.getUniqueId()).getExp() - value);
                    MainEngines.getPlugin().getFunctions().isElegible(target.getUniqueId());
                    sender.sendMessage("§aVocê removeu " + value + " de experiência de " + target.getName() + ".");
                } else {
                    if (!args[2].matches("^[0-9]*$") || Integer.parseInt(args[2]) > 100000 || Integer.parseInt(args[2]) <= 0) {
                        sender.sendMessage("§cA experiência precisa ser um número válido.");
                        return;
                    }
                    int value = Integer.parseInt(args[2]);
                    Bukkit.getScheduler().runTaskAsynchronously(MainLevels.getPlugin(), () -> {
                        try {
                            if (MainEngines.getPlugin().getMysql().getData(target.getUniqueId()).getExp() < value) {
                                sender.sendMessage("§cO valor é superior a experiência atual do jogador.");
                                return;
                            }
                            MainEngines.getPlugin().getMysql().setExp(target.getUniqueId(), MainEngines.getPlugin().getMysql().getData(target.getUniqueId()).getExp() - value);
                            sender.sendMessage("§aVocê removeu " + value + " de experiência de " + target.getName() + ".");
                        } catch (NullPointerException ex) {
                            sender.sendMessage("§cJogador não encontrado.");
                        }
                    });
                    MainEngines.getPlugin().getFunctions().isElegible(target.getUniqueId());
                }
                break;
            default:
                sender.sendMessage("§cArgumentos inválidos.");
                break;
        }
    }
}