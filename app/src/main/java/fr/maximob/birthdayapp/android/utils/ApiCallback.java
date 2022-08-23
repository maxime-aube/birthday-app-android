package fr.maximob.birthdayapp.android.utils;

public interface ApiCallback {
    void fail(String json);
    void success(String json);
}
