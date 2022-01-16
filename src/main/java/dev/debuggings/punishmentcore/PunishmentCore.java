package dev.debuggings.punishmentcore;

import dev.debuggings.punishmentcore.commands.*;
import dev.debuggings.punishmentcore.handler.BanHandler;
import dev.debuggings.punishmentcore.handler.MuteHandler;
import dev.debuggings.punishmentcore.utils.BanFilter;
import net.minecraft.server.v1_8_R3.LoginListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PunishmentCore extends JavaPlugin {

    public static PunishmentCore instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        ((Logger) LogManager.getLogger(LoginListener.class)).addFilter(new BanFilter());

        Bukkit.getConsoleSender().sendMessage("§8[§aPunishmentCore§8] §aPlugin loaded.");

        getCommand("warn").setExecutor(new Warn());
        getCommand("kick").setExecutor(new Kick());

        getCommand("mute").setExecutor(new Mute());
        getCommand("unmute").setExecutor(new Unmute());

        getCommand("tempban").setExecutor(new TempBan());
        getCommand("ban").setExecutor(new Ban());
        getCommand("unban").setExecutor(new Unban());

        getCommand("phistory").setExecutor(new PunishmentHistory());
        getCommand("getpunishment").setExecutor(new GetPunishment());

        Bukkit.getPluginManager().registerEvents(new BanHandler(), this);
        Bukkit.getPluginManager().registerEvents(new MuteHandler(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§8[§cPunishmentCore§8] §cPlugin unloaded.");
    }
}
