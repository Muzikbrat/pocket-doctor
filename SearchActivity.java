package com.example.tanisha.pocketdoctor;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
public class SearchActivity extends AppCompatActivity {
    ArrayList<String> diseases=new ArrayList<>();
    String diseasename;
    SQLiteDatabase db;
    ArrayList<String> symptoms=new ArrayList<>();
    int j = 0;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = (ListView) (findViewById(R.id.listView));
        listView.setAdapter(null);
    }
    public void Search(View view) {
        try {
            ArrayAdapter<String> adapter=null;
            listView.setAdapter(null);
            try {
                diseases.clear();
            }catch (Exception e){
                e.printStackTrace();
            }
            db = openOrCreateDatabase("pocketdoctor.db", Context.MODE_PRIVATE, null);
            String symptom = ((EditText) findViewById(R.id.symptom)).getText().toString();
            ((EditText) findViewById(R.id.symptom)).setText("");
            symptoms.add(j,symptom);
            j++;
            int i = 0;
            String searchquery = "SELECT DISTINCT DNAME FROM SYMPTOM NATURAL JOIN DISEASE WHERE DISEASE.DID=SYMPTOM.DID AND SNAME = '" + symptoms.get(0) + "'";
            if (j > 0) {
                for (int k = 1; k < j; k++) {
                    searchquery = "SELECT DISTINCT DNAME FROM SYMPTOM NATURAL JOIN DISEASE WHERE DISEASE.DID=SYMPTOM.DID AND SNAME = '"+ symptoms.get(k) + "' AND DNAME IN("+searchquery+")";
                }
            }
            Cursor cursor = db.rawQuery(searchquery, null);
            if (cursor.moveToFirst()) {
                do {
                    diseases.add(i,cursor.getString(0));
                    i++;
                } while (cursor.moveToNext());
            }
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, diseases);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // ListView Clicked item value
                    diseasename = diseases.get(position);

                    // Show Alert
                    Intent intent = new Intent(view.getContext(), DiseaseInfo.class);
                    Bundle b = new Bundle();
                    b.putString("diseasename", diseasename);
                    intent.putExtras(b);
                    view.getContext().startActivity(intent);
                }
            });
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }
    public void Refresh(View view) {
        j = 0;
        try {
            symptoms.clear();
            diseases.clear();
            listView.setAdapter(null);
            ((EditText) findViewById(R.id.symptom)).setText("");
        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(), "No Symptoms to clear!", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getApplicationContext(), "Symptoms Cleared.\nStart a new search.", Toast.LENGTH_SHORT).show();
    }
}
