package com.appb.app.appb.ui.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.appb.app.appb.R;
import com.appb.app.appb.api.API;
import com.appb.app.appb.data.DvachMediaFile;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.mvp.presenters.PostListPresenter;
import com.appb.app.appb.mvp.views.PostListView;
import com.appb.app.appb.ui.activities.PicViewerActivity;
import com.appb.app.appb.ui.activities.ThreadsListActivity;
import com.appb.app.appb.ui.adapters.PostsAdapter;
import com.appb.app.appb.ui.dialogs.AnswerDialog;
import com.appb.app.appb.utils.ViewUtils;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;
import com.vk.sdk.dialogs.VKShareDialogBuilder;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.appb.app.appb.ui.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.ui.activities.PicViewerActivity.POS;

/**
 * Created by 1 on 20.03.2017.
 */

public class PostListFragment extends BaseFragment implements PostListView {

    private static final String POSTS = "posts";
    private static final String THREAD_NUMBER = "num";
    private static final String BOARD = "board";
    private static final String TAG = "PostListFragment";

    private PostsAdapter postsAdapter;
    private ArrayList<Post> posts = new ArrayList<>();

    @BindView(R.id.rvPosts)
    RecyclerView rvPosts;
    @BindView(R.id.pbPosts)
    ProgressBar pbPosts;

    @InjectPresenter
    PostListPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            posts = savedInstanceState.getParcelable(POSTS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post_list, container, false);
        bindUI(v);
        return v;
    }

    @Override
    public void init() {
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        initAdapter();

        if (posts.size() == 0) {
            loadPosts();
        }
        ViewUtils.makeGone(((ThreadsListActivity) getActivity()).btnStar);
    }


    private void loadPosts() {
        int threadNumber = getArguments().getInt(THREAD_NUMBER);
        String board = getArguments().getString(BOARD);
        presenter.loadPosts(threadNumber, board);
    }

    public void initAdapter() {
        postsAdapter = new PostsAdapter(posts) {

            @Override
            public void onThumbnailClick(int position, ArrayList<DvachMediaFile> dvachMediaFiles) {
                super.onThumbnailClick(position, dvachMediaFiles);
                startPicViewerActivity(dvachMediaFiles, position);
            }

            @Override
            public void onAnswerClick(ArrayList<Post> posts, Post answer) {
                AnswerDialog answerDialog = new AnswerDialog((getContext()), posts, answer) {
                    @Override
                    public void onThumbnailClick(int position, ArrayList<DvachMediaFile> files) {
                        super.onThumbnailClick(position, files);
                        startPicViewerActivity(files, position);
                    }
                };
                answerDialog.show();
            }

            @Override
            public void onShareClick(Post post) {
                super.onShareClick(post);
                if (VKSdk.isLoggedIn()) {
                    downloadBitmaps(post)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<ArrayList<Bitmap>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(ArrayList<Bitmap> bitmaps) {
                                    VKUploadImage[] vkUploadImages = new VKUploadImage[bitmaps.size()];
                                    for (int i = 0; i < bitmaps.size(); i++) {
                                        vkUploadImages[i] = new VKUploadImage(bitmaps.get(i), VKImageParameters.pngImage());
                                    }
                                    showDialog(vkUploadImages, post);
                                }
                            });
                } else {
                    VKSdk.login(getActivity(), VKScope.PHOTOS, VKScope.WALL);
                }


            }
        };

        rvPosts.setAdapter(postsAdapter);
    }

    private Observable<ArrayList<Bitmap>> downloadBitmaps(Post post) {
        return Observable.create(subscriber -> {
            ArrayList<Bitmap> bitmaps = new ArrayList<>();
            for (int i = 0; i < post.getDvachMediaFiles().size(); i++) {
                try {
                    String fileUrl = API.URL + post.getDvachMediaFiles().get(i).getPath();
                    Bitmap bitmap = Glide.with(getContext()).load(fileUrl).asBitmap().into(-1, -1).get();
                    bitmaps.add(bitmap);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            subscriber.onNext(bitmaps);
        });
    }

    private void showDialog(VKUploadImage[] vkUploadImages, Post post) {
        VKShareDialogBuilder builder = new VKShareDialogBuilder();
        builder.setText(Html.fromHtml(post.getComment()));
        if (vkUploadImages.length > 0) {
            builder.setAttachmentImages(vkUploadImages);
        }
        builder.setAttachmentLink("Сосач",
                API.URL);
        builder.setShareDialogListener(new VKShareDialogBuilder.VKShareDialogListener() {
            @Override
            public void onVkShareComplete(int postId) {
                Log.d(TAG, "onVkShareComplete: " + String.valueOf(postId));
            }

            @Override
            public void onVkShareCancel() {
                Log.d(TAG, "onVkShareCancel");
            }

            @Override
            public void onVkShareError(VKError error) {
                Log.d(TAG, "onVkShareError: " + error.errorMessage);
            }
        });
        builder.show(getChildFragmentManager(), "VK_SHARE_DIALOG");
    }

    private void startPicViewerActivity(ArrayList<DvachMediaFile> dvachMediaFiles, int pos) {
        Intent intent = new Intent(getContext(), PicViewerActivity.class);
        intent.putExtra(FILES, dvachMediaFiles);
        intent.putExtra(POS, pos);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // Пользователь успешно авторизовался
            }

            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        }))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(POSTS, Parcels.wrap(posts));
    }

    public static PostListFragment create(int num, String board) {
        Bundle args = new Bundle();
        args.putInt(THREAD_NUMBER, num);
        args.putString(BOARD, board);
        PostListFragment fragment = new PostListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPostsLoaded(ArrayList<Post> _posts) {
        posts.clear();
        posts.addAll(_posts);
        postsAdapter.notifyDataSetChanged();
        pbPosts.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        ViewUtils.makeVisible(((ThreadsListActivity) getActivity()).btnStar);
        super.onDestroyView();
    }

    @Override
    public void onError(String errorMessage) {
        pbPosts.setVisibility(View.GONE);
        Snackbar snackbar = Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onLoadingEnd() {
        pbPosts.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingStart() {

    }
}

