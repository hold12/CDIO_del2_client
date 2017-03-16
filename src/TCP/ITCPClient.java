package TCP;

import java.io.IOException;

/**
 * Created by awo on 16/03/17.
 */
public interface ITCPClient {
    void connect(String host, int port) throws IOException;
    void send(String line) throws IOException;
    String receive() throws IOException;
    void close() throws IOException;
}
