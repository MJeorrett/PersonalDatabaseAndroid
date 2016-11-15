package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Context;

import java.util.List;

/**
 * Created by user on 14/11/2016.
 */

public interface PDAEntity {

    String getTitle();
    List<PDAEntity> getChildEntities();
    void createNewChildEntity( String title );

}
