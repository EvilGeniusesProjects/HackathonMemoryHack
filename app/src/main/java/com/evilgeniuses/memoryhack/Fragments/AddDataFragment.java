package com.evilgeniuses.memoryhack.Fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.evilgeniuses.memoryhack.Model.ColorizeRequestModel;
import com.evilgeniuses.memoryhack.Model.WarHero;
import com.evilgeniuses.memoryhack.R;
import com.evilgeniuses.memoryhack.services.ColorizeService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddDataFragment extends Fragment {

    private static final String API_KEY = "simKA5GwrpDm/Z6B0k6N3KKcdlP1";
    private static final int REQUEST_GALLERY = 123;
    private static final String LOG_TAG = "UPLOAD_PHOTO_ERROR";

    private ColorizeService colorizeService;
    private CompositeDisposable compositeDisposable;

    private ImageView imageView;
    private Button btnUploadPhoto;
    private Button btnUpgradePhoto;
    private ImageView shareInInstagramBtn;
    private ImageView shareInVkBtn;
    private LinearLayout linearLayout;

    private EditText editTextHeroName;
    private EditText editTextHeroLastname;
    private EditText editTextHeroMiddlename;
    private EditText editTextHeroDateOfBirth;
    private EditText editTextHeroPlaceOfBirth;
    private EditText editTextHeroPlaceOfCallRegion;
    private EditText editTextHeroYearOfCall;
    private EditText editTextHeroPlaceOfCall;
    private EditText editTextHeroMilitaryRank;
    private EditText editTextHeroYearDateOfDeath;
    private EditText editTextHeroHeroStory;
    private EditText editTextHeroLinkToThirdPartyResources;
    private EditText editTextHeroMailForAccessToYourPersonalAccount;
    private ImageView imageViewMail;
    private Button buttonAddMail;
    private Button buttonSned;
    private TextView textViewPercent;


    String authenticationID;
    DatabaseReference myRef;
    FirebaseStorage storage;
    StorageReference storageReference;

    private static final int IMAGE_REQUEST = 1;
    String percent = "0%";
    int intPercent;

    Boolean photoImage = false;
    Boolean mailImage = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_hero, container, false);
        imageView = view.findViewById(R.id.imageView);
        btnUploadPhoto = view.findViewById(R.id.btnUploadPhoto);
        btnUpgradePhoto = view.findViewById(R.id.btnUpgradePhoto);
        shareInInstagramBtn = view.findViewById(R.id.shareInstagramButtom);
        shareInVkBtn = view.findViewById(R.id.shareVkButtom);
        linearLayout = view.findViewById(R.id.linearLayout);

        textViewPercent = view.findViewById(R.id.textViewPercent);

        editTextHeroName = view.findViewById(R.id.editTextHeroName);
        editTextHeroLastname = view.findViewById(R.id.editTextHeroLastname);
        editTextHeroMiddlename = view.findViewById(R.id.editTextHeroMiddlename);

        editTextHeroDateOfBirth = view.findViewById(R.id.editTextHeroDateOfBirth);
        editTextHeroPlaceOfBirth = view.findViewById(R.id.editTextHeroPlaceOfBirth);

        editTextHeroPlaceOfCallRegion = view.findViewById(R.id.editTextHeroPlaceOfCallRegion);
        editTextHeroYearOfCall = view.findViewById(R.id.editTextHeroYearOfCall);
        editTextHeroPlaceOfCall = view.findViewById(R.id.editTextHeroPlaceOfCall);
        editTextHeroMilitaryRank = view.findViewById(R.id.editTextHeroMilitaryRank);

        editTextHeroYearDateOfDeath = view.findViewById(R.id.editTextHeroYearDateOfDeath);
        editTextHeroHeroStory = view.findViewById(R.id.editTextHeroHeroStory);
        editTextHeroLinkToThirdPartyResources = view.findViewById(R.id.editTextHeroLinkToThirdPartyResources);
        editTextHeroMailForAccessToYourPersonalAccount = view.findViewById(R.id.editTextHeroMailForAccessToYourPersonalAccount);


        editTextHeroMiddlename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFilds();
            }
        });

        editTextHeroDateOfBirth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFilds();
            }
        });

        editTextHeroPlaceOfBirth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFilds();
            }
        });

        editTextHeroPlaceOfCallRegion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFilds();
            }
        });

        editTextHeroYearOfCall.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFilds();
            }
        });

        editTextHeroPlaceOfCall.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFilds();
            }
        });

        editTextHeroMilitaryRank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFilds();
            }
        });

        editTextHeroYearDateOfDeath.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFilds();
            }
        });

        editTextHeroHeroStory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFilds();
            }
        });

        editTextHeroLinkToThirdPartyResources.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFilds();
            }
        });

        editTextHeroMailForAccessToYourPersonalAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkFilds();
            }
        });








































        imageViewMail = view.findViewById(R.id.imageViewMail);


        buttonAddMail = view.findViewById(R.id.buttonAddMail);
        buttonSned = view.findViewById(R.id.buttonSned);



        authenticationID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        compositeDisposable = new CompositeDisposable();
        btnUploadPhoto.setOnClickListener(v -> {
            Dexter.withActivity(getActivity())
                    .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, REQUEST_GALLERY);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            Toast.makeText(getActivity(), "Для загрузки фото необходимо дать разрешение на чтение внутренней памяти", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    })
                    .onSameThread()
                    .check();
        });


        buttonSned.setOnClickListener(v -> writeNewWarHero());
        buttonAddMail.setOnClickListener(v -> SelectImage());





        btnUpgradePhoto.setOnClickListener(v -> {

            final ProgressDialog pd = new ProgressDialog(getContext());

            pd.setMessage("Обработка");
            pd.show();

            Single<byte[]> singleResult = Single.create(emitter -> {
                //Lazy init
                if (colorizeService == null) {
                    colorizeService = new ColorizeService(API_KEY);
                }

                Drawable drawable = imageView.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();


                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] bitmapData = bos.toByteArray();

                File temp = new File(getActivity().getCacheDir(), System.currentTimeMillis() + ".jpg");
                boolean isNewFileCreated = temp.createNewFile();
                if (isNewFileCreated) {
                    FileOutputStream fos = new FileOutputStream(temp);
                    fos.write(bitmapData);
                    fos.flush();
                    fos.close();

                    String url = colorizeService.uploadFile(temp);
                    ColorizeRequestModel colorizeRequestModel = new ColorizeRequestModel(url);
                    byte[] bytes = colorizeService.downloadFile(colorizeRequestModel);
                    boolean isTempDeleted = temp.delete();
                    if (!isTempDeleted) {
                        Log.e(LOG_TAG, "не удалось удалить временный файл " + temp.getAbsolutePath());
                    }
                    emitter.onSuccess(bytes);
                }
            });

            compositeDisposable.add(
                    singleResult
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
                                imageView.setImageBitmap(bitmap);
                                pd.dismiss();
                                btnUpgradePhoto.setVisibility(View.GONE);
                            }));
        });

        shareInInstagramBtn.setOnClickListener(v -> shareFileToInstagram());
        shareInVkBtn.setOnClickListener(v -> shareFileTopVk());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY) {
            if (resultCode == getActivity().RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(Objects.requireNonNull(imageUri));
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    checkFace(selectedImage);
                } catch (FileNotFoundException e) {
                    Log.e(LOG_TAG, "FileNotFound exception expected: ", e);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    private void checkFace(Bitmap photo) {

        final ProgressDialog pd = new ProgressDialog(getContext());

        pd.setMessage("Загрузка");
        pd.show();

        FirebaseVisionFaceDetectorOptions options = new FirebaseVisionFaceDetectorOptions.Builder().setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.NO_LANDMARKS)
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.NO_CLASSIFICATIONS)
                .build();

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(photo);
        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance().getVisionFaceDetector(options);

        detector.detectInImage(image).addOnSuccessListener(
                faces -> {
                    pd.dismiss();
                    if (faces.size() == 1) {
                        imageView.setImageBitmap(photo);
                        btnUpgradePhoto.setVisibility(View.VISIBLE);
                        shareInInstagramBtn.setVisibility(View.VISIBLE);
                        shareInVkBtn.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);
                        photoImage = true;
                        checkFilds();
                    } else if (faces.size() > 1) {
                        Toast.makeText(getContext(), "Не допускается загрузка групповых фотографий", Toast.LENGTH_SHORT).show();
                        shareInInstagramBtn.setVisibility(View.GONE);
                        shareInVkBtn.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.GONE);
                        photoImage = false;
                    } else {
                        Toast.makeText(getContext(), "На фотографии не распознаны лица, попробуйте загрузить другие фотографии", Toast.LENGTH_SHORT).show();
                        shareInInstagramBtn.setVisibility(View.GONE);

                        linearLayout.setVisibility(View.GONE);
                        shareInVkBtn.setVisibility(View.GONE);
                        photoImage = false;
                    }

                }).addOnFailureListener(
                e -> {
                    Toast.makeText(getContext(), "На фотографии не распознаны лица, попробуйте загрузить другие фотографии", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                });
    }

    private void shareFileToInstagram() {
        sharePhoto("com.instagram.android");
    }

    private void shareFileTopVk(){
        sharePhoto("com.vkontakte.android");
    }

    private void sharePhoto(String appPackage){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uriForFile = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", createImageFile());
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriForFile);
        shareIntent.setPackage(appPackage);
        startActivity(shareIntent);
    }

    private File createImageFile() {
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapData = bos.toByteArray();

        try {
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/memory");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File temp = new File(dir, System.currentTimeMillis() + ".jpg");
            boolean isNewFileCreated = temp.createNewFile();
            if (isNewFileCreated) {
                FileOutputStream fos = new FileOutputStream(temp);
                fos.write(bitmapData);
                fos.flush();
                fos.close();

                return temp;
            }
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "FileNotFoundException expected: ", e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException expected: ", e);
        }
        return null;
    }


    /////////////////////////////////// Просто код


    private void writeNewWarHero() {
        WarHero warHero = new WarHero();
        warHero.heroName = String.valueOf(editTextHeroName.getText());
        warHero.heroLastname = String.valueOf(editTextHeroLastname.getText());
        warHero.heroMiddlename = String.valueOf(editTextHeroMiddlename.getText());
        warHero.heroDateOfBirth = String.valueOf(editTextHeroDateOfBirth.getText());
        warHero.heroPlaceOfBirth = String.valueOf(editTextHeroPlaceOfBirth.getText());
        warHero.heroPlaceOfCallRegion = String.valueOf(editTextHeroPlaceOfCallRegion.getText());
        warHero.heroYearOfCall = String.valueOf(editTextHeroYearOfCall.getText());
        warHero.heroPlaceOfCall = String.valueOf(editTextHeroPlaceOfCall.getText());
        warHero.heroMilitaryRank = String.valueOf(editTextHeroMilitaryRank.getText());
        warHero.heroYearDateOfDeath = String.valueOf(editTextHeroYearDateOfDeath.getText());
        warHero.heroHeroStory = String.valueOf(editTextHeroHeroStory.getText());
        warHero.heroLinkToThirdPartyResources = String.valueOf(editTextHeroLinkToThirdPartyResources.getText());
        warHero.heroMailForAccessToYourPersonalAccount = String.valueOf(editTextHeroMailForAccessToYourPersonalAccount.getText());

        myRef.child("Users/" + authenticationID).child("Heros").child(  String.valueOf(editTextHeroName.getText())).setValue(warHero);



        myRef = FirebaseDatabase.getInstance().getReference("Users").child(authenticationID);
        HashMap<String, Object> map = new HashMap<>();
        map.put("userPercent", "" + percent);
        myRef.updateChildren(map);
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
        mailImage = true;
        checkFilds();
    }


    private void setPercentText(int per){
        intPercent = intPercent + per;
        percent = intPercent + "%";
        textViewPercent.setText(percent);
    }

    private void checkFilds(){
        intPercent = 0;

        if(editTextHeroMiddlename.getText().length() != 0){
            setPercentText(6);
        }

        if(editTextHeroDateOfBirth.getText().length() != 0){
            setPercentText(6);
        }

        if(editTextHeroPlaceOfBirth.getText().length() != 0){
            setPercentText(6);
        }

        if(editTextHeroPlaceOfCallRegion.getText().length() != 0){
            setPercentText(6);
        }

        if(editTextHeroYearOfCall.getText().length() != 0){
            setPercentText(6);
        }

        if(editTextHeroPlaceOfCall.getText().length() != 0){
            setPercentText(6);
        }

        if(editTextHeroMilitaryRank.getText().length() != 0){
            setPercentText(6);
        }

        if(editTextHeroYearDateOfDeath.getText().length() != 0){
            setPercentText(6);
        }

        if(editTextHeroHeroStory.getText().length() != 0){
            setPercentText(10);
        }

        if(editTextHeroLinkToThirdPartyResources.getText().length() != 0){
            setPercentText(6);
        }

        if(editTextHeroMailForAccessToYourPersonalAccount.getText().length() != 0){
            setPercentText(6);
        }

        if(photoImage){
            setPercentText(15);
        }

        if(mailImage){
            setPercentText(15);
        }
    }
}
























