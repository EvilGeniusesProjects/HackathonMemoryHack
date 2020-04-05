package com.evilgeniuses.memoryhack.Fragments;

import androidx.annotation.Nullable;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.evilgeniuses.memoryhack.Activity.MainActivity;
import com.evilgeniuses.memoryhack.Interface.SwitchFragment;
import com.evilgeniuses.memoryhack.Model.User;
import com.evilgeniuses.memoryhack.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.concurrent.TimeUnit;


public class VerifiedCodeFragment extends Fragment implements View.OnClickListener {

    private SwitchFragment switchFragment;
    static String PhoneNumber;

    EditText editTextNumberCode1;
    EditText editTextNumberCode2;
    EditText editTextNumberCode3;
    EditText editTextNumberCode4;
    EditText editTextNumberCode5;
    EditText editTextNumberCode6;



    private String verificationId;
    private FirebaseAuth mAuth;

    TextView textView;
    Button buttonOk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_verified_code, container, false);

        editTextNumberCode1 = rootView.findViewById(R.id.editTextNumberCode1);
        editTextNumberCode2 = rootView.findViewById(R.id.editTextNumberCode2);
        editTextNumberCode3 = rootView.findViewById(R.id.editTextNumberCode3);
        editTextNumberCode4 = rootView.findViewById(R.id.editTextNumberCode4);
        editTextNumberCode5 = rootView.findViewById(R.id.editTextNumberCode5);
        editTextNumberCode6 = rootView.findViewById(R.id.editTextNumberCode6);

        buttonOk = rootView.findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(this);

        textView = rootView.findViewById(R.id.textView);
        textView.setText(textView.getText() + PhoneNumber + ".");

        editTextNumberCode1.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);



        editTextNumberCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                editTextNumberCode2.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editTextNumberCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                editTextNumberCode3.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextNumberCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                editTextNumberCode4.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editTextNumberCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                editTextNumberCode5.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextNumberCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                editTextNumberCode6.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        editTextNumberCode6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                editTextNumberCode6.clearFocus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mAuth = FirebaseAuth.getInstance();

        PhoneNumber.replaceAll(" ", "");
        sendVerificationCode(PhoneNumber);



        return rootView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonOk:
                if(checkField()){
                    String code = editTextNumberCode1.getText().toString() + editTextNumberCode2.getText().toString() + editTextNumberCode3.getText().toString() + editTextNumberCode4.getText().toString() + editTextNumberCode5.getText().toString() + editTextNumberCode6.getText().toString();

                    if (code.isEmpty() || code.length() < 6) {
                        editTextNumberCode1.setText("-");
                        editTextNumberCode2.setText("-");
                        editTextNumberCode3.setText("-");
                        editTextNumberCode4.setText("-");
                        editTextNumberCode5.setText("-");
                        editTextNumberCode6.setText("-");
                        editTextNumberCode1.requestFocus();
                    }else{
                        verifyCode(code);
                    }

                }else{
                    Toast.makeText(getContext(), "Не удача", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }


            public boolean checkField() {
                boolean fild = true;

                if (editTextNumberCode1.getText().toString().replaceAll(" ", "").equals("-")) {
                    fild = false;
                }

                if (editTextNumberCode2.getText().toString().replaceAll(" ", "").equals("-")) {
                    fild = false;
                }

                if (editTextNumberCode3.getText().toString().replaceAll(" ", "").equals("-")) {
                    fild = false;
                }

                if (editTextNumberCode4.getText().toString().replaceAll(" ", "").equals("-")) {
                    fild = false;
                }

                if (editTextNumberCode5.getText().toString().replaceAll(" ", "").equals("-")) {
                    fild = false;
                }

                if (editTextNumberCode6.getText().toString().replaceAll(" ", "").equals("-")) {
                    fild = false;
                }

                return fild;
            }




    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

                            final ProgressDialog pd = new ProgressDialog(getContext());
                            pd.setMessage("Uploading");
                            pd.show();

                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User value = dataSnapshot.getValue(User.class);
                                    if(value!= null){
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                        pd.dismiss();
                                    }else{
                                        switchFragment.setFragment(new CreateProfileFragment().newInstance(PhoneNumber), "Профиль");
                                        pd.dismiss();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    //Log.w(TAG, "Не удалось прочитать значение", error.toException());
                                }
                            });

                        } else {
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {

                editTextNumberCode1.setText("" + code.charAt(0));
                editTextNumberCode2.setText("" + code.charAt(1));
                editTextNumberCode3.setText("" + code.charAt(2));
                editTextNumberCode4.setText("" + code.charAt(3));
                editTextNumberCode5.setText("" + code.charAt(4));
                editTextNumberCode6.setText("" + code.charAt(5));


                Toast.makeText(getContext(), "Успешно", Toast.LENGTH_LONG).show();
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SwitchFragment) {
            switchFragment = (SwitchFragment) context;
        }
    }

    public static VerifiedCodeFragment newInstance(String number) {
        PhoneNumber = number;
        return new VerifiedCodeFragment();
    }
}
