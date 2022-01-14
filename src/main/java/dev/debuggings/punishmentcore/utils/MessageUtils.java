package dev.debuggings.punishmentcore.utils;

import dev.debuggings.punishmentcore.PunishmentCore;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class MessageUtils {

    public static final String CHAT_SEPARATOR = "§c§m-----------------------------------------------------------";

    public static String getPermBan(String id, String reason) {
        return "§cYou are permanently banned from this server!\n\n" +
            "§7Reason: §f" + reason + "\n" +
            "§7Find out more: §b§n" + PunishmentCore.instance.getConfig().getString("ban-domain") + "\n\n" +
            "§7Ban ID: §f#" + id + "\n" +
            "§7Sharing your Ban ID may affect the processing of your appeal!";
    }

    public static String getTempBan(String id, String reason, long expiresAt) {
        String timeLeft = calculateTime(expiresAt - (System.currentTimeMillis() / 1000L));

        return "§cYou are temporarily banned for §f" + timeLeft + " §cfrom this server!\n\n" +
            "§7Reason: §f" + reason + "\n" +
            "§7Find out more: §b§n" + PunishmentCore.instance.getConfig().getString("ban-domain") + "\n\n" +
            "§7Ban ID: §f#" + id + "\n" +
            "§7Sharing your Ban ID may affect the processing of your appeal!";
    }

    public static String getKick(String reason) {
        return "§cYou have been kicked!\n\n" +
            "§7Reason: §f" + reason + "\n" +
            "§7Find out more: §b§n" + PunishmentCore.instance.getConfig().getString("ban-domain") + "";
    }

    public static void sendWarn(Player target, String reason) {
        target.sendMessage("");
        target.sendMessage(MessageUtils.CHAT_SEPARATOR);
        target.sendMessage("§cYou have received a warning for " + reason);
        target.sendMessage("");
        target.sendMessage("§7Find out more here: §e" +
            PunishmentCore.instance.getConfig().getString("mute-domain"));
        target.sendMessage(MessageUtils.CHAT_SEPARATOR);
        target.sendMessage("");
    }

    public static void sendMute(Player target, String id, String reason, long expiresAt) {
        String timeLeft = calculateTime(expiresAt - (System.currentTimeMillis() / 1000L));

        target.sendMessage("");
        target.sendMessage(MessageUtils.CHAT_SEPARATOR);
        target.sendMessage("§cYou are currently muted for " + reason);
        target.sendMessage("§7Your mute will expire in §c" + timeLeft);
        target.sendMessage("");
        target.sendMessage("§7Find out more here: §e" +
            PunishmentCore.instance.getConfig().getString("mute-domain"));
        target.sendMessage("§7Mute ID: §f#" + id);
        target.sendMessage(MessageUtils.CHAT_SEPARATOR);
        target.sendMessage("");
    }

    public static String calculateTime(final long seconds) {
        final int days = (int) TimeUnit.SECONDS.toDays(seconds);
        final long hours = TimeUnit.SECONDS.toHours(seconds) - days * 24L;
        final long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60L;
        final long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60L;

        return (" " + days + "d " + hours + "h " + minute + "m " + second + "s")
            .replace(" 0d", "")
            .replace(" 0h", "")
            .replace(" 0m", "")
            .replace(" 0s", "")
            .replaceFirst(" ", "");
    }
}
