package objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import main.GameSettings;
import player.Player;

class Names {

    // This two lists contain every item name to check if the user input is an item
    private List<String> itemNames = Arrays.asList("key", "smallaxe", "normalaxe", "heavyaxe", "map", "healpotion", "regenerationpotion",
            "mysterypotion");

    private HashMap<String, String> itemNamesForOutput = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("key", "key");
            put("smallaxe", "small axe");
            put("normalaxe", "normal axe");
            put("heavyaxe", "heavy axe");
            put("map", "map");
            put("healpotion", "heal potion");
            put("regenerationpotion", "regeneration potion");
            put("mysterypotion", "mystery potion");
        }
    };

    List<String> getItemNames() {
        return itemNames;
    }

    void setItemNames(List<String> itemNames) {
        this.itemNames = itemNames;
    }

    HashMap<String, String> getItemNamesForOutput() {
        return itemNamesForOutput;
    }

    void setItemNamesForOutput(HashMap<String, String> itemNamesOutput) {
        this.itemNamesForOutput = itemNamesOutput;
    }
}

public class Item extends Objects {

    private double weight;

    // Standard constructor
    public Item() {

    }

    // constructor for Key, Map and potions
    public Item(String name, Integer[] coordinates, double weight) {
        super(name, coordinates);
        this.setWeight(weight);
    }

    // This method creates all non-weapon and non-backpack items
    public void createItem(Map map, String name, Objects objects, Integer[] coordinates, double weight) {

        map.addObjectToMap(name, coordinates);

    }

    // This Method puts the item to the inventory of the player and removes the item from the objectList
    public void pickUpItem(GameSettings gameSettings, Objects objects, Player player, Map map, Integer[] coordinates) {
        HashMap<String, Objects> itemsInThisRoom = new HashMap<String, Objects>();
        String key = null;
        String pickedUpItem;

        for (String string : map.getRooms().get(coordinates[0]).get(coordinates[1])) {
            Objects currentObject = objects.getObjectList().get(string);

            // backpack aufheben muss noch implementiert werden
            if (currentObject instanceof Item) {
                itemsInThisRoom.put(string, currentObject);
            }
        }

        switch (itemsInThisRoom.size()) {

        case 0:

            System.out.println("This room has no items which you can pick up");
            return;

        case 1:

            pickedUpItem = itemsInThisRoom.get(itemsInThisRoom.keySet().toArray()[0]).getName().replace(" ", "");
            key = itemsInThisRoom.keySet().toArray()[0].toString();

            if (player.getInventoryWeight(player) + ((Item) itemsInThisRoom.get(itemsInThisRoom.keySet().toArray()[0])).getWeight() //
                    > player.getMaxBackpackweight(player)) {
                System.out.println("You can't pick this item up because it's to heavy");
            } else {
                moveItemToBackpack(objects, player, map, pickedUpItem, key);
                return;
            }

            break;

        default:
            Names names = new Names();
            boolean itemIsInPlayerRoom = false;
            boolean itemIsTooHeavy = true;

            System.out.println("Which item do you want to pick up?");
            Scanner scan = new Scanner(System.in);
            pickedUpItem = scan.nextLine().toLowerCase().replace(" ", "");

            if (pickedUpItem.equals("noone")) {
                System.out.println("You picked nothing up");
                return;
            } else if (gameSettings.getCommandList().contains(pickedUpItem)) {
                gameSettings.outputForBasicCommands(pickedUpItem);
                pickUpItem(gameSettings, objects, player, map, coordinates);
            } else {

                for (Entry<String, Objects> entry : itemsInThisRoom.entrySet()) {
                    if (entry.getValue().getName().toLowerCase().replace(" ", "").equals(pickedUpItem)) {
                        key = entry.getKey();
                        itemIsInPlayerRoom = true;
                        if (player.getInventoryWeight(player) + ((Item) entry.getValue()).getWeight() <= player.getMaxBackpackweight(
                                player)) {
                            itemIsTooHeavy = false;
                        }
                    }
                }

                break;

            }

            if (names.getItemNames().contains(pickedUpItem)) {

                if (itemIsInPlayerRoom == false) {
                    System.out.println("This item isn't in this room");
                    System.out.println("Choose an item which is in this room");
                } else if (itemIsTooHeavy == true) {
                    System.out.println("This item is too heavy");
                    System.out.println("Choose an item which weight less");
                } else {
                    moveItemToBackpack(objects, player, map, pickedUpItem, key);
                    return;
                }

            } else {
                System.out.println("This isn't an item. Please enter a valid input");
                System.out.println(
                        "If you need a list with all items write 'item list' or if you don't want to pick up an item write 'no one'");
            }
            pickUpItem(gameSettings, objects, player, map, coordinates);
        }
    }

    private void moveItemToBackpack(Objects objects, Player player, Map map, String pickedUpItem, String key) {
        Names names = new Names();
        HashMap<String, Objects> newInventory = player.getInventory();
        HashMap<String, Objects> newObjectList = objects.getObjectList();
        ArrayList<ArrayList<ArrayList<String>>> newRooms = map.getRooms();
        newRooms.get(newObjectList.get(key).getCoordinates()[0])
                .get(newObjectList.get(key).getCoordinates()[1])
                .remove(map.searchIndexInRoomsList(key, newObjectList.get(key).getCoordinates()));
        map.setRooms(newRooms);

        newInventory.put(key, objects.getObjectList().get(key));
        newInventory.get(key).setCoordinates(new Integer[] {
                null, null
        });
        player.setInventory(newInventory);

        newObjectList.remove(key);
        objects.setObjectList(newObjectList);

        System.out.println("You picked up the " + names.getItemNamesForOutput().get(pickedUpItem));
    }

    public void putDownItem(GameSettings gameSettings, Objects objects, Player player, Map map, Integer[] coordinates) {
        HashMap<String, Objects> newInventory = player.getInventory();
        String key = null;
        String putDownItem;

        switch (newInventory.size()) {

        case 0:

            System.out.println("You don't have any items which you can put down");
            return;

        case 1:

            putDownItem = newInventory.get(newInventory.keySet().toArray()[0]).getName().replace(" ", "");
            key = newInventory.keySet().toArray()[0].toString();

            moveItemToRoom(objects, player, map, putDownItem, key);
            return;

        default:
            Names names = new Names();
            boolean itemIsInBackpack = false;

            System.out.println("Which item do you want to put down?");
            Scanner scan = new Scanner(System.in);
            putDownItem = scan.nextLine().toLowerCase().replace(" ", "");

            if (putDownItem.equals("noone")) {
                System.out.println("You picked nothing up");
                return;
            } else if (gameSettings.getCommandList().contains(putDownItem)) {
                gameSettings.outputForBasicCommands(putDownItem);
                putDownItem(gameSettings, objects, player, map, coordinates);
            } else {

                for (Entry<String, Objects> entry : newInventory.entrySet()) {
                    if (entry.getValue().getName().toLowerCase().replace(" ", "").equals(putDownItem)) {
                        key = entry.getKey();
                        itemIsInBackpack = true;
                    }
                }
            }

            if (names.getItemNames().contains(putDownItem)) {

                if (itemIsInBackpack == false) {
                    System.out.println("You don't have this item in your backpack");
                    System.out.println("Choose an item which is in your backpack");
                } else {
                    moveItemToRoom(objects, player, map, putDownItem, key);
                    return;
                }

            } else {
                System.out.println("This isn't an item. Please enter a valid input");
                System.out.println(
                        "If you need a list with all items write 'item list' or if you don't want to put down an item write 'no one'");
            }
            putDownItem(gameSettings, objects, player, map, coordinates);
        }
    }

    private void moveItemToRoom(Objects objects, Player player, Map map, String putDownItem, String key) {
        Names names = new Names();
        HashMap<String, Objects> newInventory = player.getInventory();
        HashMap<String, Objects> newObjectList = objects.getObjectList();
        ArrayList<ArrayList<ArrayList<String>>> newRooms = map.getRooms();
        Integer[] playerLocation = objects.getObjectList().get("player").getCoordinates();
        newObjectList.put(key, newInventory.get(key));
        newObjectList.get(key).setCoordinates(playerLocation);
        objects.setObjectList(newObjectList);

        newRooms.get(playerLocation[0]).get(playerLocation[1]).add(key);
        map.setRooms(newRooms);

        newInventory.remove(key);
        player.setInventory(newInventory);

        System.out.println("You put down the " + names.getItemNamesForOutput().get(putDownItem));
        return;

    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
