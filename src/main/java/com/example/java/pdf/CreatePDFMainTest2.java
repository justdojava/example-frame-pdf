package com.example.java.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

/**
 * @author panzhi
 * @since 2021-09-12
 */
public class CreatePDFMainTest2 {

    public static void main(String[] args) throws Exception {
        String qrtext = "hello world";
        ByteArrayOutputStream out = QRCode.from(qrtext).to(ImageType.PNG).stream();
        byte[] arr = out.toByteArray();
        // 对字节数组Base64编码
        String str64 = Base64.encodeBytes(arr);// 返回Base64编码过的字节数组字符串
        System.out.println(str64);
    }
}
