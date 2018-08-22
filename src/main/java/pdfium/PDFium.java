package pdfium;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;

public class PDFium implements AutoCloseable{
    private final PDFiumLib pdfium;

    public PDFium(){
        this("lib");
    }

    public PDFium(String libPath) {
        NativeLibrary.addSearchPath("pdfium_x64",libPath);
        pdfium = (PDFiumLib) Native.loadLibrary("pdfium_x64",PDFiumLib.class);
        pdfium.FPDF_InitLibrary();
    }

    @Override
    public void close() throws Exception {
        pdfium.FPDF_DestroyLibrary();
    }

    public PDFDocument loadDocument(String path){
        return this.loadDocument(path,null);
    }

    public PDFDocument loadDocument(String path, String password){
        Pointer doc = pdfium.FPDF_LoadDocument(path,password);
        return new PDFDocument(doc,pdfium);
    }
}
