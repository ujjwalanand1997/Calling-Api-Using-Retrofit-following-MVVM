package ujjwal.gl.choice.preferences;


import android.content.Context;
import android.content.SharedPreferences;

import ujjwal.gl.choice.constant.Constants;

import static ujjwal.gl.choice.constant.Constants.PREF;

public class Preference {

    private Context mContext;
    private SharedPreferences sharedPreferences;

    public static void setSharedPreference(Context context,String k, String v){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(k,v);
        editor.commit();
    }

    public static String getSharedPref(Context context,String k){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF,Context.MODE_PRIVATE);
        return sharedPreferences.getString(k,null);
    }

}
