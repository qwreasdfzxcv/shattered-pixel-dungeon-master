package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.RenderedText;

public class WndUploadScore extends Window {
    private static final int WIDTH      = 120;
    private static final int HEIGHT     = 80;

    private static final int BTN_HEIGHT = 18;
    private static final float GAP      = 2;

    public WndUploadScore() {

        super();

        RenderedText form = PixelScene.renderText( 10 );
        form.text( Messages.get(this, "upload_question"));
        add( form );

        RedButton btnYes = new RedButton( Messages.get(this, "upload_yes"));
        btnYes.setSize(WIDTH/2-GAP, BTN_HEIGHT);
        btnYes.setPos(1, 61);
        add( btnYes );

        RedButton btnNo = new RedButton( Messages.get(this, "upload_no"));
        btnNo.setSize(WIDTH/2-GAP, BTN_HEIGHT);
        btnNo.setPos(61, 61);
        add( btnNo );

        resize(WIDTH, HEIGHT);
    }
}
