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

                // reaceiving the two operand num1 and num2 into a and b
                String a = input.readUTF();
                String b = input.readUTF();

                switch (choice) {
                    case "add":
                        message = "The sum is ";
                        result = Integer.parseInt(a) + Integer.parseInt(b);
                        output.writeUTF(message); // sending the message to the socket
                        output.writeInt(result); 
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
            this.input.close();
            this.output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
