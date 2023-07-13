package enemy;

import java.util.HashMap;

import objects.Map;
import objects.Objects;

public class Enemy extends Objects {

    private int HP;

    private int maxHP;

    Object weapon;

    public Enemy() {

    }

    public Enemy(String name, Integer[] coordinates, int HP, int maxHP, Object weapon) {
        super(name, coordinates);
        this.HP = HP;
        this.maxHP = maxHP;
        this.weapon = weapon;
    }

    public void createEnemy(Map map, String name, Objects objects, Integer[] coordinates, int HP, int maxHP, Object weapon) {
        String hashMapKey = createHashMapKeyForObjectList(objects, name);

        HashMap<String, Objects> newObjectList = objects.getObjectList();
        newObjectList.put(hashMapKey, new Enemy(name, coordinates, HP, maxHP, weapon));
        objects.setObjectList(newObjectList);
        map.addObjectToMap(hashMapKey, coordinates);
    }

}
