package com.appb.app.appb.ui.adapters;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appb.app.appb.R;
import com.appb.app.appb.custom.TextViewWithClickableSpan;
import com.appb.app.appb.data.File;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.ui.dialogs.AnswerDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by 1 on 31.03.2017.
 */

public class PostsAdapter extends BaseRVAdapterWithImages<PostsAdapter.VHPost> {

    private static final String ARROWS = ">>";

    private ArrayList<Post> posts;

    protected PostsAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @Override
    public VHPost onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new VHPost(v);
    }

    @Override
    public void onBindViewHolder(VHPost vh, int position) {
        super.onBindViewHolder(vh, position);
        Post post = posts.get(position);
        String postNum = String.format("%s%s", NUMBER_SYMBOL, post.getNum());
        String postText = post.getComment();

        vh.tvCommentDate.setText(post.getDate());
        vh.tvCommentNumber.setText(postNum);
        vh.tvPosition.setText(String.valueOf(position));
        vh.btnAnswers.setOnClickListener(getAnswersOnClickListener());

        if (TextUtils.isEmpty(postText)) {
            vh.tvTextComment.setVisibility(GONE);
        } else {
            vh.tvTextComment.setVisibility(VISIBLE);
            vh.tvTextComment.setSpannableText(Html.fromHtml(postText));
            vh.tvTextComment.setLinkClickListener(number -> {
                for (int i = 0; i < posts.size(); i++) {
                    if (posts.get(i).getNum() == number) {
                        PostsAdapter.this.onAnswerClick(posts, i);
                        break;
                    }
                }
            });
        }

        setVisibilityToAnswersButton(post, vh.btnAnswers, vh.tvAnswers);
    }

    private View.OnClickListener getAnswersOnClickListener() {
        //TODO: make expandable bar
        return null;
    }

    private void setVisibilityToAnswersButton(Post post, TextView tv, TextViewWithClickableSpan tvAnswers) {
        if (post.getAnswers().size() > 0) {
            tv.setTextColor(tv.getContext().getResources().getColor(R.color.colorPrimary));
            tv.setOnClickListener(getOpenAnswerClickListener(tvAnswers, post));
        } else {
            tv.setTextColor(tv.getContext().getResources().getColor(R.color.gray));

        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private void setTextToAnswers(Post post, TextViewWithClickableSpan tv) {
        tv.setVisibility(VISIBLE);
        ArrayList<Post> answers = post.getAnswers();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < answers.size(); i++) {
            stringBuilder
                    .append(ARROWS)
                    .append(answers.get(i).getNum())
                    .append(i == answers.size() - 1 ? "" : ", ");
        }

        tv.setSpannableText(stringBuilder.toString());


        //TODO: replace code below to onBindViewHolder
        tv.setLinkClickListener(number -> {
            for (int i = 0; i < answers.size(); i++) {
                if (answers.get(i).getNum() == number) {
                    new AnswerDialog(tv.getContext(), posts, i).show();
                    break;
                }
            }
        });

    }

    public void onAnswerClick(ArrayList<Post> posts, int index) {
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public ArrayList<File> getFiles(int pos) {
        return posts.get(pos).getFiles();
    }

    private View.OnClickListener getOpenAnswerClickListener(TextViewWithClickableSpan tv, Post post) {
        return (View v) -> {
            setTextToAnswers(post, tv);
        };
    }

    class VHPost extends VHImages {

        @BindView(R.id.tvCommentDate)
        TextView tvCommentDate;
        @BindView(R.id.tvCommentNumer)
        TextView tvCommentNumber;
        @BindView(R.id.tvTextComment)
        TextViewWithClickableSpan tvTextComment;
        @BindView(R.id.btnAnswers)
        TextView btnAnswers;
        @BindView(R.id.tvPosition)
        TextView tvPosition;
        @BindView(R.id.tvAnswers)
        TextViewWithClickableSpan tvAnswers;

        VHPost(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
