package com.mycompany.marketingapp;

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
import android.text.TextUtils;

import java.util.Date;
import java.util.HashMap;

public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {
    }

    //Provider authority = com.example.<appname>.provider
    static final String PROVIDER_NAME = "com.mycompany.marketingapp.provider";
    //Path Structure: com.example.<appname>.provider/table1
    static final String URL = "content://" + PROVIDER_NAME + "/saledata";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String PROJ_NAME = "projname";
    static final String CUST_NAME = "custname";
    static final String UNIT_SOLD = "unitssold";
    static final String VALUE = "value";
    static final String DATE = "logdate";

    private static HashMap<String, String> DATA_PROJECTION_MAP;

    static final int SALEDATA = 1;
    static final int SALE_ID = 2;

    static final UriMatcher uriMatcher;

    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "saledata", SALEDATA);
        uriMatcher.addURI(PROVIDER_NAME, "saledata/#", SALE_ID);
    }

    /*Database specific constant declarations*/
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "data";
    static final String DATA_TABLE_NAME = "saledata";
    static final int DATABASE_VERSION = 1;

    //Create table with following columns
    //  1. _Id --Done
    //  2. Project Name --Done
    //  3. Sales Person name --TODO
    //  4. Customer Name --Done
    //  5. Number of Units --Done
    //  6. Value (Rs. Crs) --Done
    //  7. Brand --TODO
    //  8. Region --TODO
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + DATA_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " projname TEXT NOT NULL, " +
                    " custname TEXT NOT NULL, " +
                    " unitsold INTEGER NOT NULL" +
                    " value DOUBLE NOT NULL" +
                    " logdate DATE NOT NULL);";

    /*Helper class that actually creates and manages the provider's underlying data repository.*/
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_DB_TABLE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  DATA_TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int count = 0;

        switch (uriMatcher.match(uri)){
            case SALEDATA:
                count = db.delete(DATA_TABLE_NAME, selection, selectionArgs);
                break;
            case SALE_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(DATA_TABLE_NAME, _ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        //Implement this to handle requests for the MIME type of the data at the given URI.
        switch (uriMatcher.match(uri)){
            /*Get all student records*/
            case SALEDATA:
                //TODO: find what will come here
                //return "vnd.android.cursor.dir/vnd.example.students";
            /*Get a particular student*/
            case SALE_ID:
                //TODO: find what will come here
                //return "vnd.android.cursor.item/vnd.example.students";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        /**
         * Add a new student record
         */
        long rowID = db.insert(DATA_TABLE_NAME, "", values);
        /**
         * If record is added successfully
         */
        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public boolean onCreate() {
        // Implement this to initialize your content provider on startup.
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        //Implement this to handle query requests from clients.
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DATA_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case SALEDATA:
                qb.setProjectionMap(DATA_PROJECTION_MAP);
                break;
            case SALE_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == ""){
            /**
             * By default sort on project names
             */
            sortOrder = PROJ_NAME;
        }
        Cursor c = qb.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;

        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // Implement this to handle requests to update one or more rows.
        int count = 0;

        switch (uriMatcher.match(uri)){
            case SALEDATA:
                count = db.update(DATA_TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            case SALE_ID:
                count = db.update(DATA_TABLE_NAME, values, _ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
