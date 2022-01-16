package dev.debuggings.punishmentcore.handler;

import dev.debuggings.punishmentcore.utils.DatabaseUtils;
import dev.debuggings.punishmentcore.utils.MessageUtils;
import dev.debuggings.punishmentcore.utils.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;

public class BanHandler implements Listener {

    @EventHandler
    public void onJoin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        ArrayList<Punishment> punishments = DatabaseUtils.getPunishments(player.getUniqueId());
        punishments.forEach(punishment -> {
            if (punishment.getType() == Punishment.PunishmentType.BAN) {
                if (!punishment.getRevoked()) {
                    if (punishment.getExpiresAt() == -1) {
                        event.setKickMessage(MessageUtils.getPermBan(punishment.getId(), punishment.getReason()));
                        event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
                    }
                    else if (punishment.getExpiresAt() > (System.currentTimeMillis() / 1000L)) {
                        event.setKickMessage(MessageUtils.getTempBan(
                            punishment.getId(),
                            punishment.getReason(),
                            punishment.getExpiresAt()
                        ));
                        event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
                    }
                    Bukkit.getConsoleSender().sendMessage(
                        player.getName() + " (" + event.getAddress() + ") attempted to join while banned. Ban ID: #"
                            + punishment.getId()
                    );
                }
            }
        });
    }
}
