package com.harambeeapps.simpletextadventuregame;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2ndgengod on 1/30/2021.
 */
@Root(name="dungeon")
public class Map {
    @ElementList(inline = true)
    List<Room> rooms;

}
