package com.google.firebase.quickstart.database;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.firebase.quickstart.database.fragment.PostByCategory;

public class DocActivity extends DrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_docs);

        super.setDrawer(false);

        int MenuItem = getIntent().getIntExtra("MenuItem", 0);

        LinearLayout contentFrameLayout = (LinearLayout) findViewById(R.id.content); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_docs, contentFrameLayout);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        setTitle(R.string.docs);

        PostByCategory fragment = new PostByCategory();

        fragment.setCategory("docs");
        setTitle(R.string.docs);

        /*switch (MenuItem){

            case (R.id.menu_docs): fragment.setCategory("docs");setTitle(R.string.docs);break;
            case (R.id.menu_advice): fragment.setCategory("advice");setTitle(R.string.advice);break;
            case (R.id.menu_about): fragment.setCategory("about");setTitle(R.string.about);break;

        }*/

        fragmentTransaction.add(R.id.post_list_container, fragment);
        fragmentTransaction.commit();
    }
}
