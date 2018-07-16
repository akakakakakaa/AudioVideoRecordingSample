package com.serenegiant.audiovideosample;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by icns on 2018-07-11.
 */

public class SocketManager extends Thread {
    public static final String TAG = "SocketManager";
    public static final String SERVER_IP = "163.180.117.216";
    public static final int SERVER_PORT = 9000;
    public static final int MAX_READ_SIZE = 128;
    public enum SocketStatus {

    }

    private Socket sock;
    private InputStream sockIn;
    private OutputStream sockOut;
    private Thread inputThread;
    private boolean isAlive;
    private byte[] readBuf;

    public SocketManager() {
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            sock = new Socket(serverAddr, SERVER_PORT);
            sockIn = sock.getInputStream();
            sockOut = sock.getOutputStream();
            isAlive = true;

            readBuf = new byte[MAX_READ_SIZE];
            inputThread = new Thread(this);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void close() {
        try {
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isAlive = false;
    }

    //TODO 2018-07-11: need to change based on queue
    public void write(byte[] buf) {
        try {
            sockOut.write(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        int readLen = 0;
        while(isAlive) {
            try {
                readLen = sockIn.read(readBuf);
                if(readLen < 0) {
                    Log.d(TAG, "socket read error");
                    close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            switch(readBuf[0]) {
                case 0x00:
                    break;
                case 0x01:
                    break;
            }
        }
    }
}