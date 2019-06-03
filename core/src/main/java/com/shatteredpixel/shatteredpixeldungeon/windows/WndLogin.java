package com.shatteredpixel.shatteredpixeldungeon.windows;

import android.widget.EditText;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;

public class WndLogin extends Window {
    private static final int WIDTH      = 120;
    private static final int HEIGHT     = 80;

    private static final int BTN_HEIGHT = 18;
    private static final float GAP      = 2;

    public WndLogin() {

        super();

//        RenderedTextMultiline form1 = PixelScene.renderMultiline( 6 );
//        RenderedTextMultiline form2 = PixelScene.renderMultiline( 6 );

//        form1.text( Messages.get(this, "id"), WIDTH );
//        form1.setPos(1, 1);
//        add( form1 );
//
//        form2.text( Messages.get(this, "pwd"), WIDTH );
//        form2.setPos(1, 29);
//        add( form2 );


        RedButton btnSignIn = new RedButton( Messages.get(this, "sign_in"));
        btnSignIn.setSize(WIDTH/2-GAP, BTN_HEIGHT);
        btnSignIn.setPos(1, 61);
        add( btnSignIn );

        RedButton btnRegister = new RedButton( Messages.get(this, "register"));
        btnRegister.setSize(WIDTH/2-GAP, BTN_HEIGHT);
        btnRegister.setPos(61, 61);
        add( btnRegister );

        resize(WIDTH, HEIGHT);
    }
}
