package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Intent;

import java.util.List;

/**
 * Created by user on 14/11/2016.
 */

public interface PDAEntity {

    String getName();
    List<PDAEntity> getChildEntities();
    Intent putExtrasInIntent( Intent intent );
    void createNewChildEntity( String title );

}
