import pdfium.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class Program {

    public static void main(String[] args){
        try (PDFium pdFium = new PDFium()){
            try(PDFDocument doc = pdFium.loadDocument("C:\\Users\\pacman29\\Desktop\\Pdfium-console\\document.pdf")) {
                List<PDFPage> pages = doc.getPages();
                for(int i = 0; i<pages.size(); ++i){
                    System.out.println("rendering : "+i);
                    PDFBitmap bitmap = pages.get(i).createBitmap(3.);
                    bitmap.fillBitmap(Color.WHITE);
                    bitmap.renderPageBitmap(pages.get(i));
                    BufferedImage bufferedImage = bitmap.toBufferedImage();
                    File out = new File("testImages/"+i+".png");
                    ImageIO.write(bufferedImage,"png",out);
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
        } catch (Exception err) {
            System.out.println(err.getMessage());
            System.out.println(err.getStackTrace());
        }

    }
}
