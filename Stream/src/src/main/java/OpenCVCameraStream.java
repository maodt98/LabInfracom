package src.main.java;

import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class OpenCVCameraStream {

    //region Properties
    public static Mat frame = null;
    public static Mat frame1 = null;
    public static Mat frame2 = null;
    private static HttpStreamServer httpStreamService;
    private static HttpStreamServer1 httpStreamService1;
    private static HttpStreamServer2 httpStreamService2;
    static VideoCapture videoCapture;
    static VideoCapture videoCapture1;
    static VideoCapture videoCapture2;
    static Timer tmrVideoProcess;
    static Timer tmrVideoProcess1;
    static Timer tmrVideoProcess2;
    //endregion

    //region Methods

    public static void start() {
    	
    	videoCapture = new VideoCapture("Stream/src/video.avi");
        //videoCapture.open(0);
        if (!videoCapture.isOpened()) {
            return;
        }
     
        frame = new Mat();
        httpStreamService = new HttpStreamServer(frame);
        new Thread(httpStreamService).start();

        tmrVideoProcess = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!videoCapture.read(frame)) {
                    tmrVideoProcess.stop();
                }

                //procesed image
                httpStreamService.imag = frame;
            }
        });
        tmrVideoProcess.start();
        
        videoCapture1 = new VideoCapture("Stream/src/video1.avi");
        //videoCapture.open(0);
        if (!videoCapture1.isOpened()) {
            return;
        }
     
        frame1 = new Mat();
        httpStreamService1 = new HttpStreamServer1(frame1);
        new Thread(httpStreamService1).start();

        tmrVideoProcess1 = new Timer(60, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!videoCapture1.read(frame1)) {
                    tmrVideoProcess1.stop();
                }

                //procesed image
                httpStreamService1.imag = frame1;
            }
        });
        tmrVideoProcess1.start();
        
        videoCapture2 = new VideoCapture("Stream/src/video2.avi");
        //videoCapture.open(0);
        if (!videoCapture2.isOpened()) {
            return;
        }
     
        frame2 = new Mat();
        httpStreamService2 = new HttpStreamServer2(frame2);
        new Thread(httpStreamService2).start();

        tmrVideoProcess2 = new Timer(35, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!videoCapture2.read(frame2)) {
                    tmrVideoProcess2.stop();
                }

                //procesed image
                httpStreamService2.imag = frame2;
            }
        });
        tmrVideoProcess2.start();
    	
    }

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);//Load opencv native library
        start();
    }

    //endregion

}
