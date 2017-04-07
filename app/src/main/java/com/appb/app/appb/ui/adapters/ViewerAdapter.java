package com.appb.app.appb.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.appb.app.appb.data.File;
import com.appb.app.appb.ui.fragments.PictureFragment;
import com.appb.app.appb.ui.fragments.WebmFragment;

import java.util.ArrayList;

/**
 * Created by 1 on 26.03.2017.
 */

public class ViewerAdapter extends FragmentPagerAdapter {

    ArrayList<File> files;

    public ViewerAdapter(ArrayList<File> files, FragmentManager fm) {
        super(fm);
        this.files = files;
    }

    @Override
    public Fragment getItem(int position) {
        final int pos = position;

        String tmp = (files.get(position).getName());
        String url = "http://2ch.hk" + (files.get(position).getPath());
        String thubmUrl = "http://2ch.hk" + (files.get(position).getThumbnail());

        if (tmp.toLowerCase().contains(".webm")) {
            return WebmFragment.newInstance(url, thubmUrl);
        } else {
            return PictureFragment.newInstance(url);
        }
    }

    @Override
    public int getCount() {
        return files.size();
    }

}
