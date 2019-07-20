package com.jommobile.android.jomutils.repository;

import androidx.lifecycle.LiveData;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 7/19/2019
 */
public interface BoundResource<C> {

    public LiveData<Resource<C>> asLiveData();

}
