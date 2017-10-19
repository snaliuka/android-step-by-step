package com.exadel.sampleapp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exadel.sampleapp.R;


public class SwitchingPositionView extends FrameLayout {

    public SwitchingPositionView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public SwitchingPositionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SwitchingPositionView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SwitchingPositionView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_demo_view, this, true);

        final Button btnControl = view.findViewById(R.id.btn_say_hello);
        final LinearLayout layout = view.findViewById(R.id.ll_controls_wrapper);

        TextView textControl = view.findViewById(R.id.tv_hidden_view);

        if (attrs != null) {
            TypedArray props = context.obtainStyledAttributes(attrs, R.styleable.SwitchingPositionView);

            String leftText = props.getString(R.styleable.SwitchingPositionView_textViewText);
            textControl.setText(leftText);
        }

        btnControl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int btnPosition = 0;
                for (int i = 0; i < layout.getChildCount(); i++) {
                    if (layout.getChildAt(i).getId() == R.id.btn_say_hello) {
                        btnPosition = i;
                        break;
                    }
                }

                layout.removeViewAt(btnPosition);
                int newPosition = (btnPosition + 1) % 2;
                layout.addView(btnControl, newPosition);
            }
        });

    }
}
