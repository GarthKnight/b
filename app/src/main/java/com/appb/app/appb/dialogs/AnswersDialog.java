package com.appb.app.appb.dialogs;

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
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 1 on 31.03.2017.
 */

public class AnswersDialog extends Dialog {

    private Unbinder unbinder;
    ArrayList<Post> posts;
    ArrayList<Integer> indices;
    String answers = "";
    Context context;


    @BindView(R.id.tvwcsAnswers)
    TextViewWithClickableSpan tvwcsAnswers;

    public AnswersDialog(Context context, ArrayList<Post> posts, ArrayList<Integer> indices) {
        super(context);
        this.context = context;
        this.posts = posts;
        this.indices = indices;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_answers);
        unbinder = ButterKnife.bind(this);
        init();
    }

    public void init() {
        for (int i = 0; i < indices.size(); i++) {
            answers = answers + (">>" + posts.get(indices.get(i)).getNum() + (i == indices.size() - 1 ? "" : ", "));
        }
        tvwcsAnswers.setSpannableText(answers);
        tvwcsAnswers.setLinkListener(new TextViewWithClickableSpan.LinkClickListener() {
            @Override
            public void onLinkClick(int number) {
                int tmp = -1;

                for (int i = 0; i < posts.size(); i++) {
                    if (posts.get(i).getNum() == number) {
                        tmp = i;
                    }
                }

                if (tmp != -1) {
                    AnswerDialog answerDialog = new AnswerDialog(context, posts, tmp);
                    answerDialog.show();
                }
            }
        });
    }


}
