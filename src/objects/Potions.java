package objects;

import java.util.HashMap;
import java.util.Scanner;

import player.Player;

public class Potions extends Item {

    public Potions() {

    }

    public Potions(String name, Integer[] coordinates, double weight) {
        super(name, coordinates, weight);
    }

    @Override
    public void createItem(Map map, String name, Objects objects, Integer[] coordinates, double weight) {
        String hashMapKey = createHashMapKeyForObjectList(objects, name);

        HashMap<String, Objects> newObjectList = objects.getObjectList();
        newObjectList.put(hashMapKey, new Potions(name, coordinates, weight));
        objects.setObjectList(newObjectList);
        super.createItem(map, hashMapKey, objects, coordinates, weight);
    }

    public void usePotion(Objects objects, Player player) {
        @SuppressWarnings("resource")
        Scanner scan = new Scanner(System.in);
        System.out.println("Which potion do you want to use");
        String userInput = scan.nextLine().toLowerCase().replace(" ", "");
        boolean validInput = true;

        do {

            switch (userInput) {

            case "healpotion":
                useHealPotion(objects, player);
                break;

            case "regenerationpotion":
                useRegenerationPotion(objects, player);
                break;

            case "mystery potion":
                useMysteryPotion(objects, player);
                break;

            case "noone":
                System.out.println("You didn't use a potion");
                break;

            default:
                validInput = false;
                break;

            }

        } while (validInput == false);

    }

    private void useHealPotion(Objects objects, Player player) {
        getHealEffect(objects, player, "player");
        System.out.println("You got healed and have now " + ((Player) objects.getObjectList().get("player")).getHP());
    }

    private void useRegenerationPotion(Objects objects, Player player) {
        getRegenerationEffect(objects, player, "player");
    }

    private void useMysteryPotion(Objects objects, Player player) {

    }

    private void getHealEffect(Objects objects, Player player, String entityKey) {
        HashMap<String, Objects> objectList = objects.getObjectList();
        ((Player) objectList.get("player")).setHP(player.getHP() + 20);
        objects.setObjectList(objectList);
    }

    private void getRegenerationEffect(Objects objects, Player player, String entityKey) {

    }

    private void getDamageEffect(Objects objects, Player player, String entityKey) {
        HashMap<String, Objects> objectList = objects.getObjectList();
        ((Player) objectList.get("player")).setHP(player.getHP() - 20);
        objects.setObjectList(objectList);
    }

    private void getPoisonEffect(Objects objects, Player player, String entityKey) {

    }

}
