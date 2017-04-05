package com.appb.app.appb.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appb.app.appb.R;
import com.appb.app.appb.data.File;
import com.appb.app.appb.fragments.BaseFragment;
import com.appb.app.appb.fragments.PictureFragment;
import com.appb.app.appb.fragments.WebmFragment;

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
