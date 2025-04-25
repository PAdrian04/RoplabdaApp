package com.example.roplabdabajnoksag;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getName();
    private static final int SECRET_KEY = 19;

    EditText UserNameET;
    EditText EmailET;
    EditText PasswordET;
    EditText PasswordAgainET;

    private FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int secret_key = getIntent().getIntExtra("SECRET_KEY",0);

        if(secret_key != 19) finish();

        UserNameET = findViewById(R.id.regUserName);
        EmailET = findViewById(R.id.regEmail);
        PasswordET = findViewById(R.id.regPassword);
        PasswordAgainET = findViewById(R.id.regPasswordAgain);

        fauth = FirebaseAuth.getInstance();
    }

    public void newregister(View view) {
        String UserName = UserNameET.getText().toString();
        String Email = EmailET.getText().toString();
        String Password = PasswordET.getText().toString();
        String PasswordAgain = PasswordAgainET.getText().toString();


        if(!Password.equals(PasswordAgain)){
            Log.e(TAG,"Nem egyezik a két jelszó!");
            return;
        }

        fauth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()){
                     Atiranyitas();
                 }else{
                     Log.d(TAG, "Sikertelen!");
                     Toast.makeText(RegisterActivity.this,"Nem lett létrehozva!" + task.getException().getMessage() , Toast.LENGTH_LONG).show();
                 }
            }
        });
    }

    public void Atiranyitas(){
        Intent intent = new Intent(this, TournamentsActivity.class);
        startActivity(intent);
    }

    public void cancel(View view) {
        finish();
    }
}