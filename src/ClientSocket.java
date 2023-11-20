import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientSocket {

    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    public ClientSocket(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println("SERVER: " + socket.getRemoteSocketAddress() + " acaba de se conectar");
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


    }

    public String getMessage(){

        try {
            return in.readLine();
        } catch (IOException e) {
            return null;
        }

    }

    public SocketAddress getRemoteSocketAddress() {

        return socket.getRemoteSocketAddress();

    }

    public void close() {

        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Server: Erro ao fechar socket: " + e.getMessage());
        }


    }

    public boolean sendMsg(String msg){

        try {
            out.write(msg);
            out.newLine();
            out.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

}
