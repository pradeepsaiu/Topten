package com.example.pradeepsai.topten;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button b1;
    private String mFileContents;
    private ListView listApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.btnxml);

        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ParseApplications parse = new ParseApplications(mFileContents);
                parse.process();

                ArrayAdapter<Application> arrayAdapter = new ArrayAdapter<Application>(MainActivity.this,R.layout.list_item,parse.getApplications());
                listApps.setAdapter(arrayAdapter);
            }});

        listApps = (ListView) findViewById(R.id.listView);
        downloadData start1 = new downloadData();
        start1.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml");

    }

    private class downloadData extends AsyncTask<String,Void,String>
    {

//        StringBuilder data = new StringBuilder();

        private TextView xmlcontents;

        protected String doInBackground(String... params)
        {
            mFileContents = downloadxmlfile(params[0]);
            if(mFileContents == null)
                Log.d("downloadData","Error in donwloading the file");
            Log.d("downloadData",mFileContents);
            return mFileContents;
        }
        protected void onPostExecute(String result)
        {
//            xmlcontents =(TextView)findViewById(R.id.xmlcontent);
            super.onPostExecute(result);
            Log.d("downloadData",result);
//            xmlcontents.setText(result);

        }
    }

    private String downloadxmlfile(String urlpath)
    {
        StringBuilder tempbuffer = new StringBuilder();

        try
        {
            URL url = new URL(urlpath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int response = connection.getResponseCode();
            if(response <=0 ){}
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int charRead;
            char[] inputBuffer = new char[500];

            while(true)
            {
                charRead = isr.read(inputBuffer);
                if(charRead<=0)
                    break;
                tempbuffer.append(String.copyValueOf(inputBuffer,0,charRead));
            }

            return tempbuffer.toString();

        }
        catch(SecurityException e) {
            Log.d("AsyncTask", e.getMessage());
        }
        catch(Exception e) {
            Log.d("AsyncTast", e.getMessage());
        }
        return "";
    }
}
