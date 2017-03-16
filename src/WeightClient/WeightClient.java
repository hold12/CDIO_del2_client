package WeightClient;

/**
 * Created by awo on 2017-03-16.
 */

import TCP.TCPClient;
import TCP.ITCPClient;

public class WeightClient {
    private ITCPClient tcp;
    private String host;
    private int port = 8000; // Default port

    public WeightClient(String host, int port) {
        this.tcp = new TCPClient();
        this.host = host;
        this.port = port;
    }
}
