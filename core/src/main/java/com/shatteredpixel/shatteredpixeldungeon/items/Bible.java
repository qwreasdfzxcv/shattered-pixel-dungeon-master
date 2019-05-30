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

package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BlobImmunity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Pacified;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Pilgrim;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PilgrimPunish;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroSubClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.DM300;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Goo;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.King;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Tengu;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Yog;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.GuardianSpirit;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.RainbowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShaftParticle;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Bible extends Item {

    {
        image = ItemSpriteSheet.BIBLE;

        defaultAction = AC_READ;

        cursedKnown = true;
        unique = true;
        bones = false;
    }

    //the current charge
    private int charge = 0;
    //the build towards next charge, usually rolls over at 1.
    //better to keep charge as an int and use a separate float than casting.
    private float partialCharge = 0;
    //the maximum charge
    private int chargeCap = 15;

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( VOLUME, charge );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        charge	= bundle.getInt( VOLUME );
    }

    public void empty() {charge = 0; updateQuickslot();}

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    public boolean isFull() {
        return charge >= chargeCap;
    }

    private static final String VOLUME	= "charge";

    private static final String TXT_STATUS	= "%d/%d";

    public static final String AC_READ = "READ";

    protected void readAnimation() {
        curUser.spend( 1f );
        curUser.busy();
        ((HeroSprite)curUser.sprite).read();
    }

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add(AC_READ);
        return actions;
    }

    @Override
    public void execute(final Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals( AC_READ )) {

            if (hero.buff( Blindness.class ) != null)
            {GLog.w( Messages.get(this, "blinded") );}
            else if (charge < 1)
            {GLog.w( Messages.get(this, "no_charge") );}
            else
            { if (Dungeon.hero.subClass == HeroSubClass.SCHOLAR){
                GameScene.show(
                        new WndOptions( Messages.get(this, "name"),
                                Messages.get(this, "prompt_scholar"),
                                Messages.get(this, "guardian_spirit"),
                                Messages.get(this, "aura_of_healing"),
                                Messages.get(this, "cleanse"),
                                Messages.get(this, "divine_shield"),
                                Messages.get(this, "judgment")) {
                            @Override
                            protected void onSelect(int index) {
                                if (index == 0) {
                                    if (charge >= 1) {
                                        GLog.p(Messages.get(Bible.class, "on_guardian_spirit"));
                                        guardian_spirit(hero);
                                    } else { GLog.w(Messages.get(Bible.class, "need_more_charge")); }
                                } if (index == 1) {
                                    if (charge >= 2) {
                                        GLog.p( Messages.get(Bible.class, "on_aura_of_healing") );
                                        aura_of_healing(hero);
                                    } else { GLog.w( Messages.get(Bible.class, "need_more_charge") ); }
                                } if (index == 2) {
                                    if (charge >= 3) {
                                        GLog.p( Messages.get(Bible.class, "on_cleanse") );
                                        cleanse(hero);
                                    } else { GLog.w( Messages.get(Bible.class, "need_more_charge") ); }
                                } if (index == 3) {
                                    if (charge >= 5) {
                                        GLog.p( Messages.get(Bible.class, "on_divine_shield") );
                                        divine_shield(hero);
                                    } else { GLog.w( Messages.get(Bible.class, "need_more_charge") ); }
                                } if (index == 4) {
                                    if (charge >= 8) {
                                        GLog.p( Messages.get(Bible.class, "on_judgment") );
                                        judgment(hero);
                                    } else { GLog.w( Messages.get(Bible.class, "need_more_charge") ); }
                                }
                            }
                        }
                );
            } if (Dungeon.hero.subClass == HeroSubClass.PILGRIM){
                GameScene.show(
                        new WndOptions( Messages.get(this, "name"),
                                Messages.get(this, "prompt_pilgrim"),
                                Messages.get(this, "cleanse"),
                                Messages.get(this, "divine_shield"),
                                Messages.get(this, "chant_of_peace")) {
                            @Override
                            protected void onSelect(int index) {
                                if (index == 0) {
                                    if (charge >= 3) {
                                        GLog.p( Messages.get(Bible.class, "on_cleanse") );
                                        cleanse(hero);
                                    } else { GLog.w( Messages.get(Bible.class, "need_more_charge") ); }
                                } if (index == 1) {
                                    if (charge >= 5) {
                                        GLog.p( Messages.get(Bible.class, "on_divine_shield") );
                                        divine_shield(hero);
                                    } else { GLog.w( Messages.get(Bible.class, "need_more_charge") ); }
                                } if (index == 2) {
                                    Pilgrim pilgrim = hero.buff(Pilgrim.class);
                                    PilgrimPunish punish = hero.buff(PilgrimPunish.class);
                                    if (charge >= pilgrim.peaceCost() && punish == null) {
                                        GLog.p( Messages.get(Bible.class, "on_peace") );
                                        chant_of_peace(hero);
                                    } else if (charge >= 8) {
                                        GLog.p( Messages.get(Bible.class, "on_peace") );
                                        chant_of_peace(hero);
                                    } else { GLog.w( Messages.get(Bible.class, "need_more_charge") ); }
                                }
                            }
                        }
                );
            } else {
                GameScene.show(
                        new WndOptions( Messages.get(this, "name"),
                                Messages.get(this, "prompt"),
                                Messages.get(this, "cleanse"),
                                Messages.get(this, "divine_shield"),
                                Messages.get(this, "judgment")) {
                            @Override
                            protected void onSelect(int index) {
                                if (index == 0) {
                                    if (Dungeon.hero.subClass == HeroSubClass.CRUSADER){
                                        if (charge >= 3) {
                                            GLog.p( Messages.get(Bible.class, "on_cleanse_crusader") );
                                            cleanse_crusader(hero);
                                        } else { GLog.w( Messages.get(Bible.class, "need_more_charge") ); }
                                    } else {
                                        if (charge >= 3) {
                                            GLog.p( Messages.get(Bible.class, "on_cleanse") );
                                            cleanse(hero);
                                        } else { GLog.w( Messages.get(Bible.class, "need_more_charge") ); }
                                    }
                                } if (index == 1) {
                                    if (charge >= 5) {
                                        GLog.p( Messages.get(Bible.class, "on_divine_shield") );
                                        divine_shield(hero);
                                    } else { GLog.w( Messages.get(Bible.class, "need_more_charge") ); }
                                } if (index == 2) {
                                    if (charge >= 8) {
                                        GLog.p( Messages.get(Bible.class, "on_judgment") );
                                        judgment(hero);
                                    } else { GLog.w( Messages.get(Bible.class, "need_more_charge") ); }
                                }
                            }
                        }
                );
            } }
        }
    }

    public void cleanse( Hero hero ){
        Invisibility.dispel();
        readAnimation();
        new Flare(6, 32).show(hero.sprite, 2f);
        hero.belongings.uncurseEquipped();
        for (Buff b : hero.buffs()) {
            if (b.type == Buff.buffType.NEGATIVE && !(b instanceof Corruption)) {
                b.detach();
            }
        }

        hero.sprite.showStatus( CharSprite.CLERIC, Messages.get(this, "cleanse_read"));
        Sample.INSTANCE.play( Assets.SND_READ );
        charge -= 3;
        updateQuickslot();
    }

    public void divine_shield( Hero hero ){
        Invisibility.dispel();
        readAnimation();
        new Flare( 6, 32 ).show( hero.sprite, 2f );

        int SH = 10+3*(Dungeon.hero.lvl);
        Buff.affect( hero, Barrier.class).setShield(SH);
        Buff.affect( hero, BlobImmunity.class, 12f + (4+(Dungeon.hero.lvl/2)));
        Buff.affect( hero, Light.class, 12f + (4+(Dungeon.hero.lvl/2)));

        hero.sprite.showStatus( CharSprite.CLERIC, Messages.get(this, "divine_shield_read"));
        Sample.INSTANCE.play( Assets.SND_READ );
        charge -= 5;
        updateQuickslot();
    }

    public void judgment( Hero hero ){
        Invisibility.dispel();
        readAnimation();
        GameScene.flash( 0xFFFFFF );
        Sample.INSTANCE.play( Assets.SND_BLAST );
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (Dungeon.level.heroFOV[mob.pos] && mob.alignment == Char.Alignment.ENEMY) {
                if (mob.properties().contains(Char.Property.DEMONIC) || mob.properties().contains(Char.Property.UNDEAD)){
                    mob.damage(Math.round(12+4*(1+(Dungeon.hero.lvl))), this);
                    mob.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10 );
                    Sample.INSTANCE.play(Assets.SND_BURNING);
                    if (mob.isAlive()) {
                        Buff.prolong(mob, Blindness.class, 2f + 2*(1+(Dungeon.hero.lvl/3)));
                    }
                } else {
                    mob.damage(Math.round(12+4*(1+(Dungeon.hero.lvl/2))), this);
                    mob.sprite.centerEmitter().burst( RainbowParticle.BURST, 10 );
                    if (mob.isAlive()) {
                        Buff.prolong(mob, Blindness.class, 1f + 1*(1+(Dungeon.hero.lvl/3)));
                    }
                }
            }
        }

        if (Dungeon.level.viewDistance < 6 ){
            if (Dungeon.isChallenged(Challenges.DARKNESS)){
                Buff.affect( hero, Light.class, 8f + (1+(Dungeon.hero.lvl/3)));
            } else {
                Buff.affect( hero, Light.class, 20f + (1+(Dungeon.hero.lvl/3))*5);
            }
        }

        hero.sprite.showStatus( CharSprite.CLERIC, Messages.get(this, "judgment_read"));
        Sample.INSTANCE.play( Assets.SND_BLAST );
        charge -= 8;
        updateQuickslot();
    }

    public void cleanse_crusader( Hero hero ){
        Invisibility.dispel();
        readAnimation();
        new Flare( 6, 32 ).show( hero.sprite, 2f );
        hero.belongings.uncurseEquipped();
        Buff.affect( hero, MagicImmune.class, 24f + 2*(1+(Dungeon.hero.lvl/3)));
        for (Buff b : hero.buffs()){
            if (b.type == Buff.buffType.NEGATIVE && !(b instanceof Corruption)){
                b.detach();
            }
        }

        hero.sprite.showStatus( CharSprite.CLERIC,  Messages.get(this, "cleanse_crusader_read"));
        Sample.INSTANCE.play( Assets.SND_READ );
        charge -= 3;
        updateQuickslot();
    }

    public void aura_of_healing( Hero hero ){
        Invisibility.dispel();
        readAnimation();
        new Flare( 6, 32 ).show( hero.sprite, 2f );
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (Dungeon.level.heroFOV[mob.pos]) {
                if (mob.alignment == Char.Alignment.ALLY) {
                    int healing = Math.round(4f + 4 * (1 + (Dungeon.hero.lvl / 3)));
                    int regen = (mob.HT + healing);
                        Buff.affect(mob, Healing.class).setHeal(regen, 0.5f, 0);
                        mob.sprite.emitter().burst(Speck.factory(Speck.HEALING), 4);
                    for (Buff b : mob.buffs()){
                        if (b.type == Buff.buffType.NEGATIVE && !(b instanceof Corruption)){
                            b.detach();
                        }
                    }
                }
            }
        }

        hero.sprite.showStatus( CharSprite.CLERIC,  Messages.get(this, "aura_of_healing_read"));
        Sample.INSTANCE.play( Assets.SND_READ );
        charge -= 2;
        updateQuickslot();
    }

        public void guardian_spirit( Hero hero ){
        Invisibility.dispel();
        readAnimation();
        new Flare( 6, 32 ).show( hero.sprite, 4f );

        boolean found = false;
        for (Mob m : Dungeon.level.mobs.toArray(new Mob[0]))
            if (m instanceof GuardianSpirit) {
                found = true;
                for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
                    if (Dungeon.level.heroFOV[mob.pos]) {
                        if (mob.alignment == Char.Alignment.ALLY) {
                            Buff.affect(mob, Bless.class, 10f + (1 + (Dungeon.hero.lvl / 3)) * 2);
                            Buff.affect( hero, Bless.class, 10f + (1 + (Dungeon.hero.lvl / 3)) * 2);
                            mob.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 4);

                            hero.sprite.showStatus( CharSprite.CLERIC,  Messages.get(this, "guardian_spirit_read"));
                            Sample.INSTANCE.play( Assets.SND_READ );
                            charge -= 1;
                            updateQuickslot();
                        }
                    }
                }
            }

        if (!found) {
            ArrayList<Integer> spawnPoints = new ArrayList<Integer>();
            for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                int p = hero.pos + PathFinder.NEIGHBOURS8[i];
                if (Actor.findChar(p) == null && (Dungeon.level.passable[p] || Dungeon.level.avoid[p])) {
                    spawnPoints.add(p);
                }
            }

            if (spawnPoints.size() > 0) {
                GuardianSpirit spirit = new GuardianSpirit();
                spirit.pos = Random.element(spawnPoints);

                GameScene.add(spirit, 1f);
                CellEmitter.get(spirit.pos).start( ShaftParticle.FACTORY, 0.3f, 4 );
                CellEmitter.get(spirit.pos).start( Speck.factory(Speck.LIGHT), 0.2f, 3 );

                hero.sprite.showStatus( CharSprite.CLERIC,  Messages.get(this, "guardian_spirit_read"));
                Sample.INSTANCE.play( Assets.SND_READ );
                charge -= 1;
                updateQuickslot();
            } else GLog.i( Messages.get(this, "no_space") ); }
        }

    public void chant_of_peace( Hero hero ){
        Invisibility.dispel();
        readAnimation();
        GameScene.flash( 0xFFFFFF );
        for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
            if (mob.alignment == Char.Alignment.ENEMY
                    && !mob.properties().contains((Char.Property.BOSS))
                    && mob.buff(Pacified.class) == null) {
                mob.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 4);
                mob.sprite.showStatus( CharSprite.CLERIC, Messages.get(this, "peaced_mob"));

                for (Buff b : mob.buffs()) {
                    b.detach();
                }

                mob.HP =  mob.HT;
                Buff.affect(mob, Pacified.class);

                if (mob.properties().contains(Char.Property.NONPACIFIED)) {
                    mob.die(hero);
                }

                int EXP = mob.EXP;
                int exp = Dungeon.hero.lvl <= mob.maxLvl ? EXP : 0;
                if (exp > 0) {
                    Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Messages.get(Mob.class, "exp", exp));
                    Dungeon.hero.earnExp(exp, getClass());
                }
            }
            if (mob instanceof Goo) {
                GLog.w( Messages.get(Goo.class, "pilgrim") );
            } if (mob instanceof Tengu) {
                mob.yell( Messages.get(Tengu.class, "pilgrim") );
            } if (mob instanceof DM300) {
                mob.yell( Messages.get(DM300.class, "pilgrim") );
            } if (mob instanceof King) {
                mob.yell( Messages.get(King.class, "pilgrim") );
            } if (mob instanceof Yog) {
                mob.yell( Messages.get(Yog.class, "pilgrim") );
            }
        }

        if (Dungeon.level.viewDistance < 6 ){
            if (Dungeon.isChallenged(Challenges.DARKNESS)){
                Buff.affect( hero, Light.class, 8f + (1+(Dungeon.hero.lvl/3)));
            } else {
                Buff.affect( hero, Light.class, 20f + (1+(Dungeon.hero.lvl/3))*5);
            }
        }

        Pilgrim pilgrim = hero.buff(Pilgrim.class);
        PilgrimPunish punish = hero.buff(PilgrimPunish.class);
        if (pilgrim.peaceCost() > 0 && punish == null) {
            charge -= pilgrim.peaceCost();
        }

        hero.sprite.showStatus( CharSprite.CLERIC, Messages.get(this, "peace_read"));
        Sample.INSTANCE.play( Assets.SND_LEVELUP );
        updateQuickslot();
    }

    public void onHeroGainExp( float levelPercent, Hero hero ) {
        if (charge < chargeCap) {
            if (Dungeon.hero.subClass == HeroSubClass.SCHOLAR){
                partialCharge += 0.25f;
            }
            partialCharge += 0.5f;
            if (partialCharge >= 1) {
                partialCharge--;
                charge++;
                updateQuickslot();
                if (charge == 3){
                    hero.sprite.showStatus( CharSprite.CLERIC,  Messages.get(this, "cleanse_ready"));
                } if (charge == 5){
                    hero.sprite.showStatus( CharSprite.CLERIC,  Messages.get(this, "divine_shield_ready"));
                } if (charge == 8 && Dungeon.hero.subClass != HeroSubClass.PILGRIM){
                    hero.sprite.showStatus( CharSprite.CLERIC,  Messages.get(this, "judgment_ready"));
                } if (charge == 1 && Dungeon.hero.subClass == HeroSubClass.SCHOLAR){
                    hero.sprite.showStatus( CharSprite.CLERIC,  Messages.get(this, "guardian_spirit_ready"));
                } if (charge == 2 && Dungeon.hero.subClass == HeroSubClass.SCHOLAR){
                    hero.sprite.showStatus( CharSprite.CLERIC,  Messages.get(this, "aura_of_healing_ready"));
                } if (Dungeon.hero.subClass == HeroSubClass.PILGRIM) {
                    Pilgrim pilgrim = hero.buff(Pilgrim.class);
                    PilgrimPunish punish = hero.buff(PilgrimPunish.class);
                    if (charge == pilgrim.peaceCost()
                        && pilgrim.peaceCost() > 0) {
                        if (punish == null) {
                            hero.sprite.showStatus(CharSprite.CLERIC, Messages.get(this, "peace_ready"));
                        } else if (charge == 8) {
                            hero.sprite.showStatus(CharSprite.CLERIC, Messages.get(this, "peace_ready"));
                        }
                    }
                }
            }
        }
    }

    @Override
    public String desc() {
        return super.desc();
    }

    @Override
    public String status() {
        return Messages.format( TXT_STATUS, charge, chargeCap );
    }

    }