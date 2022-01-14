package dev.debuggings.punishmentcore.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class DatabaseUtils {

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS punishments " +
        "(type INT, punished STRING, punisher STRING, id STRING, " +
        "reason STRING, issuedAt LONG, expiresAt LONG, revoked BOOL)";
    private static final String ADD_PUNISHMENT = "INSERT INTO punishments VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PUNISHMENT = "UPDATE punishments SET revoked = ? WHERE id = ?";
    private static final String LOAD_PUNISHMENTS = "SELECT * FROM punishments WHERE punished = ?";
    private static final String LOAD_PUNISHMENT = "SELECT * FROM punishments WHERE id = ?";

    public static void addPunishment(Punishment punishment) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:punishments.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(10);

            statement.executeUpdate(CREATE_TABLE);

            PreparedStatement pstmt = connection.prepareStatement(ADD_PUNISHMENT);

            pstmt.setInt(1, punishment.getType().i);
            pstmt.setString(2, punishment.getPunished().toString());
            pstmt.setString(3, punishment.getPunisher().toString());
            pstmt.setString(4, punishment.getId());
            pstmt.setString(5, punishment.getReason());
            pstmt.setLong(6, punishment.getIssuedAt());
            pstmt.setLong(7, punishment.getExpiresAt());
            pstmt.setBoolean(8, punishment.getRevoked());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Punishment> getPunishments(UUID player) {
        Connection connection = null;

        ArrayList<Punishment> punishments = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:punishments.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(10);

            statement.executeUpdate(CREATE_TABLE);

            PreparedStatement pstmt = connection.prepareStatement(LOAD_PUNISHMENTS);
            pstmt.setString(1, player.toString());
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Punishment p = new Punishment(
                    Punishment.PunishmentType.getById(rs.getInt("type")),
                    UUID.fromString(rs.getString("punished")),
                    UUID.fromString(rs.getString("punisher")),
                    rs.getString("id"),
                    rs.getString("reason"),
                    rs.getLong("issuedAt"),
                    rs.getLong("expiresAt")
                );
                p.setRevoked(rs.getBoolean("revoked"));

                punishments.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return punishments;
    }

    public static void setRevoked(String id, boolean revoked) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:punishments.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(10);

            statement.executeUpdate(CREATE_TABLE);

            PreparedStatement pstmt = connection.prepareStatement(UPDATE_PUNISHMENT);

            pstmt.setBoolean(1, revoked);
            pstmt.setString(2, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Punishment getPunishment(String id) {
        Connection connection = null;

        Punishment punishment = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:punishments.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(10);

            statement.executeUpdate(CREATE_TABLE);

            PreparedStatement pstmt = connection.prepareStatement(LOAD_PUNISHMENT);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                punishment = new Punishment(
                    Punishment.PunishmentType.getById(rs.getInt("type")),
                    UUID.fromString(rs.getString("punished")),
                    UUID.fromString(rs.getString("punisher")),
                    rs.getString("id"),
                    rs.getString("reason"),
                    rs.getLong("issuedAt"),
                    rs.getLong("expiresAt")
                );
                punishment.setRevoked(rs.getBoolean("revoked"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return punishment;
    }

    public static boolean hasActiveBan(UUID player) {
        final boolean[] activeBan = { false };

        getPunishments(player).forEach(punishment -> {
            if (punishment.getType() == Punishment.PunishmentType.BAN) {
                if (!punishment.getRevoked()) {
                    if (
                        punishment.getExpiresAt() == -1 ||
                        punishment.getExpiresAt() > (System.currentTimeMillis() / 1000L)
                    ) {
                        activeBan[0] = true;
                    }
                }
            }
        });

        return activeBan[0];
    }

    public static boolean hasActiveMute(UUID player) {
        final boolean[] activeMute = { false };

        getPunishments(player).forEach(punishment -> {
            if (punishment.getType() == Punishment.PunishmentType.MUTE) {
                if (!punishment.getRevoked()) {
                    if (punishment.getExpiresAt() > (System.currentTimeMillis() / 1000L)) {
                        activeMute[0] = true;
                    }
                }
            }
        });

        return activeMute[0];
    }
}
