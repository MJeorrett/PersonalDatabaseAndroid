package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Context;

import java.util.List;

/**
 * Created by user on 15/11/2016.
 */

public class PDATable implements PDAEntity {

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public List<PDAEntity> getChildEntities() {
        return null;
    }

    @Override
    public void createNewChildEntity( String title) {

    }

}
