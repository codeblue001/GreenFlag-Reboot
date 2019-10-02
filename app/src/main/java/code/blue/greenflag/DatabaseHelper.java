package code.blue.greenflag;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {



    public DatabaseHelper(Context context) {
        super(context, DatabaseUtil.DATABASE_NAME, null, DatabaseUtil.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " +
                        DatabaseUtil.TaskTable.TABLE_NAME + " (" +
                        DatabaseUtil.TaskTable._ID + " INTEGER PRIMARY KEY, " +
                        DatabaseUtil.TaskTable.nameColumn + " VARCHAR(255)," +
                        DatabaseUtil.TaskTable.userNameColumn + " VARCHAR(255)," +
                        DatabaseUtil.TaskTable.passwordColumn + " VARCHAR(255)," +
                        DatabaseUtil.TaskTable.photoColumn + " VARCHAR(255)," +
                        DatabaseUtil.TaskTable.ageColumn + " INTEGER," +
                        DatabaseUtil.TaskTable.birthDateColumn + " VARCHAR(255)," +
                        DatabaseUtil.TaskTable.genderColumn + " VARCHAR(255)," +
                        DatabaseUtil.TaskTable.countryColumn + " VARCHAR(255))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS  " + DatabaseUtil.TaskTable.TABLE_NAME);
        onCreate(db);
    }
}
