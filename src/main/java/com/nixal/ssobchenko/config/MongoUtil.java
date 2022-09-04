package com.nixal.ssobchenko.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class MongoUtil {
    private static MongoClient mongoClient;

    public static MongoDatabase connect(String databaseName) {
        return getMongoClient(null).getDatabase(databaseName);
    }

    public static MongoDatabase connect(String databaseName, MongoCredential credential) {
        return getMongoClient(credential).getDatabase(databaseName);
    }

    private static MongoClient getMongoClient(MongoCredential credential) {
        if (mongoClient != null) return mongoClient;

        final MongoClientOptions.Builder options = MongoClientOptions.builder();

        return credential == null
                ? new MongoClient("localhost", 27017)
                : new MongoClient(
                new ServerAddress("localhost", 27017),
                credential,
                options.build()
        );
    }
}