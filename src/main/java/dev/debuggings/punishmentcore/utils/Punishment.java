package dev.debuggings.punishmentcore.utils;

import java.util.UUID;

public class Punishment {

    private final PunishmentType type;
    private final UUID punished;
    private final UUID punisher;
    private final String id;
    private final String reason;
    private final long issuedAt;
    private final long expiresAt;

    private boolean revoked;

    public Punishment (PunishmentType type, UUID punished, UUID punisher, String id,
                       String reason, long issuedAt, long expiresAt) {
        this.type = type;
        this.punished = punished;
        this.punisher = punisher;
        this.id = id;
        this.reason = reason;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.revoked = false;
    }

    public PunishmentType getType() {
        return type;
    }

    public UUID getPunished() {
        return punished;
    }

    public UUID getPunisher() {
        return punisher;
    }

    public String getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public long getIssuedAt() {
        return issuedAt;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public boolean getRevoked() {
        return revoked;
    }

    public void setRevoked(boolean b) {
        revoked = b;
    }

    public enum PunishmentType {
        BAN(0),
        MUTE(1),
        WARN(2),
        KICK(3);

        public final int i;

        PunishmentType(int i) {
             this.i = i;
        }

        public static Punishment.PunishmentType getById(int i) {
            for (Punishment.PunishmentType type : values()) {
                if (type.i == i) return type;
            }
            throw new IllegalArgumentException("Unknown punishment type: " + i);
        }
    }
}
