package com.example.tansen.goparty1;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.bumptech.glide.request.animation.GlideAnimationFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_recyclerview_findParty extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    //PartyData partyData;
    DatabaseReference databasepartydata;
    String zipcode;
    String hostname;
    String partyname;

    Query query;

    List<Party> partyList;
    HashMap<Integer, Party> map;
    Integer num;

    Locale locale = new Locale("en", "us");
    Geocoder geocoder;
    Location location;
    List<Address> addressList;
    String gpsZip;

    private AlertDialog mGPSDialog;
    private static final int GPS_ENABLE_REQUEST = 0x1001;


    fragment_recyclerview_findParty.OnListItemSelectedListener mListener;
    private Toolbar frag_recyc_toolbar;
    private TextView toolbarTitle;
    private Toolbar toolbar_bot;

    private FloatingActionButton floatButton;


    MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;


    @Override
    public void onStart() {
        super.onStart();
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGPSEnabled) {
            showGPSDiabledDialog();
        }
        databasepartydata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                partyList.clear();
                num = 0;
                for (DataSnapshot partySnapshot : dataSnapshot.getChildren()) {
                    Party party = partySnapshot.getValue(Party.class);
                    if (zipcode != null && party.getZipcode().equals(zipcode)) {
                        partyList.add(party);
                        System.out.println("add" + party);
                    } else if (hostname != null && party.getPartyHost().equals(hostname)) {
                        partyList.add(party);
                        System.out.println("add" + party);
                    } else if (partyname != null && party.getPartyName().equals(partyname)) {
                        partyList.add(party);
                        System.out.println("add" + party);
                    } else if (location != null && Integer.valueOf(party.getZipcode()) < Integer.valueOf(gpsZip) + 1000
                            && Integer.valueOf(party.getZipcode()) > Integer.valueOf(gpsZip) - 1000) {
                        partyList.add(party);
                    }
                }
                if (zipcode != null) {
                    Collections.sort(partyList, new Comparator<Party>() {
                        @Override
                        public int compare(Party party, Party t1) {
                            return Integer.valueOf(party.getZipcode()) - Integer.valueOf(t1.getZipcode());
                        }
                    });
                } else if (hostname != null) {
                    Collections.sort(partyList, new Comparator<Party>() {
                        @Override
                        public int compare(Party party, Party t1) {
                            String s1 = party.getPartyHost();
                            String s2 = t1.getPartyHost();
                            int x = String.CASE_INSENSITIVE_ORDER.compare(s1, s2);
                            if (x == 0) {
                                x = s1.compareTo(s2);
                            }
                            return x;
                        }
                    });
                } else if (partyname != null) {
                    Collections.sort(partyList, new Comparator<Party>() {
                        @Override
                        public int compare(Party party, Party t1) {
                            String s1 = party.getPartyName();
                            String s2 = t1.getPartyName();
                            int x = String.CASE_INSENSITIVE_ORDER.compare(s1, s2);
                            if (x == 0) {
                                x = s1.compareTo(s2);
                            }
                            return x;
                        }
                    });
                }
//                else if (location != null) {
//                    Collections.sort(partyList, new Comparator<Party>() {
//                        @Override
//                        public int compare(Party party, Party t1) {
//                            return Integer.valueOf(party.getZipcode()) - Integer.valueOf(t1.getZipcode());
//                        }
//                    });
//                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        System.out.println("1st haha");
//        GPStracker tracker= new GPStracker(getActivity().getApplicationContext());
//
//        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
//        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
////        if (isGPSEnabled) {
//        if (tracker != null) {
//            System.out.println("tracker on create: " + tracker);
//        }
//        geocoder = new Geocoder(getContext(), locale);
//        location = tracker.getLocation();
//        if (location != null) {
//            System.out.println("location on create: " + location);
//        }
//        System.out.println("2nd haha");
//        if (location != null) {
//            double lat = location.getLatitude();
//            double lon = location.getLongitude();
//            System.out.println("lat: " + lat);
//            System.out.println("lon: " + lon);
//            gpsZip = "13210";
////            try {
////                addressList = geocoder.getFromLocation(lat, lon, 1);
//////                addressList = geocoder.getFromLocationName("London", 1);
////                gpsZip = addressList.get(0).getPostalCode();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//        }
//        //      }
//        //       else {
//        //           gpsZip = "00000";
//        //       }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (fragment_recyclerview_findParty.OnListItemSelectedListener) getActivity();
    }

    public fragment_recyclerview_findParty() {
    }

    public static fragment_recyclerview_findParty newInstance(int index) {
        fragment_recyclerview_findParty ff = new fragment_recyclerview_findParty();
        Bundle args = new Bundle();
        args.putInt("index", index);
        ff.setArguments(args);
        return ff;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_recyclerview_find_party, container, false);
        floatButton = view.findViewById(R.id.floatingActionButton_findParty);
        getActivity().setTitle("Find party");

        if (getArguments().getString("zipcode") != null) {
            zipcode = getArguments().getString("zipcode");
            query = FirebaseDatabase.getInstance().getReference("partydata").orderByChild("zipcode").equalTo(zipcode);

        }

        else if (getArguments().getString("hostname") != null) {
            hostname = getArguments().getString("hostname");
            query = FirebaseDatabase.getInstance().getReference("partydata").orderByChild("partyHost").equalTo(hostname);

        }

        else if (getArguments().getString("partyname") != null) {
            partyname = getArguments().getString("partyname");
            query = FirebaseDatabase.getInstance().getReference("partydata").orderByChild("partyName").equalTo(partyname);

        }
//        else if (location != null) {
//            System.out.println("@@@" + gpsZip);
//            System.out.println("location: " + location);
//            String start = String.valueOf((Integer.valueOf(gpsZip) - 1000));
//            String end = String.valueOf((Integer.valueOf(gpsZip) + 1000));
//            query = FirebaseDatabase.getInstance().getReference("partydata").orderByChild("zipcode").startAt(start).endAt(end);
//
//        }
        // query = FirebaseDatabase.getInstance().getReference("partydata");
        if (query == null) {
            //showGPSDiabledDialog();
            System.out.println("query is null");

        } else {
            myFirebaseRecylerAdapter = new MyFirebaseRecylerAdapter(Party.class,
                    R.layout.cardview, MyFirebaseRecylerAdapter.PartyViewHolder.class,
                    query, getContext());
        }


        /*
        DatabaseReference childRef =
                FirebaseDatabase.getInstance().getReference().child("partydata").getRef();



        myFirebaseRecylerAdapter = new MyFirebaseRecylerAdapter(Party.class,
                R.layout.cardview, MyFirebaseRecylerAdapter.PartyViewHolder.class,
                childRef, getContext());
                */


        mRecyclerView = (RecyclerView) view.findViewById(R.id.cardList_findParty);

        //       mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(myFirebaseRecylerAdapter);

        partyList = new ArrayList<>();


        databasepartydata = FirebaseDatabase.getInstance().getReference("partydata");
//        if (getArguments().getString("hostname") != null) {
//            String hostname = getArguments().getString("hostname");
//        }
//        if (getArguments().getString("partyname") != null) {
//            String partyname = getArguments().getString("partyname");
//        }


        if (myFirebaseRecylerAdapter != null) {
            myFirebaseRecylerAdapter.setOnItemClickListener(new MyFirebaseRecylerAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View v, final int position) {
                    Party party = partyList.get(position);
                    System.out.println("Size: " + partyList.size());
                    for (int i = 0; i < partyList.size(); i++) {
                        System.out.println("PartyList: " + partyList.get(i).getPartyName());
                    }
                    String id = (String) party.getPartyId();
                    databasepartydata.child(id).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            HashMap<String, String> party = (HashMap<String, String>) dataSnapshot.getValue();
                            mListener.onListItemSelected(position, party);
                            //handleNavigationListener.navigateToMovieSelected(position, movie);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("My Test", "The Read failed: " + databaseError.getMessage());
                        }
                    });
                }
            });
        }

        if (mRecyclerView != null) {
            adapterAnimation();
            itemAnimation();
        }

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), activity_create_party.class);
                startActivity(intent);
            }
        });

        return view;
    }


    private void itemAnimation() {
        FadeInAnimator animator = new FadeInAnimator();
        animator.setInterpolator(new OvershootInterpolator());

        animator.setAddDuration(1000);
        animator.setRemoveDuration(1000);

        if (mRecyclerView != null) {
            mRecyclerView.setItemAnimator(animator);
        }
    }

    private void adapterAnimation() {
        //AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mRecyclerViewAdapter);
        //SlideInLeftAnimationAdapter slideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(mRecyclerViewAdapter);
        if (myFirebaseRecylerAdapter != null) {
            ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(myFirebaseRecylerAdapter);
            scaleAdapter.setInterpolator(new OvershootInterpolator());
            scaleAdapter.setDuration(500);
            if (mRecyclerView != null && scaleAdapter != null) {
                mRecyclerView.setAdapter(scaleAdapter);
            }
        }

    }

    public interface OnListItemSelectedListener {
        void onListItemSelected(int position, HashMap<String, ?> movie);
    }


    public void showGPSDiabledDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("GPS Disabled");
        builder.setMessage("Gps is disabled, in order to use the application properly you need to enable GPS of your device");
        builder.setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), GPS_ENABLE_REQUEST);
            }
        }).setNegativeButton("No, Just Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
            }
        });
        mGPSDialog = builder.create();
        mGPSDialog.show();
    }

}
