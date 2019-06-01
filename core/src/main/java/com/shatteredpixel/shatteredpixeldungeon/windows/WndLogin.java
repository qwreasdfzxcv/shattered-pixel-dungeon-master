package com.shatteredpixel.shatteredpixeldungeon.windows;

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

        RenderedTextMultiline form = PixelScene.renderMultiline( 6 );
        form.text( Messages.get(this, "id") + "\n\n" + "" + "\n\n" + Messages.get(this, "pwd") + "\n\n" + "", WIDTH );
        add( form );

        RedButton btnSignIn = new RedButton( Messages.get(this, "sign_in"));
        btnSignIn.setSize(WIDTH/2-GAP, BTN_HEIGHT);
        btnSignIn.setPos(1, 61);
        add( btnSignIn );

        RedButton btnSignUp = new RedButton( Messages.get(this, "sign_up"));
        btnSignUp.setSize(WIDTH/2-GAP, BTN_HEIGHT);
        btnSignUp.setPos(61, 61);
        add( btnSignUp );

        resize(WIDTH, HEIGHT);
    }
}
