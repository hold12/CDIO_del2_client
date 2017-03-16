package WeightClient;

/**
 * Created by awo on 2017-03-16.
 */

import TCP.TCPClient;
import TCP.ITCPClient;

import java.io.IOException;

public class WeightClient {
    private ITCPClient tcp;
    private String host;
    private int port = 8000; // Default port

    public WeightClient() {
        this.tcp = new TCPClient();
    }

    public void connect(String host, int port) throws Exception {
        this.host = host;
        this.port = port;

        try {
            tcp.connect(host, port);
        } catch (IOException e) {
            throw new Exception("Failed to connect to " + host + " on port " + port + ": " + e.getMessage());
        }
    }

    // S command
    public double getCurrentWeight() throws Exception {
        // S S      0.900 kg
        String receivedMessage = "";
        try {
            tcp.send("S");
            receivedMessage = tcp.receive();
        } catch (IOException e) {
            throw new Exception("Failed to retrieve the current weight: " + e.getMessage());
        }
        receivedMessage = receivedMessage.replace(" kg", "");
        return Double.parseDouble(receivedMessage.substring(9));
    }

    // D command
    public void writeToPrimaryDisplay(String message) throws Exception {
        try {
            tcp.send("D \"" + message + "\"");
            if (!tcp.receive().equals("D A"))
                throw new Exception("Something went wrong when displaying message on the weight.");
        } catch (IOException e) {
            throw new Exception("Failed to send the message: " + e.getMessage());
        }
    }
}
