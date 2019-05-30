package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.CorrosiveGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.ToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Charm;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corruption;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class GuardianSpirit extends NPC {

    {
        spriteClass = GuardianSpiritSprite.class;

        name = Messages.get(this, "name");

        flying = true;

        alignment = Alignment.ALLY;

        WANDERING = new Wandering();

        state = HUNTING;

        //before other mobs
        actPriority = MOB_PRIO + 1;
    }

    public GuardianSpirit(){
        super();
        updatepower();
        HP = HT;
    }

    private void updatepower(){
        defenseSkill = (Dungeon.hero.lvl+2)*2;
        HT = 8 + 3*(Dungeon.hero.lvl);
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
                && Dungeon.level.distance(enemy.pos, Dungeon.hero.pos) <= 1){
            return enemy;
        } else {
            return null;
        }
    }

    @Override
    public int damageRoll() {
        int mindamage = 1 + (Dungeon.hero.lvl/2);
        return Random.NormalIntRange( mindamage/2, mindamage );
    }

    @Override
    public int attackSkill(Char target) {
        //same accuracy as the hero.
        int acc = Dungeon.hero.lvl + 9;
        return acc;
    }

    @Override
    public int drRoll() {
        int mindr = 4 + (Dungeon.hero.lvl/3);
        return Random.NormalIntRange(mindr/2, mindr);
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );
        Buff.affect(enemy, Weakness.class, 4f);

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
        float stealth = super.stealth()*2f;
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
        immunities.add( ToxicGas.class );
        immunities.add( CorrosiveGas.class );
        immunities.add( Burning.class );
        immunities.add( ScrollOfRetribution.class );
        immunities.add( ScrollOfPsionicBlast.class );
        immunities.add( Corruption.class );
        immunities.add( Amok.class );
        immunities.add( Poison.class );
        immunities.add( Corrosion.class );
        immunities.add( Terror.class );
        immunities.add( Charm.class );
        immunities.add( Sleep.class );
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

    private static GuardianSpirit heldGuardian;

    public static void holdGuardian( Level level ){
        for (Mob mob : level.mobs.toArray( new Mob[0] )) {
            if (mob instanceof GuardianSpirit) {
                level.mobs.remove( mob );
                heldGuardian = (GuardianSpirit) mob;
                break;
            }
        }
    }

    public static void restoreGuardian(Level level, int pos ){
        if (heldGuardian != null){
            level.mobs.add( heldGuardian );

            int guardianPos;
            do {
                guardianPos = pos + PathFinder.NEIGHBOURS8[Random.Int(8)];
            } while (Dungeon.level.solid[guardianPos] || level.findMob(guardianPos) != null);

            heldGuardian.pos = guardianPos;
            heldGuardian = null;
        }
    }

    public static void clearHeldGuardian(){
        heldGuardian = null;
    }
}
