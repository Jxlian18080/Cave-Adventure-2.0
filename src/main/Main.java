package main;

import cheats.Cheats;
import enemy.Enemy;
import enemy.Trap;
import objects.Backpack;
import objects.Door;
import objects.Item;
import objects.Key;
import objects.Map;
import objects.Objects;
import objects.Potions;
import objects.Weapons;
import player.Player;

public class Main {

    public static void main(String[] args) {

        // Testkommentar123
        int testvar123 = 7;

        GameSettings gameSettings = new GameSettings();
        Objects objects = new Objects();
        Player player = new Player();
        Backpack backpack = new Backpack();
        Item item = new Item();
        Map map = new Map();
        Key key = new Key();
        Weapons weapons = new Weapons();
        Potions potions = new Potions();
        Door door = new Door();
        Enemy enemy = new Enemy();
        Trap trap = new Trap();
        Cheats cheats = new Cheats();

        System.out.println("Welcome to Cave Adventure. // Erklärung des Spiels");

        System.out.println("How big should be the map?");
        gameSettings.chooseMapSize(map);
        System.out.println("Which difficulty do you want? Easy, normal or hard");
        gameSettings.chooseDifficulty();
        map.createRooms();
        player.createPlayer(objects, map, "player", player.locationGeneratorForObjects(map), 100, 100);
        objects.createAllObjects(map, gameSettings, weapons, objects, door, key, potions, backpack, enemy, trap);

        while (gameSettings.isGameIsOver() == false) {
            player.playerAction(gameSettings, item, map, player, objects);
        }

        System.out.println("end of the code");
    }
}
