package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Intent;

import java.util.List;

/**
 * Created by user on 16/11/2016.
 */

public class PDARow implements PDAEntity {

    private PDATable mTable;
    private String mName;

    PDARow( PDATable table, String name ) {

        mTable = table;
        mName = name;
    }

    @Override
    public String getName() {

        return mName;
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
