package colormemory.vicente.com.colormemory.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vicente on 6/12/2016.
 */
public abstract class ColorMemoryPreferences {

    private Context context;

    public ColorMemoryPreferences(Context context) {
        this.context = context;
    }

    public boolean getBooleanPreference(String prefName, String key, boolean defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }

    public void saveBooleanPreference(String prefName, String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(prefName);
        editor.putBoolean(key, value);
        editor.commit();
    }

    private SharedPreferences.Editor getEditor(String prefName) {
        SharedPreferences preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return preferences.edit();
    }

}
