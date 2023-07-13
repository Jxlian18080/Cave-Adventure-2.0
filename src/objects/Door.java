package objects;

import java.util.HashMap;

public class Door extends Objects {

    public Door() {

    }

    public Door(String name, Integer[] coordinates) {
        super(name, coordinates);
    }

    public void createObject(Map map, Objects objects, String name, Integer[] coordinates) {
        String hashMapKey = createHashMapKeyForObjectList(objects, name);

        HashMap<String, Objects> newObjectList = objects.getObjectList();
        newObjectList.put(hashMapKey, new Door(name, coordinates));
        objects.setObjectList(newObjectList);
        map.addObjectToMap(hashMapKey, coordinates);
    }

}
