package WeightClient;

/**
 * Created by awo on 2017-03-16.
 */

import TCP.TCPClient;
import TCP.ITCPClient;

import java.io.IOException;

public class WeightClientController implements WeightClient.IWeightClientController {
    private ITCPClient tcp;
    private String host;
    private int port = 8000; // Default port

    public WeightClientController() {
        this.tcp = new TCPClient();
    }

    @Override
    public void connect(String host, int port) throws IOException {
        this.host = host;
        this.port = port;

        try {
            tcp.connect(host, port);
        } catch (IOException e) {
            throw new IOException("Failed to connect to " + host + " on port " + port + ": " + e.getMessage());
        }
    }

    // S (Receive stable weight)
    @Override
    public String getCurrentWeight() throws IOException {
        // S S      0.900 kg
        String receivedMessage;
        try {
            tcp.send("S");
            receivedMessage = tcp.receive();
        } catch (IOException e) {
            throw new IOException("Failed to retrieve the current weight: " + e.getMessage());
        }
        receivedMessage = receivedMessage.replace(" kg", "");
        return receivedMessage.substring(9);
    }

    // T (Tare)
    @Override
    public void tareWeight() throws IOException {
        try {
            tcp.send("T");
            // TODO: Should this not also return something?
        } catch (IOException e) {
            throw new IOException("Failed to tare the weight: " + e.getMessage());
        }
    }

    // D (Write in the primary display of the weight)
    @Override
    public void writeToPrimaryDisplay(String message) throws IOException {
        try {
            tcp.send("D \"" + message + "\"");
            if (!tcp.receive().equals("D A"))
                throw new IOException("Something went wrong when displaying message on the weight.");
        } catch (IOException e) {
            throw new IOException("Failed to send the message: " + e.getMessage());
        }
    }

    // DW (Clear the primary display)
    @Override
    public void clearPrimaryDisplay() throws IOException {
        try {
            tcp.send("DW");
            if (!tcp.receive().equals("DW A"))
                throw new IOException("Something went wrong when clearing main display.");
        } catch (IOException e) {
            throw new IOException("Failed to clear primary display: " + e.getMessage());
        }
    }

    @Override
    public void writeToSecondaryDisplay(String message) throws IOException, StringIndexOutOfBoundsException {
        if (message.length() > 30)
            throw new StringIndexOutOfBoundsException("The message must be within 30 characters long.");
        try {
            tcp.send("P111 \"" + message + "\"");
            if (!tcp.receive().equals("P111 A"))
                throw new IOException("Something went wrong when writing to the secondary display.");
        } catch (IOException e) {
            throw new IOException("Failed to write to the secondary dispaly: " + e.getMessage());
        }
    }

    @Override
    public String rm208(String primaryDisplay, String secondaryDisplay, KeyPadState keyPadState) throws IOException, StringIndexOutOfBoundsException {
        String userMessage;
        if (secondaryDisplay.length() > 30)
            throw new StringIndexOutOfBoundsException("The message to the secondary display must be within 30 characters long.");
        try {
            tcp.send("RM20 8 \"" + secondaryDisplay + "\" \"" + primaryDisplay + "\" \"" + keyPadState + "\"");
            if (!tcp.receive().equals("RM20 B"))
                throw new IOException("Something went wrong when receiving RM20 B message from the weight.");
            userMessage = tcp.receive();
            userMessage = userMessage.replace("RM20 A ", "");
        } catch (IOException e) {
            throw new IOException("Could not execute RM20 8: " + e.getMessage());
        }
        return userMessage;
    }

    @Override
    public void setNewGrossWeight(double newWeight) throws IOException {
        try {
            tcp.send("B " + newWeight);
            // TODO: Server does not send anything back, but should it not do so??
        } catch (IOException e) {
            throw new IOException("Could send set new gross value: " + e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        try {
            tcp.send("Q");
            tcp.close();
        } catch (IOException e) {
            throw new IOException("Could not close the connection: " + e.getMessage());
        }
    }
}
