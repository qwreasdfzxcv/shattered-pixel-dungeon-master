package com.shatteredpixel.shatteredpixeldungeon.windows;

import android.widget.EditText;

import com.shatteredpixel.shatteredpixeldungeon.ui.Window;

public class WndLogin extends Window {
    private int WIDTH_P = 120;
    private int WIDTH_L = 131;

    private int MIN_HEIGHT = 100;

    private int BTN_WIDTH = 50;
    private int BTN_HEIGHT = 50;

    private EditText textInput;
    private static final int MAX_LEN = 20;

    public WndLogin(){
        super();
    }
}
