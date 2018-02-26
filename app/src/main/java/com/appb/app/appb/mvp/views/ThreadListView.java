package com.appb.app.appb.mvp.views;

import com.appb.app.appb.data.Post;
import com.appb.app.appb.data.Thread;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.ArrayList;

/**
 * Created by Logvinov.sv on 09.06.2017.
 */

public interface ThreadListView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void onThreadsLoaded(ArrayList<Thread> threads);

    @StateStrategyType(SkipStrategy.class)
    void  onError(String errorMessage);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void onLoadingEnd();


}
