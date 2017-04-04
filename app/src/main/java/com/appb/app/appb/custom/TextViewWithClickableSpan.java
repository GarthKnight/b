package com.appb.app.appb.custom;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 1 on 22.03.2017.
 */

public class TextViewWithClickableSpan extends TextView {

    static String TAG = "YOBA";
    private LinkClickListener linkListener;


    public TextViewWithClickableSpan(Context context) {
        super(context);
    }

    public TextViewWithClickableSpan(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewWithClickableSpan(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLinkListener(LinkClickListener linkListener) {
        this.linkListener = linkListener;
    }

    public void setSpannableText(CharSequence textChars) {
        //// TODO: 22.03.2017
        String text = String.valueOf(textChars);
        ArrayList<Integer> indicesNum = new ArrayList<>();
        SpannableString ss = new SpannableString(text);

        int counter = text.indexOf(">>");

        if (counter != -1) {
            indicesNum.add(counter);
        }

        while (counter != -1) {
            Log.d(TAG, "counter: " + counter);
            counter = text.indexOf(">>", counter + 1);
            if (counter != -1) {
                indicesNum.add(counter);

            }

        }



        for (int i = 0; i < indicesNum.size(); i++) {
                NumberClickableSpan span = new NumberClickableSpan(text.substring(indicesNum.get(i) + 2, indicesNum.get(i) + 11)) {
                    @Override
                    public void onNumberClick(String nubmer) {
                        linkListener.onLinkClick(Integer.valueOf(nubmer));
                        Log.d(TAG, "onNumberClick: " + nubmer);
                    }
                };

//            if(text.substring(indicesNum.get(i), indicesNum.get(i)+16).contains("OP")){
//                ss.setSpan(span, indicesNum.get(i), indicesNum.get(i) + 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            } else {
                ss.setSpan(span, indicesNum.get(i), indicesNum.get(i) + 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//            }

        }

        super.setText(ss);
        setMovementMethod(LinkMovementMethod.getInstance());
    }



    private class NumberClickableSpan extends ClickableSpan {

        public String nubmer;

        public NumberClickableSpan(String number) {
            this.nubmer = number;
        }

        @Override
        public void onClick(View widget) {
            onNumberClick(nubmer);
        }

        public void onNumberClick(String nubmer) {

        }
    }


    public interface LinkClickListener {
        void onLinkClick(int number);
    }
}
