package com.p1694151.myapplication.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.p1694151.myapplication.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CustomButton extends RelativeLayout implements View.OnClickListener{

    public class TextAlignment {
        public static final int LEFT = 0;
        public static final int CENTER = 1;
    }

    @IntDef({TextAlignment.LEFT, TextAlignment.CENTER})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ViewTypeKey {
    }

    private TypedArray mTypedArray;
    private OnItemClickListener mItemClickListener;
    private TextView mTitle;
    private ProgressBar mLoadingIndicator;
    private LinearLayout mClickView;

    public CustomButton(Context context) {
        super(context);
        init(context, null);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_button, this);
        mClickView = (LinearLayout) findViewById(R.id.llClick);
        mClickView.setOnClickListener(this);
        mTitle = (TextView) findViewById(R.id.tv_button);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.progress_bar);

        if (attrs != null) {
            this.mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButton, 0, 0);
            mTitle.setText(mTypedArray.getString(R.styleable.CustomButton_buttonTitle));
        }
    }

    public void setText(String text) {
        mTitle.setText(text);
    }

    public void showProgressIndicator(boolean showProgress) {
        mLoadingIndicator.setVisibility(showProgress ? VISIBLE : INVISIBLE);
        mTitle.setVisibility(showProgress ? INVISIBLE : VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(this);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public void setClickable(boolean clickable) {
        mClickView.setClickable(clickable);
    }
}
