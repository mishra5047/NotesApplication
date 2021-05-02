package com.abhishek.notesapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.abhishek.notesapplication.Fragments.MainActivity;
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

public class LoginFragment extends Fragment {
    ImageView signIn;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    String choice;

    private GoogleSignInClient mGoogleSignInClient;

    public LoginFragment() {
    }

    public LoginFragment(String choice) {
        this.choice = choice;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        signIn = view.findViewById(R.id.googleImage);
        signIn.setOnClickListener(v -> {
            signIn();
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);


        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if(choice != null && mAuth.getCurrentUser() != null){
            FirebaseUser currentUser = mAuth.getCurrentUser();
            loadFragment(new Notes_Fragment(currentUser.getUid()));
        }else {
            SharedPreferences pref = getContext().getSharedPreferences("NotesData", 0); // 0 - for private mode
            String id = pref.getString("authId", "");
            if (!id.equals("")) {
                //Toast.makeText(getContext(), "shared", Toast.LENGTH_SHORT).show();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                Toast.makeText(getContext(), "Welcome Back," + currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();
                loadFragment(new Notes_Fragment(currentUser.getUid()));
            } else if (mAuth != null && mAuth.getCurrentUser() != null) {
                //Toast.makeText(getContext(), "shared", Toast.LENGTH_SHORT).show();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                Toast.makeText(getContext(), "Welcome Back," + currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();
                loadFragment(new Notes_Fragment(currentUser.getUid()));
                //updateUI(currentUser);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }
    // [END auth_with_google]
    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void updateUI(FirebaseUser user) {
        if(user != null){
            SharedPreferences pref = getContext().getSharedPreferences("NotesData", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("userId", user.getUid());
            //Toast.makeText(getContext(), "user id " + user.getUid(), Toast.LENGTH_SHORT).show();
            editor.commit();
            editor.apply();
            loadFragment(new Notes_Fragment(user.getUid()));
        }

    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }
}