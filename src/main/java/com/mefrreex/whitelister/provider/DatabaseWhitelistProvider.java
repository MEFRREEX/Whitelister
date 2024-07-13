package com.mefrreex.whitelister.provider;

import com.mefrreex.jooq.database.IDatabase;
import com.mefrreex.whitelister.whitelist.Whitelist;
import com.mefrreex.whitelister.whitelist.WhitelistPlayerManager;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DatabaseWhitelistProvider implements WhitelistProvider {

    private final IDatabase database;
    private final Table<?> settingsTable;
    private final Table<?> playersTable;

    public DatabaseWhitelistProvider(IDatabase database) {
        this.database = database;
        this.settingsTable = DSL.table("whitelist");
        this.playersTable = DSL.table("players");

        database.getConnection().thenAcceptAsync(connection -> {
            DSL.using(connection)
                    .createTableIfNotExists(settingsTable)
                    .column("enable", SQLDataType.BOOLEAN)
                    .column("kickOnlinePlayers", SQLDataType.BOOLEAN)
                    .column("kickMessage", SQLDataType.VARCHAR)
                    .column("allowedPlayers", SQLDataType.VARCHAR)
                    .execute();
        }).join();
        database.getConnection().thenAcceptAsync(connection -> {
            DSL.using(connection).createTableIfNotExists(playersTable)
                    .column("playerName", SQLDataType.VARCHAR(16).nullable(false))
                    .constraints(
                            DSL.constraint("pk_players").primaryKey("playerName")
                    )
                    .execute();
        }).join();
    }

    @Override
    public CompletableFuture<Void> saveWhitelist(Whitelist whitelist) {
        return database.getConnection().thenAcceptAsync(connection -> {
            var dsl = DSL.using(connection);
            int updatedRows = dsl.update(settingsTable)
                    .set(DSL.field("enable"), whitelist.isEnable())
                    .set(DSL.field("kickOnlinePlayers"), whitelist.isKickOnlinePlayers())
                    .set(DSL.field("kickMessage"), whitelist.getKickMessage())
                    .execute();
            if (updatedRows == 0) {
                dsl.insertInto(settingsTable)
                        .set(DSL.field("enable"), whitelist.isEnable())
                        .set(DSL.field("kickOnlinePlayers"), whitelist.isKickOnlinePlayers())
                        .set(DSL.field("kickMessage"), whitelist.getKickMessage())
                        .execute();
            }
        });
    }

    @Override
    public CompletableFuture<Whitelist> loadWhitelist() {
        return database.getConnection().thenApplyAsync(connection -> {
            var result = DSL.using(connection)
                    .select()
                    .from(settingsTable)
                    .fetch();

            WhitelistPlayerManager playerManager = this.createPlayerManager();

            if (result.isEmpty()) {
                return new Whitelist(playerManager);
            }

            Record record = result.get(0);

            Whitelist whitelist = new Whitelist(playerManager);
            whitelist.setEnable(record.get(DSL.field("enable", Boolean.class)));
            whitelist.setKickOnlinePlayers(record.get(DSL.field("kickOnlinePlayers", Boolean.class)));
            whitelist.setKickMessage(record.get(DSL.field("kickMessage", String.class)));

            return whitelist;
        });
    }

    private WhitelistPlayerManager createPlayerManager() {
        return new WhitelistPlayerManager() {
            @Override
            public CompletableFuture<Set<String>> getAllowedPlayers() {
                return DatabaseWhitelistProvider.this.getAllowedPlayers();
            }

            @Override
            public CompletableFuture<Boolean> isPlayerAllowed(String playerName) {
                return DatabaseWhitelistProvider.this.isPlayerAllowed(playerName);
            }

            @Override
            public CompletableFuture<Void> addAllowedPlayer(String playerName) {
                return DatabaseWhitelistProvider.this.addAllowedPlayer(playerName);
            }

            @Override
            public CompletableFuture<Void> removeAllowedPlayer(String playerName) {
                return DatabaseWhitelistProvider.this.removeAllowedPlayer(playerName);
            }
        };
    }

    @Override
    public CompletableFuture<Set<String>> getAllowedPlayers() {
        return database.getConnection().thenApplyAsync(connection -> {
            var result = DSL.using(connection)
                    .select(DSL.field("playerName"))
                    .from(playersTable)
                    .fetch();

            return result.stream()
                    .map(record -> record.get(DSL.field("playerName", String.class)))
                    .collect(Collectors.toSet());
        });
    }

    @Override
    public CompletableFuture<Boolean> isPlayerAllowed(String playerName) {
        return database.getConnection().thenApplyAsync(connection ->
                DSL.using(connection)
                        .fetchExists(DSL.selectOne()
                                .from(playersTable)
                                .where(DSL.field("playerName").eq(playerName.toLowerCase())))
        );
    }

    @Override
    public CompletableFuture<Void> addAllowedPlayer(String playerName) {
        return database.getConnection().thenAcceptAsync(connection ->
                DSL.using(connection).insertInto(playersTable)
                        .set(DSL.field("playerName"), playerName.toLowerCase())
                        .execute()
        );
    }

    @Override
    public CompletableFuture<Void> removeAllowedPlayer(String playerName) {
        return database.getConnection().thenAcceptAsync(connection ->
                DSL.using(connection).deleteFrom(playersTable)
                        .where(DSL.field("playerName").eq(playerName.toLowerCase()))
                        .execute()
        );
    }
}