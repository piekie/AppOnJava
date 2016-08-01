package com.example.pc.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


/**
 * Created by pc on 31.07.2016.
 */
public class MainActivity extends Activity {

    int i;
    private ListView listv;
    private  Realm realm;
    private List<String> data = new ArrayList<String>();
    private Button button_adding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);

        realm = Realm.getInstance(
                new RealmConfiguration.Builder(getApplicationContext())
                        .name("CountryRealm1.realm")
                        .deleteRealmIfMigrationNeeded()
                        .build()
        );

        //FillDatabase();

        listv = (ListView)findViewById(R.id.listView);

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });

        List<String> items = FillList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        listv.setAdapter(adapter);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            Country additional_country = new Country();

            additional_country.setId(data.getIntExtra("id", 0));
            //additional_country.setId(5);
            additional_country.setName(data.getStringExtra("name"));
            additional_country.setCapital(data.getStringExtra("capital"));

            realm.beginTransaction();

//            Must be copyToRealmOrUpdate because of crash when conflicting ids
            realm.copyToRealmOrUpdate(additional_country);

            realm.commitTransaction();

            List<String> items = FillList();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

            listv.setAdapter(adapter);
        }
        else
            {
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
            }

    }

    public void GoAddCountry(View v)
    {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivityForResult(intent, RequestCode.REQUEST_CODE_ADDING);
    }

    private List<String> FillList()
    {
        List<String> list = new ArrayList<String>();

        RealmResults<Country> result1 =
                realm.where(Country.class).findAll();

        for(Country c:result1)
        {
            list.add(c.getId() + ". " + c.getName() +": "+ c.getCapital());
        }

        return list;
    }

    private void FillDatabase()
    {
        Country country1 = new Country();

        country1.setId(1);
        country1.setName("Spain");
        country1.setCapital("Madrid");

        Country country2 = new Country();

        country2.setId(2);
        country2.setName("France");
        country2.setCapital("Paris");

        Country country3 = new Country();

        country3.setId(3);
        country3.setName("Germany");
        country3.setCapital("Berlin");

        Country country4 = new Country();

        country4.setId(4);
        country4.setName("Greece");
        country4.setCapital("Athens");

        realm.beginTransaction();
        Country copyofCountry1 = realm.copyToRealm(country1);
        Country copyofCountry2 = realm.copyToRealm(country2);
        Country copyofCountry3 = realm.copyToRealm(country3);
        Country copyofCountry4 = realm.copyToRealm(country4);
        realm.commitTransaction();
    }
}
