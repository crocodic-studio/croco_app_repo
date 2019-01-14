package com.crocodic.crocodicrepo.image;

import android.content.Intent;

/**
 * Created by Jason Rogena - jrogena@ona.io on 20/03/2017.
 */

public interface OnActivityResultListener {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
