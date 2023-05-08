package comm.octest.beans;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class BookImageFetcher {

    public static String getImageUrl(String bookName, String authorName) throws IOException {
        String query = "intitle:" + bookName.replace(" ", "+") + "+inauthor:" + authorName.replace(" ", "+");
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&fields=items(volumeInfo/imageLinks/thumbnail)";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        InputStream response = connection.getInputStream();
        Scanner scanner = new Scanner(response).useDelimiter("\\A");
        String responseBody = scanner.hasNext() ? scanner.next() : "";
        scanner.close();

        // Parse the JSON response and extract the thumbnail image link for the first book
        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray items = jsonResponse.getJSONArray("items");
        if (items.length() > 0) {
            JSONObject volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo");
            if (volumeInfo.has("imageLinks")) {
                return volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
            }
        }
      
        return null;
    }
}
