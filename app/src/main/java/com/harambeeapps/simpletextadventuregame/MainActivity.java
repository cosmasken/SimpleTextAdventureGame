package com.harambeeapps.simpletextadventuregame;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

/**
 * Created by 2ndgengod on 1/29/2021.
 */

public class MainActivity extends AppCompatActivity {
Button btnNorth,btnWest,btnEast,btnSouth,btnOption1,btnOption2,btnOption3;
TextView tvMap,tvStory;
String id,north,west,east,south,description;
    int NO_OF_ROOMS ;
    int CURRENTROOMNUMBER = 1;
    DatabaseHelper dbHelper;
    static final int NO_CONNECTION = -1;
    List<Room> roomList = null;
    List<Room> mroomList = null;

     Room room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = new Toolbar(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 168);
        toolbar.setLayoutParams(layoutParams);
        toolbar.setPopupTheme(R.style.Theme_SimpleTextAdventureGame);
        toolbar.setBackgroundColor(getResources().getColor(R.color.purple_200));
        toolbar.setTitle("Game");
        toolbar.setVisibility(View.VISIBLE);
        LinearLayoutCompat ll = (LinearLayoutCompat) findViewById(R.id.main);
        ll.addView(toolbar, 0);
        setSupportActionBar(toolbar);
        loadUi();
        runGame();
        startGame(CURRENTROOMNUMBER);

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


    }
    private List<Room> loadXmlFromNetwork(InputStream is) throws XmlPullParserException, IOException {
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
       
        }
        return entries;
    }

    public void runGame(){
        try {
            mroomList=  loadXmlFromNetwork(getAssets().open("test.xml"));
           // tvMap.setText(mroomList.toString());
        }catch (XmlPullParserException e){
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }
    public void nextRoom(int roomNumber){
        CURRENTROOMNUMBER = roomNumber;
        DatabaseHelper databaseHelper =new  DatabaseHelper(getApplicationContext());
       Room room = databaseHelper.getRoomFromId(roomNumber);
       tvMap.setText(Integer.toString(room.getSouth()));
    }


    public void startGame(int currentRoomId){
        switch (currentRoomId){
            case 1:
                btnNorth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAlertDialogButtonClicked();
                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAlertDialogButtonClicked();
                    }
                });
                btnEast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAlertDialogButtonClicked();
                    }
                });
                btnWest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAlertDialogButtonClicked();
                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                  nextRoom(2);

                    }
                });
                break;
            case 2:
                btnNorth.setOnClickListener(view -> Toast.makeText(getApplicationContext(),"north",Toast.LENGTH_SHORT).show());
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"south",Toast.LENGTH_SHORT).show();
                    }
                });
                btnEast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"east",Toast.LENGTH_SHORT).show();
                    }
                });
                btnWest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(),"west",Toast.LENGTH_SHORT).show();
                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case 3:
                btnNorth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnEast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnWest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case 4:
                btnNorth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnEast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnWest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case 5:
                btnNorth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnEast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnWest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case 6:
                btnNorth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnEast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnWest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case 7:
                btnNorth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnEast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnWest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case 8:
                btnNorth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnEast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnWest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            case 9:
                btnNorth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnEast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnWest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnSouth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
            default:
                break;
        }
    }

    public void showAlertDialogButtonClicked() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Exit");
        builder.setMessage("There is no way through here!!");
        // add a button
        builder.setNeutralButton("OK",null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}