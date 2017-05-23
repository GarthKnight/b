package com.appb.app.appb.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appb.app.appb.R;
import com.appb.app.appb.custom.TextViewWithClickableSpan;
import com.appb.app.appb.data.File;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.ui.activities.PicViewerActivity;
import com.appb.app.appb.ui.dialogs.AnswerDialog;
import com.appb.app.appb.ui.dialogs.AnswersForPostDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.appb.app.appb.ui.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.ui.activities.PicViewerActivity.POS;

/**
 * Created by 1 on 31.03.2017.
 */

public class PostsAdapter extends BaseRVAdapterWithImages<PostsAdapter.VHPost> {

    private ArrayList<Post> posts;
    private String answers = "";
    private static final String ARROWS = ">>";
    Context context;

    public PostsAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public VHPost onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new VHPost(v);
    }

    @Override
    public void onBindViewHolder(VHPost vh, int position) {
        super.onBindViewHolder(vh, position);

        answers = "";

        String postText = posts.get(position).getComment();
        if (TextUtils.isEmpty(postText)) {
            vh.tvTextComment.setVisibility(GONE);
        } else {
            vh.tvTextComment.setVisibility(VISIBLE);
            vh.tvTextComment.setSpannableText(Html.fromHtml(postText));
            vh.tvTextComment.setLinkClickListener(new TextViewWithClickableSpan.LinkClickListener() {
                @Override
                public void onLinkClick(int number) {
                    for (int i = 0; i < posts.size(); i++) {
                        if (posts.get(i).getNum() == number) {
                            onAnswerClick(posts, i);
                            break;
                        }
                    }
                }
            });
        }

//        checkAnswers(vh.tvAnswers, position);


        vh.tvCommentDate.setText(posts.get(position).getDate());

        String postNum = String.format("%s%s", NUMBER_SYMBOL, posts.get(position).getNum());
        vh.tvCommentNumber.setText(postNum);
        vh.tvPosition.setText(String.valueOf(position));

    }

//    private void checkAnswers(TextViewWithClickableSpan tvAnswers, int position) {
//        final ArrayList<Integer> realPosts = getRealPostsFromAnswers(position);
//        if (realPosts.size() != 0) {
//            if (realPosts.size() < 4) {
//                simpleAnswers(tvAnswers, position, realPosts);
//                Log.d("checkansss", "simpleans: " + realPosts.size() + " pos: " + position);
//            } else {
//                answersByButton(tvAnswers, position, realPosts);
//                Log.d("checkansss", "ansByBut" + realPosts);
//            }
//        } else {
//            tvAnswers.setVisibility(GONE);
//        }
//
//    }
//
//    private void simpleAnswers(TextViewWithClickableSpan tvAnswers, final int position, final ArrayList<Integer> realPosts) {
//
//        tvAnswers.setVisibility(VISIBLE);
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(answers);
//        for (int i = 0; i < realPosts.size(); i++) {
//
//            stringBuilder.append(ARROWS).append(posts.get(realPosts.get(i)).getNum())
//                    .append(i == realPosts.size() - 1 ? "" : ", ");
//        }
//        answers = stringBuilder.toString();
//        tvAnswers.setSpannableText(answers);
//
//
//        tvAnswers.setLinkClickListener(new TextViewWithClickableSpan.LinkClickListener() {
//            @Override
//            public void onLinkClick(int number) {
//                int tmp = -1;
//
//                for (int i = 0; i < posts.size(); i++) {
//                    if (posts.get(i).getNum() == number) {
//                        tmp = i;
//                    }
//                }
//
//                Log.d("SEI", "realPosts: " + realPosts + " position: " + position);
//
//                if (tmp != -1) {
//                    AnswerDialog answerDialog = new AnswerDialog(context, posts, tmp) {
//                        @Override
//                        public void onItemClick(View v, int position, int pos) {
//                            startPicViewerActivity(position, pos);
//                        }
//                    };
//                    answerDialog.show();
//                }
//            }
//        });
//
//
//    }
//
//    private void answersByButton(TextViewWithClickableSpan tvAnswers, final int position, final ArrayList<Integer> realPosts) {
//        tvAnswers.setVisibility(VISIBLE);
//        tvAnswers.setText(tvAnswers.getResources().getString(R.string.answers));
//        tvAnswers.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("SEI", "realPostsWithButton: " + realPosts + " position: " + position);
//                new AnswersForPostDialog(v.getContext(), posts, realPosts).show();
//            }
//        });
//
//    }


    public void onAnswerClick(ArrayList<Post> posts, int index) {

    }


    public ArrayList<Integer> getRealPostsFromAnswers(int position) {
        ArrayList<Integer> realPostsFromAnswers = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            if (Html.fromHtml(posts.get(i).getComment()).toString().contains(String.valueOf(posts.get(position).getNum()))) {
                realPostsFromAnswers.add(i);
                Log.d("SHU", "getRealPostsFromAnswers: " + Html.fromHtml(posts.get(i).getComment()) + " number: " + posts.get(position).getNum());
            }
        }
        return realPostsFromAnswers;
    }

    private void startPicViewerActivity(int position, int pos) {
        Intent intent = new Intent(context, PicViewerActivity.class);
        intent.putExtra(FILES, posts.get(position).getFiles());
        intent.putExtra(POS, pos);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public ArrayList<File> getFiles(int pos) {
        return posts.get(pos).getFiles();
    }

    public class VHPost extends VHImages {

        @BindView(R.id.tvCommentDate)
        TextView tvCommentDate;
        @BindView(R.id.tvCommentNumer)
        TextView tvCommentNumber;
        @BindView(R.id.tvTextComment)
        TextViewWithClickableSpan tvTextComment;
        @BindView(R.id.tvAnswers)
        TextViewWithClickableSpan tvAnswers;
        @BindView(R.id.tvPosition)
        TextView tvPosition;

        public VHPost(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
