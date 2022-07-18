package com.example.rendezvous;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private EditText mNameField, mPhoneField;
    private ProgressBar spinner;
    private Button mConfirm;
    private ImageButton mBack;
    private ImageView mProfileImage;
    private EditText mBudget;
    private Spinner need, give;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;

    private String userId, name, phone, profileImageUrl, userSex, userBudget, userNeed, userGive;
    private int needIndex, giveIndex;
    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinner = (ProgressBar) findViewById(R.id.progress_bar);
        spinner.setVisibility(View.GONE);

        mNameField = (EditText) findViewById(R.id.name);
        mPhoneField = (EditText) findViewById(R.id.phone);

        mProfileImage = (ImageView) findViewById(R.id.profileImage);
        mBack = findViewById(R.id.settingsBack);

        mConfirm = (Button) findViewById(R.id.confirm);
        mBudget = (EditText) findViewById(R.id.budget_settings);
        need = (Spinner) findViewById(R.id.spinner_need_settings);
        give = (Spinner) findViewById(R.id.spinner_give_settings);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth != null && mAuth.getCurrentUser() != null) {
            userId = mAuth.getCurrentUser().getUid();
        } else {
            finish();
        }

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.give, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        need.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter_give = ArrayAdapter.createFromResource(this,
                R.array.give, android.R.layout.simple_spinner_item);
        adapter_give.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        give.setAdapter(adapter_give);

        getUserInfo();

        mProfileImage.setOnClickListener(view -> {
            if(!checkPermission()) {
                Toast.makeText(SettingsActivity.this, "Please allow access to continue", Toast.LENGTH_SHORT).show();
                requestPermission();
            } else {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });


        mConfirm.setOnClickListener(view -> {
            saveUserInfo();
            Intent intent = new Intent(SettingsActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });

        mBack.setOnClickListener(view -> {
            spinner.setVisibility(View.VISIBLE);
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        });

        Toolbar toolbar = findViewById(R.id.settings_toolbartag);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[] {READ_EXTERNAL_STORAGE}, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            } else {
                Toast.makeText(this, "Please allow acces to continue", Toast.LENGTH_LONG).show();
            }
        }
    }

    //logout button pressed


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.ContactUs) {
            new AlertDialog.Builder(SettingsActivity.this)
                    .setTitle("Contact us")
                    .setMessage("Contact us: patrykbedek@gmail.com")
                    .setNegativeButton("Dismiss", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else if (item.getItemId() == R.id.logout) {
            spinner.setVisibility(View.VISIBLE);
            mAuth.signOut();
            Toast.makeText(this, "Log on succesfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SettingsActivity.this, ChooseLoginAndReg.class);
            startActivity(intent);
            finish();
            spinner.setVisibility(View.GONE);

        } else if (item.getItemId() == R.id.deleteAccount) {
            new AlertDialog.Builder(SettingsActivity.this)
                    .setTitle("Are you sure?")
                    .setMessage("Deleting your account will result in completly removing your account from the system")
                    .setPositiveButton("Delete", (dialogInterface, which) ->
                            mAuth.getCurrentUser().delete().addOnCompleteListener(task -> {
                                spinner.setVisibility(View.VISIBLE);

                                if (task.isSuccessful()) {
                                    deleteUserAccount(userId);
                                    Toast.makeText(SettingsActivity.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SettingsActivity.this, ChooseLoginAndReg.class);
                                    startActivity(intent);
                                    spinner.setVisibility(View.GONE);
                                    return;

                                } else {
                                    Toast.makeText(SettingsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                    Intent intent = new Intent(SettingsActivity.this, ChooseLoginAndReg.class);
                                    startActivity(intent);
                                    spinner.setVisibility(View.VISIBLE);
                                    return;
                                }
                            })
                    )
                    .setNegativeButton("Dismiss", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteMatch(String matchId, String chatId) {
        DatabaseReference matchId_in_UserId_dbReference = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(userId).child("connections").child("matches").child(matchId);

        DatabaseReference userId_in_matchId_dbReference = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(userId).child("connections").child("matches").child(userId);

        DatabaseReference yeps_in_matchId_dbReference = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(userId).child("connections").child("yeps").child(userId);

        DatabaseReference yeps_in_UserId_dbReference = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(userId).child("connections").child("yeps").child(matchId);

        DatabaseReference matchId_chat_dbReference = FirebaseDatabase.getInstance().getReference().child("Chat").child(chatId);

        matchId_chat_dbReference.removeValue();
        matchId_in_UserId_dbReference.removeValue();
        userId_in_matchId_dbReference.removeValue();
        yeps_in_matchId_dbReference.removeValue();
        yeps_in_UserId_dbReference.removeValue();
    }

    private void deleteUserAccount(String userId) {
        DatabaseReference currentUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        DatabaseReference currentUserMatchesRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId)
                .child("connections").child("matches");

        currentUserMatchesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot match : dataSnapshot.getChildren()) {
                        deleteMatch(match.getKey(), match.child("ChatId").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        currentUserRef.removeValue();
        currentUserMatchesRef.removeValue();
    }

    private void getUserInfo() {
        mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("name") != null) {
                        name = map.get("name").toString();
                        mNameField.setText(name);
                    }
                    if (map.get("phone") != null) {
                        phone = map.get("phone").toString();
                        mPhoneField.setText(phone);
                    }
                    if (map.get("sex") != null) {
                        userSex = map.get("sex").toString();
                    }
                    if (map.get("budget") != null) {
                        userBudget = map.get("budget").toString();
                    } else {
                        userBudget = "0";
                    }
                    if (map.get("give") != null) {
                        userGive = map.get("give").toString();
                    } else {
                        userGive = "";
                    }
                    if (map.get("need") != null) {
                        userNeed = map.get("need").toString();
                    } else {
                        userNeed = "";
                    }

                    String[] services = getResources().getStringArray(R.array.services);
                    needIndex = giveIndex = 0;
                    for (int i = 0; i < services.length; i++) {
                       if (userNeed.equals(services[i]))
                           needIndex = 1;
                       if (userGive.equals(services[i]))
                           needIndex = 1;
                    }

                    need.setSelection(needIndex);
                    give.setSelection(giveIndex);
                    mBudget.setText(userBudget);

                    Glide.clear(mProfileImage);

                    if(map.get("profileImageUrl") != null) {
                        profileImageUrl = map.get("profileImageUrl").toString();
                        switch (profileImageUrl) {
                            case "default" :
                                Glide.with(getApplication()).load(R.drawable.profile).into(mProfileImage);
                                break;
                            default:
                                Glide.with(getApplication()).load(profileImageUrl).into(mProfileImage);
                                break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveUserInfo() {
        name = mNameField.getText().toString();
        phone = mPhoneField.getText().toString();
        userBudget = mBudget.getText().toString();
        userGive = give.getSelectedItem().toString();
        userNeed = need.getSelectedItem().toString();

        Map userInfo = new HashMap();
        userInfo.put("name", name);
        userInfo.put("phone", phone);
        userInfo.put("need", userNeed);
        userInfo.put("give", userGive);
        userInfo.put("budget", userBudget);
        mUserDatabase.updateChildren(userInfo);
        if(resultUri != null) {
            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImages").child(userId);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = filepath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    finish();
                }
            });

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uri.isComplete());
                    Uri downloadUri = uri.getResult();
                    Map userInfo = new HashMap();
                    userInfo.put("profileImageUrl", downloadUri.toString());
                    mUserDatabase.updateChildren(userInfo);
                    finish();
                    return;
                }
            });
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            mProfileImage.setImageURI(resultUri);
        }
    }
}