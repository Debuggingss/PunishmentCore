package dev.debuggings.punishmentcore.utils;

public enum Permissions {

    WARN("punishmentcore.warn"),
    KICK("punishmentcore.kick"),
    MUTE("punishmentcore.mute"),
    UNMUTE("punishmentcore.unmute"),
    TEMPBAN("punishmentcore.tempban"),
    BAN("punishmentcore.ban"),
    UNBAN("punishmentcore.unban"),
    GET_PUNISHMENTS("punishmentcore.getpunishments");

    public final String permission;

    Permissions(String perm) {
        this.permission = perm;
    }

    public String get() {
        return this.permission;
    }
}
