package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Rankings;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;

import java.util.ArrayList;

public class WndUploadScore extends Window {

    String URL= "http://220.149.14.84/dashboard/index.php";

    private static final int WIDTH      = 120;
    private static final int HEIGHT     = 80;

    private static final int BTN_HEIGHT = 18;
    private static final float GAP      = 2;

    public WndUploadScore() {

        super();

        RenderedTextMultiline form = PixelScene.renderMultiline( 6 );
        form.text( Messages.get(this, "upload_question"), WIDTH);
        form.setPos(1, 1);
        add( form );

        RedButton btnYes = new RedButton( Messages.get(this, "upload_yes")) {
            protected void onClick() {
                upload();
                hide();
            }
        };
        btnYes.setSize(WIDTH/2-GAP, BTN_HEIGHT);
        btnYes.setPos(1, 61);
        add( btnYes );

        RedButton btnNo = new RedButton( Messages.get(this, "upload_no"));
        btnNo.setSize(WIDTH/2-GAP, BTN_HEIGHT);
        btnNo.setPos(61, 61);
        add( btnNo );

        resize(WIDTH, HEIGHT);
    }

    private void upload() {

        Rankings.Record rec = new Rankings.Record();
        ArrayList<Rankings.Record> records;
        int lastRecord = Rankings.INSTANCE.lastRecord;


    }
}
