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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.evilgeniuses.memoryhack.Model.ColorizeRequestModel;
import com.evilgeniuses.memoryhack.R;
import com.evilgeniuses.memoryhack.services.ColorizeService;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
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
import java.nio.file.Path;
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
    private ProgressBar progressBar;
    private Button btnUploadPhoto;
    private Button btnUpgradePhoto;
    private ImageView shareInInstagramBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_data, container, false);
        imageView = view.findViewById(R.id.imageView);
        progressBar = view.findViewById(R.id.progressBar);
        btnUploadPhoto = view.findViewById(R.id.btnUploadPhoto);
        btnUpgradePhoto = view.findViewById(R.id.btnUpgradePhoto);
        shareInInstagramBtn = view.findViewById(R.id.shareButtom);
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

        btnUpgradePhoto.setOnClickListener(v -> {

            progressBar.setVisibility(View.VISIBLE);

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
                                progressBar.setVisibility(View.GONE);
                                btnUpgradePhoto.setEnabled(false);
                            }));
        });

        shareInInstagramBtn.setOnClickListener(v -> shareFileToInstagram());
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
                        btnUpgradePhoto.setEnabled(true);
                        shareInInstagramBtn.setVisibility(View.VISIBLE);
                    } else if (faces.size() > 1) {
                        Toast.makeText(getContext(), "Не допускается загрузка групповых фотографий", Toast.LENGTH_SHORT).show();
                        shareInInstagramBtn.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getContext(), "На фотографии не распознаны лица, попробуйте загрузить другие фотографии", Toast.LENGTH_SHORT).show();
                        shareInInstagramBtn.setVisibility(View.GONE);
                    }

                }).addOnFailureListener(
                e -> {
                    Toast.makeText(getContext(), "На фотографии не распознаны лица, попробуйте загрузить другие фотографии", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                });
    }

    private void shareFileToInstagram() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uriForFile = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", createImageFile());
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriForFile);
        shareIntent.setPackage("com.instagram.android");
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
}