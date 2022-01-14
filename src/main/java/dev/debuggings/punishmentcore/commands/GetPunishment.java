package dev.debuggings.punishmentcore.commands;

import dev.debuggings.punishmentcore.utils.DatabaseUtils;
import dev.debuggings.punishmentcore.utils.NameFetcher;
import dev.debuggings.punishmentcore.utils.Permissions;
import dev.debuggings.punishmentcore.utils.Punishment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class GetPunishment implements CommandExecutor {

    private static final SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(Permissions.GET_PUNISHMENTS.get())) {
            sender.sendMessage("§cYou do not have permission to execute this command!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cIncorrect syntax. Correct: /getpunishment <id>");
            return true;
        }

        Punishment punishment = DatabaseUtils.getPunishment(args[0].toUpperCase());

        if (punishment == null) {
            sender.sendMessage("§cCan't find punishment by ID: '" + args[0].toUpperCase() + "'.");
            return true;
        }

        String expiresAt = "§4PERM";
        if (punishment.getExpiresAt() != -1) {
            expiresAt = "§e" + dt.format(new Date(punishment.getExpiresAt() * 1000L));
        }

        String punisher = "Console";
        if (!punishment.getPunisher().equals(new UUID(0, 0))) {
            punisher = NameFetcher.getNameOf(punishment.getPunisher());
        }

        sender.sendMessage("§8§m--------------------------");
        sender.sendMessage("§8#" + punishment.getId() + ":");
        sender.sendMessage("§7Type: §8" + punishment.getType());
        sender.sendMessage("§7Punished Player: §f" + NameFetcher.getNameOf(punishment.getPunished()));
        sender.sendMessage("§7Punished Player UUID: §f" + punishment.getPunished());
        sender.sendMessage("§7Punisher: §f" + punisher);
        if (punisher != null && !punisher.equals("Console"))
            sender.sendMessage("§7Punisher UUID: §f" + punishment.getPunisher());
        sender.sendMessage("§7Reason: §f" + punishment.getReason());
        sender.sendMessage("§7Issued At: §e" + dt.format(new Date(punishment.getIssuedAt() * 1000L)));
        sender.sendMessage("§7Expires At: §f" + expiresAt);
        sender.sendMessage("§7Revoked: " + (punishment.getRevoked() ? "§aYes" : "§cNo"));
        sender.sendMessage("§8§m--------------------------");

        return true;
    }
}
