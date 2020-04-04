package com.evilgeniuses.memoryhack.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.evilgeniuses.memoryhack.Interface.SwitchFragment;
import com.evilgeniuses.memoryhack.R;

public class VerifiedCodeFragment extends Fragment implements View.OnClickListener {

    private SwitchFragment switchFragment;
    static String PhoneNumber;

    EditText editTextNumberCode1;
    EditText editTextNumberCode2;
    EditText editTextNumberCode3;
    EditText editTextNumberCode4;
    EditText editTextNumberCode5;
    EditText editTextNumberCode6;

    TextView textView;

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

        textView = rootView.findViewById(R.id.textView);
        textView.setText(textView.getText() + PhoneNumber + ".");
        PhoneNumber.replaceAll(" ", "");

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




        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }





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
