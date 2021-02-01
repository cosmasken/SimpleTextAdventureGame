package com.harambeeapps.simpletextadventuregame;

import retrofit2.http.GET;

/**
 * Created by 2ndgengod on 1/31/2021.
 */

public interface AppInterface {
    @GET("/xml/test.xml")
    Map getRooms();
}
