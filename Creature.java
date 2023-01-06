abstract class Creature {

    protected int[] stats;
    private String name;
    private int modifier = 1;
    private int intention;

    public Creature(int damage, int defense, int attacks, int health, String name) {
        this.stats = new int[] {damage, defense, attacks, health};
        this.name = name;
    }

    public void fight(Creature enemy, int userBoost, int enemyBoost) {

        if (this.getHealth() > 0) {

            //create temporary sets of stats for only this turn
            int[] playerStats = cloneStats(this, userBoost);
            int[] enemyStats = cloneStats(enemy, enemyBoost);

            //resolve (damage-defense)*attacks
            enemy.stats[3] -= resolveFight(this, enemy, playerStats, enemyStats);
            if (enemy.stats[3] < 0) {
                enemy.setHealth(0);
            }
        }
    }

    private int[] cloneStats(Creature animal, int boost) {
        int[] newStats = animal.stats.clone();
        if (boost < 3) {
            newStats[boost] += animal.modifier;
        }
        return newStats;
    }

    private int resolveFight(Creature creature, Creature enemyCreature, int[] player, int[] enemy) {
        int damageSum = 0;
        for (int i = 0; i < player[2]; i++) {
            System.out.println(creature.getName() + "'s Attack: " + (i+1));
            damageSum += resolveAttack(creature.fightUnique(enemyCreature.defendUnique(player.clone(), enemy.clone()),enemy.clone()), creature);
            if (enemy[1] > 0) {
                enemy[1] -= 1;
            }
        }
        return damageSum;
    }

    private int resolveAttack(int[][] toCompare, Creature attacker) {
        int damage = toCompare[0][0]-toCompare[1][1];
        //just for resolve class's healing
        if (toCompare[0][3] > attacker.getHealth()) {
            attacker.setHealth(attacker.getHealth()+(toCompare[0][3] - attacker.getHealth()));
        }
        System.out.println("Damage: " + damage);
        if (damage > 0) {
            return damage;
        }
        return 0;
    }

    protected abstract int[][] fightUnique(int[] player, int[] opponent);
    protected abstract int[] defendUnique(int[] player, int[] opponent);

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return stats[3];
    }

    public void setHealth(int health) {
        this.stats[3] = health;
    }

    public void Equip() {

    }

    public int getIntention() {
        return intention;
    }

    public void setRandomIntention() {
        intention = (int) (Math.random()*3);
    }

}