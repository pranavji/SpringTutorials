package com.ospyn.helpers;

import java.io.*;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.core.io.ClassPathResource;

public class ZXingHelper {

    public  void getQRCodeImage(String text, int width, int height, String fname) {
        try {

            String x= this.getClass().getResource("../../../static/QRCodes/").getPath();
            File Qrpath = new File(x);
            if(!Qrpath.exists())
            {
                try{
                    Qrpath.mkdir();

                }
                catch(SecurityException se){
                    se.printStackTrace();
                }
            }
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", byteArrayOutputStream);
            OutputStream outputStream = new FileOutputStream(new File(x+fname+".png"), true);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}