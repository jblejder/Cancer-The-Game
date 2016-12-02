package com.kutapps.keyten.shared.database;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.kutapps.keyten.shared.database.constants.DatabaseFields;
import com.kutapps.keyten.shared.database.models.KeytenModel;

public class DatabaseHelper
{
    private FirebaseDatabase db;

    public DatabaseHelper()
    {
        db = FirebaseDatabase.getInstance();
    }

    public void setKeyten(KeytenModel model)
    {
        db.getReference(DatabaseFields.KEYTEN).setValue(new Gson().toJson(model));
    }

    public void listenKeytenChange(final DbListener<KeytenModel> listener)
    {
        db.getReference(DatabaseFields.KEYTEN).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Gson gson = new Gson();
                String value = dataSnapshot.getValue(String.class);
                KeytenModel model = gson.fromJson(value, KeytenModel.class);
                listener.newValue(model);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                listener.error(databaseError);
            }
        });
    }

    public interface DbListener<T>
    {
        void newValue(T model);

        void error(DatabaseError error);
    }
}
