package com.harambeeapps.simpletextadventuregame;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlPullParserHandler {
    // We don't use namespaces
    private static final String ns = null;

    public List< Room> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

  //  The readFeed() method does the actual work of processing the feed.
    private List<Room> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Room> entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "dungeon");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("room")) {
                entries.add(readRoom(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    // Parses the contents of an room. If it encounters a id, north, or west tag, hands them off
    // to their respective "read" methods for processing. Otherwise, skips the tag.
    private Room readRoom(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "room");
        String id = null;
        String north = null;
        String west = null;
        String east = null;
        String south = null;
        String description = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("id")) {
                id = readId(parser);
            } else if (name.equals("north")) {
                north = readNorth(parser);
            } else if (name.equals("west")) {
                west = readWest(parser);
            } else if (name.equals("east")) {
                east = readEast(parser);
            }
            else if (name.equals("south")) {
                south = readSouth(parser);
            }
            else if (name.equals("description")) {
                description = readDescription(parser);
            }else {
                skip(parser);
            }
        }
        return new Room(Integer.parseInt(id), Integer.parseInt(north), Integer.parseInt(west),Integer.parseInt(east),Integer.parseInt(south),description);
    }

    private String readId(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "id");
        String id = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "id");
        return id;
    }
    private String readNorth(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "north");
        String north = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "north");
        return north;
    }
    private String readWest(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "west");
        String west = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "west");
        return west;
    }

    private String readEast(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "east");
        String east = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "east");
        return east;
    }
    private String readSouth(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "south");
        String south = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "south");
        return south;
    }
    private String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, "description");
        return description;
    }
    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

}