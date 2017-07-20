package m_cafe.android.myapplicationdev.com.m_cafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayAdapter aa;
    ArrayList<String> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listview_categories);
        al = new ArrayList<>();
        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String id = prefs.getString("id", "");
        final String apikey = prefs.getString("apikey", "");

        HttpRequest request = new HttpRequest("http://10.0.2.2/C302_P09/getMenuCategories.php");
        request.setMethod("POST");
        request.addData("loginId", id);
        request.addData("apikey", apikey);
        request.execute();

        try {
            String jsonString = request.getResponse();
            JSONArray jsonArray = new JSONArray(jsonString);
            System.out.println(jsonArray);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String cat = obj.getString("menu_item_category_description");
                al.add(cat);

            }
            lv.setAdapter(aa);
            aa.notifyDataSetChanged();


        } catch (Exception e) {
            e.printStackTrace();
        }


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, ItemsActivity.class);
                intent.putExtra("pos", position+1);
                startActivity(intent);

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menulogout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout) {

            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);

        }


        return super.onOptionsItemSelected(item);
    }


}
