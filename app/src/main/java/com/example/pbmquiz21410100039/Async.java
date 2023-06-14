package com.example.pbmquiz21410100039;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Async extends AsyncTask<String, Integer, String> {
    ArrayList<HashMap<String, String>> listItems = new ArrayList<>();

    @Override
    protected String doInBackground(String... strings) {
        String data = "";

        try {
            // mengambil data return dari URL
            data = getUrl("http://10.0.2.2/pbmquiz/viewbarang.php");

            // Initiate things to JSON
            JSONObject json = new JSONObject(data);
            JSONArray array = json.getJSONArray("barang");
            // Logika Looping Parsing to Arraylist
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String id = String.valueOf(obj.getInt("id"));
                String nama = obj.getString("nama");
                String harga = obj.getString("harga");
                String stock = obj.getString("stock");
                HashMap<String, String> item = new HashMap<>();
                item.put("id", id);
                item.put("nama", nama);
                item.put("harga", harga);
                item.put("stock", stock);
                listItems.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "doInBackground: " + e.getMessage());
        }

        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        // Adapter untuk ListView
        ListAdapter adapter = new SimpleAdapter(MainActivity.xc, listItems, R.layout.layout_adapter_barang, new String[]{"id", "nama", "harga", "stock"}, new int[]{R.id.txtId, R.id.txtNama, R.id.txtHarga, R.id.txtStock});
        MainActivity.listView.setAdapter(adapter);
    }

    public String getUrl(String urlString) throws IOException {
        String data = "";
        InputStream inStream = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            inStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
            StringBuilder sb = new StringBuilder();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "getUrl: " + e.getMessage());
        } finally {
            inStream.close();
            connection.disconnect();
        }
        return data;
    }
}