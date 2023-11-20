import java.io.*;
import java.net.ServerSocket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ChatServer {

    private final int PORT = 5000;
    private ServerSocket serverSocket;
    private final List<ClientSocket> clients = new LinkedList<>();

    public void start () throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Server: conectado");
        clientConnectionLoop();

    }

    private void clientConnectionLoop() throws IOException {
        while (true) {
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept());
            clients.add(clientSocket);

            new Thread(() -> clientMessageLoop(clientSocket)).start();



        }

    }
    private void clientMessageLoop(ClientSocket clientSocket) {
        String msg;
        try {
            while ((msg = clientSocket.getMessage()) != null) {
                if("--sair".equalsIgnoreCase(msg)) {
                    return;
                }
                System.out.println(clientSocket.getRemoteSocketAddress() +": "+ msg);
                sendMsgToAll(clientSocket, msg);
            }
        } finally {
            clientSocket.close();
        }

    }

    private void sendMsgToAll(ClientSocket sender, String msg){
        Iterator<ClientSocket> iterator = clients.iterator();

        while (iterator.hasNext()){
            ClientSocket clientSocket = iterator.next();
            if(!sender.equals(clientSocket)) {
                if(!clientSocket.sendMsg(msg)){
                    iterator.remove();

                }
            }
        }
    }

    public static void main(String[] args) {

        try {
            ChatServer chatServer = new ChatServer();
            chatServer.start();
        } catch (IOException e) {
            System.out.println("erro ao iniciar o servidor"+ e.getMessage());
        }
    }

}
