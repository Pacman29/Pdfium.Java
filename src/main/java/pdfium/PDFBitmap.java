package pdfium;

import com.sun.jna.Pointer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteOrder;


public class PDFBitmap implements AutoCloseable {
    private final Pointer handler;
    private final PDFiumLib pdfium;

    private int height = 0;
    private int width = 0;

    PDFBitmap(Pointer bitmap, PDFiumLib pdfium){
        this.pdfium = pdfium;
        this.handler = bitmap;
    }

    public void fillBitmap(Color color){
        this.fillRect(0,0,this.getWidth(),this.getHeight(),color);
    }

    public void fillRect(int left, int top, int width, int height, Color color){
        this.pdfium.FPDFBitmap_FillRect(this.handler,left,top,width,height,color.getRGB());
    }

    public int getHeight() {
        if(height == 0){
            height = this.pdfium.FPDFBitmap_GetHeight(this.handler);
        }
        return height;
    }

    public int getWidth(){
        if(width == 0){
            width = this.pdfium.FPDFBitmap_GetWidth(this.handler);
        }
        return width;
    }

    public void renderPageBitmap(PDFPage page){
        this.pdfium.FPDF_RenderPageBitmap(this.handler,page.handler,0,0,getWidth(),getHeight(),0,0);
    }


    public BufferedImage toBufferedImage(){
        int w = getWidth(), h = getHeight();
        BufferedImage bufferedImage = new BufferedImage(w,h,BufferedImage.TYPE_4BYTE_ABGR);
        int pixelCount = w*h;
        Pointer buffer = this.pdfium.FPDFBitmap_GetBuffer(this.handler);
        int[] rgbaArray = buffer.getIntArray(0,pixelCount);
        bufferedImage.setRGB(0,0,w,h,rgbaArray,0,w);
        return bufferedImage;
    }

    @Override
    public void close() throws Exception {
        this.pdfium.FPDFBitmap_Destroy(this.handler);
    }
}
