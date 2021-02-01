package com.harambeeapps.simpletextadventuregame;

/**
 * Created by 2ndgengod on 1/30/2021.
 */

class Player {

    //player class must have naem,location,inventory and source
    //instead of an array of descriptions,use array of locale objects

    private String playerName;
    public String inventory;
    public int score;
    public int currentRoom;

    public Player(String playerName, String inventory, int score, int currentRoom) {
        this.playerName = playerName;
        this.inventory = inventory;
        this.score = score;
        this.currentRoom = currentRoom;
    }
}
