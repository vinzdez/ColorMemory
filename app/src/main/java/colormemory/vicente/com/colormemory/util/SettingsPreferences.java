package colormemory.vicente.com.colormemory.util;

import android.content.Context;

/**
 * Created by Asus on 6/12/2016.
 */
public class SettingsPreferences extends ColorMemoryPreferences {

    protected static final String PREFS_NAME_SETTINGS = "Settings";

    private static final String PREFS_KEY_SHOWCASE_APPEARED = "SHOWCASE_APPEARED";

    public SettingsPreferences(Context context) {
        super(context);
    }

    public boolean isShowCaseAppeared() {
        return getBooleanPreference(PREFS_NAME_SETTINGS, PREFS_KEY_SHOWCASE_APPEARED, false);
    }

    public SettingsPreferences disableShowCaseAppearance() {
        saveBooleanPreference(PREFS_NAME_SETTINGS, PREFS_KEY_SHOWCASE_APPEARED, true);
        return this;
    }
}
