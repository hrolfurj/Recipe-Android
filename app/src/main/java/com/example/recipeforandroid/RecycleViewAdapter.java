package com.example.recipeforandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

/**
 * Þetta er adapter-class fyrir RecycleView listann inní RecipeListActivity fyrir mock-object.
 *
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    List<Recipe> recipeList;
    Context context;

    public RecycleViewAdapter(List<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_recipePic;
        TextView tv_recipeTitle;
        TextView tv_recipeTag;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_recipePic = itemView.findViewById(R.id.iv_recipePicture);
            tv_recipeTitle = itemView.findViewById(R.id.tv_recipe_name);
            tv_recipeTag = itemView.findViewById(R.id.tv_recipe_tag);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_recipe,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_recipeTitle.setText(recipeList.get(position).getTitle());
        holder.tv_recipeTag.setText(recipeList.get(position).getTag());
        Glide.with(this.context).load(recipeList.get(position).getUpload_image()).into(holder.iv_recipePic);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

}
