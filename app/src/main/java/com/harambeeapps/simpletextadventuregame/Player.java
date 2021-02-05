package com.harambeeapps.simpletextadventuregame;

/**
 * Created by 2ndgengod on 1/30/2021.
 */

class Player {

    //player class must have name,location,inventory and source
    //instead of an array of descriptions,use array of locale objects

    public String inventory;
    public int playerXp;
    public int playerId;
    private String playerName;

    public Player(String inventory, int playerXp, int playerId, String playerName) {
        this.inventory = inventory;
        this.playerXp = playerXp;
        this.playerId = playerId;
        this.playerName = playerName;
    }
}
