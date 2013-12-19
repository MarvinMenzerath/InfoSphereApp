package de.boge.infosphere;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;

@SuppressWarnings("deprecation")
@SuppressLint("InlinedApi")
public class SettingsActivity extends SherlockPreferenceActivity {

	/**
	 * Lade die Einstellungen und ihre Werte und passe die ActionBar an
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		setupSimplePreferencesScreen();
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		
		//TODO: Einstellungen reinbringen (XML-Datei) und (wenn m�glich) Summary verkn�pfen
	}
	
	/**
	 * L�dt die Einstellungen aus der XML-Datei
	 */
	private void setupSimplePreferencesScreen() {
		addPreferencesFromResource(R.xml.prefs);
		
		// Zeige die gew�hlte Einstellung als Summary des Eintrags an
		//bindPreferenceSummaryToValue(findPreference("gui_schriftgroe�e"));
	}
	
	/**
	 * Helfer, der die gew�hlte Einstellung als Summary anzeigt
	 * @param preference Gew�hlte Einstellung
	 */
	@SuppressWarnings("unused")
	private static void bindPreferenceSummaryToValue(Preference preference) {
		preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
		sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));
	}

	/**
	 * Wartet auf �nderungen in den Einstellungen
	 */
	private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object value) {
			String stringValue = value.toString();
			if (preference instanceof ListPreference) {
				ListPreference listPreference = (ListPreference) preference;
				int index = listPreference.findIndexOfValue(stringValue);
				preference.setSummary(index >= 0 ? listPreference.getEntries()[index] : null);
			} else {
				preference.setSummary(stringValue);
			}
			return true;
		}
	};
	
	/**
	 * Schlie�e diese Activity, sobald auf das Home-Icon geklickt wird (--> Zur�ck)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
            finish();
        }
		return super.onOptionsItemSelected(item);
	}
}