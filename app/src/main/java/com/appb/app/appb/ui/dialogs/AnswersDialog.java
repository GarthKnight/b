package com.appb.app.appb.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.appb.app.appb.R;
import com.appb.app.appb.custom.TextViewWithClickableSpan;
import com.appb.app.appb.data.Post;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 1 on 31.03.2017.
 */

public class AnswersDialog extends Dialog {

    private static final String ARROWS = ">>";

    private ArrayList<Post> posts;
    private ArrayList<Integer> indices;
    private String answers = "";

    @BindView(R.id.tvwcsAnswers)
    TextViewWithClickableSpan clickableTextView;

    public AnswersDialog(Context context, ArrayList<Post> posts, ArrayList<Integer> indices) {
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
        init();
    }

    public void init() {
        for (int i = 0; i < indices.size(); i++) {
            new StringBuilder(answers)
                    .append(ARROWS + posts.get(indices.get(i)).getNum())
                    .append(i == indices.size() - 1 ? "" : ", ");
//            answers = answers + (ARROWS + posts.get(indices.get(i)).getNum() + (i == indices.size() - 1 ? "" : ", "));
        }
        clickableTextView.setSpannableText(answers);
        clickableTextView.setLinkClickListener(new TextViewWithClickableSpan.LinkClickListener() {
            @Override
            public void onLinkClick(int number) {

                int tmp = -1;

                for (int i = 0; i < posts.size(); i++) {
                    if (posts.get(i).getNum() == number) {
                        tmp = i;
                    }
                }

                if (tmp != -1) {
                    AnswerDialog answerDialog = new AnswerDialog(getContext(), posts, tmp);
                    answerDialog.show();
                }
            }
        });
    }


}
