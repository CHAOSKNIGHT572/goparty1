package com.example.tansen.goparty1;

import android.app.Fragment;
import android.content.Intent;
import android.icu.text.MessagePattern;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by tansen on 8/15/17.
 */

public class fragment_create_party extends Fragment {

    EditText editText_partyName;
    EditText editText_partyHost;
    Spinner editText_partySize;
    Spinner editText_partyTheme;
    TimePicker editText_startTime;
    TimePicker editText_endTime;
    EditText editText_address;
    EditText editText_zipcode;
    EditText editText_description;

    DatabaseReference databaseParty;


    Button create_btn;

    public fragment_create_party() {
    }

    public static fragment_create_party newInstance(int index) {
        fragment_create_party ff = new fragment_create_party();
        Bundle args = new Bundle();
        args.putInt("index", index);
        ff.setArguments(args);
        return ff;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.layout_fragment_create_party, container, false);

        databaseParty = FirebaseDatabase.getInstance().getReference("partydata");
        editText_partyName = view.findViewById(R.id.detail_patryName);
        editText_partyHost = view.findViewById(R.id.detail_host);
        editText_partySize = view.findViewById(R.id.detail_size);
        editText_partyTheme = view.findViewById(R.id.detail_theme);
        editText_startTime = view.findViewById(R.id.detail_startTime);
        editText_endTime = view.findViewById(R.id.detail_endTime);
        editText_address = view.findViewById(R.id.detail_address);
        editText_zipcode = view.findViewById(R.id.detail_zipcode);
        editText_description = view.findViewById(R.id.detail_description);


        editText_startTime.setIs24HourView(true);
        editText_endTime.setIs24HourView(true);

        create_btn = view.findViewById(R.id.button_create);

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Party party = addParty();
                if (party != null) {
                    Toast.makeText(getContext(), "You are hosting your own party. Find it by nearby parties or Find parties", Toast.LENGTH_LONG).show();
//                getActivity().finish();
//                Intent intent = new Intent(getActivity(), activity_recyclerview.class);
//                startActivity(intent);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.id_activity_create_party_layout, fragment_party_detail.newInstance(party))
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
        return view;
    }

    private Party addParty() {
        String partyName = editText_partyName.getText().toString();
        String partyHost = editText_partyHost.getText().toString();
        String partySize = editText_partySize.getSelectedItem().toString();
        String partyTheme = editText_partyTheme.getSelectedItem().toString();
        String startTime = String.format("%02d:%02d", editText_startTime.getHour(), editText_startTime.getMinute());
        String endTime = String.format("%02d:%02d", editText_endTime.getHour(), editText_endTime.getMinute());
        String description = editText_description.getText().toString();
        String address = editText_address.getText().toString();
        String zipcode = editText_zipcode.getText().toString();
        Party party = null;

        if (!TextUtils.isEmpty(partyName)) {
            String id = databaseParty.push().getKey();
            party = new Party(partyName, partyHost, partySize, startTime, endTime, address, zipcode, partyTheme, description, id);
            databaseParty.child(id).setValue(party);
            Toast.makeText(this.getContext(), "Party Added", Toast.LENGTH_LONG);
        } else {
            Toast.makeText(this.getContext(), "Please enter a unique Party Name.", Toast.LENGTH_LONG).show();
        }

        return party;
    }
}
