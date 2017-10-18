package com.exadel.sampleapp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exadel.sampleapp.R;

/**
 * This is a class file for a custom view, which looks like a switch control but with
 * two labels for each control position
 */
public class LabeledSwitch extends LinearLayout {

    protected TextView leftLabelView;

    protected TextView rightLabelView;

    protected SwitchCompat toggleControl;

    private SwitchListener switchListener;
    private int colorEnabled;
    private int colorDisabled;

    private Object optionLeft;
    private Object optionRight;

    public LabeledSwitch(Context context) {
        super(context);
        init(context, null);
    }

    public LabeledSwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LabeledSwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LabeledSwitch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_labeled_switch, this, true);
        leftLabelView = view.findViewById(R.id.tv_left_text);
        rightLabelView = view.findViewById(R.id.tv_right_text);
        toggleControl = view.findViewById(R.id.cfs_toggle);

        TypedArray props = context.obtainStyledAttributes(attrs, R.styleable.LabeledSwitch);

        float textSize = props.getDimensionPixelSize(R.styleable.LabeledSwitch_label_text_size, -1);
        if (textSize > 0) {
            leftLabelView.setTextSize(Dimension.DP, textSize);
            rightLabelView.setTextSize(Dimension.DP, textSize);
        }

        int padding = props.getDimensionPixelSize(R.styleable.LabeledSwitch_label_padding, -1);
        if (padding > 0) {
            leftLabelView.setPadding(0, 0, padding, 0);
            rightLabelView.setPadding(padding, 0, 0, 0);
        }

        colorEnabled = props.getColor(R.styleable.LabeledSwitch_label_text_color, -1);
        colorDisabled = props.getColor(R.styleable.LabeledSwitch_label_text_color_disabled, -1);

        boolean checked = props.getBoolean(R.styleable.LabeledSwitch_enabled, false);
        toggleControl.setChecked(checked);

        String leftText = props.getString(R.styleable.LabeledSwitch_label_left);
        leftLabelView.setText(leftText);

        String rightText = props.getString(R.styleable.LabeledSwitch_label_right);
        rightLabelView.setText(rightText);

        props.recycle();

        toggleControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onCheckChange(isChecked);
            }
        });
    }

    public void setSwitchListener(SwitchListener switchListener) {
        this.switchListener = switchListener;
    }

    public void setOptions(Object left, Object right) {
        optionLeft = left;
        optionRight = right;
    }

    protected void onCheckChange(boolean checked) {
        boolean colorsSet = colorEnabled != -1 && colorDisabled != -1;
        if (checked && colorsSet) {
            leftLabelView.setTextColor(colorDisabled);
            rightLabelView.setTextColor(colorEnabled);
        } else if (colorsSet) {
            leftLabelView.setTextColor(colorEnabled);
            rightLabelView.setTextColor(colorDisabled);
        }
        if (switchListener == null) {
            return;
        }
        toggleControl.setChecked(checked);
        if (!checked) {
            switchListener.onSwitched(Option.LEFT, optionLeft);
        } else {
            switchListener.onSwitched(Option.RIGHT, optionRight);
        }
    }

    public void selectOption(Object option) {
        if (option == null && optionLeft == null) {
            onCheckChange(false);
            return;
        }
        if (option == null && optionRight == null) {
            onCheckChange(true);
            return;
        }
        if (option == null || option.equals(optionLeft)) {
            onCheckChange(false);
        } else if (option.equals(optionRight)) {
            onCheckChange(true);
        }
    }

    public void setStateEnabled(boolean enabled) {
        toggleControl.setChecked(enabled);
    }

    public void setDisabled(boolean disabled) {
        toggleControl.setEnabled(!disabled);
    }

    public interface SwitchListener {
        void onSwitched(Option option, Object optionValue);
    }

    public enum Option {
        LEFT,
        RIGHT
    }
}
