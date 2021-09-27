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
            if (!sender.hasPermission("lb.admin.fragments")) {
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
        int value;
        switch (args[0]) {
            case "definir":
                if (!MainLevels.getPlugin().getFunctions().isInteger(args[2])) {
                    sender.sendMessage("§cA experiência precisa ser um número.");
                    return;
                }
                value = Integer.parseInt(args[2]);
                if (value > 100000) {
                    sender.sendMessage("§cA experiência precisa ser inferior a 100001.");
                    return;
                }
                if (value < 1) {
                    sender.sendMessage("§cA experiência precisa ser superior a 0.");
                    return;
                }
                Bukkit.getScheduler().runTaskAsynchronously(MainLevels.getPlugin(), () -> {
                    try {
                        MainEngines.getPlugin().getMysql().setExp(target.getUniqueId(), value);
                        sender.sendMessage("§aVocê definiu a experiência de " + target.getName() + " para " + value + ".");
                    } catch (NullPointerException ex) {
                        sender.sendMessage("§cJogador não encontrado.");
                    }
                });
                break;
            case "adicionar":
                if (!MainLevels.getPlugin().getFunctions().isInteger(args[2])) {
                    sender.sendMessage("§cA experiência precisa ser um número.");
                    return;
                }
                value = Integer.parseInt(args[2]);
                if (value > 100000) {
                    sender.sendMessage("§cA experiência precisa ser inferior a 100001.");
                    return;
                }
                if (value < 1) {
                    sender.sendMessage("§cA experiência precisa ser superior a 0.");
                    return;
                }
                Bukkit.getScheduler().runTaskAsynchronously(MainLevels.getPlugin(), () -> {
                    try {
                        if (MainEngines.getPlugin().getMysql().getDatas(target.getUniqueId()).getExp() + value > 100000) {
                            sender.sendMessage("§cA experiência atual do jogador somado ao valor é superior a 100000.");
                            return;
                        }
                        MainEngines.getPlugin().getMysql().setExp(target.getUniqueId(), MainEngines.getPlugin().getMysql().getDatas(target.getUniqueId()).getLevel() + value);
                        sender.sendMessage("§aVocê adicionou " + value + " de experiência para " + target.getName() + ".");
                    } catch (NullPointerException ex) {
                        sender.sendMessage("§cJogador não encontrado.");
                    }
                });
                break;
            case "remover":
                if (!MainLevels.getPlugin().getFunctions().isInteger(args[2])) {
                    sender.sendMessage("§cA experiência precisa ser um número.");
                    return;
                }
                value = Integer.parseInt(args[2]);
                if (value < 1) {
                    sender.sendMessage("§cA experiência precisa ser superior a 0.");
                    return;
                }
                Bukkit.getScheduler().runTaskAsynchronously(MainLevels.getPlugin(), () -> {
                    try {
                        if (MainEngines.getPlugin().getMysql().getDatas(target.getUniqueId()).getExp() < value) {
                            sender.sendMessage("§cA experiência atual do jogador é inferior a " + value + ".");
                            return;
                        }
                        MainEngines.getPlugin().getMysql().setExp(target.getUniqueId(), MainEngines.getPlugin().getMysql().getDatas(target.getUniqueId()).getExp() - value);
                        sender.sendMessage("§aVocê removeu " + value + " de experiência de " + target.getName() + ".");
                    } catch (NullPointerException ex) {
                        sender.sendMessage("§cJogador não encontrado.");
                    }
                });
                break;
            default:
                sender.sendMessage("§cArgumentos inválidos.");
                break;
        }
    }
}