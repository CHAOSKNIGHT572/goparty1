package com.example.tansen.goparty1;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by tansen on 8/16/17.
 */

public class fragment_find_party extends Fragment {
    EditText zipcode;
    EditText hostName;
    EditText partyName;

    Button search_btn;
    public fragment_find_party() {
    }

    public static fragment_find_party newInstance(int index) {
        fragment_find_party ff = new fragment_find_party();
        Bundle args = new Bundle();
        args.putInt("index", index);
        ff.setArguments(args);
        return ff;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_find_party, container, false);
        zipcode = view.findViewById(R.id.id_findByZipcode);
        hostName = view.findViewById(R.id.id_findByHostName);
        partyName = view.findViewById(R.id.id_findByPartyName);
        search_btn = view.findViewById(R.id.btn_search);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchzipcode = zipcode.getText().toString();
                String searchhostname = hostName.getText().toString();
                String searchpartyname = partyName.getText().toString();

                fragment_recyclerview_findParty ff = new fragment_recyclerview_findParty();
                Bundle args = new Bundle();

                if (searchzipcode.length() != 0 && searchhostname.length()==0 && searchpartyname.length() == 0) {
                    args.putString("zipcode", searchzipcode);
                    ff.setArguments(args);
                    System.out.println("searchzipcode");
                    getFragmentManager().beginTransaction()
                            .replace(R.id.id_layout_activity_findparty, ff)
                            .addToBackStack(null)
                            .commit();
                } else if (searchzipcode.length() == 0 && searchhostname.length() != 0 && searchpartyname.length() == 0) {
                    args.putString("hostname", searchhostname);
                    ff.setArguments(args);
                    System.out.println("searchhostname");
                    getFragmentManager().beginTransaction()
                            .replace(R.id.id_layout_activity_findparty, ff)
                            .addToBackStack(null)
                            .commit();
                } else if (searchzipcode.length() == 0 && searchhostname.length()==0 && searchpartyname.length() != 0) {
                    args.putString("partyname", searchpartyname);
                    ff.setArguments(args);
                    System.out.println("searchpartyname");
                    getFragmentManager().beginTransaction()
                            .replace(R.id.id_layout_activity_findparty, ff)
                            .addToBackStack(null)
                            .commit();
                } else {
                    System.out.println("ops");
                    Toast.makeText(getContext(), "Please fill out only one of the three criterias.", Toast.LENGTH_SHORT);
                }
            }
        });

        return view;
    }
}
