package lb.levels.utils;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class FunctionsManager {

    public boolean isInteger(String value) {
        try {
            Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public UUID getUUID(OfflinePlayer player) {
        return player.getUniqueId();
    }
}
