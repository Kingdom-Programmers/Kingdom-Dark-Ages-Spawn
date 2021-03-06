package spawnsystem.Subsystems;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import spawnsystem.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class StorageSubsystem {

    Main main = null;

    public StorageSubsystem(Main plugin) {
        main = plugin;
    }

    public void save() {
        saveSpawnFilenames();
        saveSpawns();
    }

    private void saveSpawnFilenames() {
        try {
            File saveFolder = new File("./plugins/Kingdom-Spawn-System/");
            if (!saveFolder.exists()) {
                saveFolder.mkdir();
            }
            File saveFile = new File("./plugins/Kingdom-Spawn-System/" + "spawn-filenames.txt");
            saveFile.createNewFile();

            FileWriter saveWriter = new FileWriter(saveFile);

            // actual saving takes place here
            for (int i = 0; i < main.playersWithSpawns.size(); i++) {
                saveWriter.write(main.playersWithSpawns.get(i) + ".txt" + "\n");
            }

            saveWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred while saving spawn filenames.");
        }
    }

    private void saveSpawns() {
        for (UUID playerName : main.playersWithSpawns) {

            try {
                File saveFolder = new File("./plugins/Kingdom-Spawn-System/");
                if (!saveFolder.exists()) {
                    saveFolder.mkdir();
                }
                File saveFile = new File("./plugins/Kingdom-Spawn-System/" + playerName + ".txt");
                saveFile.createNewFile();

                FileWriter saveWriter = new FileWriter("./plugins/Kingdom-Spawn-System/" + playerName + ".txt");

                // actual saving takes place here
                saveWriter.write(playerName.toString() + "\n");

                // save details
                saveWriter.write(main.playerSpawns.get(playerName).getWorld().getName() + "\n");
                saveWriter.write(main.playerSpawns.get(playerName).getX() + "\n");
                saveWriter.write(main.playerSpawns.get(playerName).getY() + "\n");
                saveWriter.write(main.playerSpawns.get(playerName).getZ() + "\n");

                saveWriter.close();

            } catch (IOException e) {
                System.out.println("An error occurred saving the record belonging to " + playerName);
                e.printStackTrace();
            }

        }
    }

    public void load () {
        loadSpawns();
    }

    private void loadSpawns() {
        try {
            File loadFile = new File("./plugins/Kingdom-Spawn-System/" + "spawn-filenames.txt");
            Scanner loadReader = new Scanner(loadFile);

            // actual loading
            while (loadReader.hasNextLine()) {
                String filename = loadReader.nextLine();

                // load
                try {
                    File loadFile2 = new File("./plugins/Kingdom-Spawn-System/" + filename);
                    Scanner loadReader2 = new Scanner(loadFile2);

                    UUID playerUUID = null;

                    // actual loading
                    if (loadReader2.hasNextLine()) {
                        playerUUID = UUID.fromString(loadReader2.nextLine());
                    }

                    World world = null;
                    double x = 0;
                    double y = 0;
                    double z = 0;

                    try {

                        if (loadReader2.hasNextLine()) {
                            world = main.getServer().createWorld(new WorldCreator(loadReader2.nextLine()));
                        }
                        else {
                            System.out.println("World name not found in file!");
                        }
                        if (loadReader2.hasNextLine()) {
                            x = Double.parseDouble(loadReader2.nextLine());
                        }
                        else {
                            System.out.println("X position not found in file!");
                        }
                        if (loadReader2.hasNextLine()) {//
                            y = Double.parseDouble(loadReader2.nextLine());
                        }
                        else {
                            System.out.println("Y position not found in file!");
                        }
                        if (loadReader2.hasNextLine()) {
                            z = Double.parseDouble(loadReader2.nextLine());
                        }
                        else {
                            System.out.println("Z position not found in file!");
                        }

                        // set location
                        if (world != null && x != 0 && y != 0 && z != 0) {
                            main.playerSpawns.put(playerUUID, new Location(world, x, y, z));
                            main.playersWithSpawns.add(playerUUID);
//                            System.out.println("Spawn of " + playerUUID + " successfully set to " + x + ", " + y + ", " + z + ".");
                        }
                        else {
                            System.out.println("One of the variables the spawn location depends on wasn't loaded!");
                        }

                    }
                    catch(Exception e) {
                        System.out.println("An error occurred loading the spawn position.");
                        e.printStackTrace();
                    }

                    loadReader2.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred loading the file " + filename + ".");
                    e.printStackTrace();
                }

            }

            loadReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading the spawns!");
        }
    }

}
