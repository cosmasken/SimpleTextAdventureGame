package com.harambeeapps.simpletextadventuregame;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import androidx.annotation.NonNull;

/**
 * Created by 2ndgengod on 1/29/2021.
 */

public class Room {
    public Room() {

    }


    public Room(int id, int north, int east, int south, int west, String description) {
       this.id = id;
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.description = description;
    }


    private int id;
    private int north;
    private int east;
    private int south;
    private int west;
    private String description;

    /*Room() {
        north = NO_CONNECTION;
        east = NO_CONNECTION;
        south = NO_CONNECTION;
        west = NO_CONNECTION;
        description = "nothing";
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNorth() {
        return north;
    }

    public void setNorth(int north) {
        this.north = north;
    }

    public int getEast() {
        return east;
    }

    public void setEast(int east) {
        this.east = east;
    }

    public int getSouth() {
        return south;
    }

    public void setSouth(int south) {
        this.south = south;
    }

    public int getWest() {
        return west;
    }

    public void setWest(int west) {
        this.west = west;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return id+north+west+east+south+description;
    }
}