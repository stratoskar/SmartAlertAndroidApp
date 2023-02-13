package com.example.smartalert;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;import android.widget.ListView;

public class Summary extends Activity {

    // Array of strings...
    ListView simpleList;
    String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};

    @Override   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      setContentView(R.layout.summary);
        simpleList = (ListView)findViewById(R.id.simpleListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.LabelLogin, countryList);
        simpleList.setAdapter(arrayAdapter);
    }
}
