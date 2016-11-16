package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Intent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 16/11/2016.
 */

public class PDARow implements PDAEntity {

    private PDATable mTable;
    private HashMap<String, String> mData;

    PDARow(PDATable table, HashMap<String, String> data ) {

        mTable = table;
        mData = data;
    }

    @Override
    public String getName() {

        List<String> values = getValues();
        String name = null;

        switch ( values.size() ) {

            case 0:
                name = "<row empty>";
                break;

            case 1:
                name = values.get( 0 );
                break;

            case 2:
                name = values.get( 0 ) + values.get( 1 );
        }

        return name;
    }

    public List<String> getValues() {

        return new ArrayList<>( mData.values() );
    }

    @Override
    public List<PDAEntity> getChildEntities() {
//        return null as PDARow will never be the mEntity in a PDAEntityListFragment
        return null;
    }

    @Override
    public Intent putExtrasInIntent(Intent intent) {
        return null;
    }

    @Override
    public void createNewChildEntity(String title) {
//        not implemented as PDARow will never be the mEntity in a PDAEntityListFragment
    }

    @Override
    public String getChildTypeDescription() {
//        return null as PDARow will never be the mEntity in a PDAEntityListFragment
        return null;
    }

    @Override
    public PDAEntityType getType() {

        return PDAEntityType.ROW;
    }
}
