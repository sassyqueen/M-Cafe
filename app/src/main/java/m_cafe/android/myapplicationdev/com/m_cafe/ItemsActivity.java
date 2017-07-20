package m_cafe.android.myapplicationdev.com.m_cafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter aaItems;
    ArrayList<MenuItems> alItems;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        lv = (ListView) findViewById(R.id.listview_items);
        alItems = new ArrayList<>();
        aaItems = new ArrayAdapter<MenuItems>(this, android.R.layout.simple_list_item_1, alItems);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String id = prefs.getString("id", "");
        final String apikey = prefs.getString("apikey", "");

        Intent i = getIntent();
        pos = i.getIntExtra("pos", 0);
        HttpRequest request = new HttpRequest("http://10.0.2.2/C302_P09/getMenuItemsByCategory.php?category_id=" + pos);
        request.setMethod("POST");
        request.addData("loginId", id);
        request.addData("apikey", apikey);
        request.execute();

        try {
            String jsonString = request.getResponse();
            JSONArray jsonArray = new JSONArray(jsonString);
            System.out.println(jsonString);
            for (int items = 0 ; items < jsonArray.length(); items++){
                JSONObject obj = jsonArray.getJSONObject(items);
                String desc = obj.getString("menu_item_description");
                String menu_id = obj.getString("menu_item_id");
                MenuItems item  = new MenuItems(desc, menu_id);
                alItems.add(item);


            }


            lv.setAdapter(aaItems);
            aaItems.notifyDataSetChanged();


        } catch (Exception e) {
            e.printStackTrace();
        }

        registerForContextMenu(lv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.logout) {
            Intent i = new Intent(ItemsActivity.this, LoginActivity.class);
            startActivity(i);


        }
        else if (id == R.id.add_item){
            Intent i = new Intent(ItemsActivity.this, AddItemActivity.class);
            i.putExtra("pos", pos);
            startActivityForResult(i,9);
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Only handle when 2nd activity closed normally
        //  and data contains something
        if(resultCode == RESULT_OK){
            if (data != null) {
                // Get data passed back from 2nd activity

                if(requestCode == 9){
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    final String id = prefs.getString("id", "");
                    final String apikey = prefs.getString("apikey", "");

                    Intent i = getIntent();
                    pos = i.getIntExtra("pos", 0);
                    HttpRequest request = new HttpRequest("http://10.0.2.2/C302_P09/getMenuItemsByCategory.php?category_id=" + pos);
                    request.setMethod("POST");
                    request.addData("loginId", id);
                    request.addData("apikey", apikey);
                    request.execute();

                    try {
                        String jsonString = request.getResponse();
                        JSONArray jsonArray = new JSONArray(jsonString);
                        System.out.println(jsonString);
                        for (int items = 0 ; items < jsonArray.length(); items++){
                            JSONObject obj = jsonArray.getJSONObject(items);
                            String desc = obj.getString("menu_item_description");
                            String menu_id = obj.getString("menu_item_id");
                            MenuItems item  = new MenuItems(desc, menu_id);
                            alItems.add(item);


                        }


                        lv.setAdapter(aaItems);
                        aaItems.notifyDataSetChanged();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Toast.makeText(ItemsActivity.this, "Record created!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu m, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(m, v, menuInfo);
        m.add(0, 0, 0, "Update menu item");
        m.add(1, 0, 1, "Delete menu item");


    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item;

        if (item.getGroupId() == 0) {



        } else if (item.getGroupId() == 1) {


        }

        return super.onContextItemSelected(item); //pass menu item to the superclass implementation.
    }

}
