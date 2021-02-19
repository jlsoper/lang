import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class ListFontsForJava
{
  public static void main(String[] a)
  {
    GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();

    Font[] fonts = environment.getAllFonts(); // Get the fonts

    for (Font f : fonts)
    {
      System.out.println(f.getFontName());
    }
  }
}
