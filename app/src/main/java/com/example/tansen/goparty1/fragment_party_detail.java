package com.example.tansen.goparty1;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_party_detail extends Fragment {
//    private static final String ARG_PARTY = "party";
//    private HashMap<String, ?> party;
//    DatabaseReference databaseparty;
//
//    Integer imgId;

//    public static fragment_party_detail newInstance(HashMap<String, ?> party) {
//        fragment_party_detail fragment = new fragment_party_detail();
//        Bundle args = new Bundle();
//        args.putSerializable(ARG_PARTY, party);
//        fragment.setArguments(args);
//
//        return fragment;
//    }

//    public fragment_party_detail() {
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            party = (HashMap<String, ?>) getArguments().getSerializable(ARG_PARTY);
//        }
//        setHasOptionsMenu(true);
//
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle bundle) {
//        View view = inflater.inflate(R.layout.fragment_party_found_detail, container, false);
//
//
//        final TextView partyName = (TextView) view.findViewById(R.id.detail_patryName_create);
//        final TextView partyHost = (TextView) view.findViewById(R.id.detail_host_create);
//        final TextView partySize = (TextView) view.findViewById(R.id.detail_size_create);
//        final TextView theme = (TextView) view.findViewById(R.id.detail_theme_create);
//        final TextView startTime = (TextView) view.findViewById(R.id.detail_startTime_create);
//        final TextView endTime = (TextView) view.findViewById(R.id.detail_endTime_create);
//        final TextView description = (TextView) view.findViewById(R.id.detail_description_create);
//        final TextView address = (TextView) view.findViewById(R.id.detail_address_create);
//        final TextView zipcode = (TextView) view.findViewById(R.id.detail_zipcode_create);
//        final ImageView imageView = (ImageView) view.findViewById(R.id.detail_image_create);
//
////        DatabaseReference childRef =
////                FirebaseDatabase.getInstance().getReference().child("partydata").getRef();
//        getActivity().setTitle("Party detail");
//
//        partyName.setText((String) party.get("partyName"));
//        partyHost.setText((String) party.get("partyHost"));
//        partySize.setText((String) party.get("partySize"));
//        theme.setText((String) party.get("theme"));
//        address.setText((String) party.get("address"));
//        zipcode.setText((String) party.get("zipcode"));
//        partySize.setText((String) party.get("partySize"));
//        startTime.setText((String) party.get("startTime"));
//        endTime.setText((String) party.get("endTime"));
//        description.setText((String) party.get("description"));
//
//        switch ((String)party.get("theme")) {
//            case "Birthday":
//                imgId = R.drawable.birthday_party;
//                break;
//            case "Wedding":
//                imgId = R.drawable.wedding_party;
//                break;
//            case "Graduation":
//                imgId = R.drawable.graduation_party;
//                break;
//            case "Festival":
//                imgId = R.drawable.festival_party;
//                break;
//            case "Others":
//                imgId = R.drawable.others_party;
//                break;
//        }
//        if (imgId != null) {
//            imageView.setImageResource(imgId);
//        }
//
//        return view;
//    }

    private static final String ARG_PARTY = "party";
    private Party party;
    DatabaseReference databaseparty;

    Integer imgId;

    public static fragment_party_detail newInstance(Party party) {
        fragment_party_detail fragment = new fragment_party_detail();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARTY, party);
        fragment.setArguments(args);

        return fragment;
    }

    public fragment_party_detail() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            party = (Party) getArguments().getSerializable(ARG_PARTY);
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
        View view = inflater.inflate(R.layout.fragment_party_detail, container, false);


        final TextView partyName = (TextView) view.findViewById(R.id.detail_patryName_create);
        final TextView partyHost = (TextView) view.findViewById(R.id.detail_host_create);
        final TextView partySize = (TextView) view.findViewById(R.id.detail_size_create);
        final TextView theme = (TextView) view.findViewById(R.id.detail_theme_create);
        final TextView startTime = (TextView) view.findViewById(R.id.detail_startTime_create);
        final TextView endTime = (TextView) view.findViewById(R.id.detail_endTime_create);
        final TextView description = (TextView) view.findViewById(R.id.detail_description_create);
        final TextView address = (TextView) view.findViewById(R.id.detail_address_create);
        final TextView zipcode = (TextView) view.findViewById(R.id.detail_zipcode_create);
        final ImageView imageView = (ImageView) view.findViewById(R.id.detail_image_create);

//        DatabaseReference childRef =
//                FirebaseDatabase.getInstance().getReference().child("partydata").getRef();
        getActivity().setTitle("Party detail");

        partyName.setText((String) party.getPartyName());
        partyHost.setText((String) party.getPartyHost());
        partySize.setText((String) party.getPartySize());
        theme.setText((String) party.getTheme());
        address.setText((String) party.getAddress());
        zipcode.setText((String) party.getZipcode());
        startTime.setText((String) party.getStartTime());
        endTime.setText((String) party.getEndTime());
        description.setText((String) party.getDescription());

        switch ((String)party.getTheme()) {
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
