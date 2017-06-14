package com.appb.app.appb.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.appb.app.appb.R;
import com.appb.app.appb.custom.TextViewWithClickableSpan;
import com.appb.app.appb.data.Post;
import com.appb.app.appb.ui.activities.PicViewerActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.appb.app.appb.ui.activities.PicViewerActivity.FILES;
import static com.appb.app.appb.ui.activities.PicViewerActivity.POS;

/**
 * Created by 1 on 31.03.2017.
 */

public class AnswersForPostDialog extends Dialog {

    private static final String ARROWS = ">>";

    private ArrayList<Post> posts;
    private ArrayList<Integer> indices;
    private String answers = "";

    @BindView(R.id.tvwcsAnswers)
    TextViewWithClickableSpan clickableTextView;

    public AnswersForPostDialog(Context context, ArrayList<Post> posts, ArrayList<Integer> indices) {
        super(context);
        this.posts = posts;
        this.indices = indices;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setContentView(R.layout.dialog_answers);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(answers);
        for (int i = 0; i < indices.size(); i++) {

            stringBuilder.append(ARROWS).append(posts.get(indices.get(i)).getNum())
                    .append(i == indices.size() - 1 ? "" : ", ");
//            answers = answers + (ARROWS + posts.get(indices.get(i)).getNum() + (i == indices.size() - 1 ? "" : ", "));
        }
        answers = stringBuilder.toString();
        clickableTextView.setSpannableText(answers);
        clickableTextView.setLinkClickListener(number -> {

            int tmp = -1;

            for (int i = 0; i < posts.size(); i++) {
                if (posts.get(i).getNum() == number) {
                    tmp = i;
                }
            }

            if (tmp != -1) {
                AnswerDialog answerDialog = new AnswerDialog(getContext(), posts, tmp){
                    @Override
                    public void onItemClick(View v, int position, int pos) {
                        startPicViewerActivity(position, pos);
                    }
                };
                answerDialog.show();
            }
        });
    }

    private void startPicViewerActivity(int position, int pos) {
        Intent intent = new Intent(getContext(), PicViewerActivity.class);
        intent.putExtra(FILES, posts.get(position).getFiles());
        intent.putExtra(POS, pos);
        getContext().startActivity(intent);
    }


}
