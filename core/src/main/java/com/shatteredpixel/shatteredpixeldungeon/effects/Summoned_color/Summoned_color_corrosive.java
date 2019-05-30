/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.effects.Summoned_color;

import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.watabou.noosa.Gizmo;

public class Summoned_color_corrosive extends Gizmo{

    private CharSprite target;

    public Summoned_color_corrosive( CharSprite target ) {
        super();

        this.target = target;
    }

    @Override
    public void update() {
        super.update();

        target.tint( 0xFF8800, 0.6f );
    }

    public void remove() {

        target.resetColor();
        killAndErase();

    }

    public static Summoned_color_corrosive apply(CharSprite sprite ) {

        Summoned_color_corrosive corrosive = new Summoned_color_corrosive( sprite );
        if (sprite.parent != null)
            sprite.parent.add( corrosive );

        return corrosive;
    }

}
