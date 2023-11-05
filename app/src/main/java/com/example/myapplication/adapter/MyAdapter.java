package com.example.myapplication.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.UserData;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<UserData> dataList;
    private Context context;

    public MyAdapter(Context context, List<UserData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserData userData = dataList.get(position);
        holder.nameTextView.setText("Name: " + userData.getName());
        holder.emailTextView.setText("Email: " + userData.getEmail());
        holder.birthdayTextView.setText("Birthday: " + userData.getBirthday());
        int resourceIdByName = getResourceIdByName(userData.getAvatar());
        Drawable drawable = context.getResources().getDrawable(resourceIdByName);
        holder.avatarView.setImageDrawable(drawable);
    }

    private int getResourceIdByName(String resourceName) {
        Resources resources = context.getResources();
        return resources.getIdentifier(resourceName, "drawable", context.getPackageName());
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView emailTextView;
        TextView birthdayTextView;
        ImageView avatarView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.nameTextView);
            emailTextView = view.findViewById(R.id.emailTextView);
            birthdayTextView = view.findViewById(R.id.birthdayTextView);
            birthdayTextView = view.findViewById(R.id.birthdayTextView);
            avatarView = view.findViewById(R.id.avatarView);
        }
    }
}