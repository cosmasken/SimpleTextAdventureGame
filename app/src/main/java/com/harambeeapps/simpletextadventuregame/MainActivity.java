package com.harambeeapps.simpletextadventuregame;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

/**
 * Created by 2ndgengod on 1/29/2021.
 */

public class MainActivity extends AppCompatActivity {
    static final int NO_CONNECTION = -1;
Button btnNorth,btnWest,btnEast,btnSouth,btnOption1,btnOption2,btnOption3;
TextView tvMap,tvStory;
String id,north,west,east,south,description;
    int NO_OF_ROOMS ;
    int CURRENTROOMNUMBER = 0;
    DatabaseHelper dbHelper;
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
        newGame();


    }
//LOAD USER INTERFACE
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
        tvStory.setMovementMethod(new ScrollingMovementMethod());


    }
    //LOADS XML FROM GIVEN INPU STREAM
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
        String title =  null;
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
                    Integer.toString(entry.getWest()),Integer.toString(entry.getEast()),Integer.toString(entry.getSouth()),
                    entry.getTitle(),entry.getDescription());

           id=Integer.toString(entry.getId());
           north =Integer.toString(entry.getNorth()) ;
           west=Integer.toString(entry.getWest()) ;
           east = Integer.toString(entry.getEast());
           south= Integer.toString(entry.getSouth());
            title =entry.getTitle();
           description =entry.getDescription();
       
        }
        return entries;
    }
//GET GAME DATA FROM XML FILE IN ASSETS FOLDER
    //CAN BE CHANGED TO GET DATA FROM ANY DATA SOURCE
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
    //CHANGE THE TEXT BASED ON ROOM NUMBER
    public void nextRoom(int roomNumber){
        DatabaseHelper databaseHelper =new  DatabaseHelper(getApplicationContext());
       Room room = databaseHelper.getRoomFromId(roomNumber);
       tvMap.setText(room.getTitle());
        tvStory.setText(room.getDescription());
        databaseHelper.close();
    }
//lAUNCH NEW GAME
public void  newGame(){
        nextRoom(0);
      btnNorth.setOnClickListener(new View.OnClickListener() {
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
            CURRENTROOMNUMBER = 1;
            nextRoom(1);
            gotoRoomOne();

        }
    });
}
//GO TO THE FIRST ROOM
public void gotoRoomOne(){

    btnNorth.setOnClickListener(view -> showAlertDialogButtonClicked());

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
            CURRENTROOMNUMBER = 2;
            nextRoom(2);
            gotoRoomTwo();
        }
    });
}
    //GO TO THE SECOND ROOM
public void gotoRoomTwo(){
    btnNorth.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CURRENTROOMNUMBER = 1;
            nextRoom(1);
            gotoRoomOne();
        }
    });
    btnSouth.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CURRENTROOMNUMBER = 6;
            nextRoom(6);
            gotoRoomSix();
        }
    });
    btnEast.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CURRENTROOMNUMBER = 3;
            nextRoom(3);
            gotoRoomThree();
        }
    });
    btnWest.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showAlertDialogButtonClicked();
        }
    });
}
    //GO TO THE THIRD ROOM
    private void gotoRoomThree() {
        btnNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogButtonClicked();
            }
        });

        btnEast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENTROOMNUMBER = 4;
                nextRoom(4);
                gotoRoomFour();

            }
        });
        btnWest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENTROOMNUMBER =2;
                nextRoom(2);
                gotoRoomTwo();
            }
        });
        btnSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENTROOMNUMBER =7;
                nextRoom(7);
                gotoRoomSeven();

            }
        });
    }








    //GO TO THE FOURTH ROOM
    public void gotoRoomFour(){
        btnNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENTROOMNUMBER =5;
                nextRoom(5);
                gotoRoomFive();
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
                CURRENTROOMNUMBER =3;
                nextRoom(3);
                gotoRoomThree();
            }
        });
        btnSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogButtonClicked();
            }
        });
}
    //GO TO THE FIFTH ROOM
    private void gotoRoomFive() {
        btnNorth.setOnClickListener(new View.OnClickListener() {
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
                CURRENTROOMNUMBER =4;
                nextRoom(4);
                gotoRoomFour();
            }
        });
    }
    //GO TO THE SIXTH ROOM
    private void gotoRoomSix() {
        btnNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENTROOMNUMBER = 2;
                nextRoom(2);
                gotoRoomTwo();
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
                CURRENTROOMNUMBER = 7 ;
                nextRoom(7);
                gotoRoomSeven();
            }
        });
        btnWest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogButtonClicked();
            }
        });
    }
    //GO TO THE SEVENTH ROOM
    private void gotoRoomSeven() {
        btnNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENTROOMNUMBER =3;
                nextRoom(3);
                gotoRoomThree();
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
                CURRENTROOMNUMBER =6;
                nextRoom(6);
                gotoRoomSix();
            }
        });
        btnSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENTROOMNUMBER =8;
                nextRoom(8);
                gotoRoomEight();
            }
        });
    }
    //GO TO THE EIGHT ROOM
    private void gotoRoomEight() {
        btnNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENTROOMNUMBER = 7;
                nextRoom(7);

                gotoRoomSeven();
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
                CURRENTROOMNUMBER = 9;
                nextRoom(9);
                gotoRoomNine();
            }
        });
    }

    //GO TO THE NINTH ROOM
    private void gotoRoomNine() {
        btnNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CURRENTROOMNUMBER = 8;
                nextRoom(8);
                gotoRoomEight();
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
                showAlertDialogButtonClicked();
            }
        });
    }

//SHOW DIALOG WHEN THERE IS NO EXIT
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

    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

        int itemId = item.getItemId();
        if (itemId == R.id.menu_save) {// Toast.makeText(MainActivity.this, "Save is Selected", Toast.LENGTH_SHORT).show();
            databaseHelper.saveGameState(CURRENTROOMNUMBER);
            Toast.makeText(getApplicationContext(), "Game Saved Successfully", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.menu_load) {// Toast.makeText(MainActivity.this, "Load Selected", Toast.LENGTH_SHORT).show();
            CURRENTROOMNUMBER = databaseHelper.loadGamestate();
            nextRoom(CURRENTROOMNUMBER);
             Toast.makeText(getApplicationContext(),"Game Loaded Successfully",Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.menu_clear) {
            databaseHelper.deleteGameSate();
              Toast.makeText(getApplicationContext(),"Game data cleared Successfully",Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}