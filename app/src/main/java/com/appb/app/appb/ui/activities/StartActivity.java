package com.appb.app.appb.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.appb.app.appb.R;
import com.appb.app.appb.data.Board;
import com.appb.app.appb.data.File;
import com.appb.app.appb.mvp.presenters.BoardsListPresenter;
import com.appb.app.appb.mvp.views.BoardlistView;
import com.appb.app.appb.ui.adapters.BoardListAdapter;
import com.appb.app.appb.ui.fragments.ThreadListFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.ArrayList;

import butterknife.BindView;

import static com.appb.app.appb.ui.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.ui.activities.PicViewerActivity.POS;

public class StartActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, BoardlistView {

    @BindView(R.id.rvBoards)
    RecyclerView rvBoard;
    BoardListAdapter boardListAdapter;
    ArrayList<Board> boards = new ArrayList<>();
    ArrayList<String> boardsNames = new ArrayList<>();

    @InjectPresenter
    BoardsListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        log("Start Activity onCreate");
        bindUI(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void init() {
        if (boards.size() == 0){
            loadBoardsRX();
        }
        getBoardsNames();
    }

    private void loadBoardsRX(){
        presenter.getBoards();
    }


    private void initRV(ArrayList<Board> different) {
        rvBoard.setLayoutManager(new LinearLayoutManager(this));
        boardListAdapter = new BoardListAdapter(different){
            @Override
            public void onBoardClick(String board) {
                super.onBoardClick(board);
               addFragment(ThreadListFragment.create(board), true);
            }
        };
        rvBoard.setAdapter(boardListAdapter);
    }

    public void getBoardsNames(){
        presenter.getBoardsNames();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBoardsLoaded(ArrayList<Board> _boards) {
        boards = _boards;
        initRV(boards);
    }

    @Override
    public void getBoardsNames(ArrayList<String> _boardsNames) {
        boardsNames = _boardsNames;
    }

    @Override
    public void onError(String error) {
        log("onFailure: " + error);

    }

    public static void openImages(Context c, ArrayList<File> files, int pos) {
        Intent intent = new Intent(c, PicViewerActivity.class);
        intent.putExtra(FILES, files);
        intent.putExtra(POS, pos);
        c.startActivity(intent);
    }
}
