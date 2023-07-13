package player;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import main.GameSettings;
import objects.Item;
import objects.Map;
import objects.Objects;

public class Player extends Objects {

    private int HP;

    private int maxHP;

    private HashMap<String, Objects> inventory = new HashMap<String, Objects>();

    private String playerBackpack = "small backpack";

    private Scanner scan = new Scanner(System.in);

    public Player() {

    }

    public Player(String name, Integer[] coordinates, int HP, int maxHP) {
        super(name, coordinates);
        this.setHP(HP);
        this.maxHP = maxHP;
    }

    public void createPlayer(Objects objects, Map map, String name, Integer[] coordinates, int HP, int maxHP) {
        String hashMapKey = "player";

        HashMap<String, Objects> newObjectList = objects.getObjectList();
        newObjectList.put(hashMapKey, new Player(name, coordinates, HP, maxHP));
        objects.setObjectList(newObjectList);
        map.addObjectToMap(hashMapKey, coordinates);
    }

    // This Method checks the user Input and react to it
    public void playerAction(GameSettings gameSettings, Item item, Map map, Player player, Objects objects) {

        String userInput = scan.nextLine().toLowerCase().replace(" ", "");

        switch (userInput) {

        case "gonorth":

            playerIsMoving(objects, map, "north", 0, -1);
            break;

        case "goeast":

            playerIsMoving(objects, map, "east", 1, 0);
            break;

        case "gosouth":

            playerIsMoving(objects, map, "south", 0, 1);
            break;

        case "gowest":

            playerIsMoving(objects, map, "west", -1, 0);
            break;

        // just for debugging, will be removed for the final project
        case "showcoordinates":

            System.out.println(objects.getObjectList().get("player").getCoordinates()[0]);
            System.out.println(objects.getObjectList().get("player").getCoordinates()[1]);
            break;

        case "pickup":

            item.pickUpItem(gameSettings, objects, player, map, objects.getObjectList().get("player").getCoordinates());
            break;

        case "putdown":

            item.putDownItem(gameSettings, objects, player, map, objects.getObjectList().get("player").getCoordinates());
            break;

        case "showbackpack":

            outputInventory(player);
            break;

        default:
            System.out.println("This isn't a command");
            break;

        }
    }

    /*
     * This Method checks if the player can move to the chosen room and moves the player
     *
     * Returned value: true = the player moved, false = the player is still on the position he was before
     */
    public void playerIsMoving(Objects objects, Map map, String direction, int xMovingDistance, int yMovingDistance) {
        Integer[] playerLocation = objects.getObjectList().get("player").getCoordinates();
        boolean playerMoved = false;

        if (map.isRoomOutOfMap(objects, map, "player", xMovingDistance, yMovingDistance) == false) {

            // Checks if the target room has objects
            boolean hasRoomObjects = map.hasRoomObjects(map, new Integer[] {
                    playerLocation[0] + xMovingDistance, playerLocation[1] + yMovingDistance
            });

            objectMoves(objects, map, "player", xMovingDistance, yMovingDistance);

            System.out.println("You moved to the room in the " + direction);

            map.outputRoom(objects, map, playerLocation, hasRoomObjects);

            playerMoved = true;

        } else {
            System.out.println("There is a wall. You can't go there!");
        }

        // return playerMoved;
    }

    public double getMaxBackpackweight(Player player) {

        switch (player.playerBackpack) {
        case "small backpack":
            return 2;

        case "medium backpack":
            return 3;

        case "large backpack":
            return 4;

        default:
            System.err.println("No backpack found at Player:" + new Throwable().getStackTrace()[0].getLineNumber());
            return 0;
        }
    }

    public void outputInventory(Player player) {
        System.out.println("You have in your backpack:");

        for (Entry<String, Objects> entry : player.getInventory().entrySet()) {
            System.out.println(entry.getValue().getName());
        }
    }

    public double getInventoryWeight(Player player) {
        double weight = 0;
        for (Entry<String, Objects> entry : player.getInventory().entrySet()) {
            weight += ((Item) entry.getValue()).getWeight();
        }
        return weight;
    }

    public HashMap<String, Objects> getInventory() {
        return inventory;
    }

    public void setInventory(HashMap<String, Objects> inventory) {
        this.inventory = inventory;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int hP) {
        HP = hP;
    }

}
