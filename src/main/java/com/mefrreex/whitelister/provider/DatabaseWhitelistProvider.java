package com.mefrreex.whitelister.provider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mefrreex.jooq.database.IDatabase;
import com.mefrreex.whitelister.whitelist.Whitelist;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.io.StringReader;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DatabaseWhitelistProvider implements WhitelistProvider {

    private final IDatabase database;
    private final Table<?> table;

    private final Gson gson = new Gson();

    public DatabaseWhitelistProvider(IDatabase database) {
        this.database = database;
        this.table = DSL.table("whitelist");

        database.getConnection().thenAcceptAsync(connection -> {
            DSL.using(connection)
                    .createTableIfNotExists(table)
                    .column("enable", SQLDataType.BOOLEAN)
                    .column("kickOnlinePlayers", SQLDataType.BOOLEAN)
                    .column("kickMessage", SQLDataType.VARCHAR)
                    .column("allowedPlayers", SQLDataType.VARCHAR)
                    .execute();
        }).join();
    }

    @Override
    public CompletableFuture<Void> saveWhitelist(Whitelist whitelist) {
        return database.getConnection().thenAcceptAsync(connection -> {
            int updatedRows = DSL.using(connection).update(table)
                    .set(DSL.field("enable"), whitelist.isEnable())
                    .set(DSL.field("kickOnlinePlayers"), whitelist.isKickOnlinePlayers())
                    .set(DSL.field("kickMessage"), whitelist.getKickMessage())
                    .set(DSL.field("allowedPlayers"), gson.toJson(whitelist.getAllowedPlayers()))
                    .execute();
            if (updatedRows == 0) {
                DSL.using(connection).insertInto(table)
                        .set(DSL.field("enable"), whitelist.isEnable())
                        .set(DSL.field("kickOnlinePlayers"), whitelist.isKickOnlinePlayers())
                        .set(DSL.field("kickMessage"), whitelist.getKickMessage())
                        .set(DSL.field("allowedPlayers"), gson.toJson(whitelist.getAllowedPlayers()))
                        .execute();
            }

        });
    }

    @Override
    public CompletableFuture<Whitelist> loadWhitelist() {
        return database.getConnection().thenApplyAsync(connection -> {
            Result<Record> result = DSL.using(connection)
                    .select()
                    .from(table)
                    .fetch();

            if (result.isEmpty()) {
                return new Whitelist();
            }

            Record record = result.get(0);

            Whitelist whitelist = new Whitelist();
            whitelist.setEnable(record.get(DSL.field("enable", Boolean.class)));
            whitelist.setKickOnlinePlayers(record.get(DSL.field("kickOnlinePlayers", Boolean.class)));
            whitelist.setKickMessage(record.get(DSL.field("kickMessage", String.class)));

            String allowedPlayersString = record.get(DSL.field("allowedPlayers", String.class));
            Set<String> allowedPlayers = gson.fromJson(new StringReader(allowedPlayersString), new TypeToken<Set<String>>(){}.getType());
            whitelist.setAllowedPlayers(allowedPlayers);

            return whitelist;
        });
    }
}
