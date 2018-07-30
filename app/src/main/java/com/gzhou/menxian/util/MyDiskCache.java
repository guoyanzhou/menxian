package com.gzhou.menxian.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyDiskCache {
    private DiskLruCache cache = null;
    private static final int VER = 1;
    private static final int MAX_SIZE = 10 * 1024;

    public static final MyDiskCache newInstance(final @NonNull Context context,
                                                  final @NonNull String dirName,
                                                  final int countPerEntry) {
        return new MyDiskCache(context, dirName, countPerEntry);
    }

    private MyDiskCache(final @NonNull Context context,
                        final @NonNull String dirName,
                        final int countPerEntry) {
        final File fileCache = new File(context.getApplicationContext().getCacheDir(), dirName);
        if (!fileCache.exists()) {
            fileCache.mkdir();
        }
        try {
            cache = DiskLruCache.open(fileCache, VER, countPerEntry, MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<Integer> getFavoritesList() {
        List<Integer> list = new ArrayList<>();
        return list;
    }
}
