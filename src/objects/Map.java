package objects;

import java.util.ArrayList;
import java.util.HashMap;

public class Map extends Item {

    // The Rooms are located in a square which size is between 3x3 and 9x9. The player can choose the size of the rooms
    // Inner ArrayList: Collects all HashMap Keys which are in this room
    // Middle ArrayList: y coordinate of the rooms
    // Outer ArrayList: x coordinate of the rooms
    private ArrayList<ArrayList<ArrayList<String>>> rooms = new ArrayList<ArrayList<ArrayList<String>>>();

    private int mapSize;

    public Map() {

    }

    public Map(String name, Integer[] coordinates, double weight) {
        super(name, coordinates, weight);
    }

    @Override
    public void createItem(Map map, String name, Objects objects, Integer[] coordinates, double weight) {
        String hashMapKey = createHashMapKeyForObjectList(objects, name);

        HashMap<String, Objects> newObjectList = objects.getObjectList();
        newObjectList.put(hashMapKey, new Map(name, coordinates, weight));
        objects.setObjectList(newObjectList);
        super.createItem(map, hashMapKey, objects, coordinates, weight);
    }

    // The map size gets initialized here
    // The variable "mapSize" is the size for the column and row
    public void createRooms() {

        for (int i = 0; i < mapSize; i++) {
            rooms.add(new ArrayList<ArrayList<String>>());
            for (int j = 0; j < mapSize; j++) {
                rooms.get(i).add(new ArrayList<String>());
            }
        }
    }

    // This method add objects to the room based on the coordinates
    public void addObjectToMap(String objectName, Integer[] coordinates) {
        rooms.get(coordinates[0]).get(coordinates[1]).add(objectName);
    }

    // Checks if the target location is out of map
    public boolean isRoomOutOfMap(Objects objects, Map map, String movingObject, int xMovingDistance, int yMovingDistance) {
        HashMap<String, Objects> newObjectList = objects.getObjectList();

        if (newObjectList.get(movingObject).getCoordinates()[0] + xMovingDistance < 0 || //
                newObjectList.get(movingObject).getCoordinates()[0] + xMovingDistance > map.getMapSize() - 1 || //
                newObjectList.get(movingObject).getCoordinates()[1] + yMovingDistance < 0 || //
                newObjectList.get(movingObject).getCoordinates()[1] + yMovingDistance > map.getMapSize() - 1) {
            return true;
        } else {
            return false;
        }
    }

    // Checks if the room has objects
    public boolean hasRoomObjects(Map map, Integer[] coordinates) {
        if (map.getRooms().get(coordinates[0]).get(coordinates[1]).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public void outputRoom(Objects objects, Map map, Integer[] coordinates, boolean roomHasObjects) {

        if (roomHasObjects == false) {
            System.out.println("This room is empty");

        } else {
            System.out.println("In this room is:");

            for (String string : map.getRooms().get(coordinates[0]).get(coordinates[1])) {

                if (!(objects.getObjectList().get(string).getName().equals("player")) && //
                        objects.getObjectList().get(string).getCoordinates()[0] != null) {

                    System.out.println(objects.getObjectList().get(string).getName());
                }
            }
        }
    }

    // Searches the matching index for the specified object in the rooms list
    public int searchIndexInRoomsList(String searchedName, Integer[] coordinates) {
        int counter = 0;
        for (String name : rooms.get(coordinates[0]).get(coordinates[1])) {

            if (name.equals(searchedName)) {
                break;

            } else {
                counter++;
            }
        }

        return counter;
    }

    public int getMapSize() {
        return mapSize;
    }

    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<ArrayList<ArrayList<String>>> rooms) {
        this.rooms = rooms;
    }
}
