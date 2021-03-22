using System;

namespace MyApp
{
  // dotnet new console

  class Program
  {
    static void Main(string[] args)
    {
      Console.WriteLine("Hello World!");

      Console.Write("Enter your 1st name: ");
      String input = Console.ReadLine();

      Console.WriteLine("You entered: " + input);

    }
  }
}

