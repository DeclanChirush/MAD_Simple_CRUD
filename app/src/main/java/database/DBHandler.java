package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "UserDetails.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserProfile.User.TABLE_NAME + " (" +
                    UserProfile.User.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    UserProfile.User.COLUMN_NAME_USERNAME + " TEXT," +
                    UserProfile.User.COLUMN_NAME_DOB + " TEXT," +
                    UserProfile.User.COLUMN_NAME_PASSWORD + " TEXT," +
                    UserProfile.User.COLUMN_NAME_GENDER + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserProfile.User.TABLE_NAME;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long addInfo(String username, String dob, String password, String gender){

        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(UserProfile.User.COLUMN_NAME_USERNAME, username);
        values.put(UserProfile.User.COLUMN_NAME_DOB, dob);
        values.put(UserProfile.User.COLUMN_NAME_PASSWORD, password);
        values.put(UserProfile.User.COLUMN_NAME_GENDER, gender);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserProfile.User.TABLE_NAME, null, values);

        return newRowId;
    }

    public boolean updateInfo(String username, String dob, String password, String gender){
        SQLiteDatabase db = getWritableDatabase();

        // New value for one column
        String title = "MyNewTitle";
        ContentValues values = new ContentValues();
        values.put(UserProfile.User.COLUMN_NAME_DOB, dob);
        values.put(UserProfile.User.COLUMN_NAME_PASSWORD, password);
        values.put(UserProfile.User.COLUMN_NAME_GENDER, gender);


        // Which row to update, based on the title
        String selection = UserProfile.User.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = { username };

        int count = db.update(
                UserProfile.User.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count >=1){
            return true;
        }else{
            return false;
        }
    }

    public List readAllInfo(){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.User.COLUMN_NAME_USERNAME,
                UserProfile.User.COLUMN_NAME_DOB,
                UserProfile.User.COLUMN_NAME_PASSWORD,
                UserProfile.User.COLUMN_NAME_GENDER

        };

        // Filter results WHERE "title" = 'My Title'
        // String selection = UserProfile.User.COLUMN_NAME_USERNAME + " = ?";
        // String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserProfile.User.COLUMN_NAME_USERNAME + " ASC";

        Cursor cursor = db.query(
                UserProfile.User.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List userDetails = new ArrayList<>();
        while(cursor.moveToNext()) {
            long userName = cursor.getLong(
                    cursor.getColumnIndexOrThrow(UserProfile.User.COLUMN_NAME_USERNAME));
            userDetails.add(userName);
        }
        cursor.close();

        return userDetails;

    }

    public List readAllInfo(String username){
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserProfile.User.COLUMN_NAME_USERNAME,
                UserProfile.User.COLUMN_NAME_DOB,
                UserProfile.User.COLUMN_NAME_PASSWORD,
                UserProfile.User.COLUMN_NAME_GENDER

        };

        // Filter results WHERE "title" = 'My Title'
         String selection = UserProfile.User.COLUMN_NAME_USERNAME + " LIKE ?";
         String[] selectionArgs = { username };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserProfile.User.COLUMN_NAME_USERNAME + " ASC";

        Cursor cursor = db.query(
                UserProfile.User.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List userInfo = new ArrayList<>();
        while(cursor.moveToNext()) {

            String userName = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.User.COLUMN_NAME_USERNAME));
            String dob = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.User.COLUMN_NAME_DOB));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.User.COLUMN_NAME_PASSWORD));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.User.COLUMN_NAME_GENDER));

            userInfo.add(userName); //0
            userInfo.add(dob); //1
            userInfo.add(password); //2
            userInfo.add(gender); //3
        }
        cursor.close();

        return userInfo;

    }

    public int deleteInfo( String username ){
        SQLiteDatabase db = getWritableDatabase();

        // Define 'where' part of query.
        String selection = UserProfile.User.COLUMN_NAME_USERNAME + " LIKE ?";

        // Specify arguments in placeholder order.
        String[] selectionArgs = { username };

        // Issue SQL statement.
        int deletedRow = db.delete(UserProfile.User.TABLE_NAME, selection, selectionArgs);

        return  deletedRow;

    }

}