package com.evilgeniuses.memoryhack.Fragments;
import android.content.Context;
import com.evilgeniuses.memoryhack.Interface.SwitchFragment;
import com.evilgeniuses.memoryhack.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

    public class SiteFragment extends Fragment implements View.OnClickListener {

        private SwitchFragment switchFragment;

        WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.fragment_site, container, false);
        View rootView = inflater.inflate(R.layout.check_check, container, false);

//        webView = rootView.findViewById(R.id.webView);
//
//        webView.setWebViewClient(new WebViewClient());
//        webView.getSettings().setJavaScriptEnabled(true);
//
//        webView.loadUrl("https://foto.pamyat-naroda.ru/");



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

        public static SiteFragment newInstance() {
            return new SiteFragment();
        }

    }