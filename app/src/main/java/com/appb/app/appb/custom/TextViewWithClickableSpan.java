package com.appb.app.appb.custom;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by 1 on 22.03.2017.
 */

public class TextViewWithClickableSpan extends android.support.v7.widget.AppCompatTextView {

    private final static String TAG = "YOBA";
    private static final int ANSWER_NUMBER_LENGTH = 11;
    private static final int ARROWS_LENGTH = 2;
    private static final String ARROWS = ">>";

    private LinkClickListener linkClickListener;

    public TextViewWithClickableSpan(Context context) {
        super(context);
    }

    public TextViewWithClickableSpan(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewWithClickableSpan(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLinkClickListener(LinkClickListener linkClickListener) {
        this.linkClickListener = linkClickListener;
    }

    public void setSpannableText(CharSequence textChars) {

        ArrayList<Integer> answersLinkArray = new ArrayList<>();

        String text = String.valueOf(textChars);
        SpannableString ss = new SpannableString(text);

        int startOfAnswerLink = text.indexOf(ARROWS);

        if (startOfAnswerLink != -1) {
            answersLinkArray.add(startOfAnswerLink);
        }

        while (startOfAnswerLink != -1) {

            Log.d(TAG, "counter: " + startOfAnswerLink);

            startOfAnswerLink = text.indexOf(ARROWS, startOfAnswerLink + 1);
            if (startOfAnswerLink != -1) {
                answersLinkArray.add(startOfAnswerLink);
            }
        }

        for (int i = 0; i < answersLinkArray.size(); i++) {

            int startOfAnswerNumber = answersLinkArray.get(i) + ARROWS_LENGTH;
//            int endOfAnswerNumber = answersLinkArray.get(i) + ANSWER_NUMBER_LENGTH;
            int endOfAnswerNumber = getEndOfAnswer(answersLinkArray.get(i), text);

            String answerNumber = text.substring(startOfAnswerNumber, endOfAnswerNumber);
            NumberClickableSpan span = getSpanForNumber(answerNumber);
            ss.setSpan(span, answersLinkArray.get(i), endOfAnswerNumber, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        super.setText(ss);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    private int getEndOfAnswer(int startIndex, String text){

        int startOfAnswerNumber = startIndex + ARROWS_LENGTH;
        int endOfAnswerNumber = startOfAnswerNumber;

        for (int i = startOfAnswerNumber; i < text.length(); i++){
            if(Character.isDigit(text.charAt(i))) {
                endOfAnswerNumber++;
            } else {
                break;
            }
        }
        return endOfAnswerNumber;
    }

    private NumberClickableSpan getSpanForNumber(String number) {
        return new NumberClickableSpan(number) {
            @Override
            public void onNumberClick(String number) {
                linkClickListener.onLinkClick(Integer.valueOf(number));
                Log.d(TAG, "onNumberClick: " + number);
            }
        };
    }


    private class NumberClickableSpan extends ClickableSpan {

        public String number;

        public NumberClickableSpan(String number) {
            this.number = number;
        }

        @Override
        public void onClick(View widget) {
            onNumberClick(number);
        }

        public void onNumberClick(String number) {

        }
    }

    public interface LinkClickListener {
        void onLinkClick(int number);
    }
}
