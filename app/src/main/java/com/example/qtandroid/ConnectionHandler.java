package com.example.qtandroid;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ConnectionHandler {
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ConnectionHandler() throws UnknownHostException {
        InetAddress add = InetAddress.getByName("127.0.0.1");
        try {
            Socket s = new Socket(add, 8080);
            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public String learnFromFile(String tableName, double radius) throws IOException, ClassNotFoundException, ServerException {
        out.writeObject(3);
        out.writeObject(tableName);

        out.writeObject(radius);
        String result = (String) in.readObject();
        if (result.equals("OK"))
            return (String) in.readObject();
        else throw new ServerException(result);
    }

    public void storeTableFromDb(String tableName) throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(0);
        out.writeObject(tableName);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    public String learningFromDbTable(Double radius) throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(1);

        out.writeObject(radius);
        String result = (String) in.readObject();
        if (result.equals("OK")) {
            result = "Number of Clusters:" + in.readObject();
            result += (String) in.readObject();
            return result;
        } else throw new ServerException(result);
    }

    public void storeClusterInFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(2);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    //not supported
    public String[] getTableNames() {
        String[] tables = null;
        return tables;
    }


}
