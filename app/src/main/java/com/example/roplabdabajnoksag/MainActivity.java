package com.example.roplabdabajnoksag;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    //elpocsékoltam 12 órát arra hogy működjön a kilistázás, de tovább már nem bírom. Feladtam...
//elpocsékoltam 12 órát arra hogy működjön a kilistázás, de tovább már nem bírom. Feladtam...
//elpocsékoltam 12 órát arra hogy működjön a kilistázás, de tovább már nem bírom. Feladtam...
//elpocsékoltam 12 órát arra hogy működjön a kilistázás, de tovább már nem bírom. Feladtam...
//elpocsékoltam 12 órát arra hogy működjön a kilistázás, de tovább már nem bírom. Feladtam...
    private static final String TAG = MainActivity.class.getName();
    private static final int SECRET_KEY = 19;
    EditText UserNameET;
    EditText PasswordET;

    private FirebaseAuth fauth;

    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        UserNameET = findViewById(R.id.editText);
        PasswordET = findViewById(R.id.editText2);

        fauth = FirebaseAuth.getInstance();
    }

    public void login(View view) {


        String UserName = UserNameET.getText().toString().trim();
        String Password = PasswordET.getText().toString().trim();
        fauth.signInWithEmailAndPassword(UserName,Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Atiranyitas();
                }else{
                    Log.d(TAG, "Sikertelen!");
                    Toast.makeText(MainActivity.this,"Sikertelen bejelentkezés!" + task.getException().getMessage() , Toast.LENGTH_LONG).show();
                }
            }
        });

        Log.i(TAG, UserName + Password);
    }

    public void Atiranyitas(){
        Intent intent = new Intent(this, MatchListActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("SECRET_KEY", 19);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        Toast.makeText(this, "Üdvözlünk az alkalmazásban!", Toast.LENGTH_LONG).show();
        loginButton = findViewById(R.id.button2);
        registerButton = findViewById(R.id.button);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade_in);

        loginButton.startAnimation(animation);
        animation = AnimationUtils.loadAnimation(this,R.anim.slide_in_row);

        registerButton.startAnimation(animation);
    }
}