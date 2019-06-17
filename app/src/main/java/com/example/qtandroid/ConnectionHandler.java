package com.example.qtandroid;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ConnectionHandler extends AsyncTask<String, Void, Void> {
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public static final String LEARN_FILE = "3";
    public static final String LEARN_DB = "1";
    public static final String STORE_TABLE = "0";
    public static final String SAVE_FILE = "2";
    public static final String GET_TABLES = "4";

    private String result = "MAMT";

    @Override
    protected Void doInBackground(String... strings) {
        try {
            InetAddress add = InetAddress.getByName("192.168.1.8");
            System.out.println("addr = " + add);
            try {
                System.out.println("PRIMA");
                Socket s = new Socket(add, 8080);
                System.out.println("CIAOMAMMA");
                System.out.println(s);
                out = new ObjectOutputStream(s.getOutputStream());
                in = new ObjectInputStream(s.getInputStream());

                switch (strings[0]) {
                    case STORE_TABLE:
                        storeTableFromDb(strings[1]);
                        break;
                    case LEARN_FILE:
                        result = learnFromFile(strings[1], Double.parseDouble(strings[2]));
                        break;
                    case LEARN_DB:
                        result = learningFromDbTable(Double.parseDouble(strings[1]));
                        break;
                    case SAVE_FILE:
                        storeClusterInFile();
                        break;
                    case GET_TABLES:
                        getTableNames();
                        break;
                    default:
                        System.out.println("MACHECAZZONESO");
                }


                s.close();

            } catch (IOException e) {
                System.out.println("IOEXCEPTION");
                System.out.println(e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception");
                System.out.println("messaggio " + e.getMessage());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getResult() {
        return result;
    }

    private String learnFromFile(String tableName, double radius) throws IOException, ClassNotFoundException, ServerException {
        out.writeObject(3);
        out.writeObject(tableName);

        out.writeObject(radius);
        String result = (String) in.readObject();
        if (result.equals("OK"))
            return (String) in.readObject();
        else throw new ServerException(result);
    }

    private void storeTableFromDb(String tableName) throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(0);
        out.writeObject(tableName);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    private String learningFromDbTable(Double radius) throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(1);

        out.writeObject(radius);
        String result = (String) in.readObject();
        if (result.equals("OK")) {
            result = "Number of Clusters:" + in.readObject() + "\n";
            result += (String) in.readObject();
            return result;
        } else throw new ServerException(result);
    }

    private void storeClusterInFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(2);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    //not supported
    private String[] getTableNames() {
        String[] tables = null;
        return tables;
    }


}
