package com.crocodic.crocodicrepo.image;

public interface IImage {

    void addOnActivityResultListener(Integer requestCode, OnActivityResultListener onActivityResultListener);

    void addOnActivityRequestPermissionResultListener(Integer requestCode, OnActivityRequestPermissionResultListener onActivityRequestPermissionResultListener);

    void removeOnActivityRequestPermissionResultListener(Integer requestCode);

}
