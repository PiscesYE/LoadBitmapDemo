package com.piscesye.loadbitmapdemo.tool;

import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class TagTaskDrawable extends BitmapDrawable {
    private WeakReference<LoadBitmapFromStorageTask> taskReference;

    TagTaskDrawable(LoadBitmapFromStorageTask task){
        taskReference = new WeakReference(task);
    }

    public Reference<LoadBitmapFromStorageTask> getTaskReference() {
        return taskReference;
    }
}
