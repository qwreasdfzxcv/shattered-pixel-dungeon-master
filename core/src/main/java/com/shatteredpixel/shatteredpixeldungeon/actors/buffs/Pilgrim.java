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

package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class Pilgrim extends Buff {

    {
        type = buffType.POSITIVE;
    }

    private int stacks = 0;

    public void set(){
        if (stacks < 0){
        stacks = 1;}
        BuffIndicator.refreshHero();
    }

    public void gainStack(){
        stacks = Math.min(stacks+1, 30);
        BuffIndicator.refreshHero();
    }

    public void loseStack(){
        if (stacks > 0){
        stacks -= 1;}
        BuffIndicator.refreshHero();
    }

    public int stacks(){
        return stacks+1;
    }

    public float regenBonus(){ return 0.5f * stacks; }

    public int peaceCost(){
        return 8 - stacks;
    }

    public int hungerBonus(){ return Math.round(0.025f * stacks); }

    public int stealthBonus(){
        return Math.round(0.25f * stacks);
    }

    @Override
    public int icon() {
        return BuffIndicator.LEVITATION;
    }

    @Override
    public void tintIcon(Image icon) {
        if (stacks <= 1) {
            icon.tint(0xb3b3b3, 0.8f);
        } if (stacks == 2) {
            icon.tint(0xb3b3b3, 0.5f);
        } if (stacks == 3) {
            icon.tint(0xb3b3b3, 0.2f);
        }
    }

    @Override
    public String toString() {
        return Messages.get(this, "name");
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc", stacks);
    }

    private static final String STACKS = "stacks";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(STACKS, stacks);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        stacks = bundle.getInt(STACKS);
    }
}
