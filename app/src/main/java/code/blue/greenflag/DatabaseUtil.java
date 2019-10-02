package code.blue.greenflag;

import android.provider.BaseColumns;

public class DatabaseUtil {


    //Todo Declare variable for database name and table name
    public static final String DATABASE_NAME = "registrationdb";
    public static final int DATABASE_VERSION = 1;

    public  class TaskTable implements BaseColumns{
        //https://www.youtube.com/watch?v=5ISNPFmuOU8&t=23s
        public static final String TABLE_NAME = "registered_users";
        public static final String nameColumn = "name";
        public static final String userNameColumn = "username";
        public static final String passwordColumn = "password";
        public static final String photoColumn = "photo";
        public static final String ageColumn = "age";
        public static final String birthDateColumn = "birthDate";
        public static final String genderColumn = "gender";
        public static final String countryColumn = "country";


    }

}
