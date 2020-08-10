package spawnsystem.EventHandlers;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.player.PlayerInteractEvent;
import spawnsystem.Main;

public class PlayerInteractEventHandler {

    Main main = null;

    public PlayerInteractEventHandler(Main plugin) {
        main = plugin;
    }

    public void handle(PlayerInteractEvent event) {
        // if player is right clicking a block
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock != null) {
            // if that block is a sign
            if (main.utilities.isSign(clickedBlock)) {
                // if that sign has [Spawn]
                Sign sign = (Sign) clickedBlock;
                if (sign.getLine(0).contains("[Spawn]")) {
                    // acquire coordinates
                    int x = Integer.parseInt(sign.getLine(1));
                    int y = Integer.parseInt(sign.getLine(2));;
                    int z = Integer.parseInt(sign.getLine(3));;
                    World world = null;

                    // set player's spawn
                    main.utilities.setPlayersSpawn(event.getPlayer(), world, x, y, z);
                }
            }
        }
    }

}
