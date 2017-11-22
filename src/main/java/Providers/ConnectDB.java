package Providers;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;

public class ConnectDB {

	MongoCollection<Document> collection;

	public ConnectDB() {
		MongoClientURI connectionString = new MongoClientURI(
				"mongodb://morning:morning@morning.taxionline.vn:2411/taxi_dev?3t.uriVersion=2&3t.connection.name=taxidev&3t.connectionMode=direct&readPreference=primary");
		MongoClient mongoClient = new MongoClient(connectionString);
		MongoDatabase database = mongoClient.getDatabase("taxi_dev");
		this.collection = database.getCollection("requests");
	}

	public String GetPhoneNumber() {
		Document fields = new Document();
		fields.put("phone", 1);
		fields.put("_id", 0);
		Document cursor = collection.find().projection(fields).sort(new Document("_id", -1)).first();
		String phone = cursor.values() + "";
		return (phone.substring(1, phone.length() - 1));
	}

	public void DeleteData(String numberphone) {
		collection.deleteMany(eq("phone", numberphone));
	}

	public void InsertData() {
		Document doc1 = new Document("phone", "01649558528").append("carType", 2).append("note", "abc xyz")
				.append("user", new ObjectId("59b28a3a43a071083c1170db"))
				.append("bAddress", "12 Duy Tân, Cầu Giấy, Hanoi, Vietnam");
		collection.insertOne(doc1);
		System.out.println(doc1.toJson());
	}

	public void EditStatus(String phone, int status) {
		collection.updateOne(eq("phone", phone), new Document("$set", new Document("status", status)));
	}

	public static void main(String args[]) {
		MongoClientURI connectionString = new MongoClientURI(
				"mongodb://morning:morning@morning.taxionline.vn:2411/taxi_dev?3t.uriVersion=2&3t.connection.name=taxidev&3t.connectionMode=direct&readPreference=primary");
		MongoClient mongoClient = new MongoClient(connectionString);
		MongoDatabase database = mongoClient.getDatabase("taxi_dev");
		MongoCollection<Document> collection = database.getCollection("requests");
		collection.updateOne(eq("phone", "001828828282"), new Document("$set", new Document("phone", "099999999999")));

	}
}