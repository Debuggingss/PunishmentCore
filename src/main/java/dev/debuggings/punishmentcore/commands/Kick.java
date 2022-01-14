package dev.debuggings.punishmentcore.commands;

import dev.debuggings.punishmentcore.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Kick implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(Permissions.KICK.get())) {
            sender.sendMessage("§cYou do not have permission to execute this command!");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§cIncorrect syntax. Correct: /kick <name> <reason>");
            return true;
        }

        Player selected = Bukkit.getPlayerExact(args[0]);

        if (selected == null) {
            sender.sendMessage("§cCan't find player '" + args[0] + "'.");
            return true;
        }

        StringBuilder reason = new StringBuilder();

        for (int i = 1; i < args.length; ++i) {
            reason.append(args[i]).append(" ");
        }

        reason = new StringBuilder(reason.substring(0, reason.length() - 1));

        Punishment punishment = new Punishment(
            Punishment.PunishmentType.KICK,
            selected.getUniqueId(),
            (sender instanceof Player) ? ((Player) sender).getUniqueId() : new UUID(0,0),
            "",
            reason.toString(),
            System.currentTimeMillis() / 1000L,
            -1
        );

        DatabaseUtils.addPunishment(punishment);

        if (selected.isOnline()) {
            selected.kickPlayer(MessageUtils.getKick(reason.toString()));
        }

        sender.sendMessage("§aKicked '" + args[0] + "'.");

        return true;
    }
}
