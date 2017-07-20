package m_cafe.android.myapplicationdev.com.m_cafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class UpdateActivity extends AppCompatActivity {

    EditText updatePrice, updateMenuItem;
    Button update;
    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updatePrice = (EditText)findViewById(R.id.update_price);
        updateMenuItem = (EditText)findViewById(R.id.update_menu_item);
        update = (Button) findViewById(R.id.update);
        errorMessage = (TextView)findViewById(R.id.errorUpdate);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(UpdateActivity.this);
                final String id = prefs.getString("id", "");
                final String apikey = prefs.getString("apikey", "");

                Intent i = getIntent();
                int cat_id = i.getIntExtra("cat_id", 0);
                HttpRequest request = new HttpRequest("http://10.0.2.2/C302_P09/updateMenuItemById.php");
                request.setMethod("POST");
                request.addData("loginId", id);
                request.addData("apikey", apikey);
                request.addData("desc", updateMenuItem.getText().toString());
                request.addData("price", updatePrice.getText().toString());
                request.addData("id", String.valueOf(cat_id));
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
                        errorMessage.setText("Invalid entry/Unable to update a record");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
