package com.example.tansen.goparty1;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tansen on 8/15/17.
 */

public class PartyData {
    List<Map<String, ?>> partyList;
    DatabaseReference mRef;
    MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;
    Context mContext;

    public void setAdapter(MyFirebaseRecylerAdapter mAdapter) {
        myFirebaseRecylerAdapter = mAdapter;
    }

    public void removeItemFromServer(Map<String,?> movie){
        if(movie!=null){
            String id = (String)movie.get("id");
            mRef.child(id).removeValue();
        }
    }

    public void addItemToServer(Map<String,?> party){
        if(party!=null){
            String id = (String) party.get("id");
            mRef.child(id).setValue(party);
        }
    }

    public DatabaseReference getFireBaseRef(){
        return mRef;
    }
    public void setContext(Context context){mContext = context;}

    public List<Map<String, ?>> getPartyList() {
        return partyList;
    }

    public int getSize(){
        return partyList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < partyList.size()){
            return (HashMap) partyList.get(i);
        } else return null;
    }


    public void onItemRemovedFromCloud(HashMap item){
        int position = -1;
        String id=(String)item.get("id");
        for(int i=0;i<partyList.size();i++){
            HashMap party = (HashMap)partyList.get(i);
            String mid = (String)party.get("id");
            if(mid.equals(id)){
                position= i;
                break;
            }
        }
        if(position != -1){
            partyList.remove(position);
            Toast.makeText(mContext, "Item Removed:" + id, Toast.LENGTH_SHORT).show();

        }
    }

    public void onItemAddedToCloud(HashMap item){
        int insertPosition = 0;
        String id=(String)item.get("id");
        for(int i=0;i<partyList.size();i++){
            HashMap party = (HashMap)partyList.get(i);
            String mid = (String)party.get("id");
            if(mid.equals(id)){
                return;
            }
            if(mid.compareTo(id)<0){
                insertPosition=i+1;
            }else{
                break;
            }
        }
        partyList.add(insertPosition,item);
        Toast.makeText(mContext, "Item added:" + id, Toast.LENGTH_SHORT).show();

    }

    public void onItemUpdatedToCloud(HashMap item){
        String id=(String)item.get("id");
        for(int i=0;i<partyList.size();i++){
            HashMap party = (HashMap)partyList.get(i);
            String mid = (String)party.get("id");
            if(mid.equals(id)){
                partyList.remove(i);
                partyList.add(i,item);
                Log.d("My Test: NotifyChanged",id);

                break;
            }
        }

    }

    public void initializeDataFromCloud() {
        partyList.clear();
        mRef.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildAdded", dataSnapshot.toString());
                HashMap<String,String> party = (HashMap<String,String>)dataSnapshot.getValue();
                onItemAddedToCloud(party);

            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildChanged", dataSnapshot.toString());
                HashMap<String,String> movie = (HashMap<String,String>)dataSnapshot.getValue();
                onItemUpdatedToCloud(movie);
            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Log.d("MyTest: OnChildRemoved", dataSnapshot.toString());
                HashMap<String,String> movie = (HashMap<String,String>)dataSnapshot.getValue();
                onItemRemovedFromCloud(movie);
            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public PartyData(){

        partyList = new ArrayList<Map<String,?>>();
        mRef = FirebaseDatabase.getInstance().getReference().child("partydata").getRef();
        myFirebaseRecylerAdapter = null;
        mContext = null;

    }


    public int findFirst(String query){

        for(int i=0;i<partyList.size();i++){
            HashMap hm = (HashMap)getPartyList().get(i);
            if(((String)hm.get("name")).toLowerCase().contains(query.toLowerCase())){
                return i;
            }
        }
        return 0;

    }
}
