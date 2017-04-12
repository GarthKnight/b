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
import com.appb.app.appb.ui.dialogs.AnswersForPostDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by 1 on 31.03.2017.
 */

public class PostsAdapter extends BaseRVAdapterWithImages<PostsAdapter.VHPost> {

    private ArrayList<Post> posts;

    public PostsAdapter(ArrayList<Post> posts) {
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

        checkAnswers(vh.tvAnswers, position);
        vh.tvCommentDate.setText(posts.get(position).getDate());

        String postNum = String.format("%s%s", NUMBER_SYMBOL , posts.get(position).getNum());
        vh.tvCommentNumber.setText(postNum);

    }

    private void checkAnswers(TextView tvAnswers, int position) {
        final ArrayList<Integer> realPosts = getRealPostsFromAnswers(position);
        if (realPosts.size() != 0) {
            tvAnswers.setVisibility(VISIBLE);
            tvAnswers.setText(tvAnswers.getResources().getString(R.string.answers));
            tvAnswers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AnswersForPostDialog(v.getContext(), posts, realPosts).show();
                }
            });
        } else {
            tvAnswers.setVisibility(GONE);
        }
    }

    public void onAnswerClick(ArrayList<Post> posts, int index) {

    }


    public ArrayList<Integer> getRealPostsFromAnswers (int position) {
        ArrayList<Integer> realPostsFromAnswers = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getComment().contains(String.valueOf(posts.get(position).getNum()))) {
                realPostsFromAnswers.add(i);
            }
        }
        return realPostsFromAnswers;
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
        TextView tvAnswers;

        public VHPost(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
