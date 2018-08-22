package pdfium;


import com.sun.jna.Pointer;

public class PDFPage implements AutoCloseable {
    final Pointer handler;
    private final PDFiumLib pdfium;

    private double height = 0;
    private double width = 0;

    PDFPage(Pointer page, PDFiumLib pdfium){
        this.handler = page;
        this.pdfium = pdfium;
    }

    public double getHeight(){
        if(height == 0){
            height = pdfium.FPDF_GetPageHeight(this.handler);
        }
        return  height;
    }

    public double getWidth(){
        if(width == 0){
            width = pdfium.FPDF_GetPageWidth(this.handler);
        }
        return width;
    }

    public PDFBitmap createBitmap(double scale){
        return this.createBitmap(
                (int)this.getWidth(),
                (int)this.getHeight(),
                scale,
                true);
    }

    public PDFBitmap createBitmap(int width,int height, double scale){
        return this.createBitmap(width,height,scale,true);
    }

    public PDFBitmap createBitmap(int width, int height, double scale, boolean hasAlpha){
        Pointer bitmap = this.pdfium.FPDFBitmap_Create((int)(width*scale),(int)(height*scale),hasAlpha);
        return new PDFBitmap(bitmap,pdfium);
    }

    @Override
    public void close() throws Exception {
        this.pdfium.FPDF_ClosePage(this.handler);
    }
}
