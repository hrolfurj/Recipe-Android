package com.example.recipeforandroid.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.recipeforandroid.Persistence.Entities.Recipe2;
import com.example.recipeforandroid.Persistence.Entities.Recipe2;
import com.example.recipeforandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Þetta er adapter-class fyrir RecycleView listann inní RecipeListActivity fyrir mock-object.
 *
 */
public class RecycleViewAdapter2 extends RecyclerView.Adapter<RecycleViewAdapter2.MyViewHolder> implements Filterable {
    private final RecycleViewInterface recyclerViewInterface;
    private List<Recipe2> recipeList;
    List<Recipe2> recipeListAll;
    Context context;

    public RecycleViewAdapter2(List<Recipe2> recipeList, Context context, RecycleViewInterface recyclerViewInterface) {
        this.recipeList = recipeList;
        recipeListAll = new ArrayList<>(recipeList);
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void removeItem(int position) {
        recipeList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreRecipe(Recipe2 recipe, int position) {
        recipeList.add(position, recipe);
        notifyItemInserted(position);
    }

    @Override
    public Filter getFilter() {
        return search_Filter;
    }

    private Filter search_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Recipe2> filteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(recipeListAll);
            }
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(Recipe2 recipe : recipeListAll) {
                    if(recipe.getRecipeTitle().toLowerCase().contains(filterPattern) ||
                            recipe.getRecipeTag().toLowerCase().contains(filterPattern) ||
                            recipe.getRecipeText().toLowerCase().contains(filterPattern)) {
                        filteredList.add(recipe);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            recipeList.clear();
            recipeList.addAll((List) filterResults.values);
            notifyDataSetChanged();



        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_recipePic;
        TextView tv_recipeTitle;
        TextView tv_recipeTag;

        public MyViewHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);

            iv_recipePic = itemView.findViewById(R.id.iv_recipePicture);
            tv_recipeTitle = itemView.findViewById(R.id.tv_recipe_name);
            tv_recipeTag = itemView.findViewById(R.id.tv_recipe_tag);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            recycleViewInterface.onItemClick(pos);
                        }
                    }

                }
            });
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_recipe,parent,false);
        MyViewHolder holder = new MyViewHolder(view, recyclerViewInterface);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_recipeTitle.setText(recipeList.get(position).getRecipeTitle());
        holder.tv_recipeTag.setText(recipeList.get(position).getRecipeTag());
        Glide.with(this.context).load(recipeList.get(position).getRecipeImagePath()).into(holder.iv_recipePic);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

}