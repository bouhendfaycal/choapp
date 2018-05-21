package com.google.firebase.quickstart.database.viewholder;

import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.*;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.quickstart.database.R;
import com.google.firebase.quickstart.database.models.Post;


public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public ImageView imgView;
    private WebView webView;

    public TextView numStarsView;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.post_title);
        //authorView = itemView.findViewById(R.id.post_author);
        imgView = itemView.findViewById(R.id.post_img);
        webView = itemView.findViewById(R.id.post_webview);
        starView = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.post_num_stars);


    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        //authorView.setText(post.author);
        Glide.with(imgView.getContext()).load(post.img).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher).centerCrop()).into(imgView);
        numStarsView.setText(String.valueOf(post.starCount));

        webView.setVisibility(View.GONE);

        starView.setOnClickListener(starClickListener);
    }
}
