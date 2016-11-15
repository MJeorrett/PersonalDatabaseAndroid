package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Intent;

import java.util.List;

/**
 * Created by user on 15/11/2016.
 */

public class PDAColumn implements PDAEntity {

    private PDATable mTable;
    private String mName;

    PDAColumn( PDATable table, String name ) {

        mTable = table;
        mName = name;
    }

    @Override
    public String getName() {

        return mName;
    }

    @Override
    public List<PDAEntity> getChildEntities() {
        return null;
    }

    @Override
    public Intent putExtrasInIntent(Intent intent) {
        return null;
    }

    @Override
    public void createNewChildEntity(String title) {

    }

}
