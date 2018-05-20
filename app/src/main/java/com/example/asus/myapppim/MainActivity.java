package com.example.asus.myapppim;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asus.myapppim.Models.Chaine;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button up,down,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,backButton,homeButton;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.1.13:8088");
        } catch (URISyntaxException e) {}
    }
    Context ctx;

    String HttpUrl = "http://192.168.1.13:8080/api/history";
    okhttp3.Response response;

    private String[] channels = {"bein","canal","mtv","el hiwar","tunisie1",
            "hannibal","mbc2","lbc","france 2","tf1","france24","attessia"};
    private List<Chaine> chaines;
    private int i=0;
    private int j;
    String chaine;
    private static String id_rec="1";
    Date currentTime;
    Chaine chaineReceived;
    Date date_end_channel;
    //Socket socket;
    EditText idtext;
    public Context getCtx() {
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        chaines=new ArrayList<Chaine>();


        mSocket.connect();
        mSocket.on("output", onNewMessage);

        idtext = findViewById(R.id.idtext);
        Chaine ch = new Chaine("NBA NEWS");
        Chaine ch1=new Chaine("SUNDANCE TV");
        Chaine ch2=new Chaine("DISNEY CINEMA");
        Chaine ch3=new Chaine("FRANCE 24");
        Chaine ch4=new Chaine("MCM");
        Chaine ch5=new Chaine("Watania 1");
        Chaine ch6=new Chaine("MTV");
        Chaine ch7=new Chaine("TMC");
        Chaine ch8=new Chaine("BET");
        Chaine ch9=new Chaine("EXTREME SPORTS CHANNEL");
        chaines.add(ch);chaines.add(ch1);chaines.add(ch2);chaines.add(ch3);chaines.add(ch4);chaines.add(ch5);chaines.add(ch6);
        chaines.add(ch7);chaines.add(ch8);chaines.add(ch9);

        up=(Button)findViewById(R.id.up);
        down=(Button)findViewById(R.id.down);
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);
        btn4=(Button)findViewById(R.id.btn4);
        btn5=(Button)findViewById(R.id.btn5);
        btn6= findViewById(R.id.btn6);
        btn7=(Button)findViewById(R.id.btn7);
        btn8=(Button)findViewById(R.id.btn8);
        btn9=(Button)findViewById(R.id.btn9);
        backButton=(Button)findViewById(R.id.back);
        homeButton=(Button)findViewById(R.id.home);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        backButton.setOnClickListener(this);
        homeButton.setOnClickListener(this);

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chaines.get(i).getNom_chaine().equals("bein")){
                    j=0;
                    i=7;

                    chaine=chaines.get(i).getNom_chaine();
                    currentTime = Calendar.getInstance().getTime();
                    chaineReceived=chaines.get(i);
                   // callWebService(chaineReceived,currentTime);


                    ///////////// socket.io usage
                    try {
                        CallSocket(chaineReceived,currentTime);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                } else {
                    j=getI();
                    i=i-1;
                    chaine=chaines.get(i).getNom_chaine();
                    currentTime = Calendar.getInstance().getTime();
                    chaineReceived=chaines.get(i);
                    System.out.println(currentTime);
                   // callWebService(chaineReceived,currentTime);

                    ///////////// socket.io usage
                    try {
                        CallSocket(chaineReceived,currentTime);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chaines.get(i).getNom_chaine().equals("france24")){
                    j=7;
                    i=0;
                    chaine=chaines.get(i).getNom_chaine();

                    currentTime = Calendar.getInstance().getTime();
                    chaineReceived=chaines.get(i);
                    //callWebService(chaineReceived,currentTime);
                   // addChaine(chaine,currentTime.toString());


                    ///////////// socket.io usage
                    try {
                        CallSocket(chaineReceived,currentTime);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                } else {
                    j=getI();
                    i=i+1;
                    chaine=chaines.get(i).getNom_chaine();
                    currentTime = Calendar.getInstance().getTime();
                    chaineReceived=chaines.get(i);
                    System.out.println(currentTime);
                    //callWebService(chaineReceived,currentTime);

                    ///////////// socket.io usage
                    try {
                        CallSocket(chaineReceived,currentTime);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

    }



    void    CallSocket( Chaine chaine, Date date) throws URISyntaxException {
        final Chaine ch = chaine;
        /*socket = IO.socket("http://192.168.1.3:8088");
        socket.on(io.socket.client.Socket.EVENT_CONNECT, new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                JSONObject request=new JSONObject();
                try {
                    request.put("recepteur", 3);
                    request.put("nom_chaine", ch.getNom_chaine());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                socket.emit("input", request);
                //socket.disconnect();
            }


        });
        socket.on("output", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("output socket",( args[0]).toString());
            }
        });
        socket.connect();*/
        JSONObject request=new JSONObject();
        try {
            request.put("recepteur", Integer.valueOf(idtext.getText().toString()));
            request.put("nom_chaine", ch.getNom_chaine());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.emit("input", request);


    }


    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray data = (JSONArray) args[0];
                    Log.d("socket reslt",data.toString());
                }
            });
        }


    };

   @Override
   public void onClick(View view) {
       switch (view.getId()){
           case R.id.btn1:
               j=getI();
               i=0;
               chaine=chaines.get(i).getNom_chaine();
               currentTime = Calendar.getInstance().getTime();
               chaineReceived=chaines.get(i);
               //callWebService(chaineReceived,currentTime);

               ///////////// socket.io usage
               try {
                   CallSocket(chaineReceived,currentTime);
               } catch (URISyntaxException e) {
                   e.printStackTrace();
               }
               break;
           case R.id.btn2:
               j=getI();
               i=1;
               chaine=chaines.get(i).getNom_chaine();
               currentTime = Calendar.getInstance().getTime();
               chaineReceived=chaines.get(i);
               //callWebService(chaineReceived,currentTime);

               ///////////// socket.io usage
               try {
                   CallSocket(chaineReceived,currentTime);
               } catch (URISyntaxException e) {
                   e.printStackTrace();
               }
               break;
           case R.id.btn3:
               j=getI();
               i=2;
               chaine=chaines.get(i).getNom_chaine();
               currentTime = Calendar.getInstance().getTime();
               chaineReceived=chaines.get(i);
               //callWebService(chaineReceived,currentTime);

               ///////////// socket.io usage
               try {
                   CallSocket(chaineReceived,currentTime);
               } catch (URISyntaxException e) {
                   e.printStackTrace();
               }
               break;
           case R.id.btn4:
               j=getI();
               i=3;
               chaine=chaines.get(i).getNom_chaine();
               currentTime = Calendar.getInstance().getTime();
               chaineReceived=chaines.get(i);
               //callWebService(chaineReceived,currentTime);

               ///////////// socket.io usage
               try {
                   CallSocket(chaineReceived,currentTime);
               } catch (URISyntaxException e) {
                   e.printStackTrace();
               }
               break;
           case R.id.btn5:
               j=getI();
               i=4;
               chaine=chaines.get(i).getNom_chaine();
               currentTime = Calendar.getInstance().getTime();
               chaineReceived=chaines.get(i);
               //callWebService(chaineReceived,currentTime);

               ///////////// socket.io usage
               try {
                   CallSocket(chaineReceived,currentTime);
               } catch (URISyntaxException e) {
                   e.printStackTrace();
               }
               break;
           case R.id.btn6:
               j=getI();
               i=5;
               chaine=chaines.get(i).getNom_chaine();
               currentTime = Calendar.getInstance().getTime();
               chaineReceived=chaines.get(i);
               //callWebService(chaineReceived,currentTime);

               ///////////// socket.io usage
               try {
                   CallSocket(chaineReceived,currentTime);
               } catch (URISyntaxException e) {
                   e.printStackTrace();
               }
               break;
           case R.id.btn7:
               j=getI();
               i=6;
               chaine=chaines.get(i).getNom_chaine();
               currentTime = Calendar.getInstance().getTime();
               chaineReceived=chaines.get(i);


               //callWebService(chaineReceived,currentTime);

               ///////////// socket.io usage
               try {
                   CallSocket(chaineReceived,currentTime);
               } catch (URISyntaxException e) {
                   e.printStackTrace();
               }               break;
           case R.id.btn8:
               j=getI();
               i=7;
               chaine=chaines.get(i).getNom_chaine();
               currentTime = Calendar.getInstance().getTime();
               chaineReceived=chaines.get(i);
               callWebService(chaineReceived,currentTime);
               break;
           case R.id.btn9:
               j=getI();
               i=8;
               chaine=chaines.get(i).getNom_chaine();
               currentTime = Calendar.getInstance().getTime();
               chaineReceived=chaines.get(i);
               //callWebService(chaineReceived,currentTime);

               ///////////// socket.io usage
               try {
                   CallSocket(chaineReceived,currentTime);
               } catch (URISyntaxException e) {
                   e.printStackTrace();
               }                break;
           case R.id.back:
               i=j;
               chaine=chaines.get(i).getNom_chaine();
               currentTime = Calendar.getInstance().getTime();
               chaineReceived=chaines.get(i);
               //callWebService(chaineReceived,currentTime);

               ///////////// socket.io usage
               try {
                   CallSocket(chaineReceived,currentTime);
               } catch (URISyntaxException e) {
                   e.printStackTrace();
               }               break;
           case R.id.home:
               i=0;
               chaine=chaines.get(i).getNom_chaine();
               currentTime = Calendar.getInstance().getTime();
               chaineReceived=chaines.get(i);
               //callWebService(chaineReceived,currentTime);

               ///////////// socket.io usage
               try {
                   CallSocket(chaineReceived,currentTime);
               } catch (URISyntaxException e) {
                   e.printStackTrace();
               }               break;

       }
   }
    protected void callWebService(final Chaine chaine,final Date date) {
        Date currentTime = Calendar.getInstance().getTime();

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.POST,HttpUrl,
                createChannelMapperObejct(chaine,date),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("reponse", "" + response);
                    }

    }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
        }
    })
            {
                @Override

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("x-access-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImFobWVkYmhkIiwiZW1haWwiOiJhaG1lZC5iZW5oZW5kYUBlc3ByaXQudG4iLCJpYXQiOjE1MjQxODA3NDYsImV4cCI6MTUyNDI2NzE0Nn0.ih8ePoy2zPA_UqL_pxbTO6QcZQMdnLn5WLlo4Syv1vg");
                headers.put("Content-Type", "application/json");

                return headers;
            }
        };




        queue.add(jsObjRequest);
}

    public static JSONObject createChannelMapperObejct(Chaine chaine,Date date)
    {
        JSONObject request=new JSONObject();
        try {
            request.put("recepteur", 3);
            request.put("nom_chaine", chaine.getNom_chaine());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }


    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("output", onNewMessage);
    }
}
