package com.kamiamia.pcnavdroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class MainActivity extends ActionBarActivity {

    String serverName = "192.168.2.8";
    int port = 3006;
    String coordinates;


    Button leftClick;
    Button rightClick;
    LinearLayout touchPad;
    TextView cords;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leftClick = (Button) findViewById(R.id.leftClick);
        rightClick = (Button) findViewById(R.id.rightClick);
        cords = (TextView) findViewById(R.id.cords);
        touchPad = (LinearLayout) findViewById(R.id.touchPad);

        //Touch listener for Linear Layout touchPad. Gets fired every time touchPad is touched
        touchPad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                //Get x and y coordinate position of LinearLayout when touched
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();

                cords.setText("Cor-ordinates: " + x + "-" + y);

                coordinates = Integer.toString(x) + ':' + Integer.toString(y);

                //Creates an object of SendCords and executes it
                SendCords sendCords = new SendCords(serverName, port);
                sendCords.execute();

                return true;
            }
        });

        //On click leftClick send L:xx as the coordinates
        leftClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cords.setText("L");
                coordinates = "L:xx";

                SendCords sendCords = new SendCords(serverName, port);
                sendCords.execute();

            }
        });

        //On click rightClick send R:xx as the coordinates
        rightClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cords.setText("R");
                coordinates = "R:xx";

                SendCords sendCords = new SendCords(serverName, port);
                sendCords.execute();
            }
        });

    }


    //SendCords class opens a socket connection to server and sends the coordinates to the server in an AsyncTask
    public class SendCords extends AsyncTask<Void,Void,Void>{
        String serverName;
        int port;

        SendCords(String addr, int prt){
            serverName = addr;
            port = prt;
        }

        @Override
        protected Void doInBackground(Void... arg0){

            try{

                //Open socket on serverName and port
                Socket client = new Socket(serverName,port);

                //Get output stream of socket client
                OutputStream outToServer = client.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);

                //Write coordinates to server stream
                out.writeUTF(coordinates);
                client.close();
            }
            catch(IOException e)

            {
                e.printStackTrace();
            }



            return null;

        }
    }

}
