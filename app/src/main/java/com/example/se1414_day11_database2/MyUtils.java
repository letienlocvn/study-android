package com.example.se1414_day11_database2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class MyUtils {

    public void copyFile(String resource, String destination) throws Exception {
        File srcF = new File(resource);
        File desF = new File(destination);
        FileChannel srcChannel = new FileInputStream(srcF).getChannel();
        FileChannel desChannel = new FileOutputStream(desF).getChannel();
        desChannel.transferFrom(srcChannel, 0, srcChannel.size());

    }
}
