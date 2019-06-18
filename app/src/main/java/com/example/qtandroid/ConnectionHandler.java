package com.example.qtandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class ConnectionHandler extends AsyncTask<String, Void, String> {
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Context context;
    private ProgressDialog progress;

    public static final String ERROR = "error";
    public static final String DONE = "done";

    public static final String STORE_TABLE = "0";
    public static final String LEARN_DB = "1";
    public static final String SAVE_FILE = "2";
    public static final String LEARN_FILE = "3";
    public static final String GET_TABLES = "4";

    private String result = "";
    private LinkedList<String> tables = new LinkedList<String>();

    public ConnectionHandler(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            InetAddress add = InetAddress.getByName("192.168.0.7");
            //System.out.println("addr = " + add);
            try {
                Socket s = new Socket();
                s.connect(new InetSocketAddress(add, 8080), 2000);
                //System.out.println("socket = " + s);
                out = new ObjectOutputStream(s.getOutputStream());
                in = new ObjectInputStream(s.getInputStream());

                switch (strings[0]) {
                    case STORE_TABLE:
                        storeTableFromDb(strings[1]);
                        break;
                    case LEARN_DB:
                        result = learningFromDbTable(Double.parseDouble(strings[1]));
                        break;
                    case SAVE_FILE:
                        storeClusterInFile();
                        break;
                    case LEARN_FILE:
                        result = learnFromFile(strings[1], Double.parseDouble(strings[2]));
                        break;
                    case GET_TABLES:
                        tables = getTableNames();
                        break;
                    default:
                }
                s.close();

            } catch (SocketTimeoutException e) {
                return ERROR;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return DONE;
    }

    @Override
    protected void onPreExecute() {

        progress = new ProgressDialog(context);
        progress.setTitle("Stabilendo la connessione...");
        progress.setMessage("attendi...");
        progress.show();

    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public String getResult() {
        return result;
    }

    public LinkedList<String> getTables() {
        return tables;
    }

    private String learnFromFile(String tableName, double radius) throws IOException, ClassNotFoundException, ServerException {
        out.writeObject(3);
        out.writeObject(tableName);
        out.writeObject(radius);
        String result = (String) in.readObject();
        if (result.equals("OK")) {
            return (String) in.readObject();
        }

        else throw new ServerException(result);
    }

    private void storeTableFromDb(String tableName) throws ServerException, IOException, ClassNotFoundException {
        out.writeObject(0);
        out.writeObject(tableName);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    private String learningFromDbTable(Double radius) throws ServerException, IOException, ClassNotFoundException {
        out.writeObject(1);

        out.writeObject(radius);
        String result = (String) in.readObject();
        if (result.equals("OK")) {
            result = "Number of Clusters:" + in.readObject() + "\n";
            result += (String) in.readObject();
            return result;
        } else throw new ServerException(result);
    }

    private void storeClusterInFile() throws ServerException, IOException, ClassNotFoundException {
        out.writeObject(2);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    //not supported
    private LinkedList<String> getTableNames() throws IOException, ClassNotFoundException {
        out.writeObject(4);
        LinkedList<String> tables = (LinkedList<String>) in.readObject();
        return tables;
    }

    @Override //probabilmente non serve
    protected void onPostExecute (String a) {
        progress.dismiss();
        System.out.println(result);
    }

}
