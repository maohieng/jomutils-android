package com.jommobile.android.util.model;

import java.util.Date;

/**
 * @author MAO Hieng on 12/31/18.
 */
public interface BaseModel extends LongId {

    public Date getCreatedDate();

    public Date getModifiedDate();

    public boolean isSync();

    public Flavor getFlavor();

}
