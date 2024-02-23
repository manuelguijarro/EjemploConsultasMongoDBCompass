# Proyecto Pokémon Java

Este proyecto tiene como objetivo realizar consultas a una base de datos MongoDB utilizando Java para trabajar con datos de Pokémon.

## Instrucciones

1. Clona este repositorio en tu máquina local.
2. Carga el archivo JSON adjunto en tu base de datos MongoDB.
3. Ejecuta el código Java para realizar las consultas especificadas.

## Consultas
1.  Seleccionar los 10 primeros pokemon que tengan 2 evoluciones siguientes. Ordenar la salida por nombre del pokemon Ascendente (Z a la A).
```
    private static void ejercicio1() {

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("pokemon");
            MongoCollection<Document> collection = database.getCollection("listapokemon");

            List<Document> results = new ArrayList<>();

            collection.find(size("next_evolution",2)).limit(10).sort(descending("name")).into(results);

            for (Document result : results)
                System.out.println("Nombre pokemon: " +result.get("name"));

        }
    }
```
<br>

2. Seleccionar el segundo pokemon cuyo huevo eclosione a los 2 km.
```
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
```
<br>

3. Mostrar todos los pokemon cuyo id sea mayor a 50 y menor que 60.
```
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
```
<br> 

4. Mostrar todos los pokemon que sean una evolución intermedia (es decir, que tenga evolución previa y evolución siguiente). Se deberá mostrar sólo el nombre de ese pokemon, de la evolución previa y de la evolución siguiente.
```
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
```
<br> 

5. Mostrar los pokemon que tengan el campo candy_count y que "Grass" NO esté entre sus debilidades.
```
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