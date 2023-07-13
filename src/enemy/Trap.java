package enemy;

import java.util.HashMap;

import objects.Map;
import objects.Objects;

public class Trap extends Objects {

    private int damage;

    public Trap() {

    }

    public Trap(String name, Integer[] coordinates, int damage) {
        super(name, coordinates);
        this.damage = damage;
    }

    public void createTrap(Map map, String name, Objects objects, Integer[] coordinates, int damage) {
        String hashMapKey = createHashMapKeyForObjectList(objects, name);

        HashMap<String, Objects> newObjectList = objects.getObjectList();
        newObjectList.put(hashMapKey, new Trap(name, coordinates, damage));
        objects.setObjectList(newObjectList);
        map.addObjectToMap(hashMapKey, coordinates);
    }

}
