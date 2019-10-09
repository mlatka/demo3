import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.apache.log4j.BasicConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class TestFirebase {

    public static void main(String[] args) throws IOException {



        BasicConfigurator.configure();

        // Use a service account
        InputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("/home/mirek/Downloads/fir-demo1-31c51-firebase-adminsdk-d3516-8aa6315c8e.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://fir-demo1-31c51.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);

        Firestore db = FirestoreClient.getFirestore();


        DocumentReference docRef = db.collection("users").document("alovelace");
// Add document data  with id "alovelace" using a hashmap
        Map<String, Object> data = new HashMap<>();
        data.put("first", "Ada");
        data.put("last", "Lovelace");
        data.put("born", 1815);
//asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
// ...
// result.get() blocks on response
        try {
            System.out.println("Update time : " + result.get().getUpdateTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

}
