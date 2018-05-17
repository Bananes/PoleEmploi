package como.poleeploi.applicationpleemploi;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.io.PrintWriter;
import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.*;
import java.io.*;
public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private List<String> lstItems = new LinkedList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       mTextMessage = (TextView) findViewById(R.id.message);
        // connexion au serveur et recupérer les données
      runThread();
        //String data = "{\"level\":2,\"id\":\"123\",\"type\":\"Time\"}\n";
       //dispalyData(data);
    }

    private void runThread() {
      // mTextMessage.setText(" \n Vous n'êtes pas connecté aux serveur   \n");
        new Thread() {
            public void run() {
                String hostname = "192.168.43.230";
                int intPort = 8181;
                Socket socket = null;
                try {
                      //  mTextMessage.append("  connection au serveur \n");
                    socket = new Socket(hostname, intPort);
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try{

                    PrintWriter  out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //mTextMessage.setText("  Vous êtes connecté \n");
                    String data;
                        while (!bf.ready()  ) {
                       //   String dada = "{\"level\":2,\"id\":\"123\",\"type\":\"Time\"}";

                            data =  bf.readLine();
                            // affichege des donnees
                            dispalyData(data);
                        //mTextMessage.append(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally
                {
                    //Closing the socket
                    try
                    {
                        socket.close();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    private void dispalyData ( String data) {

        try {
            JSONObject jsnobject = new JSONObject(data);
            String strLveel = jsnobject.getString("level");
            String strId = jsnobject.getString("id");
            String strType = jsnobject.getString("type");

            // Test
            /*
            mTextMessage.append("  strLveel   " + strLveel + " \n");
            mTextMessage.append("  strId   " + strId + " \n");
            mTextMessage.append("  strType   " + strType + " \n");
            */

            ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.container);
            ConstraintSet set = new ConstraintSet();
            set.clone(layout);

            //ListView lv = (ListView) findViewById(R.id.list);
            //lstItems.add("La poste numéro " + strId + " à besoin d'aide ");
            //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
            //        android.R.layout.simple_list_item_1, lstItems);


            final String text = "La poste numéro " + strId + " à besoin d'aide ";

            layout.post(new Runnable() {
                @Override
                public void run() {
                    ListView DynamicListView = new ListView(getApplicationContext());
                    lstItems.add(text);
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.listTemp);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>
                            (MainActivity.this, android.R.layout.simple_list_item_1, lstItems);

                    DynamicListView.setAdapter(adapter);
                    linearLayout.addView(DynamicListView);
                }
            });

        } catch (JSONException jex) {
            jex.printStackTrace();
        }
    }
}
