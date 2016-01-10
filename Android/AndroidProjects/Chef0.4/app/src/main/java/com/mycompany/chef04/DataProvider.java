package com.mycompany.chef04;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import java.util.HashMap;

public class DataProvider extends ContentProvider{
    public DataProvider(){

    }
    // fields for my content provider
    static final String PROVIDER_NAME = "com.mycompany.chef04.MasterData1";
    static final String URL = "content://" + PROVIDER_NAME + "/MasterTable1";
    static final Uri CONTENT_URI = Uri.parse(URL);

    // fields for the database (Table Food_VaL)
    static final String ID = "foodid";
    static final String FOODName = "foodname";
    static final String VegNonveg = "vegnonveg";
    static final String Char1 = "char1";
    static final String Char2 = "char2";
    static final String Char3 = "char3";
    static final String Char4 = "char4";
    static final String FoodImagelink = "foodimagelink";
    static final String ChefName = "chefname";
    static final String ChefPhoneNumber = "chefphonenumber";
    static final String ChefEmail = "chefemail";
    static final String ChefImageLink = "chefimagelink";
    static final String ChefDescription = "chefdescription";
    static final String LocationSer1 = "locser1";
    static final String LocationSer2 = "locser2";
    static final String LocationSer3 = "locser3";


    // integer values used in content URI
    static final int MASTERTABLE = 1;
    static final int PROD_ID = 2;

    DBHelper dbHelper;
    // projection map for a query
    private static HashMap<String, String> ProjectionMap;
    // maps content URI "patterns" to the integer values that were set above
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "MasterTable", MASTERTABLE);
        uriMatcher.addURI(PROVIDER_NAME, "MasterTable/#", PROD_ID);
    }
    // database declarations
    private SQLiteDatabase database;
    static final String DATABASE_NAME = "Database1";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "HOMECHEF_TABLE";
    static final String CREATE_TABLE =
            " CREATE TABLE " + TABLE_NAME +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " foodname TEXT NOT NULL, " +
                    " vegnonveg TEXT NOT NULL," +
                    " char1 TEXT NOT NULL," +
                    " char2 TEXT NOT NULL," +
                    " char3 TEXT NOT NULL," +
                    " char4 TEXT NOT NULL," +
                    " foodimagelink TEXT NOT NULL," +
                    " chefname TEXT NOT NULL, " +
                    " chefphonenumber TEXT NOT NULL," +
                    " chefemail TEXT NOT NULL," +
                    " chefimagelink TEXT NOT NULL," +
                    " chefdescription TEXT NOT NULL," +
                    " locser1 TEXT NOT NULL," +
                    " locser2 TEXT NOT NULL," +
                    " locser3 TEXT NOT NULL);";

    // class that creates and manages the provider's database
    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(DBHelper.class.getName(),
                    "Upgrading database from version " + oldVersion + " to "
                            + newVersion + ". Old data will be destroyed");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        // This is implemented to initialize your content provider on startup.
       Context context = getContext();
       dbHelper = new DBHelper(context);
        // permissions to be writable
        database = dbHelper.getWritableDatabase();
        if(database == null) return false;
        else
        return true;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // This is Implemented to handle query requests from clients.
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // the TABLE_NAME to query on
        queryBuilder.setTables(TABLE_NAME);
        // switch (uriMatcher.match(uri)) {
        // maps all database column names
//            case FRIENDS:
        queryBuilder.setProjectionMap(ProjectionMap);
//                break;
//            case FRIENDS_ID:
//                queryBuilder.appendWhere( ID + "=" + uri.getLastPathSegment());
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown URI " + uri);
        //}
        if (sortOrder == null || sortOrder == ""){
            // No sorting-> sort on names by default
            sortOrder = FOODName;
        }
        Cursor cursor = queryBuilder.query(database, projection, selection,
                selectionArgs, null, null, sortOrder);
        // register to watch a content URI for changes
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // This is implemented to handle requests to insert a new row.
        long row = database.insert(TABLE_NAME, "", values);
        // If record is added successfully
        if(row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Values in this application are not meant to be updated");
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        count = database.delete(TABLE_NAME, null, null);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
        //throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
