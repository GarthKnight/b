package com.appb.app.appb.mvp.views;

import com.appb.app.appb.data.Board;
import com.appb.app.appb.data.Boards;
import com.appb.app.appb.data.Category;
import com.arellomobile.mvp.MvpView;

import java.util.ArrayList;

/**
 * Created by Logvinov.sv on 13.06.2017.
 */

public interface BoardlistView extends MvpView {
    void  onError(String error);

}
