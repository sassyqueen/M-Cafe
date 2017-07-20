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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddItemActivity extends AppCompatActivity {
    EditText menuitem, price;
    Button add;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        menuitem = (EditText)findViewById(R.id.menu_item);
        price = (EditText)findViewById(R.id.price);
        add = (Button)findViewById(R.id.add);
        error = (TextView)findViewById(R.id.errormsg);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AddItemActivity.this);
                final String id = prefs.getString("id", "");
                final String apikey = prefs.getString("apikey", "");

                Intent i = getIntent();
                int pos = i.getIntExtra("pos", 0);
                HttpRequest request = new HttpRequest("http://10.0.2.2/C302_P09/addMenuItem.php");
                request.setMethod("POST");
                request.addData("loginId", id);
                request.addData("apikey", apikey);
                request.addData("cat_id", String.valueOf(pos));
                request.addData("desc", menuitem.getText().toString());
                request.addData("price", price.getText().toString());

                request.execute();

                try {
                    String jsonString = request.getResponse();
                    JSONObject object = new JSONObject(jsonString);

                    Boolean status = object.getBoolean("status");

                    if (status){
                        Intent intent =  new Intent();
                        setResult(RESULT_OK, intent);
                        finish();

                    }
                    else if (!status){
                        error.setText("Invalid entry/Unable to insert a record");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            Intent i = new Intent(AddItemActivity.this, LoginActivity.class);
            startActivity(i);


        }


        return super.onOptionsItemSelected(item);
    }


}
