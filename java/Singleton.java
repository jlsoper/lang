// File Name: Singleton.java
public class Singleton
{
  private static Singleton singleton = new Singleton();

  private int counter = 0;
  
  /* A private Constructor prevents any other
   * class from instantiating.
   */
  private Singleton()
  {
  }

  /* Static 'instance' method */
  public static Singleton getInstance()
  {
    return singleton;
  }

  protected void iMethod()
  {
    System.out.println("output from the 'instance' method, counter = " + ++counter);
  }

  protected static void sMethod()
  {
    System.out.println("output from a static method");
  }
}
