package TCP;

import java.net.*;
import java.io.*;

/**
 * Created by Anders Wiberg Olsen on 2017-06-03.
 */

public class TCPClient implements ITCPClient{
    private Socket socket;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    public TCPClient() {
    }

    @Override
    public synchronized void connect(String host, int port) throws IOException {
        if (socket != null)
            throw new IOException("Already connected. Disconnect first.");

        socket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void send(String line) throws IOException {
        if (socket == null)
            throw new IOException("Not connected");
        try {
            writer.write(line + "\r\n");
            writer.flush();
        } catch (IOException e) {
            socket = null;
            throw e;
        }
    }

    @Override
    public String receive() throws IOException {
        return reader.readLine();
    }

    @Override
    public void close() throws IOException {
        socket.close();
        socket = null;
    }
}