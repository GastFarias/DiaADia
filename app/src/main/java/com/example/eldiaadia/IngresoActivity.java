package com.example.eldiaadia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class IngresoActivity extends AppCompatActivity {

    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    int RC_SING_IN = 1;
    private FirebaseAuth mAuth;
   // public Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso);
  //      button = findViewById(R.id.SingIn_button);
        //AutenticaConFirebase();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        mAuth = FirebaseAuth.getInstance();
     //   button.setOnClickListener(new View.OnClickListener() {
     //       @Override
     //       public void onClick(View view) {
     //           signIn();
    //        }
     //   });
    }

    public void signIn(View view) {
        Toast.makeText(this, "Ingresando", Toast.LENGTH_SHORT).show();
        //Intent signInIntent = oneTapClient.getSignInIntent();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //startActivity(signInIntent);
        startActivityForResult(signInIntent,RC_SING_IN);
    }

    public void AutenticaConFirebase() {
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SING_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(this, "firebaseAuthWithGoogle: " + account.getId(), Toast.LENGTH_SHORT).show();
                firebaseAuthWhithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, "Error, login sin exito", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error, login sin exito(else)", Toast.LENGTH_SHORT).show();
        }


        //        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQ_ONE_TAP:
//                try {
//                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
//                    String idToken = credential.getGoogleIdToken();
//                    if (idToken !=  null) {
//                        // Got an ID token from Google. Use it to authenticate
//                        // with Firebase.
//                        Log.d(TAG, "Got ID token.");
//                    }
//                } catch (ApiException e) {
//                    // ...
//                }
//                break;
    }

    private void firebaseAuthWhithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Ingreso conseguido", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Ingreso fallido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


//        SignInCredential googleCredential = null;
//        try {
//            googleCredential = oneTapClient.getSignInCredentialFromIntent(data);
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//        String idToken = googleCredential.getGoogleIdToken();
//        if (idToken !=  null) {
//            // Got an ID token from Google. Use it to authenticate
//            // with Firebase.
//            AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
//            mAuth.signInWithCredential(firebaseCredential)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                // Sign in success, update UI with the signed-in user's information
//                                Log.d(TAG, "signInWithCredential:success");
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                updateUI(user);
//                            } else {
//                                // If sign in fails, display a message to the user.
//                                Log.w(TAG, "signInWithCredential:failure", task.getException());
//                                updateUI(null);
//                            }
//                        }
//
//                        private void updateUI(FirebaseUser user) {
//                            if (user != null){
//                                Toast.makeText(getApplicationContext(), "Ingreso correcto", Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
//                                startActivity(i);
//                            }
//                        }
//                    });
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        if (currentUser != null) {
            Toast.makeText(this, "Ingreso correcto", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }
}



//
//    int RC_SIGN_IN = 1;
//    private FirebaseAuth mAuth;
//    private GoogleSignInClient mGoogleSignInClient;
//
//    private SignInClient oneTapClient;
//    private BeginSignInRequest signInRequest;
//
//    // ...
//    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
//    private boolean showOneTapUI = true;
//
//    // ...
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ingreso);
//
//        /*
//        GoogleSignInOptions gsd = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//                */
//        BeginSignInRequest signInRequest = BeginSignInRequest.builder()
//                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        // Your server's client ID, not your Android client ID.
//                        .setServerClientId(getString(R.string.default_web_client_id))
//                        // Only show accounts previously used to sign in.
//                        .setFilterByAuthorizedAccounts(true)
//                        .build())
//                .build();
//
//        oneTapClient = Identity.getSignInClient(this);
//        signInRequest = BeginSignInRequest.builder()
//                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
//                        .setSupported(true)
//                        .build())
//                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                        .setSupported(true)
//                        // Your server's client ID, not your Android client ID.
//                        .setServerClientId(getString(R.string.default_web_client_id))
//                        // Only show accounts previously used to sign in.
//                        .setFilterByAuthorizedAccounts(true)
//                        .build())
//                // Automatically sign in when exactly one credential is retrieved.
//                .setAutoSelectEnabled(true)
//                .build();
//
//        //mGoogleSignInClient = GoogleSignIn.getClient(this,gsd);
//
//        mAuth = FirebaseAuth.getInstance();
//
//
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQ_ONE_TAP:
//                try {
//                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
//                    String idToken = credential.getGoogleIdToken();
//                    if (idToken != null) {
//
//                        // Got an ID token from Google. Use it to authenticate
//                        // with Firebase.
//                        //Log.d(TAG, "Got ID token.");
//                    }
//                } catch (ApiException e) {
//                    // ...
//                }
//                break;
//        }
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        //updateUI(currentUser);
//
//        if (currentUser != null) {
//            Intent i = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(i);
//        }
//
//    }
//
