package com.appb.app.appb.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.appb.app.appb.api.API;
import com.appb.app.appb.data.DvachMediaFile;
import com.appb.app.appb.ui.fragments.PictureFragment;
import com.appb.app.appb.ui.fragments.WebmFragment;

import java.util.ArrayList;

/**
 * Created by 1 on 26.03.2017.
 */

public class ViewerAdapter extends FragmentPagerAdapter {

    private static final String WEBM = ".webm";

    private ArrayList<DvachMediaFile> dvachMediaFiles;

    public ViewerAdapter(ArrayList<DvachMediaFile> dvachMediaFiles, FragmentManager fm) {
        super(fm);
        this.dvachMediaFiles = dvachMediaFiles;
    }

    @Override
    public Fragment getItem(int position) {

        String fileName = dvachMediaFiles.get(position).getName();
        String fileUrl = API.URL + dvachMediaFiles.get(position).getPath();
        String thumbUrl = API.URL + dvachMediaFiles.get(position).getThumbnail();

        if (fileName.toLowerCase().contains(WEBM)) {
            return WebmFragment.newInstance(fileUrl, thumbUrl);
        } else {
            return PictureFragment.newInstance(fileUrl);
        }
    }

    @Override
    public int getCount() {
        return dvachMediaFiles.size();
    }

}
