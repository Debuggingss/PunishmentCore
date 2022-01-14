package dev.debuggings.punishmentcore.handler;

import dev.debuggings.punishmentcore.PunishmentCore;
import dev.debuggings.punishmentcore.utils.DatabaseUtils;
import dev.debuggings.punishmentcore.utils.MessageUtils;
import dev.debuggings.punishmentcore.utils.Punishment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class MuteHandler implements Listener {

    private static final List<?> BLACKLISTED_CMDS = PunishmentCore.instance.getConfig().getList("mute-commands");

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (containsBlacklisted(event.getMessage())) {
            Player player = event.getPlayer();

            ArrayList<Punishment> punishments = DatabaseUtils.getPunishments(player.getUniqueId());
            punishments.forEach(punishment -> {
                if (punishment.getType() == Punishment.PunishmentType.MUTE) {
                    if (!punishment.getRevoked()) {
                        if (punishment.getExpiresAt() > (System.currentTimeMillis() / 1000L)) {
                            MessageUtils.sendMute(
                                player,
                                punishment.getId(),
                                punishment.getReason(),
                                punishment.getExpiresAt()
                            );
                            event.setCancelled(true);
                        }
                    }
                }
            });
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        ArrayList<Punishment> punishments = DatabaseUtils.getPunishments(player.getUniqueId());
        punishments.forEach(punishment -> {
            if (punishment.getType() == Punishment.PunishmentType.MUTE) {
                if (!punishment.getRevoked()) {
                    if (punishment.getExpiresAt() > (System.currentTimeMillis() / 1000L)) {
                        MessageUtils.sendMute(
                            player,
                            punishment.getId(),
                            punishment.getReason(),
                            punishment.getExpiresAt()
                        );
                        event.setCancelled(true);
                    }
                }
            }
        });
    }

    private static boolean containsBlacklisted(String msg) {
        for (Object cmd : BLACKLISTED_CMDS) {
            if (msg.startsWith((String) cmd)) return true;
        }
        return false;
    }
}
