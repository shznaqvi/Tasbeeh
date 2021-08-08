package com.technart.apps.tasbeeh_thecounter;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class WMWorker extends Worker {
    public WMWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {

        // Do the work here--in this case, upload the images.
        PrintThis();

        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }

    private void PrintThis() {
        try {
            Socket sock = new Socket("10.1.42.21", 9100);
            PrintWriter oStream = new PrintWriter(sock.getOutputStream());
            oStream.println("HI,test from Android Device");
            oStream.println("\n\n\n");
            oStream.close();
            sock.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
