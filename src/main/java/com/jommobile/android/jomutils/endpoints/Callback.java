package com.jommobile.android.jomutils.endpoints;

public interface Callback<T> {

  void onResponse(Promise<T> call, T response);

  /**
   * Invoked when a network exception occurred talking to the server or when an unexpected
   * exception occurred creating the request or processing the response.
   */
  void onFailure(Promise<T> call, Throwable t);
}
