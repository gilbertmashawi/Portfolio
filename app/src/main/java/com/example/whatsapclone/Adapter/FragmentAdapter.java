package com.example.whatsapclone.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsapclone.Fragments.callsFragment;
import com.example.whatsapclone.Fragments.chatFragment;
import com.example.whatsapclone.Fragments.statusFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {

        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new chatFragment();
            case 1:return  new statusFragment();
            case 2:return new callsFragment();
            default:return new chatFragment();

        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title=null;
        if (position==0){
            title="Chats";
        }
        if (position==1){
            title="Status";
        }

        if (position==2){
            title="calls";
        }
        return title;
    }
}
