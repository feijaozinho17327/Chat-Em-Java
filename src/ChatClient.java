import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient implements Runnable{
    private final String SERVER_ADDRESS = "127.0.0.1";
    private ClientSocket clientSocket;

    private Scanner scan;


    public ChatClient() {
        scan = new Scanner(System.in);




    }
    public void start() throws IOException {


        try {
            clientSocket = new ClientSocket(new Socket(SERVER_ADDRESS, 5000));
            new Thread(this).start();
            messageLoop();
        } finally {
            clientSocket.close();
        }

    }
    @Override
    public void run() {
        String msg;

        while ((msg = clientSocket.getMessage()) != null) {
            System.out.println("Mensagem recebida: " + msg);
        }
    }
    public void messageLoop() throws IOException {
        String msg;

        do {
            System.out.print("digite uma mensagem: ");
            msg = scan.nextLine();
            clientSocket.sendMsg(msg);


        }while (!msg.equals("--sair"));


    }



    public static void main(String[] args) {


        try {
            ChatClient chatClient = new ChatClient();
            chatClient.start();
        } catch (IOException e) {
            System.out.println("Erro ao iniciar cliente " + e.getMessage());
        }

    }


}
