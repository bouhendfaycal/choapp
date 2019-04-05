
package com.google.firebase.quickstart.database;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mxn on 2016/12/13.
 * MenuListFragment
 */

public class MenuListFragment extends Fragment {

    private ImageView ivMenuUserProfilePhoto;
    private TextView UserDisplayName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,
                false);


        LayoutInflater.from(getContext()).inflate(R.layout.view_global_menu_header, container);


        ivMenuUserProfilePhoto = (ImageView) container.findViewById(R.id.ivMenuUserProfilePhoto);
        UserDisplayName = (TextView) container.findViewById(R.id.user_display_name);

        NavigationView vNavigation = (NavigationView) view.findViewById(R.id.vNavigation);
        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //menu items that uses Post List Activity
                List<Integer> MenuItems = Arrays.asList(
                        R.id.menu_docs,
                        R.id.menu_advice,
                        R.id.menu_about
                );

                Intent in = new Intent();

                switch (menuItem.getItemId()) {
                    case (R.id.menu_index):
                        in = new Intent(getActivity(), MainActivity.class);
                        break;
                    case (R.id.menu_mesures):
                        in = new Intent(getActivity(), EntryListActivity.class);
                        break;
                    case (R.id.menu_docs):
                        in = new Intent(getActivity(), DocActivity.class);
                        break;
                    case (R.id.menu_advice):
                        in = new Intent(getActivity(), AdviceActivity.class);
                        break;
                    case (R.id.menu_survey):
                        in = new Intent(getActivity(), SurveyActivity.class);
                        break;
                    case (R.id.menu_alarm):
                        in = new Intent(getActivity(), AlarmListActivity.class);
                        break;
                    case (R.id.menu_note):
                        in = new Intent(getActivity(), NotesActivity.class);
                        break;
                    case (R.id.menu_call):
                        in = new Intent(getActivity(), CallActivity.class);
                        break;
                    case (R.id.menu_about):
                        in = new Intent(getActivity(), AboutActivity.class);
                        break;
                }

                ((DrawerActivity) getActivity()).CloseDrawer();
                startActivity(in);

                /*if(MenuItems.contains(menuItem.getItemId())) {
                    Toast.makeText(getActivity(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                }*/
                return false;
            }
        });
        setupHeader();
        return view;
    }

    private void setupHeader() {
        UserDisplayName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        //FirebaseAuth.getInstance().getCurrentUser().getDisplayName()
        int avatarSize = getResources().getDimensionPixelSize(R.dimen.global_menu_avatar_size);
        //String profilePhoto = getResources().getString(R.string.user_profile_photo);
        String profilePhoto = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
        Picasso.with(getActivity())
                .load(profilePhoto)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(ivMenuUserProfilePhoto);
    }

}
