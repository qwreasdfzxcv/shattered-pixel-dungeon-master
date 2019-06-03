package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndLogin;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTextInput;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Button;

public class LoginButton extends Button {
    protected Image image;

    public LoginButton() {
        super();

        width = image.width;
        height = image.height;
    }

    @Override
    protected void createChildren(){
        super.createChildren();

        image = Icons.NOTES.get();
        add(image);
    }

    @Override
    protected void layout(){
        super.layout();

        image.x = x;
        image.y = y;
    }

    @Override
    protected void onTouchDown(){
        image.brightness(1.5f);
        Sample.INSTANCE.play(Assets.SND_CLICK);
    }

    @Override
    protected void onTouchUp(){image.resetColor();}

    @Override
    protected void onClick(){
        WndTextInput form = new WndTextInput( "id", "password", "", "", false, "로그인", "가입");
        add(form);
    }
}
