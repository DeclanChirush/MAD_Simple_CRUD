package database;

import android.provider.BaseColumns;

public final class UserProfile {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserProfile() {}

    /* Inner class that defines the table contents */
    public static class User implements BaseColumns {

        public static final String TABLE_NAME = "userInfor";
        public static final String COLUMN_NAME_ID = "_ID";
        public static final String COLUMN_NAME_USERNAME = "userName";
        public static final String COLUMN_NAME_DOB = "dateOfBirth";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_GENDER = "gender";

    }
}