package com.example.pc.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends Activity {

    private Button add_country_button;
    private EditText editText_id;
    private EditText editText_country;
    private EditText editText_capital;
    private int country_id;
    private String country_name, capital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        add_country_button = (Button)findViewById(R.id.button);
        editText_id = (EditText)findViewById(R.id.editText_id);
        editText_country = (EditText)findViewById(R.id.editText_countryname);
        editText_capital = (EditText)findViewById(R.id.editText_capital);

    }

    public void AddCountry(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);

        country_id = Integer.parseInt(editText_id.getText().toString());
        country_name = editText_country.getText().toString();
        capital = editText_capital.getText().toString();

        if (country_id >0 && country_name.length() > 0 && capital.length() > 0) {

            intent.putExtra("id", country_id);
            intent.putExtra("name", country_name);
            intent.putExtra("capital", capital);

            setResult(RESULT_OK, intent);
            finish();
        }
        else
        {
            Toast.makeText(this, "Make sure data is Ok /n " +
                    "(id greater than 0, and country`s name and capital are not empty)",
                    Toast.LENGTH_LONG).show();
        }

    }
}
