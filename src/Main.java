import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.size;
import static com.mongodb.client.model.Sorts.ascending;

public class Main {
    private static String uri = "mongodb://localhost:27017/";
    public static void main(String[] args) {

        ejercicio1();

    }

    private static void ejercicio1() {
        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("pokemon");
            MongoCollection<Document> collection = database.getCollection("listapokemon");

            List<Document> results = new ArrayList<>();
            collection.find(size("next_evolution",2)).limit(10).sort(ascending("name")).into(results);

            for (Document result : results)
                System.out.println("Nombre pokemon: " +result.get("name"));

        }
    }
}
