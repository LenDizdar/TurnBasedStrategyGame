import javax.swing.*;

abstract class Creature {

    protected int[] stats;
    private String name;
    private int modifier;
    private int intention;
    private int maxHealth;
    private boolean isStunned;
    public ImageIcon sprite;

    public Creature(int damage, int defense, int attacks, int speed, int health, int modifier, String name, ImageIcon sprite) {
        this.stats = new int[] {damage, defense, attacks, health, speed};
        this.name = name;
        this.maxHealth = health;
        this.modifier = modifier;
        this.sprite = sprite;
    }

    public void fight(Creature enemy, int userBoost, int enemyBoost, Application scene, boolean isPlayer) {

        if (this.getHealth() > 0) {

            //create temporary sets of stats for only this turn
            int[] playerStats = cloneStats(this, userBoost);
            int[] enemyStats = cloneStats(enemy, enemyBoost);
            if (this.isStunned) {
                playerStats[2] = -50;
                this.stunned(false);
            }
            //resolve (damage-defense)*attacks
            enemy.stats[3] -= resolveFight(this, enemy, playerStats, enemyStats, scene, isPlayer);
            if (enemy.stats[3] < 0) {
                enemy.setHealth(0);
            }
        }
    }

    private int[] cloneStats(Creature animal, int boost) {
        int[] newStats = animal.stats.clone();
        newStats[boost] += animal.modifier;
        return newStats;
    }

    private int resolveFight(Creature creature, Creature enemyCreature, int[] player, int[] enemy, Application scene, boolean isPlayer) {
        int damageSum = 0;
        for (int i = 0; i < player[2]; i++) {
            System.out.println(creature.getName() + "'s Attack: " + (i+1));
            damageSum += resolveAttack(creature.fightUnique(enemyCreature.defendUnique(player.clone(), enemy.clone(), scene),enemy.clone(), scene), creature, enemyCreature);
            if (enemy[1] > 0) {
                enemy[1] -= 1;
            }
        }
        return damageSum;
    }

    private int resolveAttack(int[][] toCompare, Creature attacker, Creature enemy) {
        int damage = toCompare[0][0]-toCompare[1][1];
        if (toCompare[1][4] == 0) {
            enemy.stunned(true);
        }
        //just for resolve class's healing
        if (toCompare[1][4] == -1) {
            attacker.setHealth(attacker.getHealth()+1);
        }
        if  (enemy.getHealth() > 0 && toCompare[0][4] == -1) {
            attacker.setHealth(attacker.getHealth()-1);
        }
        System.out.println("Damage: " + damage);
        return Math.max(damage, 0);
    }

    private void stunned(boolean isStunned) {
        this.isStunned = isStunned;
    }

    protected abstract int[][] fightUnique(int[] player, int[] opponent, Application scene);
    protected abstract int[] defendUnique(int[] player, int[] opponent, Application scene);

    public void buff(int stat) {
        this.stats[stat] += 2;
    }

    public void fullHeal() {
        if (this.maxHealth > this.getHealth()) {
            this.stats[3] = maxHealth;
            Main.scene.updateHealth(true, true, this);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public ImageIcon getSprite() {
        return sprite;
    }

    public int getHealth() {
        return stats[3];
    }

    public int getMaxHealth() {
        return maxHealth;
    }
    public int[] getStats() {return stats;}

    public void setHealth(int health) {
        this.stats[3] = health;
    }

    public int getIntention() {
        return intention;
    }

    public void setRandomIntention() {
        intention = (int) (Math.random()*3);
    }

}