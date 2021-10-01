package com.example.media.Pager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.media.Directory;
import com.example.media.List.Item;
import com.example.media.Lists;

import java.util.ArrayList;


public class ViewPagerAdapter  extends FragmentStateAdapter {

    public Lists lists;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, Lists lists) {
        super(fragmentActivity);
        this.lists=lists;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                Bundle bundle=new Bundle();
                bundle.putParcelable("data",lists.getSongs());

                AllSongsFragment allSongsFragment =new AllSongsFragment();

                allSongsFragment.setArguments(bundle);

                return allSongsFragment;


        }
        return new SongListFragment();
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    /*public ViewPagerAdapter(MainActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TestFragment();
            case 1:
                return new AllSongsFragment();
            case 2:
                return new SongListFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }*/
}
