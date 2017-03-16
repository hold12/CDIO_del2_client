package Lang;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Anders Wiberg Olsen on 2016-09-12.
 */

public class Lang {
    private static String lang;
    private static String country;
    @SuppressWarnings("FieldCanBeLocal")
    private static Locale locale;

    private static ResourceBundle resourceBundle;

    public static String msg(String message) { return resourceBundle.getString(message); }

    public static void setLanguage(String[] args) {
        if (args.length != 2) {
            lang = "da";
            country = "DK";
        } else if (args.length == 2){
            lang = args[0];
            country = args[1];
        } else {
            System.out.println("Usage: java -jar game.jar <language> <country>");
        }

        try
        {
            locale = new Locale(lang, country);
            resourceBundle = ResourceBundle.getBundle("lang", locale);
        }
        catch (Exception ex)
        {
            System.out.println("You either specified some wrong arguments or you specified a language that doesn't exist in this game.");
            System.out.println("Usage: java -jar game.jar <language> <country> (ex. da DK)");
        }

    }
}