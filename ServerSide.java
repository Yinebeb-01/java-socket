/*  Author : Yinebeb Tariku
 Socket Programming by using Java: 
    *In this assignment, I implement a socket programming in java which support more than one client to request
    and served by a single server by using threading.

    *Here in the server side implementation(code), there are two classes the one(ServerSide) for creating the server and ClientThread 
    for handling any client using multithreading. 

    *Based on clients request /choice, this server return an addition 
    or multiplication or power result of two entered number from the client.
    
    file name : ServerSide.java
*/

import java.io.*; // to import input output streams.
import java.net.*; // to import java's API networking packages.

// Server class
public class ServerSide {
    public static void main(String[] args) throws IOException {
        // server listening on port 5056
        try (ServerSocket SerSocket = new ServerSocket(5056)) {
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");

            // while loop inorder to get client's request repeatedlly.
            while (true) {
                Socket socket = null; // Intializing Socket named socket
                try {
                    // socket object to receive incoming client requests
                    socket = SerSocket.accept();

                    System.out.println("A new client is connected : " + socket);

                    // declaring input and out streams objects named input and output.
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                    System.out.println("Assigning new thread for this client");

                    // create a new thread object, by default Thread is imported implicitly.
                    Thread t = new ClientThread(socket, input, output);

                    // start the thread by invoking the start() method
                    t.start();

                } catch (Exception e) {
                    socket.close();
                    e.printStackTrace();
                }
            }
        }
    }
}

// ClientThread class
class ClientThread extends Thread {
    final DataInputStream input;
    final DataOutputStream output;
    final Socket socket;

    // Constructor
    public ClientThread(Socket socket, DataInputStream input, DataOutputStream output) {
        this.socket = socket;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        String choice;
        String message;
        int result;
        while (true) {
            try {
                // receive client's choice via the socket.
                choice = input.readUTF();

                if (choice.equals("exit")) {
                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    System.out.println("Connection closed,waiting new coming connection.");
                    break;
                }

                // reaceiving the two operand num1 and num2 into a and b. Since I am reading via
                // readUTF, it will return the value as strings.
                String a = input.readUTF();
                String b = input.readUTF();

                // do the math based on client's choice and write to the stream, socket to send
                // back to the client.
                switch (choice) {

                    case "add":
                        message = "The sum is ";
                        result = Integer.parseInt(a) + Integer.parseInt(b);
                        output.writeUTF(message); // sending the message to the socket, which will be fetched by the
                                                  // client
                        output.writeInt(result); // sending the message to the socket, which will be fetched by the
                                                 // client
                        break;

                    case "multi":
                        message = "The product is ";
                        result = Integer.parseInt(a) * Integer.parseInt(b);
                        output.writeUTF(message);
                        output.writeInt(result);
                        break;

                    case "pow":
                        message = "The power is ";
                        result = (int) Math.pow(Integer.parseInt(a), Integer.parseInt(b));
                        output.writeUTF(message);
                        output.writeInt(result);
                        break;

                    default:
                        output.writeUTF("Invalid input");
                        output.writeInt(0);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // closing resources
            this.input.close();
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
