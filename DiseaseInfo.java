package com.example.tanisha.pocketdoctor;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
public class DiseaseInfo extends AppCompatActivity {
    String diseasename, did, text;
    SQLiteDatabase db;
    Cursor c1, c2, c3;
    ArrayList<String> sname=new ArrayList<>(), mname=new ArrayList<>(), timesaday=new ArrayList<>();
    ArrayList<Integer> severity=new ArrayList<>();
    int symptoms = 0, medicines = 0;
    ListView mlistview, slistview;
    ListAdapter mlistadapter, slistadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            symptoms = 0;
            medicines = 0;
            setContentView(R.layout.activity_disease_info);
            Bundle b = getIntent().getExtras();
            diseasename = b.getString("diseasename");
            ((TextView)findViewById(R.id.dname)).setText(diseasename.toUpperCase());
            db = openOrCreateDatabase("pocketdoctor.db", Context.MODE_PRIVATE, null);
            c1 = db.rawQuery("SELECT DID,INFO FROM DISEASE WHERE DNAME = '" + diseasename + "'", null);
            if (c1.moveToFirst()) {
                did = c1.getString(0);
                text = c1.getString(1);
                ((TextView) findViewById(R.id.info)).setText(text);
                c2 = db.rawQuery("SELECT SNAME,SEVERITY FROM SYMPTOM WHERE DID = '" + did + "'", null);
                if (c2.moveToFirst()) {
                    do {
                        sname.add(symptoms,c2.getString(0));
                        severity.add(symptoms,c2.getInt(1));
                        symptoms++;
                    } while (c2.moveToNext());
                }
                c3 = db.rawQuery("SELECT MNAME,TIMESADAY FROM MEDICINE WHERE DID = '" + did + "'", null);
                if (c3.moveToFirst()) {
                    do {
                        mname.add(medicines,c3.getString(0));
                        timesaday.add(medicines,c3.getString(1));
                        medicines++;
                    } while (c3.moveToNext());
                }
                mlistview = (ListView) findViewById(R.id.medicinelistview);
                slistview = (ListView) findViewById(R.id.symptomlistview);
                mlistadapter = new MedicineAdapter(this, mname, timesaday);
                slistadapter = new SymptomAdapter(this, sname, severity);
                mlistview.setAdapter(mlistadapter);
                slistview.setAdapter(slistadapter);
                mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String alertMsg = "Medicine: " + mname.get(position) + "\nNo of times: " + timesaday.get(position);
                        // Show Alert
                        Toast.makeText(getApplicationContext(), alertMsg, Toast.LENGTH_LONG).show();
                    }
                });
                slistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String alertMsg = "Symptom: " + sname.get(position) + "\nSeverity: " + severity.get(position);
                        // Show Alert
                        Toast.makeText(getApplicationContext(), alertMsg, Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "NO SUCH DISEASE", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void call(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:9611855999"));
        startActivity(callIntent);
    }
}
