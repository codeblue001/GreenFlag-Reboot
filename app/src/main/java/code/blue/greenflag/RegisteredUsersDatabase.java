package code.blue.greenflag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class RegisteredUsersDatabase extends AppCompatActivity {

    RecyclerView recyclerView;
    CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CustomAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);
    }

    private Cursor getAllItems() {
        DatabaseHelper database = new DatabaseHelper(this);
        SQLiteDatabase readableDB = database.getReadableDatabase();

        return readableDB.query(
                DatabaseUtil.TaskTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

}
