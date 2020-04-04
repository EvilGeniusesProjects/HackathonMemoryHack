package com.evilgeniuses.memoryhack.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.evilgeniuses.memoryhack.Interface.SwitchFragment;
import com.evilgeniuses.memoryhack.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PhoneNumberFragment extends Fragment implements View.OnClickListener {

    private SwitchFragment switchFragment;

    EditText editText;
    FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_phone_number, container, false);
        editText = rootView.findViewById(R.id.editText);
        floatingActionButton = rootView.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton:
                checkNumberField();
                break;
        }
    }

    private void checkNumberField(){


        if (editText.getText().toString().replaceAll("[\\s\\d]", "").length() > 0) {
            Toast.makeText(getContext(), "Неправильный номер", Toast.LENGTH_SHORT).show();
        } else {
            switchFragment.setFragment(new VerifiedCodeFragment().newInstance(" +7 " + editText.getText().toString()), "Проверка телефона");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SwitchFragment) {
            switchFragment = (SwitchFragment) context;
        }
    }

    public static PhoneNumberFragment newInstance() {
        return new PhoneNumberFragment();
    }
}
