package com.example.helloworld;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY_ID = "session_user_id";
    String SESSION_KEY_TOKEN = "session_user_token";
    String SESSION_KEY_NAME = "session_user_name";
    String SESSION_KEY_ROLE = "session_user_role";


    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public  void saveSession(User user){
        String token = user.getToken();
        String id = user.getId();
        String name = user.getName();
        int role = user.getRole();

        editor.putInt(SESSION_KEY_ROLE, role);
        editor.putString(SESSION_KEY_TOKEN, token);
        editor.putString(SESSION_KEY_NAME, name);
        editor.putString(SESSION_KEY_ID, id);
        editor.commit();

    }

    public User getSession(){
        int role = sharedPreferences.getInt(SESSION_KEY_ROLE, -1);
        String token = sharedPreferences.getString(SESSION_KEY_TOKEN, null);
        String id = sharedPreferences.getString(SESSION_KEY_ID, null);
        String name = sharedPreferences.getString(SESSION_KEY_NAME, null);

        if(role == -1){
            return null;
        }else {
            return new User(id,name , role, token);
        }
    }

    public void removeSession(){
        editor.putInt(SESSION_KEY_ROLE,-1);
    }
}
