package com.example.tansen.goparty1; //change the package name to your project's package name

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class MyFirebaseRecylerAdapter extends FirebaseRecyclerAdapter<Party, MyFirebaseRecylerAdapter.PartyViewHolder> {

    private Context mContext;
    static OnItemClickListener mItemClickListener;
    public Integer imgId;

    public interface OnItemClickListener {
        void OnItemClick(View v, int position);
       // void OnItemLongClick(View v, int position);
        //void onOverFlowMenuClick(View v, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener
                                               mItemClickListener ) {
        this.mItemClickListener = mItemClickListener;
    }

    public MyFirebaseRecylerAdapter(Class<Party> modelClass, int modelLayout,
                                    Class<PartyViewHolder> holder, Query query, Context context) {
        super(modelClass, modelLayout, holder, query);
        this.mContext = context;
    }

    public MyFirebaseRecylerAdapter(Class<Party> modelClass, int modelLayout,
                                    Class<PartyViewHolder> holder, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, holder, ref);
        this.mContext = context;
    }



    @Override
    protected void populateViewHolder(PartyViewHolder partyViewHolder, Party party, int i) {

        //TODO: Populate viewHolder by setting the party attributes to cardview fields

        partyViewHolder.pName.setText(party.getPartyName());
      //  System.out.println("nameofparty" + party.getPartyName());
        partyViewHolder.pHost.setText(party.getPartyHost());
        partyViewHolder.pTheme.setText("(" + party.getTheme() + ")");
        partyViewHolder.pTime.setText("Start at: " + party.getStartTime());
        partyViewHolder.pZipcode.setText(party.getZipcode());

        switch (party.getTheme()) {
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
            partyViewHolder.pImage.setImageResource(imgId);
        }
        //Picasso.with(mContext).load(party.getUrl()).into(partyViewHolder.mvIcon);

    }

    //TODO: Populate ViewHolder and add listeners.
    public static class PartyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mvIcon;
        public TextView pName;
        public TextView pHost;
        public TextView pTheme;
        public TextView pTime;
        public TextView pZipcode;
        public ImageView pImage;

       // public ImageView mvOverflow;

        public PartyViewHolder(View view) {
            super(view);
           // mvIcon = (ImageView) view.findViewById(R.id.party);
            pName = (TextView) view.findViewById(R.id.card_partyName);
            pHost = (TextView) view.findViewById(R.id.card_host);
            pTheme = (TextView) view.findViewById(R.id.card_theme);
            pTime = (TextView) view.findViewById(R.id.card_time);
            pZipcode = (TextView) view.findViewById(R.id.card_zipcode);
            pImage = (ImageView) view.findViewById(R.id.card_image);


           // mvOverflow = (ImageView) view.findViewById(R.id.overflowicon);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemClick(v, getAdapterPosition());
                    }
                }
            });
//
//            view.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    if (mItemClickListener != null) {
//                        mItemClickListener.OnItemLongClick(view, getAdapterPosition());
//                    }
//                    return true;
//                }
//            });

//            if (mvOverflow != null) {
//                mvOverflow.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (mItemClickListener != null) {
//                            mItemClickListener.onOverFlowMenuClick(v, getAdapterPosition());
//                        }
//                    }
//                });
//            }
        }


    }
}
