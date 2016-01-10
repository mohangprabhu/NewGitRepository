package com.mycompany.msversion03;

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

public class SalesDataProvider extends ContentProvider {
    public SalesDataProvider() {
    }

    // fields for my content provider
    static final String PROVIDER_NAME = "com.mycompany.provider.SaleData";
    static final String URL = "content://" + PROVIDER_NAME + "/MasterTable";
    static final Uri CONTENT_URI = Uri.parse(URL);

    // fields for the database
    static final String ID = "id";
    static final String BRAND = "brand";
    static final String REG = "region";
    static final String PROJ = "project";
    static final String SLPERSON = "salesperson";
    static final String CSTNME = "custname";
    static final String CSTSRC= "custsrc";
    static final String UNTSLD = "unitssold";
    static final String VALUE = "value";
    static final String CSTOCC = "custocc";
    static final String CSTINF = "custinf";
    static final String DTE = "date";

    // integer values used in content URI
    static final int MASTERTABLE = 1;
    static final int SALE_ID = 2;

    DBHelper dbHelper;

    // projection map for a query
    private static HashMap<String, String> SaleMap;
    // maps content URI "patterns" to the integer values that were set above
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "MasterTable", MASTERTABLE);
        uriMatcher.addURI(PROVIDER_NAME, "MasterTable/#", SALE_ID);
    }
    // database declarations
    private SQLiteDatabase database;
    static final String DATABASE_NAME = "SaleMaster";
    static final String TABLE_NAME = "saleTable";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_TABLE =
            " CREATE TABLE " + TABLE_NAME +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " brand TEXT NOT NULL, " +
                    " region TEXT NOT NULL," +
                    " project TEXT NOT NULL," +
                    " salesperson TEXT NOT NULL," +
                    " custname TEXT NOT NULL," +
                    " custsrc TEXT NOT NULL," +
                    " unitssold TEXT NOT NULL," +
                    " value TEXT NOT NULL," +
                    " custocc TEXT NOT NULL," +
                    " custinf TEXT," +
                    " date TEXT NOT NULL);";

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
        else return true;
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
                queryBuilder.setProjectionMap(SaleMap);
//                break;
//            case FRIENDS_ID:
//                queryBuilder.appendWhere( ID + "=" + uri.getLastPathSegment());
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown URI " + uri);
        //}
        if (sortOrder == null || sortOrder == ""){
            // No sorting-> sort on names by default
            sortOrder = PROJ;
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
