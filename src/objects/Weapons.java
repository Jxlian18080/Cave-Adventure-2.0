package objects;

import java.util.HashMap;

public class Weapons extends Item {

    private int attackDamage;

    private int criticalHitDamageBoost;

    // 0 = Weapon is attached to nothing
    // 1 = Weapon is attached to player
    // 2 = Weapon is attached to an enemy
    private int isAttachedTo;

    public Weapons() {

    }

    public Weapons(String name, Integer[] coordinates, double weight, int attackDamage, int criticalHitDamageBoost) {
        super(name, coordinates, weight);
        this.attackDamage = attackDamage;
        this.criticalHitDamageBoost = criticalHitDamageBoost;
    }

    // This method creates all weapon items
    public String createItem(Map map, String name, Objects objects, Integer[] coordinates, double weight, int attackDamage,
            int criticalHitDamageBoost, boolean addToMap) {
        String hashMapKey = createHashMapKeyForObjectList(objects, name);

        HashMap<String, Objects> newObjectList = objects.getObjectList();
        newObjectList.put(hashMapKey, new Weapons(name, coordinates, weight, attackDamage, criticalHitDamageBoost));
        objects.setObjectList(newObjectList);

        if (addToMap == true) {
            super.createItem(map, hashMapKey, objects, coordinates, weight);
        }

        return hashMapKey;
    }

    public int getIsAttachedTo() {
        return isAttachedTo;
    }

    public void setIsAttachedTo(int isAttachedTo) {
        this.isAttachedTo = isAttachedTo;
    }
}
