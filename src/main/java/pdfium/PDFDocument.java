package pdfium;

import com.sun.jna.Pointer;

import java.util.ArrayList;
import java.util.List;

public class PDFDocument implements AutoCloseable{
    private final Pointer handler;
    private final PDFiumLib pdfium;
    private int pageCount = 0;

    public PDFDocument(Pointer pointer, PDFiumLib pdfium) {
        this.handler = pointer;
        this.pdfium = pdfium;
    }


    @Override
    public void close() throws Exception {
        this.pdfium.FPDF_CloseDocument(this.handler);
    }

    public int getPageCount() {
        if(pageCount == 0){
            pageCount = this.pdfium.FPDF_GetPageCount(this.handler);
        }
        return pageCount;
    }

    public PDFPage getPage(int idx) throws IndexOutOfBoundsException{
        if(0 > idx || idx >= getPageCount()){
            throw new IndexOutOfBoundsException();
        }
        Pointer page = this.pdfium.FPDF_LoadPage(this.handler,idx);
        return new PDFPage(page,pdfium);
    }

    public List<PDFPage> getPages() throws IndexOutOfBoundsException{
        List<PDFPage> pages = new ArrayList<PDFPage>();
        for(int i = 0; i<getPageCount(); ++i){
            pages.add(this.getPage(i));
        }
        return pages;
    }
}
