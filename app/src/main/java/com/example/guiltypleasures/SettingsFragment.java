package com.example.guiltypleasures;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private TextView changeEmail;
    private TextView changeUserName;
    private CheckBox switchChildMode;
    private CheckBox switchLightMode;
    private CheckBox switchProfileComments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_fragment,container,false);

        switchChildMode = v.findViewById(R.id.boxChildMode);
        switchLightMode = v.findViewById(R.id.boxLightMode);
        switchProfileComments = v.findViewById(R.id.boxProfileComments);
        changeUserName = v.findViewById(R.id.buttonChangeUsername);
        changeEmail = v.findViewById(R.id.buttonChangeEmail);

        mPreferences = getActivity().getSharedPreferences("com.example.Prefs_Guilty_Pleasures", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();

        CheckSharedPrefs();

        switchChildMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchChildMode.isChecked()){
                mEditor.putString(getString(R.string.ChildMode), "true");
                mEditor.commit();
            }else{
                mEditor.putString(getString(R.string.ChildMode), "false");
                mEditor.commit();
            }
        });

        switchProfileComments.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchProfileComments.isChecked()){
                mEditor.putString(getString(R.string.ProfileComments), "true");
                mEditor.commit();
            }else{
                mEditor.putString(getString(R.string.ProfileComments), "false");
                mEditor.commit();
            }
        });

        switchLightMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchLightMode.isChecked()){
                mEditor.putString(getString(R.string.LightMode), "true");
                mEditor.commit();
            }else{
                mEditor.putString(getString(R.string.LightMode), "false");
                mEditor.commit();
            }
        });

        return v;
    }
    private void CheckSharedPrefs(){
        String LightMode = mPreferences.getString(getString(R.string.LightMode), "false");
        String ChildMode = mPreferences.getString(getString(R.string.ChildMode), "false");
        String ProfileComments = mPreferences.getString(getString(R.string.ProfileComments), "false");

        if (LightMode.equals("true")){
            switchLightMode.setChecked(true);
        }
        if (ChildMode.equals("true")){
            switchChildMode.setChecked(true);
        }
        if (ProfileComments.equals("true")){
            switchProfileComments.setChecked(true);
        }
    }
}
