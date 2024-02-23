import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

public class Main {
    private static String uri = "mongodb://localhost:27017/";

    public static void main(String[] args) {

        //ejercicio1();
        //ejercicio2();
        //ejercicio3();
        //ejercicio4();
        ejercicio5();
    }




    private static void ejercicio1() {

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("pokemon");
            MongoCollection<Document> collection = database.getCollection("listapokemon");

            List<Document> results = new ArrayList<>();

            collection.find(size("next_evolution", 2)).limit(10).sort(descending("name")).into(results);

            for (Document result : results)
                System.out.println("Nombre pokemon: " + result.get("name"));

        }
    }

    private static void ejercicio2() {

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("pokemon");
            MongoCollection<Document> collection = database.getCollection("listapokemon");

            List<Document> results = new ArrayList<>();

            Bson equalComparison = eq("egg", "2 km");

            collection.find(equalComparison).into(results);

            Document result = results.get(1);

            System.out.println("Nombre pokemon: " + result.get("name"));

        }
    }

    private static void ejercicio3() {

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("pokemon");
            MongoCollection<Document> collection = database.getCollection("listapokemon");

            List<Document> results = new ArrayList<>();

            Bson andComparison = and(gt("_id", 50), lt("_id", 60));

            collection.find(andComparison).into(results);

            for (Document result:results)
                System.out.println("Nombre pokemon: " + result.get("name"));

        }
    }
    private static void ejercicio4() {

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("pokemon");
            MongoCollection<Document> collection = database.getCollection("listapokemon");

            List<Document> results = new ArrayList<>();

            Bson existsComparison = and(exists("prev_evolution"), exists("next_evolution"));

            collection.find(existsComparison).into(results);

            for (Document result:results) {

                System.out.println("-POKEMON-");

                System.out.println("Nombre pokemon: " + result.get("name"));

                result.getList("prev_evolution", Document.class).forEach((pokemon) -> System.out.println("Nombre pokemon evolucion precesora: " + pokemon.getString("name")));

                result.getList("next_evolution", Document.class).forEach((pokemon) -> System.out.println("Nombre pokemon evolucion siguiente: " + pokemon.getString("name")));
            }
        }
    }

    private static void ejercicio5() {

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("pokemon");
            MongoCollection<Document> collection = database.getCollection("listapokemon");

            List<Document> results = new ArrayList<>();

            Bson existsComparison = and(exists("candy_count"), nin("weaknesses","Grass"));

            collection.find(existsComparison).into(results);

            for (Document result:results) {

                System.out.println("-POKEMON-");
                System.out.println(result);

            }
        }
    }
}

