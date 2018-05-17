package como.poleeploi.applicationpleemploi;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private TextView reslutText;

    private List<String> lstItems = new LinkedList<String>();

private   ArrayAdapter<String> adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       mTextMessage = (TextView) findViewById(R.id.message);
       reslutText = (TextView) findViewById(R.id.result);
        // connexion au serveur et recupérer les données
      runThread();
      // test
        //String data = "{\"level\":2,\"id\":\"123\",\"type\":\"Time\"}\n";
       //dispalyData(data);
    }

    private void runThread() {
      mTextMessage.setText(" \n Vous n'êtes pas connecté   !!!\n");
        new Thread() {
            public void run() {
                String hostname = "192.168.43.230";
                int intPort = 8181;
                Socket socket = null;
                try {
                      //  connexion
                    socket = new Socket(hostname, intPort);
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try{

                    PrintWriter  out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    mTextMessage.setText("  Vous êtes connecté \n");
                    String data;
                        while (true  ) {
                       //   String dada = "{\"level\":2,\"id\":\"123\",\"type\":\"Time\"}";
                            data =  bf.readLine();
                            // affichage des donnees
                            dispalyData(data);
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

            String labelType ="";

            switch (strType)
            {
                case "Time-Elapsed":
                    labelType = "L'utilisateur de la poste numéro " + strId + " a mis trop de temps pour remplir le formulaire";
                    break;
                case "Amount-Click":
                    labelType = "L'utilisateur de la poste numéro " + strId + " a un problème avcec les clicks de la souris";
                    break;
                case "Amount-Error":
                    labelType = "L'utilisateur de la poste numéro " + strId + " a fait beaucoup d'erreur";
                    break;
                case "Mouse-Speed":
                    labelType = "L'utilisateur de la poste numéro " + strId + " fait des mouvements trop rapide";
                    break;
                    default:
                        labelType = "L'utilisateur de la poste numéro " + strId + " à besoin d'aide";
                        break;
            }

            ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.container);
            ConstraintSet set = new ConstraintSet();
            set.clone(layout);
            final String text = labelType;
            layout.post(new Runnable() {

                @Override
                public void run() {
                    reslutText.append(text +" \n " + " \n");
                }

            });

        } catch (JSONException jex) {
            jex.printStackTrace();}
    }
}
