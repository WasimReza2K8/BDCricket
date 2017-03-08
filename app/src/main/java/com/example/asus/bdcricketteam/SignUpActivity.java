/*
package com.example.asus.bdcricketteam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.bdcricketteam.datamodel.Users;
import com.example.asus.bdcricketteam.prefmanager.OnPreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;

*/
/**
 * Created by ASUS on 10/8/2016.
 *//*

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AnonymousAuth";

    // [START declare_auth]
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    private EditText mEmailField, mPasswordField, mUserName;
    private ImageView userImage;
    private Button createAccount, signIn, signOut;
    private final int RESULT_LOAD_IMG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    File file = saveImage(user.getUid());
                    if (file != null && file.exists()) {
                        uploadImage(file);
                    }
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]

        // Fields
        mPasswordField = (EditText) findViewById(R.id.field_password);
        mEmailField = (EditText) findViewById(R.id.field_email);
        mUserName = (EditText) findViewById(R.id.field_username);
        userImage = (ImageView) findViewById(R.id.icon);
        createAccount = (Button) findViewById(R.id.button_anonymous_sign_up);
        signIn = (Button) findViewById(R.id.button_anonymous_sign_in);
        signOut = (Button) findViewById(R.id.button_anonymous_sign_out);

        // Click listeners
        createAccount.setOnClickListener(this);
        signIn.setOnClickListener(this);
        signOut.setOnClickListener(this);
        //  findViewById(R.id.button_anonymous_sign_out).setOnClickListener(this);
        findViewById(R.id.fab).setOnClickListener(this);
        userImage.setOnClickListener(this);
        Picasso.with(this).load(OnPreferenceManager.getInstance(this).getImagePath()).into(userImage);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.create_account));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
        //findViewById(R.id.button_link_account).setOnClickListener(this);
    }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                && null != data) {
            // Get the Image from data

            Uri selectedImage = data.getData();
            CropImage.activity(selectedImage)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
            */
/*String[] filePathColumn = { MediaStore.Images.Media.DATA };

            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            ImageView imgView = (ImageView) findViewById(R.id.imgView);
            // Set the Image in ImageView after decoding the String
            imgView.setImageBitmap(BitmapFactory
                    .decodeFile(imgDecodableString));*//*


        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Picasso.with(this).load(resultUri).into(userImage);
                OnPreferenceManager.getInstance(this).setDefaultImageIndicator(false);
                OnPreferenceManager.getInstance(this).setImagePath(resultUri.getPath());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else {
            Toast.makeText(this, "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }
    }

    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // [END on_stop_remove_listener]

    private void createAccount() {
        showProgressDialog();
        // [START signin_anonymously]
        if (createAccount.getText().toString()
                .equalsIgnoreCase(getString(R.string.create_account))) {
            if (isValidEmail(mEmailField.getText())) {
                if (mPasswordField.getText().toString().length() >= 6) {
                    mAuth.createUserWithEmailAndPassword(mEmailField.getText().toString(),
                            mPasswordField.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "createAccount:onComplete:" + task.isSuccessful());

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "createAccount", task.getException());
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        onAuthSuccess(task.getResult().getUser());
                                        showHideButton(true);
                                    }

                                    // [START_EXCLUDE]
                                    hideProgressDialog();
                                    // [END_EXCLUDE]
                                }
                            });
                } else {
                    Toast.makeText(SignUpActivity.this, "Password should be contains 6 characters.",
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(SignUpActivity.this, "Invalid email address.",
                        Toast.LENGTH_SHORT).show();
            }
            // [END signin_anonymously]
        }


    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = mUserName.getText().toString();
        if (username.isEmpty()) {
            username = usernameFromEmail(user.getEmail());
        }

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
       */
/* startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();*//*

    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void writeNewUser(String userId, String name, String email) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Users user = new Users(name, email);
        mDatabase.child("users").child(userId).setValue(user);
    }

    private void showHideButton(boolean signed) {
        if (signed) {
            signIn.setVisibility(View.GONE);
            createAccount.setVisibility(View.GONE);
            signOut.setVisibility(View.VISIBLE);
        } else {
            signIn.setVisibility(View.VISIBLE);
            createAccount.setVisibility(View.VISIBLE);
            signOut.setVisibility(View.GONE);
        }
    }

    private void signIn() {
        showProgressDialog();
        // [START signin_anonymously]
        if (createAccount.getText().toString()
                .equalsIgnoreCase(getString(R.string.create_account))) {
            if (isValidEmail(mEmailField.getText())) {
                if (mPasswordField.getText().toString().length() >= 6) {
                    mAuth.signInWithEmailAndPassword(mEmailField.getText().toString(),
                            mPasswordField.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "createAccount:onComplete:" + task.isSuccessful());
                                    // showHideButton(true);
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, "createAccount", task.getException());
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        showHideButton(true);
                                    }

                                    // [START_EXCLUDE]
                                    hideProgressDialog();
                                    // [END_EXCLUDE]
                                }
                            });
                } else {
                    Toast.makeText(SignUpActivity.this, "Password should be contains 6 characters.",
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(SignUpActivity.this, "Invalid email address.",
                        Toast.LENGTH_SHORT).show();
            }
        }

        // [END signin_anonymously]
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void linkAccount() {
        // Make sure form is valid
        if (!validateLinkForm()) {
            return;
        }

        // Get email and password from form
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        // Create EmailAuthCredential with email and password
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        // Link the anonymous user to the email credential
        showProgressDialog();
        // [START link_credential]
        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "linkWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END link_credential]
    }

    private boolean validateLinkForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        //  TextView idView = (TextView) findViewById(R.id.anonymous_status_id);
        //TextView emailView = (TextView) findViewById(R.id.anonymous_status_email);
        boolean isSignedIn = (user != null);

        // Status text
      */
/*  if (isSignedIn) {
            idView.setText(getString(R.string.id_fmt, user.getUid()));
            emailView.setText(getString(R.string.email_fmt, user.getEmail()));
        } else {
            idView.setText(R.string.signed_out);
            emailView.setText(null);
        }*//*


        // Button visibility
        if (isSignedIn) {
            // createAccount.setText(getString(R.string.sign_out));
            showHideButton(true);
        } else {
            //    createAccount.setText(getString(R.string.create_account));
            showHideButton(false);
        }
        // findViewById(R.id.button_anonymous_sign_in).setEnabled(!isSignedIn);
        // findViewById(R.id.button_anonymous_sign_out).setEnabled(isSignedIn);
        // findViewById(R.id.button_link_account).setEnabled(isSignedIn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_anonymous_sign_in:
                signIn();
                break;
            case R.id.button_anonymous_sign_up:
                createAccount();
                break;
            case R.id.button_anonymous_sign_out:
                signOut();
                break;
            case R.id.fab:
                goToGallery();
                break;
            case R.id.icon:
                goToGallery();
                break;


        }
    }

    private void goToGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    private File saveImage(String userId) {
        File direct = new File(Environment.getExternalStorageDirectory() + "/DirName");

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/BDCRICKET/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File("/sdcard/BDCRICKET/"), userId);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            ((BitmapDrawable) userImage.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void uploadImage(File imageUrl) {
        Uri file = Uri.fromFile(imageUrl);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://bd-cricket.appspot.com");
        StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.e("downloadURL", downloadUrl + "");
            }
        });
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgressDialog();
    }
}
*/
