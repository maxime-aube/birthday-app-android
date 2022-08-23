package fr.maximob.birthdayapp.android.utils;

import android.content.Context;

import org.json.JSONException;

import java.text.ParseException;

import fr.maximob.birthdayapp.android.models.User;

public class Util {

    private static final String PREF_FILE = "pref_file";
    private static final String USER = "user";

    public static void setUser(Context context, String json) {
        // TODO : sauvegarder
    }

    public static User getUser(Context context) throws JSONException, ParseException {
        // TODO : restaurer
        return null;
    }

    public static boolean isUserNameValid(String userName) {
        // TODO : écrire votre règle pour un username valide
        return false;
    }

    public static boolean isPasswordValid(String password) {
        // TODO : écrire votre règle pour un password valide
        return false;
    }
}
