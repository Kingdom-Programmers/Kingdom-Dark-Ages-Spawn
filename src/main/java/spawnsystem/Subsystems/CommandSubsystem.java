package spawnsystem.Subsystems;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import spawnsystem.Main;

public class CommandSubsystem {

    Main main = null;

    public CommandSubsystem(Main plugin) {
        main = plugin;
    }

    public boolean interpretCommand(CommandSender sender, String label, String[] args) {

        if (label.equalsIgnoreCase("resetspawn")) {

            // if sender instanceof player
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (args.length > 0) {
                    if (player.hasPermission("spawnsystem.reset.others") || player.hasPermission("spawnsystem.admin")) {
                        // reset a specific player's spawn
                        String targetPlayer = args[0];
                        main.utilities.resetSpawn(UtilitySubsystem.findUUIDBasedOnPlayerName(targetPlayer));
                        player.sendMessage(ChatColor.GREEN + "Spawn reset for " + targetPlayer + "!");
                        try {
                            main.getServer().getPlayer(targetPlayer).sendMessage(ChatColor.GREEN + "Your spawn has been reset!");
                        } catch(Exception e) {

                        }
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Sorry! In order to use this command, you need the following permission: 'spawnsystem.reset.others'");
                    }

                }
                else {
                    if (player.hasPermission("spawnsystem.reset.self") || player.hasPermission("spawnsystem.admin")) {
                        // reset the spawn of the command sender
                        main.utilities.resetSpawn(UtilitySubsystem.findUUIDBasedOnPlayerName(player.getName()));
                        player.sendMessage(ChatColor.GREEN + "You have reset your spawn!");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Sorry! In order to use this command, you need the following permission: 'spawnsystem.reset.self'");
                    }
                }
            }

        }

        return false;
    }

}
