package com.example.java.pdf;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class CreatePDFUtil {
	
	public static final String pdfPath = "/Users/panzhi/Documents/pz/coding/learning/java-example/example-frame-pdf/pdf-file";

	public static final int page_size = 100;

	public static final int font_size = 8;

	public static void createA4DeliveryPDF(String pdfHtml,String fileName) throws DocumentException, IOException {
		File file = new File(pdfPath);
		if(!file.isDirectory()) {
			file.mkdirs();
		}
		String DEST = pdfPath + File.separatorChar + fileName + ".pdf";
		File targetFile = new File(DEST);
		if(targetFile.exists()) {
			targetFile.delete();
		}


		Document document = new Document(PageSize.A4, 25, 25, 15, 40);// 左、右、上、下间距
        Rectangle pageSize = new Rectangle(25, 25, PageSize.A4.getHeight(), PageSize.A4.getWidth());
        pageSize.rotate();
        document.setPageSize(pageSize);

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));

		PdfReportHeaderFooter header = new PdfReportHeaderFooter("", font_size, PageSize.A4);
		writer.setPageEvent(header);
		writer.addViewerPreference(PdfName.PRINTSCALING, PdfName.NONE);
		document.open();

		// CSS
		CSSResolver cssResolver = new StyleAttrCSSResolver();
		CssAppliers cssAppliers = new CssAppliersImpl(new MyFontProvider());

		//html
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
		htmlContext.setImageProvider(new AbstractImageProvider() {
			@Override
			public Image retrieve(String src) {
				
				int pos = src.indexOf("base64,");
				try {
					if (src.startsWith("data") && pos > 0) {
						byte[] img = Base64.decode(src.substring(pos + 7));
						return Image.getInstance(img);
					} else if (src.startsWith("http")) {
						return Image.getInstance(src);
					}
				} catch (BadElementException ex) {
					return null;
				} catch (IOException ex) {
					return null;
				}
				return null;
			}

			@Override
			public String getImageRootPath() {
				return null;
			}
		});

		// Pipelines
		PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
		HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
		CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

		// XML Worker
		XMLWorker worker = new XMLWorker(css, true);
		XMLParser p = new XMLParser(worker);
		p.parse(new ByteArrayInputStream(pdfHtml.getBytes()));
		
		document.close();
	}
	
	
	private static String readFile() {
		StringBuffer textHtml = new StringBuffer();
		try {
			File file = new File("/Users/panzhi/Documents/pz/coding/learning/java-example/example-frame-pdf/src/main/resources/template/printDemo2.html");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
            	textHtml.append(tempString);
            }
            reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return textHtml.toString();
	}
	
	private static String createQRCode(String data) {
		String qrtext = data;
		ByteArrayOutputStream out = QRCode.from(qrtext).to(ImageType.PNG).stream();
		byte[] arr = out.toByteArray();
		// 对字节数组Base64编码
		String str64 = Base64.encodeBytes(arr);// 返回Base64编码过的字节数组字符串
		return str64;
	}
	
	public static void main(String[] args) throws DocumentException, IOException {
		String html = readFile();
		createA4DeliveryPDF(html,"printDemo-" + System.currentTimeMillis());
	}

}
