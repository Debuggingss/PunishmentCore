package dev.debuggings.punishmentcore.commands;

import dev.debuggings.punishmentcore.utils.DatabaseUtils;
import dev.debuggings.punishmentcore.utils.Permissions;
import dev.debuggings.punishmentcore.utils.Punishment;
import dev.debuggings.punishmentcore.utils.UUIDFetcher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class Unmute implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(Permissions.UNMUTE.get())) {
            sender.sendMessage("§cYou do not have permission to execute this command!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cIncorrect syntax. Correct: /unmute <name>");
            return true;
        }

        UUID target = UUIDFetcher.getUUIDOf(args[0]);

        if (target == null) {
            sender.sendMessage("§cCan't find player '" + args[0] + "'.");
            return true;
        }

        DatabaseUtils.getPunishments(target).forEach(punishment -> {
            if (punishment.getType() == Punishment.PunishmentType.MUTE) {
                DatabaseUtils.setRevoked(punishment.getId(), true);
            }
        });

        sender.sendMessage("§aUnmute '" + args[0] + "'.");

        return true;
    }
}
