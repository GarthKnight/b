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

    static String TAG = TextViewWithClickableSpan.class.getSimpleName();
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
        ArrayList<Integer> indices = new ArrayList<>();
        SpannableString ss = new SpannableString(text);
        int counter = text.indexOf(">>");
        int spaceCounter = text.indexOf(" ", counter);

        if (counter != -1) {
            indices.add(counter);
        }

        while (counter != -1) {
            Log.d(TAG, "counter: " + counter);
            counter = text.indexOf(">>", counter+1);
            if (counter != -1) {
                indices.add(counter);
            }
        }

        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.d("yoba", "pashel nahui: ");
                linkListener.onLinkClick(12345);
            }
        };

        for(int i = 0; i < indices.size(); i++){
            ss.setSpan(span, indices.get(i), indices.get(i)+11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        Log.d(TAG, "setSpannableText: " + text);
        super.setText(ss);
        setMovementMethod(LinkMovementMethod.getInstance());
    }



    public interface LinkClickListener{
        void onLinkClick(int number);
    }
}
