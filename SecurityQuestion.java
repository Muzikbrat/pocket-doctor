package com.example.tanisha.pocketdoctor; import android.content.Context; import android.content.Intent; import android.database.Cursor; import android.database.sqlite.SQLiteDatabase; import android.os.Bundle; import android.support.v7.app.AppCompatActivity; import android.view.View; import android.widget.EditText; import android.widget.TextView; import android.widget.Toast; public class SecurityQuestion extends AppCompatActivity { SQLiteDatabase db; String email, question, answer; int flag = 0; int tries = 0; @Override protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_security_question); tries = 0; flag = 0;
Bundle b = getIntent().getExtras(); email = b.getString("forgotemail"); db = openOrCreateDatabase("pocketdoctor.db", Context.MODE_PRIVATE, null); Cursor cursor = db.rawQuery("SELECT QUESTION,ANSWER FROM USER WHERE EMAIL LIKE '" + email + "'", null); if (cursor.moveToFirst()) { question = cursor.getString(0); answer = cursor.getString(1); ((TextView) findViewById(R.id.securityquestion)).setText(question); } else { Toast.makeText(getApplicationContext(), "Wrong Email!!", Toast.LENGTH_LONG).show(); startActivity(new Intent(this, ForgotPassword.class)); } } public void GetPassword(View view) { String enteredanswer = ((EditText) findViewById(R.id.securityanswer)).getText().toString(); if (enteredanswer.equals(answer)) { flag = 1; } Cursor cursor = db.rawQuery("SELECT PASSWORD FROM USER WHERE EMAIL LIKE '" + email + "'", null); cursor.moveToFirst(); String password = cursor.getString(0); if (flag == 1) { Toast.makeText(getApplicationContext(), password, Toast.LENGTH_LONG).show(); Intent intent = new Intent(this, LoginActivity.class); startActivity(intent); } else { tries++; Toast.makeText(getApplicationContext(), "Incorrect Answer!!", Toast.LENGTH_LONG).show(); if (tries > 2) { Toast.makeText(getApplicationContext(), "No of tries exceeded!!", Toast.LENGTH_SHORT).show(); startActivity(new Intent(this, LoginActivity.class)); } } } }
