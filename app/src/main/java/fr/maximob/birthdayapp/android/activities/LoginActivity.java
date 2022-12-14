package fr.maximob.birthdayapp.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import fr.maximob.birthdayapp.android.R;
import fr.maximob.birthdayapp.android.utils.ApiCallback;
import fr.maximob.birthdayapp.android.utils.Util;
import fr.maximob.birthdayapp.android.utils.UtilApi;

public class LoginActivity extends AppCompatActivity implements ApiCallback {

    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        handler = new Handler();

        mEmailView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login);
        mProgressView = findViewById(R.id.loading);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // rien.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // rien.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();
                mLoginFormView.setEnabled(Util.isUserNameValid(email) && Util.isPasswordValid(password));
            }
        };
        mEmailView.addTextChangedListener(textWatcher);
        mPasswordView.addTextChangedListener(textWatcher);

        mPasswordView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                try {
                    attemptLogin();
                } catch (NetworkErrorException e) {
                    Log.d("NETWORK ERROR", e.getMessage());
                }
            }
            return false;
        });

        mLoginFormView.setOnClickListener(v -> {
            try {
                attemptLogin();
            } catch (NetworkErrorException e) {
                Log.d("NETWORK ERROR", e.getMessage());
            }
        });
    }

    private void attemptLogin() throws NetworkErrorException {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        Log.d("LOGIN", "attempt login with usename = " + email + " and password = " + password);

        boolean cancel = false;
        View focusView = null;

        if (!Util.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!Util.isUserNameValid(email)) {
            mEmailView.setError(getString(R.string.invalid_username));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);

            Map<String, String> map = new HashMap<>();
            map.put("username", email);
            map.put("password", password);

            if (Util.isActiveNetwork(this)) {
                UtilApi.post(UtilApi.URL_LOGIN, map, this);
            } else {
                throw new NetworkErrorException("No active network available");
            }
        }
    }

    private void showProgress(boolean visible) {
        mPasswordView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void fail(final String json) {
        handler.post(() -> {
            Log.d("ERREUR", "Erreur : " + json);
            mProgressView.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public void success(final String json) {
        handler.post(() -> {
            Log.d("SUCCESS", "Success : " + json);
            Toast.makeText(this, "Connexion r??ussie", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ListActivity.class));
        });
    }
}