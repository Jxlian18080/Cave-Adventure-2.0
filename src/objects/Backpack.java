package objects;

import java.util.HashMap;

public class Backpack extends Item {

    double currentBackpackWeight;

    double maxBackpackWeight;

    public Backpack() {

    }

    public Backpack(String name, Integer[] coordinates, double maxBackpackWeight) {
        super(name, coordinates, 0);
        this.maxBackpackWeight = maxBackpackWeight;
    }

    @Override
    public void createItem(Map map, String name, Objects objects, Integer[] coordinates, double weight) {
        String hashMapKey = createHashMapKeyForObjectList(objects, name);

        if (name.equals("medium backpack")) {
            maxBackpackWeight = 3;
        } else {
            maxBackpackWeight = 4;
        }

        HashMap<String, Objects> newObjectList = objects.getObjectList();
        newObjectList.put(hashMapKey, new Backpack(name, coordinates, maxBackpackWeight));
        objects.setObjectList(newObjectList);
        map.addObjectToMap(hashMapKey, coordinates);
    }

}
