package com.js.zadaniedomowe2.tasks;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//This class will hold the list that will be displayed
//to the user by the TaskFragment.
public class TaskListContent {

    public static final List <Task> ITEMS = new ArrayList <Task> ();

    public static final Map <String, Task> ITEM_MAP = new HashMap <String, Task> ();

    private static final int COUNT = 5;

    static {
        addItem ( new Task ( String.valueOf ( 1 ), "Juwenalia", "W Poznaniu.", "01.02.19" ) );
        addItem ( new Task ( String.valueOf ( 2 ), "Koncert", "Na świeżym powietrzu.", "13.09.19" ) );
        addItem ( new Task ( String.valueOf ( 3 ), "Bieg", "Maraton.", "25.09.19" ) );
    }

    public static void addItem(Task item) {
        ITEMS.add ( item );
        ITEM_MAP.put ( item.id, item );
    }
    public static class Task implements Parcelable {
        public final String id;
        public final String title;
        public final String details;
        public String picPath;
        public final String data;

        public Task(String id, String title, String details, String data) {
            this.id = id;
            this.title=title;
            this.details=details;
            this.picPath="";
            this.data=data;
        }
        public Task(String id, String title, String details,String data, String picPath) {
            this.id = id;
            this.title=title;
            this.details=details;
            this.picPath=picPath;
            this.data=data;
        }

        protected Task(Parcel in) {
            id = in.readString ();
            title = in.readString ();
            picPath = in.readString ();
            details = in.readString ();
            data = in.readString ();
        }

        public static final Creator <Task> CREATOR = new Creator <Task> () {
            @Override
            public Task createFromParcel(Parcel in) {
                return new Task ( in );
            }

            @Override
            public Task[] newArray(int size) {
                return new Task[size];
            }
        };

        @Override
        public String toString() {
            return title;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString ( id );
            dest.writeString ( title );
            dest.writeString ( picPath );
            dest.writeString ( details );
            dest.writeString ( data );
        }
        public void setPicPath(String path)
        {
            this.picPath = path;
        }
    }
    public static void removeItem(int position)
    {
        String itemId = ITEMS.get ( position ).id;

        ITEMS.remove ( position );

        ITEM_MAP.remove ( itemId );
    }
    public static void clearList()
    {
        ITEMS.clear ();
        ITEM_MAP.clear ();
    }
}
