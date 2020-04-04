package com.evilgeniuses.memoryhack.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.evilgeniuses.memoryhack.Data.CountryData;
import com.evilgeniuses.memoryhack.Interface.SwitchFragment;
import com.evilgeniuses.memoryhack.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PhoneNumberFragment extends Fragment implements View.OnClickListener {

    private SwitchFragment switchFragment;

    EditText editText;
    EditText editTextRegionCode;
    FloatingActionButton floatingActionButton;
    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_phone_number, container, false);
        editText = rootView.findViewById(R.id.editText);
        editTextRegionCode = rootView.findViewById(R.id.editTextRegionCode);
        floatingActionButton = rootView.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);

        spinner = rootView.findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                editTextRegionCode.setText("+" + code);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                editTextRegionCode.setText("+" + code);
            }

        });
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
            String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
            switchFragment.setFragment(new VerifiedCodeFragment().newInstance(" +" + code + " " + editText.getText().toString()), "Проверка телефона");
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
