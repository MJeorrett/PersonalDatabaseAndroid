package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by user on 16/11/2016.
 */

public class PDARow implements PDAEntity {

    private PDATable mTable;
    private UUID mId;
    private HashMap<String, String> mData;

    PDARow( PDATable table, HashMap<String, String> data ) {

        mTable = table;

        String uuidString = data.get("uuid");
        mId = UUID.fromString(uuidString);
        data.remove("uuid");
        data.remove("_id");

        mData = data;
    }

    PDARow( PDATable table, List<String> columnNames ) {

        mTable = table;
        mId = UUID.randomUUID();
        mData = new HashMap<>();

        for ( String columnName : columnNames ) {

            mData.put( columnName, "" );
        }

        mData.remove( "_id" );
        mData.remove( "uuid" );
    }

    @Override
    public String getName() {

        List<Pair<String, String>> fields = getFields();

        String name = "";
        int numFields = fields.size();

        if ( numFields == 0 ) {

            name = "<row empty>";

        } else {

            Pair aField;

            for ( int i = 0; i < numFields; i++ ) {

                aField = fields.get( i );
                name += aField.first + ": " + aField.second;
                if ( i < numFields - 1 ) name += " | ";
            }
        }

        return name;
    }

    public ContentValues getContentValues() {

        ContentValues contentValues = new ContentValues();

        for ( Map.Entry<String, String> entry : mData.entrySet() ) {

            contentValues.put( entry.getKey(), entry.getValue() );
        }

        contentValues.put( "uuid", mId.toString() );

        return contentValues;
    }

    public List<Pair<String, String>> getFields() {

        List<Pair<String, String>> fields = new ArrayList<>();
        Pair<String, String> pair;

        for ( Map.Entry<String, String> entry : mData.entrySet() ) {

            pair = new Pair<String, String>(entry.getKey(), entry.getValue());
            fields.add(pair);
        }

        return fields;
    }

    public void setField( String fieldName, String newValue ) {

        mData.put( fieldName, newValue );
        mTable.updateRow( this );
    }

    @Override
    public PDAEntityType getType() {

        return PDAEntityType.ROW;
    }

    public UUID getId() {

        return mId;
    }

    @Override
    public List<PDAEntity> getChildEntities( @Nullable PDAEntityType type ) {
//        return null as PDARow will never be the mEntity in a PDAEntityListFragment
        return null;
    }

    @Override
    public Intent putExtrasInIntent( Intent intent ) {
        return null;
    }

    @Override
    public void createNewChildEntity( Context context, String title ) {
//        not implemented as PDARow will never be the mEntity in a PDAEntityListFragment
    }

    @Override
    public String getChildTypeDescription() {
//        return null as PDARow will never be the mEntity in a PDAEntityListFragment
        return null;
    }
}
