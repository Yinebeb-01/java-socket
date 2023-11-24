
import java.io.*; // to import input output streams.
import java.net.*; // to import java's API networking packages.

// Server class
public class ServerSide {
    public static void main(String[] args) throws IOException {
        // server listening on port 5056
        try (ServerSocket SerSocket = new ServerSocket(5056)) {
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");

            while (true) {
                Socket socket = null;
                try {
                    // socket object receiving client requests
                    socket = SerSocket.accept();

                    System.out.println("A new client is connected : " + socket);

                    // declaring input and out streams objects
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                    System.out.println("Assigning new thread for this client");

                    // create a new thread object
                    Thread t = new ClientThread(socket, input, output);
                    t.start();

                } catch (Exception e) {
                    socket.close();
                    e.printStackTrace();
                }
            }
        }
    }
}
