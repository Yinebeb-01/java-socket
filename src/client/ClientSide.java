import java.io.*;
import java.net.*;
import java.util.Scanner;

// ClientSide class
public class ClientSide {
    public static void main(String[] args) throws IOException {
        try {
            Scanner scn = new Scanner(System.in);
            // establish a connection with the server at port 5056
            Socket socket = new Socket("127.0.0.1", 5056);

            // obtaining input and output streams
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            while (true) {
                System.out.println(
                        "What do you want to do? (Type your choice,please!):\n\tadd for Addition\n\tmulti"+ 
                        " for multiplication\n\tpow for power\n\texit to terminate connection.");
                String tosend = scn.nextLine();
                output.writeUTF(tosend);

                if (tosend.equals("exit")) {
                    System.out.println("Closing this connection : " + socket);
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                System.out.println("Enter num1:");
                String num1 = scn.nextLine();
                System.out.println("Enter num2:");
                String num2 = scn.nextLine();

                output.writeUTF(num1);
                output.writeUTF(num2);

                // reading message from the server via the socket
                String received = input.readUTF(); 
                int res = input.readInt();
                System.out.println(received + res);
            }

            // close resources
            scn.close();
            input.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
