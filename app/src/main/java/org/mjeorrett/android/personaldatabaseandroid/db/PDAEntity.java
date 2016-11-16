package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by user on 14/11/2016.
 */

public interface PDAEntity {

    String getName();
    List<PDAEntity> getChildEntities( @Nullable PDAEntityType type );
    Intent putExtrasInIntent( Intent intent );
    void createNewChildEntity( String title );
    String getChildTypeDescription();
    PDAEntityType getType();

}
