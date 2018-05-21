package com.google.firebase.quickstart.database.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class PostByCategory extends PostListFragment {
    private String Category;

    public PostByCategory() {
        setCategory("");
    }


    public void setCategory(String category) {
        Category = category;
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        return databaseReference.child("posts").orderByChild("category").equalTo(Category);
    }
}
