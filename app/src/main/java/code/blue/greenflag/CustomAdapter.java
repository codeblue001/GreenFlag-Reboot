package code.blue.greenflag;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    //https://www.youtube.com/watch?v=_m-Ve-BAYe0
    private Context mContext;
    private Cursor mCursor;


    public CustomAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView nameText, genderText, countryText, ageText;
        public ImageView profileImageView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.databaseNameTextView);
            genderText = itemView.findViewById(R.id.databaseGenderTextView);
            countryText = itemView.findViewById(R.id.dataBaseCountryTextView);
            ageText = itemView.findViewById(R.id.databaseBirthDateTextView);
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_layout, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        if (!mCursor.move(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(DatabaseUtil.TaskTable.nameColumn));
        String gender = mCursor.getString(mCursor.getColumnIndex(DatabaseUtil.TaskTable.genderColumn));
        int age = mCursor.getInt(mCursor.getColumnIndex(DatabaseUtil.TaskTable.ageColumn));
        String country = mCursor.getString(mCursor.getColumnIndex(DatabaseUtil.TaskTable.countryColumn));

        holder.nameText.setText(name);
        holder.genderText.setText(name);
        holder.ageText.setText(name);
        holder.countryText.setText(name);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    public void swapCursor(Cursor newCursor){
        if (mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null){
            notifyDataSetChanged();
        }
    }

}
