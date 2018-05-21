package com.google.firebase.quickstart.database.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class LikedPostsFragment extends PostListFragment {

    public LikedPostsFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("posts").orderByChild("stars/" + getUid()).equalTo(true);
    }
}
