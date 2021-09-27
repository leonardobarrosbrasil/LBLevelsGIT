package lb.levels.utils;

public class FunctionsManager {

    public boolean isInteger(String value) {
        try {
            Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
