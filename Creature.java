abstract class Creature {

    protected int[] stats;
    private String name;
    private int modifier = 1;

    public Creature(int damage, int defense, int attacks, int health) {
        this.stats = new int[] {damage, defense, attacks, health};
    }

    public void fight(Creature enemy, int userBoost, int enemyBoost) {

        if (this.getHealth() > 0) {

            //create temporary sets of stats for only this turn
            int[] playerStats = cloneStats(this, userBoost);
            int[] enemyStats = cloneStats(enemy, enemyBoost);

            //resolve (damage-defense)*attacks
            enemy.stats[3] -= resolveFight(this, enemy, playerStats, enemyStats);
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
            damageSum += resolveAttack(creature.fightUnique(enemyCreature.defendUnique(player.clone(), enemy.clone()),enemy.clone()));
            if (enemy[1] > 0) {
                enemy[1] -= 1;
            }
        }
        return damageSum;
    }

    private int resolveAttack(int[][] toCompare) {
        int damage = toCompare[0][0]-toCompare[1][1];
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

    public void Equip() {

    }

}