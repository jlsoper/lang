// File Name: SingletonDemo.java
public class SingletonDemo
{

  public static void main(String[] args)
  {
    Singleton one = Singleton.getInstance();
    
    Singleton.sMethod();  // static method
    
    one.iMethod();  // method belonging to an instance
  }
}
