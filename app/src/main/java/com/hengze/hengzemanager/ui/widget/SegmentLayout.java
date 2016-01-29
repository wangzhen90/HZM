package com.hengze.hengzemanager.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.hengze.hengzemanager.R;

/**
 * Created by dell on 2016/1/29.
 */
public class SegmentLayout extends RadioGroup {

    int mBorderWidth = (int) getResources().getDimension(R.dimen.segment_layout_border_width);
    Resources resources = getResources();
    int mTintColor = Color.BLUE;
    int mCheckedTextColor = Color.WHITE;
    float mCornerRadius = getResources().getDimension(R.dimen.segment_layout_corner_radius);
    private final int checked = R.drawable.radio_checked;
    private final int unchecked = R.drawable.radio_unchecked;
    LayoutSelector mLayoutSelector;

    public SegmentLayout(Context context) {
        super(context);
        mLayoutSelector = new LayoutSelector(mCornerRadius);
    }

    public SegmentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        mLayoutSelector = new LayoutSelector(mCornerRadius);
    }


    private void initAttrs(AttributeSet attrs) {

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.SegmentLayout, 0, 0);
        try {
            mBorderWidth = (int) typedArray.getDimension(R.styleable.SegmentLayout_sl_border_width, R.dimen.segment_layout_border_width);
            mCornerRadius = typedArray.getDimension(R.styleable.SegmentLayout_sl_corner_radius, R.dimen.segment_layout_corner_radius);
            mTintColor = typedArray.getColor(R.styleable.SegmentLayout_sl_tint_color, getResources().getColor(R.color.radio_button_selected_color));
            mCheckedTextColor = typedArray.getColor(R.styleable.SegmentLayout_sl_checked_text_color, getResources().getColor(android.R.color.white));
        } finally {
            typedArray.recycle();
        }


    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        update();
    }


    void update() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            updateBackground(child);

            if (i == count - 1) return;

            LayoutParams rawParams = (LayoutParams) child.getLayoutParams();
            LayoutParams params = new LayoutParams(rawParams.width, rawParams.height, rawParams.weight);
            if (getOrientation() == LinearLayout.HORIZONTAL) {
                params.setMargins(0, 0, -mBorderWidth, 0);
            } else {
                params.setMargins(0, 0, 0, -mBorderWidth);
            }

            child.setLayoutParams(params);

        }

    }

    void updateBackground(View child) {

        //设置文字的颜色
        ColorStateList colorStateList = new ColorStateList(new int[][]{
                {android.R.attr.state_pressed},
                {-android.R.attr.state_pressed, -android.R.attr.state_checked},
                {-android.R.attr.state_pressed, android.R.attr.state_checked}},
                new int[]{mTintColor, mTintColor, mCheckedTextColor});

        ((Button) child).setTextColor(colorStateList);

        //设置背景shape（填充色，边框，圆角）
        Drawable checkedDrawable = getResources().getDrawable(checked).mutate();
        Drawable uncheckedDrawable = getResources().getDrawable(unchecked).mutate();

        ((GradientDrawable) checkedDrawable).setColor(mTintColor);
        ((GradientDrawable) checkedDrawable).setStroke(mBorderWidth, mTintColor);
        ((GradientDrawable) uncheckedDrawable).setStroke(mBorderWidth, mTintColor);

        ((GradientDrawable) checkedDrawable).setCornerRadii(mLayoutSelector.getChildRadii(child));
        ((GradientDrawable) uncheckedDrawable).setCornerRadii(mLayoutSelector.getChildRadii(child));
        //创建背景Drawable
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_checked}, uncheckedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, checkedDrawable);

        //设置背景
        if (Build.VERSION.SDK_INT >= 16) {
            child.setBackground(stateListDrawable);
        } else {
            child.setBackgroundDrawable(stateListDrawable);
        }

    }

    final float r1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
            , 0.1f, getResources().getDisplayMetrics());    //0.1 dp to px



    private class LayoutSelector {

        private int children;
        private int child;
        private final int SELECTED_LAYOUT = R.drawable.radio_checked;
        private final int UNSELECTED_LAYOUT = R.drawable.radio_unchecked;

        private float r;    //this is the radios read by attributes or xml dimens
        private final float r1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , 0.1f, getResources().getDisplayMetrics());    //0.1 dp to px
        private final float[] rLeft;    // left radio button
        private final float[] rRight;   // right radio button
        private final float[] rMiddle;  // middle radio button
        private final float[] rDefault; // default radio button
        private final float[] rTop;     // top radio button
        private final float[] rBot;     // bot radio button
        private float[] radii;          // result radii float table

        public LayoutSelector(float cornerRadius) {
            children = -1; // Init this to force setChildRadii() to enter for the first time.
            child = -1; // Init this to force setChildRadii() to enter for the first time
            r = cornerRadius;
            rLeft = new float[]{r, r, r1, r1, r1, r1, r, r};
            rRight = new float[]{r1, r1, r, r, r, r, r1, r1};
            rMiddle = new float[]{r1, r1, r1, r1, r1, r1, r1, r1};
            rDefault = new float[]{r, r, r, r, r, r, r, r};
            rTop = new float[]{r, r, r, r, r1, r1, r1, r1};
            rBot = new float[]{r1, r1, r1, r1, r, r, r, r};
        }

        private int getChildren() {
            return SegmentLayout.this.getChildCount();
        }

        private int getChildIndex(View view) {
            return SegmentLayout.this.indexOfChild(view);
        }

        private void setChildRadii(int newChildren, int newChild) {

            // If same values are passed, just return. No need to update anything
            if (children == newChildren && child == newChild)
                return;

            // Set the new values
            children = newChildren;
            child = newChild;

            // if there is only one child provide the default radio button
            if (children == 1) {
                radii = rDefault;
            } else if (child == 0) { //left or top
                radii = (getOrientation() == LinearLayout.HORIZONTAL) ? rLeft : rTop;
            } else if (child == children - 1) {  //right or bottom
                radii = (getOrientation() == LinearLayout.HORIZONTAL) ? rRight : rBot;
            } else {  //middle
                radii = rMiddle;
            }
        }

        /* Returns the selected layout id based on view */
        public int getSelected() {
            return SELECTED_LAYOUT;
        }

        /* Returns the unselected layout id based on view */
        public int getUnselected() {
            return UNSELECTED_LAYOUT;
        }

        /* Returns the radii float table based on view for Gradient.setRadii()*/
        public float[] getChildRadii(View view) {
            int newChildren = getChildren();
            int newChild = getChildIndex(view);
            setChildRadii(newChildren, newChild);
            return radii;
        }
    }

}
