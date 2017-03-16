import WeightClient.WeightClient;

/**
 * Created by awo on 16/03/17.
 */
public class Main {
    public static void main(String[] args) {
        WeightClient wc = new WeightClient();
        try {
            wc.connect("localhost", 8000);
            System.out.println("Weight is currently " + wc.getCurrentWeight() + "!");
            wc.writeToPrimaryDisplay("Idiot!");
        } catch (Exception e) {
            System.err.println("FEJL!!! : " + e.getMessage());
        }
    }
}
