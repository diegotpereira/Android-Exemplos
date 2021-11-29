package br.java.a18_persistencia;

import android.preference.Preference;
import android.preference.PreferenceFragment;

public class ConfigFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return false;
    }
}
