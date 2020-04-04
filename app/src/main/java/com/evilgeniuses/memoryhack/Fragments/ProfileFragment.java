package com.evilgeniuses.memoryhack.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.evilgeniuses.memoryhack.Interface.SwitchFragment;
import com.evilgeniuses.memoryhack.Model.User;
import com.evilgeniuses.memoryhack.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private SwitchFragment switchFragment;

    ImageView imageViewProfileImage;

    TextView textViewSetProfileImage;

    EditText editTextUsername;
    EditText editTextEmail;
    EditText editTextName;
    EditText editTextLastname;

    Button buttonLogout;


    DatabaseReference myRef;

    private static final int IMAGE_REQUEST = 1;
    private Uri filePath;
    private StorageTask uploadTask;
    StorageReference storageReference;
    FirebaseStorage storage;
    FirebaseUser user;



    @Nullable
@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_profile, container, false);


    imageViewProfileImage = rootView.findViewById(R.id.imageViewProfileImage);

    textViewSetProfileImage = rootView.findViewById(R.id.textViewSetProfileImage);

    editTextUsername = rootView.findViewById(R.id.editTextUsername);
    editTextEmail = rootView.findViewById(R.id.editTextEmail);
    editTextName = rootView.findViewById(R.id.editTextName);
    editTextLastname = rootView.findViewById(R.id.editTextLastname);

    buttonLogout = rootView.findViewById(R.id.buttonLogout);

    textViewSetProfileImage.setOnClickListener(this);
    buttonLogout.setOnClickListener(this);

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    myRef = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

    storage = FirebaseStorage.getInstance();
    storageReference = storage.getReference();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);
                Glide.with(getContext()).load(value.userProfileImageURL).override(512, 512).into(imageViewProfileImage);
                editTextUsername.setText(value.userUsername);
                editTextEmail.setText(value.userEmail);
                editTextName.setText(value.userName);
                editTextLastname.setText(value.userLastname);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        });

    return rootView;
}
    @Override
    public void onClick(View v) {

    }

@Override
public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    if (context instanceof SwitchFragment) {
        switchFragment = (SwitchFragment) context;
    }
}

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

}