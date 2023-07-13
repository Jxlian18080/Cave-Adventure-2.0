package objects;

import java.util.HashMap;

public class Key extends Item {

    public Key() {

    }

    public Key(String name, Integer[] coordinates, double weight) {
        super(name, coordinates, weight);
    }

    @Override
    public void createItem(Map map, String name, Objects objects, Integer[] coordinates, double weight) {
        String hashMapKey = createHashMapKeyForObjectList(objects, name);

        HashMap<String, Objects> newObjectList = objects.getObjectList();
        newObjectList.put(hashMapKey, new Key(name, coordinates, weight));
        objects.setObjectList(newObjectList);
        super.createItem(map, hashMapKey, objects, coordinates, weight);
    }

}
