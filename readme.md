# Java socket programming

## Description
This mini-project showcases the basics of socket programming in Java, inspired by the concepts covered in the **Distributed Systems** course.

In this project, I've implemented Java socket programming enabling multiple clients to request services from a single server, utilizing threading for efficient handling.

### Client Side
The ClientSide class manages client requests. Within this section of the code, the exchange of information occurs between the client and the server's thread handler. For instance, the client inputs a command, such as 'add' to request the addition of two numbers, and the server responds by sending back the sum.

### Server Side
The server-side implementation involves two classes: ServerSide, responsible for creating the server, and ClientThread, which handles individual client requests through multithreading.

Based on the clients' requested operations, the server processes addition, multiplication, or power operations using the two numbers provided by the client.



    