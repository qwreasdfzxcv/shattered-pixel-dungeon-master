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

package com.shatteredpixel.shatteredpixeldungeon.items.armor;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Pacified;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class ClericArmor extends ClassArmor {

    {
        image = ItemSpriteSheet.ARMOR_CLERIC;
    }

    @Override
    public void doSpecial() {

        for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
            if (Dungeon.level.heroFOV[mob.pos] && mob.buff(Pacified.class) == null) {
                Buff.prolong( mob, Blindness.class, 8f);
                int oppositeHero = mob.pos + (mob.pos - curUser.pos);
                Ballistica trajectory = new Ballistica(mob.pos, oppositeHero, Ballistica.MAGIC_BOLT);
                WandOfBlastWave.throwChar(mob, trajectory, 3);
            }
        }

        Buff.prolong( curUser, Light.class, 100f);

        curUser.HP -= (curUser.HP / 3);

        curUser.spend( Actor.TICK );
        curUser.sprite.operate( curUser.pos );
        curUser.busy();

        GameScene.flash( 0xFFFFFF );
        Sample.INSTANCE.play( Assets.SND_BLAST );
    }
}