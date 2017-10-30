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
import java.util.HashMap;

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
    private String answersText = "";
    private static final String ARROWS = ">>";


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

        answersText = "";
        vh.tvAnswers.setVisibility(VISIBLE);

        String postText = posts.get(position).getComment();
        if (TextUtils.isEmpty(postText)) {
            vh.tvTextComment.setVisibility(GONE);
        } else {
            vh.tvTextComment.setVisibility(VISIBLE);
            vh.tvTextComment.setSpannableText(Html.fromHtml(postText));
            vh.tvTextComment.setLinkClickListener(number -> {
                for (int i = 0; i < posts.size(); i++) {
                    if (posts.get(i).getNum() == number) {
                        onAnswerClick(posts, i);
                        break;
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

//    private void setAnswers(TextViewWithClickableSpan tvAnswers, int position) {
//
//        ArrayList<Integer> realAnswers = answers.get(posts.get(position).getNum());
//
//        if (realAnswers.size() > 0) {
//            tvAnswers.setVisibility(VISIBLE);
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append(answersText);
//            for (int i = 0; i < realAnswers.size(); i++) {
//                stringBuilder.append(ARROWS).append(realAnswers.get(i))
//                        .append(i == realAnswers.size() - 1 ? "" : ", ");
//            }
//            answersText = stringBuilder.toString();
//            tvAnswers.setSpannableText(answersText);
//
//            tvAnswers.setLinkClickListener(number -> {
//                for (int i = 0; i < posts.size(); i++) {
//                    if (posts.get(i).getNum() == number) {
//                        new AnswerDialog(tvAnswers.getContext(), posts, i).show();
//                        break;
//                    }
//                }
//
//            });
//
//        }
//
//
//    }

    public void onPictureClick(int position, ArrayList<File> files) {

    }

    //
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
    private void simpleAnswers(TextViewWithClickableSpan tvAnswers, final int position) {

        ArrayList<Integer> answersNumbers = getAnswersNumbers(position);
        Context context = tvAnswers.getContext();
        if (answersNumbers != null){

            tvAnswers.setVisibility(VISIBLE);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(answersText);

            for (int i = 0; i < answersNumbers.size(); i++) {

                stringBuilder
                        .append(ARROWS)
                        .append(posts.get(answersNumbers.get(i)).getNum())
                        .append(i == answersNumbers.size() - 1 ? "" : ", ");
            }

            answersText = stringBuilder.toString();
            tvAnswers.setSpannableText(answersText);
            tvAnswers.setLinkClickListener(number -> {


            });

        } else {
            tvAnswers.setVisibility(GONE);
        }
    }

    private void startPicViewerActivity(ArrayList<File> files, int pos, Context context) {
        Intent intent = new Intent(context, PicViewerActivity.class);
        intent.putExtra(FILES, files);
        intent.putExtra(POS, pos);
        context.startActivity(intent);
    }

    private TextViewWithClickableSpan.LinkClickListener getLinkClickListener(Context context){
        return new TextViewWithClickableSpan.LinkClickListener() {
            @Override
            public void onLinkClick(int number) {

                int answerPostNumber = -1;

                for (int i = 0; i < posts.size(); i++) {
                    if (posts.get(i).getNum() == number) {
                        answerPostNumber = i;
                    }
                }

                if (answerPostNumber != -1) {
                    AnswerDialog answerDialog = new AnswerDialog(context, posts, answerPostNumber) {
                        @Override
                        public void onThumbnailClick(int position, ArrayList<File> files) {
                            super.onThumbnailClick(position, files);
                            startPicViewerActivity(files, position, context);
                        }
                    };
                    answerDialog.show();
                }

            }
        };
    }

    private ArrayList<Integer> getAnswersNumbers(int position) {

        ArrayList<Integer> answersNumber = new ArrayList<>();
        ArrayList<Post> answers = posts.get(position).getAnswers();
        for (Post answer : answers) {
            answersNumber.add(answer.getNum());
        }

        if (answersNumber.size() > 0) {
            return answersNumber;
        } else {
            return null;
        }
    }

    private void getAnswers() {
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

    class VHPost extends VHImages {

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

        VHPost(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
