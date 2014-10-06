package Components;

public class Passenger {
    protected int health, stamina, hunger, weight;
    protected String name;

    public void changeHealth(int amt) {
        health += amt;
    }
    public void changeStamina(int amt) {
        stamina += amt;
    }
    public void changeHunger(int amt) {
        hunger += amt;
    }
    public int getHealth(){
        return health;
    }
    public int getStamina() {
        return stamina;
    }
    public int getHunger() {
        return hunger;
    }
    public int getWeight(){
        return weight;
    }
    public String getName() {
        return name;
    }

}
