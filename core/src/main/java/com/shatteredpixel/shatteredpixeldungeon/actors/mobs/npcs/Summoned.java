package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Drowsy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SoulMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Summoned.Summoned_blasting;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Summoned.Summoned_brightning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Summoned.Summoned_chilly;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Summoned.Summoned_corrosive;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Summoned.Summoned_destructive;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Summoned.Summoned_dooming;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Summoned.Summoned_firey;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Summoned.Summoned_herbral;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Summoned.Summoned_protective;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Summoned.Summoned_shocking;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Bee;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.King;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Piranha;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Statue;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Swarm;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Wraith;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Yog;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Lightning;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PurpleParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAggression;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorruption;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.StatueSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.BArray;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashMap;

public class Summoned extends NPC {

    {
        spriteClass = SummonedSprite.class;

        name = Messages.get(this, "name");

        flying = true;

        alignment = Alignment.ALLY;

        WANDERING = new Wandering();

        state = HUNTING;

        properties.add(Property.INORGANIC);
        properties.add(Property.SUMMONED);

        //before other mobs
        actPriority = MOB_PRIO + 1;
    }

    public Summoned(){
        super();
        updatepower();
        HP = HT;
    }

    private void updatepower(){
        HT = 4 + Dungeon.depth * 4;
        defenseSkill = 2 + Dungeon.depth;
    }

    @Override
    protected boolean act() {
        updatepower();

        if (!isAlive())
            return true;
        if (!Dungeon.hero.isAlive()){
            sprite.die();
            destroy();
            return true;
        }
        return super.act();
    }

    @Override
    protected Char chooseEnemy() {
        Char enemy = super.chooseEnemy();

        //will never attack something far from the player
        if (enemy != null && Dungeon.level.mobs.contains(enemy)
                && Dungeon.level.distance(enemy.pos, Dungeon.hero.pos) <= 6){
            return enemy;
        } else {
            return null;
        }
    }

    @Override
    public int damageRoll() {
        int mindamage = 2 + 2*(Dungeon.depth/3);
        return Random.NormalIntRange( mindamage/2, mindamage );
    }

    @Override
    public int attackSkill(Char target) {
        int acc = Dungeon.depth + 9;
        return acc;
    }

    @Override
    public int drRoll() {
        int mindr = 1 + (Dungeon.depth/3);
        return Random.NormalIntRange(mindr/2, mindr);
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );

        if (buff(Summoned_blasting.class) != null){
            Ballistica trajectory = new Ballistica(this.pos, enemy.pos, Ballistica.STOP_TARGET);
            trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size()-1), Ballistica.PROJECTILE);
            WandOfBlastWave.throwChar(enemy, trajectory, 2);

        } if (buff(Summoned_brightning.class) != null){
            Buff.prolong(enemy, Blindness.class, 4f);
            if (enemy.properties().contains(Char.Property.DEMONIC) || enemy.properties().contains(Char.Property.UNDEAD)){
                enemy.sprite.emitter().start( ShadowParticle.UP, 0.05f, 12 );
                Sample.INSTANCE.play(Assets.SND_BURNING);

                damage *= 1.333f; }

        } if (buff(Summoned_chilly.class) != null){
            Buff.prolong(enemy, Chill.class, 4f);

        } if (buff(Summoned_corrosive.class) != null){
            Buff.affect(enemy, Ooze.class).set( 4f );

        } if (buff(Summoned_firey.class) != null){
            Buff.affect( enemy, Burning.class ).reignite( enemy );

        } if (buff(Summoned_herbral.class) != null){
            int c = Dungeon.level.map[enemy.pos];
            if ( c == Terrain.EMPTY || c == Terrain.EMPTY_DECO
                    || c == Terrain.EMBERS || c == Terrain.GRASS){
                Level.set(enemy.pos, Terrain.HIGH_GRASS);
                GameScene.updateMap(enemy.pos);
                CellEmitter.get( enemy.pos ).burst( LeafParticle.LEVEL_SPECIFIC, 4 ); }

        } if (buff(Summoned_protective.class) != null){
            Buff.prolong(this, StoneOfAggression.Aggression.class, 8f);

        } if (buff(Summoned_shocking.class) != null){
            affected_shocking.clear();

            arcs.clear();
            arc_shocking(this, enemy, 2);

            affected_shocking.remove(enemy); //defender isn't hurt by lightning
            for (Char enemy_shocked : affected_shocking) {
                enemy_shocked.damage((int)Math.ceil(damage/3f), this);
            }

            this.sprite.parent.addToFront( new Lightning( arcs, null ) );

        } if (buff(Summoned_destructive.class) != null){
            enemy.sprite.centerEmitter().burst( PurpleParticle.BURST, 4 );

        } if (buff(Summoned_dooming.class) != null){
            if (enemy.buff(Cripple.class) != null)
            enemy.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10 );

            Mob enemy0 = (Mob) enemy;

            float MINOR_DEBUFF_WEAKEN = 4/5f;
            HashMap<Class<? extends Buff>, Float> MINOR_DEBUFFS = new HashMap<>();
            {
                MINOR_DEBUFFS.put(Weakness.class,       2f);
                MINOR_DEBUFFS.put(Cripple.class,        1f);
                MINOR_DEBUFFS.put(Blindness.class,      1f);
                MINOR_DEBUFFS.put(Terror.class,         1f);

                MINOR_DEBUFFS.put(Chill.class,          0f);
                MINOR_DEBUFFS.put(Ooze.class,           0f);
                MINOR_DEBUFFS.put(Roots.class,          0f);
                MINOR_DEBUFFS.put(Vertigo.class,        0f);
                MINOR_DEBUFFS.put(Drowsy.class,         0f);
                MINOR_DEBUFFS.put(Bleeding.class,       0f);
                MINOR_DEBUFFS.put(Burning.class,        0f);
                MINOR_DEBUFFS.put(Poison.class,         0f);
            }

            float MAJOR_DEBUFF_WEAKEN = 2/3f;
            HashMap<Class<? extends Buff>, Float> MAJOR_DEBUFFS = new HashMap<>();
            {
                MAJOR_DEBUFFS.put(Amok.class,           3f);
                MAJOR_DEBUFFS.put(Slow.class,           2f);
                MAJOR_DEBUFFS.put(Paralysis.class,      1f);

                MAJOR_DEBUFFS.put(Charm.class,          0f);
                MAJOR_DEBUFFS.put(MagicalSleep.class,   0f);
                MAJOR_DEBUFFS.put(SoulMark.class,       0f);
                MAJOR_DEBUFFS.put(Corrosion.class,      0f);
                MAJOR_DEBUFFS.put(Frost.class,          0f);
                MAJOR_DEBUFFS.put(Doom.class,           0f);
            }

            float corruptingPower = 1 + Dungeon.depth/3f;

            //base enemy resistance is usually based on their exp, but in special cases it is based on other criteria
            float enemyResist = 1 + enemy0.EXP;
            if (enemy instanceof Mimic || enemy instanceof Statue){
                enemyResist = 1 + Dungeon.depth;
            } else if (enemy instanceof Piranha || enemy instanceof Bee) {
                enemyResist = 1 + Dungeon.depth/2f;
            } else if (enemy instanceof Wraith) {
                //this is so low because wraiths are always at max hp
                enemyResist = 0.5f + Dungeon.depth/8f;
            } else if (enemy instanceof Yog.BurningFist || enemy instanceof Yog.RottingFist) {
                enemyResist = 1 + 30;
            } else if (enemy instanceof Yog.Larva || enemy instanceof King.Undead){
                enemyResist = 1 + 5;
            } else if (enemy instanceof Swarm){
                //child swarms don't give exp, so we force this here.
                enemyResist = 1 + 3;
            }

            //100% health: 3x resist   75%: 2.1x resist   50%: 1.5x resist   25%: 1.1x resist
            enemyResist *= 1 + 2*Math.pow(enemy.HP/(float)enemy.HT, 2);

            //debuffs placed on the enemy reduce their resistance
            for (Buff buff : enemy.buffs()){
                if (MAJOR_DEBUFFS.containsKey(buff.getClass()))         enemyResist *= MAJOR_DEBUFF_WEAKEN;
                else if (MINOR_DEBUFFS.containsKey(buff.getClass()))    enemyResist *= MINOR_DEBUFF_WEAKEN;
                else if (buff.type == Buff.buffType.NEGATIVE)           enemyResist *= MINOR_DEBUFF_WEAKEN;
            }

            //cannot re-corrupt or doom an enemy, so give them a major debuff instead
            if(enemy.buff(Corruption.class) != null || enemy.buff(Doom.class) != null){
                corruptingPower = enemyResist - 0.001f;
            }

            if (corruptingPower > enemyResist){
                if(enemy.buff(Corruption.class) != null || enemy.buff(Doom.class) != null){
                    GLog.w( Messages.get(WandOfCorruption.class, "already_corrupted") );
                }

            } else {
                float debuffChance = corruptingPower / enemyResist;
                if (Random.Float() < debuffChance){
                    HashMap<Class<? extends Buff>, Float> debuffs = new HashMap<>(MAJOR_DEBUFFS);
                    for (Buff existing : enemy.buffs()){
                        if (debuffs.containsKey(existing.getClass())) {
                            debuffs.put(existing.getClass(), 0f);
                        }
                    }
                    for (Class<?extends Buff> toAssign : debuffs.keySet()){
                        if (debuffs.get(toAssign) > 0 && enemy.isImmune(toAssign)){
                            debuffs.put(toAssign, 0f);
                        }
                    }

                    //all buffs with a > 0 chance are flavor buffs
                    Class<?extends FlavourBuff> debuffCls = (Class<? extends FlavourBuff>) Random.chances(debuffs);

                    if (debuffCls != null){
                        Buff.append(enemy, debuffCls, 6 + Dungeon.depth/3f);
                    } else {
                        if (enemy.buff(Corruption.class) != null || enemy.buff(Doom.class) != null) {
                            GLog.w(Messages.get(WandOfCorruption.class, "already_corrupted"));
                        }

                    }
                } else {
                    HashMap<Class<? extends Buff>, Float> debuffs = new HashMap<>(MINOR_DEBUFFS);
                    for (Buff existing : enemy.buffs()){
                        if (debuffs.containsKey(existing.getClass())) {
                            debuffs.put(existing.getClass(), 0f);
                        }
                    }
                    for (Class<?extends Buff> toAssign : debuffs.keySet()){
                        if (debuffs.get(toAssign) > 0 && enemy.isImmune(toAssign)){
                            debuffs.put(toAssign, 0f);
                        }
                    }

                    //all buffs with a > 0 chance are flavor buffs
                    Class<?extends FlavourBuff> debuffCls = (Class<? extends FlavourBuff>) Random.chances(debuffs);

                    if (debuffCls != null){
                        Buff.append(enemy, debuffCls, 6 + Dungeon.depth/3f);
                    } else {
                        if(enemy.buff(Corruption.class) != null || enemy.buff(Doom.class) != null){
                            GLog.w( Messages.get(this, "already_corrupted") );
                        }
                    }
                }
            }
        }
        return damage;
    }

    @Override
    protected float attackDelay() {
        float delay = super.attackDelay();
        return delay;
    }

    @Override
    protected boolean canAttack(Char enemy) {
        return super.canAttack(enemy);
    }

    @Override
    public int defenseProc(Char enemy, int damage) {
        return super.defenseProc(enemy, damage);
    }

    @Override
    public float speed() {
        float speed = super.speed();
        return speed;
    }

    @Override
    public float stealth() {
        float stealth = super.stealth();
        return stealth;
    }

    @Override
    public boolean interact() {
        updatepower();
        if (Dungeon.level.passable[pos] || Dungeon.hero.flying) {
            int curPos = pos;

            moveSprite( pos, Dungeon.hero.pos );
            move( Dungeon.hero.pos );

            Dungeon.hero.sprite.move( Dungeon.hero.pos, curPos );
            Dungeon.hero.move( curPos );

            Dungeon.hero.spend( 1 / Dungeon.hero.speed() );
            Dungeon.hero.busy();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
    }

    @Override
    public void destroy() {
        updatepower();
        super.destroy();
    }

    {
        immunities.add( ScrollOfRetribution.class );
        immunities.add( ScrollOfPsionicBlast.class );
        immunities.add( Corruption.class );
        immunities.add( Amok.class );
        immunities.add( Terror.class );
        immunities.add( Charm.class );
        immunities.add( Sleep.class );
        immunities.add( Cripple.class );
    }

    private class Wandering extends Mob.Wandering {

        @Override
        public boolean act(boolean enemyInFOV, boolean justAlerted) {
            if (enemyInFOV) {

                enemySeen = true;

                notice();
                alerted = true;
                state = HUNTING;
                target = enemy.pos;

            } else {

                enemySeen = false;

                int oldPos = pos;
                //always move towards the hero when wandering
                if (getCloser(target = Dungeon.hero.pos)) {
                    //moves 2 tiles at a time when returning to the hero from a distance
                    if (!Dungeon.level.adjacent(Dungeon.hero.pos, pos)) {
                        getCloser(target = Dungeon.hero.pos);
                    }
                    spend(1 / speed());
                    return moveSprite(oldPos, pos);
                } else {
                    spend(TICK);
                }

            }
            return true;
        }
    }

    private static Summoned heldSummoned;

    public static void holdSummoned( Level level ){
        for (Mob mob : level.mobs.toArray( new Mob[0] )) {
            if (mob instanceof Summoned) {
                level.mobs.remove( mob );
                heldSummoned = (Summoned) mob;
                break;
            }
        }
    }

    public static void restoreSummoned(Level level, int pos ){
        if (heldSummoned != null){
            level.mobs.add( heldSummoned );

            int summonedPos;
            do {
                summonedPos = pos + PathFinder.NEIGHBOURS8[Random.Int(8)];
            } while (Dungeon.level.solid[summonedPos] || level.findMob(summonedPos) != null);

            heldSummoned.pos = summonedPos;
            heldSummoned = null;
        }
    }

    public static void clearHeldSummoned(){
        heldSummoned = null;
    }

    public static class SummonedSprite extends StatueSprite {

        public SummonedSprite(){
            super();
            tint(0x9370DB, 0.5f);
        }

        @Override
        public void resetColor() {
            super.resetColor();
            tint(0x9370DB, 0.5f);
        }
    }

    private ArrayList<Char> affected_shocking = new ArrayList<>();

    private ArrayList<Lightning.Arc> arcs = new ArrayList<>();

    private void arc_shocking( Char attacker, Char defender, int dist ) {

        affected_shocking.add(defender);

        defender.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
        defender.sprite.flash();

        PathFinder.buildDistanceMap( defender.pos, BArray.not( Dungeon.level.solid, null ), dist );
        for (int i = 0; i < PathFinder.distance.length; i++) {
            if (PathFinder.distance[i] < Integer.MAX_VALUE) {
                Char n = Actor.findChar(i);
                if (n != null && n != attacker && n != Dungeon.hero && !affected_shocking.contains(n)) {
                    arcs.add(new Lightning.Arc(defender.sprite.center(), n.sprite.center()));
                    arc_shocking(attacker, n, (Dungeon.level.water[n.pos] && !n.flying) ? 2 : 1);
                }
            }
        }
    }
}