package com.example.recipeforandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    List<Recipes> recipeList;
    Context context;

    public RecycleViewAdapter(List<Recipes> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_new_recipe, parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.input_title.setText(recipeList.get(position).getTitle());
        holder.input_tag.setText(recipeList.get(position).getTag());
        holder.input_Description.setText(String.valueOf(recipeList.get(position).getDescription()));
        Glide.with(this.context).load(recipeList.get(position).getUpload_image()).into(holder.view_image);

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        EditText input_title;
        EditText input_tag;
        EditText input_Description;
        ImageView view_image;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            input_title = itemView.findViewById(R.id.view_Title);
            input_tag = itemView.findViewById(R.id.view_Tag);
            input_Description = itemView.findViewById(R.id.view_Description);
            view_image = itemView.findViewById(R.id.view_Image);

        }
    }
}
