package dev.debuggings.punishmentcore.commands;

import dev.debuggings.punishmentcore.utils.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PunishmentHistory implements CommandExecutor {

    private static final SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(Permissions.GET_PUNISHMENTS.get())) {
            sender.sendMessage("§cYou do not have permission to execute this command!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cIncorrect syntax. Correct: /phistory <name>");
            return true;
        }

        UUID target = UUIDFetcher.getUUIDOf(args[0]);

        if (target == null) {
            sender.sendMessage("§cCan't find player '" + args[0] + "'.");
            return true;
        }

        sender.sendMessage("§8§m--------------------------");
        sender.sendMessage("§7" + args[0] + "'s Punishment History:");
        DatabaseUtils.getPunishments(target).forEach(punishment -> {
            String issuedAt = "§e" + dt.format(new Date(punishment.getIssuedAt() * 1000L));

            String expiresAt = "§4PERM";
            if (punishment.getExpiresAt() != -1) {
                expiresAt = "§e" + dt.format(new Date(punishment.getExpiresAt() * 1000L));
            }

            String id = "";
            if (punishment.getType() != Punishment.PunishmentType.KICK) {
                id = " §7ID: §f#" + punishment.getId();
            }

            String type = "§8" + punishment.getType().toString();

            String revoked = " §7Revoked: " + (punishment.getRevoked() ? "§aYes" : "§cNo");

            sender.sendMessage(type + "§7: " + issuedAt + " §7TO §e" + expiresAt + id + revoked);
        });
        sender.sendMessage("§8§m--------------------------");

        return true;
    }
}
