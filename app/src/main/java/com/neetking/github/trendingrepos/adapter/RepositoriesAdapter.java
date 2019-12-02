package com.neetking.github.trendingrepos.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neetking.github.trendingrepos.R;
import com.neetking.github.trendingrepos.model.Repository;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by neetking on 02/12/2019.
 */

public class RepositoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Repository> repositories;

    public RepositoriesAdapter(List<Repository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_repository, parent, false);

        return new RepositoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                final RepositoryViewHolder RepositoryVH = (RepositoryViewHolder) holder;
                RepositoryVH.name.setText(repositories.get(position).getmName());
                RepositoryVH.description.setText(repositories.get(position).getmDescription());
                float starsNumber = repositories.get(position).getmStarsNumber(); // return the number of stars devided by 1000
                if (starsNumber > 1000) starsNumber /= 1000;
                repositories.get(position).setmStarsNumber(starsNumber);
                // get only the first digit afrer comma and then append the value with 'k'
                RepositoryVH.stars.setText(new DecimalFormat("##.#").format(starsNumber) + "k");
                RepositoryVH.username.setText(repositories.get(position).getOwner().getLogin());
                Picasso.get().load(repositories.get(position).getOwner()
                        .getAvatar_url()).resize(200, 200).centerCrop().onlyScaleDown()
                        .into(RepositoryVH.avatar);

    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }


    public static class RepositoryViewHolder extends  RecyclerView.ViewHolder {
        TextView name,description,username,stars;
        ImageView avatar;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            username = itemView.findViewById(R.id.username);
            avatar = itemView.findViewById(R.id.avatar);
            stars = itemView.findViewById(R.id.stars);
        }
    }


}
