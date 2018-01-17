package com.amazon.alexa.avs.realbutton;

public interface OnRealButtonClickListener {
    int TYPE_SINGLE_CLICK = 0;
    int TYPE_DOUBLE_CLICK = 1;
    int TYPE_LONG_CLICK = 2;

    void onClick(int type);
}
