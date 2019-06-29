package com.example.hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    protected EditText email;
    protected EditText password;
    protected Button Login;
    protected Button SignUp;
    protected FirebaseAuth mAuth;

    protected ProgressBar pbHeaderProgress;
    public  static final String EXTRA_EMAIL="com.example.login.EXTRA_EMAIL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        Login=findViewById(R.id.Login);
        SignUp=findViewById(R.id.SignUp);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        pbHeaderProgress= findViewById(R.id.spinner);
        pbHeaderProgress.setVisibility(View.INVISIBLE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email!=null&&password!=null&& TextUtils.isEmpty(email.getText().toString())==false&&TextUtils.isEmpty(password.getText().toString())==false){
                    mSginIN(email.getText().toString(), password.getText().toString());

                }
                else
                { Toast.makeText(MainActivity.this, "Fill both email with password please!",
                        Toast.LENGTH_SHORT).show();

                }

            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email!=null&&password!=null&&TextUtils.isEmpty(email.getText().toString())==false&&TextUtils.isEmpty(password.getText().toString())==false)
                    mSginUp(email.getText().toString(),password.getText().toString());
                else
                { Toast.makeText(MainActivity.this, "Fill both email with password please!",
                        Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void mSginIN(String toString, String toString1){
        pbHeaderProgress.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(toString, toString1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("success", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            cases(task);

                            updateUI(null);
                        }

                        // ...
                    }
                });
        pbHeaderProgress.setVisibility(View.INVISIBLE);
    }

    private void mSginUp(String toString, String toString1) {
        pbHeaderProgress.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(toString,toString1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("success", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            cases(task);

                            updateUI(null);
                        }

                        // ...
                    }
                });
        pbHeaderProgress.setVisibility(View.INVISIBLE);
    }
    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null)
        {
            openNewActivity(currentUser.getEmail());
        }
    }

    private void openNewActivity(String email) {
        Intent intent= new Intent(this,AppWindow.class);
        intent.putExtra(EXTRA_EMAIL,email);

        startActivity(intent);
    }
    public void cases(Task<AuthResult> task){
        // If sign in fails, display a message to the user.
        Log.e("error", "createUserWithEmail:failure", task.getException());
        String type=task.getException().toString();

         if(type.contains("The email address is badly formatted"))
            Toast.makeText(MainActivity.this, "The Email isn't formatted correctly",
                    Toast.LENGTH_SHORT).show();
        else if(type.contains("There is no user record corresponding to this identifier"))
            Toast.makeText(MainActivity.this, "No Such Email exists",
                    Toast.LENGTH_SHORT).show();
        else if(type.contains("The password is invalid"))
            Toast.makeText(MainActivity.this, "InCorrect Password",
                    Toast.LENGTH_SHORT).show();
        else if(type.contains("The email address is already in use by another account"))
            Toast.makeText(MainActivity.this, "The Email is already exists",
                    Toast.LENGTH_SHORT).show();
        else if(type.contains("A network error"))
            Toast.makeText(MainActivity.this, "Network is down",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "Your password is too little",
                    Toast.LENGTH_SHORT).show();

    }
}
