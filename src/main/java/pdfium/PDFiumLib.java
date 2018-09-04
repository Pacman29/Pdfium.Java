package pdfium;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public interface PDFiumLib extends Library {
    enum FPDFBitmap_format {
        DefaultNotUse,
        FPDFBitmap_Gray,
        FPDFBitmap_BGR,
        FPDFBitmap_BGRx,
        FPDFBitmap_BGRA
    }

    Pointer FPDF_LoadDocument(String file_path, String password);
    void FPDF_CloseDocument (Pointer doc);
    Integer FPDF_GetPageCount(Pointer doc);
    Pointer FPDF_LoadPage(Pointer doc, int index);
    void FPDF_ClosePage(Pointer page);
    long FPDF_GetLastError();
    void FPDF_InitLibrary();
    void FPDF_DestroyLibrary();
    Double FPDF_GetPageHeight(Pointer page);
    Double FPDF_GetPageWidth(Pointer page);
    Pointer FPDFBitmap_Create(int width, int height, Boolean hasAlpha);
    Pointer FPDFBitmap_CreateEx(int width, int height, int format, Pointer first_scan, int stride);
    void FPDFBitmap_Destroy(Pointer bitmap);
    int FPDFBitmap_GetHeight(Pointer bitmap);
    int FPDFBitmap_GetWidth(Pointer bitmap);
    /*
    [in]	bitmap	Handle to a FPDF_BITMAP object which specifies a valid device independent bitmap.
[in]	page	Handle to a FPDF_PAGE object that specifies a valid PDF page.
[in]	start_x	Left pixel position of the display area in the bitmap coordinate.
[in]	start_y	Top pixel position of the display area in the bitmap coordinate.
[in]	size_x	Horizontal size (in pixels) for displaying the page.
[in]	size_y	Vertical size (in pixels) for displaying the page.
[in]	rotate	Page orientation:
0: normal
1: rotated 90 degrees clockwise
2: rotated 180 degrees
3 :rotated 90 degrees counter-clockwise
[in]	flags	Render flags. 0 means normal display, or please refer to macro definitions defined in "Macro Definitions for Render Flags" and this can be one or combination of these macros.
     */
    void FPDF_RenderPageBitmap(Pointer bitmap,Pointer page,	int start_x, int start_y, int size_x, int size_y, int rotate, int flags);
    Pointer FPDFBitmap_GetBuffer(Pointer bitmap);
    Integer FPDFBitmap_GetStride(Pointer bitmap);
    void FPDFBitmap_FillRect(Pointer bitmap, int left, int top, int width, int height, int color);
}
