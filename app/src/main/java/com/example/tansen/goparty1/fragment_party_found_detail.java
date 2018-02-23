package com.example.tansen.goparty1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by tansen on 8/16/17.
 */

public class fragment_party_found_detail extends Fragment {
    private static final String ARG_PARTY = "party";
    private HashMap<String, ?> party;
    DatabaseReference databaseparty;

    Integer imgId;

    public static fragment_party_found_detail newInstance(HashMap<String, ?> party) {
        fragment_party_found_detail fragment = new fragment_party_found_detail();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARTY, party);
        fragment.setArguments(args);

        return fragment;
    }

    public fragment_party_found_detail() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            party = (HashMap<String, ?>) getArguments().getSerializable(ARG_PARTY);
        }
        setHasOptionsMenu(true);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_party_found_detail, container, false);


        final TextView partyName = (TextView) view.findViewById(R.id.detail_patryName);
        final TextView partyHost = (TextView) view.findViewById(R.id.detail_host);
        final TextView partySize = (TextView) view.findViewById(R.id.detail_size);
        final TextView theme = (TextView) view.findViewById(R.id.detail_theme);
        final TextView startTime = (TextView) view.findViewById(R.id.detail_startTime);
        final TextView endTime = (TextView) view.findViewById(R.id.detail_endTime);
        final TextView description = (TextView) view.findViewById(R.id.detail_description);
        final TextView address = (TextView) view.findViewById(R.id.detail_address);
        final TextView zipcode = (TextView) view.findViewById(R.id.detail_zipcode);
        final ImageView imageView = (ImageView) view.findViewById(R.id.detail_image);

//        DatabaseReference childRef =
//                FirebaseDatabase.getInstance().getReference().child("partydata").getRef();
        getActivity().setTitle("Party detail");

        partyName.setText((String) party.get("partyName"));
        partyHost.setText((String) party.get("partyHost"));
        partySize.setText((String) party.get("partySize"));
        theme.setText((String) party.get("theme"));
        address.setText((String) party.get("address"));
        zipcode.setText((String) party.get("zipcode"));
        partySize.setText((String) party.get("partySize"));
        startTime.setText((String) party.get("startTime"));
        endTime.setText((String) party.get("endTime"));
        description.setText((String) party.get("description"));

        switch ((String)party.get("theme")) {
            case "Birthday":
                imgId = R.drawable.birthday_party;
                break;
            case "Wedding":
                imgId = R.drawable.wedding_party;
                break;
            case "Graduation":
                imgId = R.drawable.graduation_party;
                break;
            case "Festival":
                imgId = R.drawable.festival_party;
                break;
            case "Others":
                imgId = R.drawable.others_party;
                break;
        }
        if (imgId != null) {
            imageView.setImageResource(imgId);
        }

        return view;
    }
}