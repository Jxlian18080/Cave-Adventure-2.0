package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import enemy.Enemy;
import enemy.Trap;
import main.GameSettings;

public class Objects {

    private String name;

    // The type is Integer that the values can be null
    // coordinates[0] = x Coordinate
    // coordinates[1] = y Coordinate
    private Integer[] coordinates = new Integer[2];

    private HashMap<String, Objects> objectList = new HashMap<String, Objects>();

    private Random randomNumber = new Random();

    public Objects() {

    }

    public Objects(String name, Integer[] coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public void createAllObjects(Map map, GameSettings gameSettings, Weapons weapons, Objects objects, Door door, Key key, Potions potions,
            Backpack backpack, Enemy enemy, Trap trap) {
        int numberKeys = map.getMapSize() * map.getMapSize() / 16;
        if (numberKeys == 0) {
            numberKeys = 1;
        }

        int numberDoors = numberKeys;

        // This calculates the number of empty rooms. The "1" is the room for the player
        int numberEmptyRooms = map.getMapSize() * map.getMapSize() - (1 + numberKeys + numberDoors);

        int calculationsVar = numberEmptyRooms / 7;
        int remainingFields = numberEmptyRooms % 7;
        int numberWeapons = calculationsVar * 2;
        int numberMaps = calculationsVar * 2;
        int numberPotions = calculationsVar;
        int numberBackpacks = calculationsVar;
        int numberTraps = calculationsVar;
        int numberOrgs = (map.getMapSize() * map.getMapSize()) / (5 - gameSettings.getDifficulty());

        switch (remainingFields) {
        case 6:
            numberWeapons += 1;
        case 5:
            numberTraps += 1;
        case 4:
            numberBackpacks += 1;
        case 3:
            numberMaps += 1;
        case 2:
            numberPotions += 1;
        case 1:
            numberWeapons += 1;
        }

        createBasicKeys(objects, key, map, numberKeys);
        createBasicDoors(objects, door, map, numberDoors);
        createBasicWeapons(objects, weapons, map, gameSettings, numberWeapons);
        createBasicMaps(objects, map, numberMaps);
        createBasicPotions(objects, potions, map, gameSettings, numberPotions);
        createBasicBackpacks(objects, backpack, map, gameSettings, numberBackpacks);
        createBasicTraps(objects, trap, map, gameSettings, numberTraps);
        createBasicOrgs(objects, weapons, enemy, map, gameSettings, numberOrgs);
    }

    // This Method creates all the keys
    public void createBasicKeys(Objects objects, Key key, Map map, int numberKeys) {

        for (int i = 0; i < numberKeys; i++) {
            key.createItem(map, "key", objects, locationGeneratorForObjects(map), 0.2f);
        }
    }

    // This Method creates all the doors
    public void createBasicDoors(Objects objects, Door door, Map map, int numberDoors) {

        for (int i = 0; i < numberDoors; i++) {
            door.createObject(map, objects, "door", locationGeneratorForObjects(map));
        }
    }

    // This Method creates all the weapons
    public void createBasicWeapons(Objects objects, Weapons weapons, Map map, GameSettings gameSettings, int numberWeapons) {

        String objectName;
        double weight;
        int attackDamage;
        int criticalHitDamageBoost;

        for (int i = 0; i < numberWeapons; i++) {

            // This calculates the spawn rates for small, normal and heavy Axes based on the difficulty
            switch (randomNumber.nextInt(6) - gameSettings.getDifficulty()) {

            case 1:
            case 2:
                objectName = "normal axe";
                weight = 1f;
                attackDamage = 25;
                criticalHitDamageBoost = 15;

                break;
            case 3:
            case 4:
            case 5:
                objectName = "heavy axe";
                weight = 1.5f;
                attackDamage = 35;
                criticalHitDamageBoost = 20;

                break;

            default:
                objectName = "small axe";
                weight = 0.5f;
                attackDamage = 15;
                criticalHitDamageBoost = 10;
                break;
            }
            weapons.createItem(map, objectName, objects, locationGeneratorForObjects(map), weight, attackDamage, //
                    criticalHitDamageBoost, true);
        }
    }

    // This Method creates all the maps
    public void createBasicMaps(Objects objects, Map map, int numberMaps) {

        for (int i = 0; i < numberMaps; i++) {
            map.createItem(map, "map", objects, locationGeneratorForObjects(map), 0.3f);
        }
    }

    // This Method creates all the potions
    public void createBasicPotions(Objects objects, Potions potions, Map map, GameSettings gameSettings, int numberPotions) {

        String objectName;

        for (int i = 0; i < numberPotions; i++) {

            if (randomNumber.nextInt(4) - gameSettings.getDifficulty() <= 0) {
                objectName = "mystery potion";
            } else {

                if (randomNumber.nextInt(2) == 0) {
                    objectName = "heal potion";
                } else {
                    objectName = "regeneration potion";
                }
            }

            potions.createItem(map, objectName, objects, locationGeneratorForObjects(map), 0.6f);
        }
    }

    // This Method creates all the backpacks
    public void createBasicBackpacks(Objects objects, Backpack backpack, Map map, GameSettings gameSettings, int numberBackpacks) {

        String objectName;

        for (int i = 0; i < numberBackpacks; i++) {

            // This calculates the spawn rates for medium and large backpacks based on the difficulty
            switch (randomNumber.nextInt(6) - gameSettings.getDifficulty()) {

            case 3:
            case 4:
            case 5:
                objectName = "large backpack";
                break;

            default:
                objectName = "medium backpack";
                break;
            }

            backpack.createItem(map, objectName, objects, locationGeneratorForObjects(map), 0);

        }
    }

    // This Method creates all the traps
    public void createBasicTraps(Objects objects, Trap trap, Map map, GameSettings gameSettings, int numberTraps) {

        int damage;

        for (int i = 0; i < numberTraps; i++) {

            switch (randomNumber.nextInt(6) - gameSettings.getDifficulty()) {

            case 1:
            case 2:
                damage = 15;
                break;

            case 3:
            case 4:
            case 5:
                damage = 10;
                break;

            default:
                damage = 20;
                break;

            }

            trap.createTrap(map, "trap", objects, locationGeneratorForObjects(map), damage);
        }
    }

    // This Method creates all the orgs and the weapons for the orgs
    public void createBasicOrgs(Objects objects, Weapons weapons, Enemy enemy, Map map, GameSettings gameSettings, int numberOrgs) {

        HashMap<String, Objects> newObjectList = objects.getObjectList();

        String orgName;
        String weaponName = null;
        String weaponKey;
        double weight = 0;
        for (int i = 0; i < numberOrgs; i++) {
            int attackDamage = 0;
            int criticalHitDamageBoost = 0;
            int HP;
            int maxHP;

            if (gameSettings.generateRandomNumber(new int[][] {
                    {
                            20, 80
                    }, {
                            25, 75
                    }, {
                            35, 65
                    }
            }) == 0) {

                weaponName = "legendary sword";
                weight = 4f;
                attackDamage = 45;
                criticalHitDamageBoost = 20;
                weaponKey = weapons.createItem(map, weaponName, objects, new Integer[] {
                        null, null
                }, weight, attackDamage, criticalHitDamageBoost, false);

                orgName = "giant org";
                HP = 100;
                maxHP = 100;

            } else {

                // if (gameSettings.calculateRandomNumberBasedOnDifficulty(50, 35, 20)) {
                //
                // } else if (gameSettings.calculateRandomNumberBasedOnDifficulty(70, 70, 20)) {
                //
                // } else {
                //
                // }

                switch (gameSettings.generateRandomNumber(new int[][] {
                        {
                                50, 35, 15
                        }, {
                                35, 40, 25
                        }, {
                                20, 45, 35
                        }
                })) {

                case 0:
                    weaponName = "small axe";
                    weight = 0.5f;
                    attackDamage = 15;
                    criticalHitDamageBoost = 5;
                    break;

                case 1:
                    weaponName = "normal axe";
                    weight = 1f;
                    attackDamage = 25;
                    criticalHitDamageBoost = 10;
                    break;

                case 2:
                    weaponName = "heavy axe";
                    weight = 1.5f;
                    attackDamage = 35;
                    criticalHitDamageBoost = 15;
                    break;

                default:
                    System.err.println("Error at Objects:" + Thread.currentThread().getStackTrace()[0].getLineNumber());
                    System.exit(0);
                }

                weaponKey = weapons.createItem(map, weaponName, objects, new Integer[] {
                        null, null
                }, weight, attackDamage, criticalHitDamageBoost, false);

                orgName = "org";
                HP = 35;
                maxHP = 35;
            }

            Integer[] orgCoordinates = locationGeneratorForEnemies(map);
            enemy.createEnemy(map, orgName, objects, orgCoordinates, HP, maxHP, newObjectList.get(weaponKey));
            ((Weapons) newObjectList.get(weaponKey)).setIsAttachedTo(2);
            newObjectList.get(weaponKey).setCoordinates(orgCoordinates);
            objects.setObjectList(newObjectList);

        }
    }

    // This Method generates the locations for the Objects
    public Integer[] locationGeneratorForObjects(Map map) {
        int xCoordinate = 0;
        int yCoordinate = 0;
        boolean examination = false;

        while (examination == false) {
            xCoordinate = randomNumber.nextInt(map.getMapSize());
            yCoordinate = randomNumber.nextInt(map.getMapSize());

            if (map.getRooms().get(xCoordinate).get(yCoordinate).isEmpty()) {
                coordinates[0] = xCoordinate;
                coordinates[1] = yCoordinate;
                examination = true;
            }
        }

        return new Integer[] {
                xCoordinate, yCoordinate
        };
    }

    // This Method generates the locations for the Enemies
    public Integer[] locationGeneratorForEnemies(Map map) {
        int xCoordinate = 0;
        int yCoordinate = 0;
        boolean validField = false;

        while (validField == false) {
            xCoordinate = randomNumber.nextInt(map.getMapSize());
            yCoordinate = randomNumber.nextInt(map.getMapSize());

            validField = true;

            for (String string : map.getRooms().get(xCoordinate).get(yCoordinate)) {

                if (string.toLowerCase().contains("org") || //
                        string.toLowerCase().contains("key") || //
                        string.toLowerCase().contains("door") || //
                        string.toLowerCase().contains("player") || //
                        string.toLowerCase().contains("trap")) {
                    validField = false;
                }
            }

        }

        return new Integer[] {
                xCoordinate, yCoordinate
        };
    }

    // This Method creates keys for the hashMap "objectList"
    public String createHashMapKeyForObjectList(Objects objects, String name) {
        boolean validKey = false;
        int counter = 0;

        while (validKey == false) {
            if (objects.getObjectList().containsKey(name.replace(" ", "") + counter)) {
                counter++;
            } else {
                validKey = true;
            }
        }

        return name.replace(" ", "") + counter;
    }

    /*
     * This Method calculates where the specified object moves and change the location in the rooms and objectList for the specified object
     * 
     * xMovingDistance and yMovingDistance are distances how far the object moves. Negative values are for north and west and positive
     * values are for south and east
     * 
     * Returned values: true = object moved; false = object stayed on his position
     */
    public void objectMoves(Objects objects, Map map, String movingObject, int xMovingDistance, int yMovingDistance) {
        ArrayList<ArrayList<ArrayList<String>>> newRooms = map.getRooms();
        HashMap<String, Objects> newObjectList = objects.getObjectList();

        newRooms.get(newObjectList.get(movingObject).getCoordinates()[0] + xMovingDistance)
                .get(newObjectList.get(movingObject).getCoordinates()[1] + yMovingDistance)
                .add(newRooms.get(newObjectList.get(movingObject).getCoordinates()[0])
                        .get(newObjectList.get(movingObject).getCoordinates()[1])
                        .get(map.searchIndexInRoomsList(movingObject, newObjectList.get(movingObject).getCoordinates())));

        newRooms.get(newObjectList.get(movingObject).getCoordinates()[0])
                .get(newObjectList.get(movingObject).getCoordinates()[1])
                .remove(map.searchIndexInRoomsList(movingObject, newObjectList.get(movingObject).getCoordinates()));

        map.setRooms(newRooms);

        newObjectList.get(movingObject).getCoordinates()[0] += xMovingDistance;
        newObjectList.get(movingObject).getCoordinates()[1] += yMovingDistance;
        setObjectList(newObjectList);

    }

    public Integer[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Integer[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Objects> getObjectList() {
        return objectList;
    }

    public void setObjectList(HashMap<String, Objects> objectList) {
        this.objectList = objectList;
    }

}
