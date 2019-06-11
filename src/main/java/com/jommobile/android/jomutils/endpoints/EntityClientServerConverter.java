package com.jommobile.android.jomutils.endpoints;

import androidx.annotation.Nullable;

import com.google.api.client.json.GenericJson;
import com.jommobile.android.jomutils.db.BaseEntity;

import java.util.List;

public class EntityClientServerConverter<CE extends BaseEntity, SE extends GenericJson> {

    private final Class<CE> mCeClass;
    private final Class<SE> mSeClass;

    public EntityClientServerConverter(Class<CE> ceClass, Class<SE> seClass) {
        mCeClass = ceClass;
        mSeClass = seClass;
    }

    public SE toServerEntity(CE clientEntity, @Nullable Class<?>... serverInnerTypes) {
        return ResponseAccessObjects.createResponse(mSeClass, clientEntity, serverInnerTypes);
    }

    public CE toClientEntity(SE serverEntity, @Nullable Class<?>... clientInnerTypes) {
        return ResponseAccessObjects.createClientObject(mCeClass, serverEntity, clientInnerTypes);
    }

    public List<CE> toClientEntities(List<SE> serverEntities) {
        return ResponseAccessObjects.createClientObjectList(mCeClass, serverEntities);
    }


}
