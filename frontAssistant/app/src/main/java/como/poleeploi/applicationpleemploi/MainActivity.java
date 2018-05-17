package como.poleeploi.applicationpleemploi;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import android.util.Log;

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
import java.util.ArrayList;

import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;
public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private List lstItems = new LinkedList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        // connexion au serveur et recupérer les données
        runThread();
    }

    private void runThread() {
       mTextMessage.setText(" \n Vous n'êtes pas connecté aux serveur   \n");
        new Thread() {
            public void run() {
                Socket socket = null;
                InetAddress group = null;

                try {

                      //  mTextMessage.append("  connection au serveur \n");

                        socket = new Socket("192.168.0.5", 8181);
                      mTextMessage.setText("  Vous êtes connecté \n");

                        BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        while (true) {
                       //   String dada = "{\"level\":2,\"id\":\"123\",\"type\":\"Time\"}";
                            String data =    bf.readLine().toString();
                            // affichege des donnees
                            dispalyData(data);
                        //mTextMessage.append(data);

                    }
                } catch (IOException e) {
                    System.out.println(e.toString());
                    mTextMessage.append("  error  " + e.toString());
                } finally {
                    if (socket == null) {
                        //runThread();
                    }
                }
            }
        }.start();
    }

    private void dispalyData( String data) {

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

            ListView lv = (ListView) findViewById(R.id.list);

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1, lstItems);
            lstItems.add("La poste numéro " + strId + " à besoin d'aide ");

            lv.setAdapter(adapter);

        } catch (JSONException jex) {
            mTextMessage.append(jex.toString());
            jex.printStackTrace();
        }
    }
}
