package pl.dupesko.dupesko;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.dupesko.dupesko.Fragments.PlaylistFragment;
import pl.dupesko.dupesko.Model.Playlist;

public class SpinnerAdapter<T> extends ArrayAdapter<T> {
    private Context mContext;
    private ArrayList<T> mObjects;

    public SpinnerAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        mContext = context;
        mObjects = new ArrayList<>(objects);
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Nullable
    @Override
    public T getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(mContext);
        label.setBackgroundColor(Color.WHITE);
        label.setText(mObjects.get(position).toString());
        return getView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(mContext);
        label.setBackgroundColor(Color.WHITE);
        label.setText(mObjects.get(position).toString());
        return label;
    }
}
