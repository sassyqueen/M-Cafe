package m_cafe.android.myapplicationdev.com.m_cafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONTokener;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button login;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login =  (Button)findViewById(R.id.Login);
        error = (TextView)findViewById(R.id.error);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = username.getText().toString();
                String pw = password.getText().toString();


                //TODO 01 Insert/modify code here to send a HttpRequest to doLogin.php
                HttpRequest request = new HttpRequest("http://10.0.2.2/C302_P09/doLogin.php");
                request.setMethod("POST");
                request.addData("username", uName);
                request.addData("password", pw);
                request.execute();

                /******************************/
                try{
                    String jsonString = request.getResponse();
                    JSONObject jsonObject = new JSONObject(jsonString);

                    Boolean authenticate = jsonObject.getBoolean("authenticated");
                    if (authenticate){

                        String id = jsonObject.getString("id");
                        String apikey = jsonObject.getString("apikey");


                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor prefEdit = prefs.edit();
                        prefEdit.putString("id", id);
                        prefEdit.putString("apikey", apikey);
                        prefEdit.commit();


                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);

                    }
                    else if (!authenticate){
                        error.setText("Login incorrect");
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}
