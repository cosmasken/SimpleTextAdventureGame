package com.harambeeapps.simpletextadventuregame;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobprofs.retrofit.converters.SimpleXmlConverter;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import retrofit.RestAdapter;

/**
 * Created by 2ndgengod on 1/29/2021.
 */

public class MainActivity extends AppCompatActivity {
Button btnNorth,btnWest,btnEast,btnSouth,btnOption1,btnOption2,btnOption3;
TextView tvMap,tvStory;
String id,north,west,east,south,description;
    int NO_OF_ROOMS ;
    static final int NO_CONNECTION = -1;
    List<Room> roomList;

     Room room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadUi();

    }

    public void loadUi(){
        btnNorth = findViewById(R.id.btnNorth);
        btnWest = findViewById(R.id.btnWest);
        btnEast = findViewById(R.id.btnEast);
        btnSouth = findViewById(R.id.btnSouth);
        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        tvMap = findViewById(R.id.tvMap);
        tvStory = findViewById(R.id.tvStory);
        btnOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                  String data=  loadXmlFromNetwork(getAssets().open("test.xml"));
                    tvMap.setText(data);
                }catch (XmlPullParserException e){
                    e.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
               // Log.d("TAGGGGGGGGGGGGGGGGGG","FIIIIIIIIIIIIIIIIIIIIII");
            }
        });
    }
    // Implementation of AsyncTask used to download XML feed from stackoverflow.com.
    private class DownloadXmlTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                Log.d("AAAAAAAAAAAAAAAAAAAAAAAAAA","AAAAAAAAAAAAAAAAA");
                return loadXmlFromNetwork(getApplicationContext().getAssets().open("test.xml"));

            } catch (IOException e) {
                return getResources().getString(R.string.connection_error);
            } catch (XmlPullParserException e) {
                return getResources().getString(R.string.error);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            setContentView(R.layout.activity_main);
            // Displays the HTML string in the UI via a WebView
            //TextView textView = (TextView) findViewById(R.id.tvMap);
           tvMap.setText(result);
        }
    }

    // Uploads XML from stackoverflow.com, parses it, and combines it with
// HTML markup. Returns HTML string.
    private String loadXmlFromNetwork(InputStream is) throws XmlPullParserException, IOException {
        InputStream stream = null  ;
        List<Room> mylist= null;
        // Instantiate the parser
        XmlPullParserHandler xmlParser = new XmlPullParserHandler();
        List<Room> entries = null;
        String id = null;
        String north = null;
        String west = null;
        String east = null;
        String south = null;
        String description = null;

        try {

            stream = is;
            entries = xmlParser.parse(stream);
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (stream != null) {

                stream.close();
            }
        }

        // XmlPullParserHandler returns a List (called "entries") of Room objects.
        // Each Room object represents a single post in the XML feed.
        // This section processes the entries list to combine each entry with HTML markup.
        // Each entry is displayed in the UI as a link that optionally includes
        // a text summary.
        for (Room entry : entries) {
            DatabaseHelper dbHandler = new DatabaseHelper(getApplicationContext());
            dbHandler.insertRoomDetails(Integer.toString(entry.getId()),Integer.toString(entry.getNorth()),
                    Integer.toString(entry.getWest()),Integer.toString(entry.getEast()),Integer.toString(entry.getSouth()),entry.getDescription());

           id=Integer.toString(entry.getId());
           north =Integer.toString(entry.getNorth()) ;
           west=Integer.toString(entry.getWest()) ;
           east = Integer.toString(entry.getEast());
           south= Integer.toString(entry.getSouth());
           description =entry.getDescription();
           Toast.makeText(getApplicationContext(),id+north+west+east+south+description,Toast.LENGTH_SHORT).show();
        }
        return entries.toString();
    }

    // Given a string representation of a URL, sets up a connection and gets
// an input stream.


}