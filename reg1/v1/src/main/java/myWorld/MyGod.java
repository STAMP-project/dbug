package myWorld;

// **********************************************************************
import myWorld.HelloApp;

// **********************************************************************
public class MyGod
{
   // **********************************************************************
   // public
   // **********************************************************************
   // ******** methods

   // **********************************************************************
   public static void main(String[] args)
   {
      int printcount = 1;
      String fileName = "";
      boolean printOnStdout = true;

      if (args.length > 0)
      {
         if (args[0].equals("-h"))
         {
            printHelp();
            System.exit(0);
         }
         try
         {
            printcount = Integer.parseInt(args[0]);
         }
         catch (NumberFormatException e)
         {
            System.err.println("Argument" + args[0] + " must be an integer, -h for help");
            System.exit(1);
         }
         if (args.length > 1)
         {
            fileName = args[1];
         }
         if (args.length > 2)
         {
            if (args[2].equals("-no_stdout"))
            {
               printOnStdout = false;
            }
            else
            {
               System.err.println("Unknown argument: " + args[1] + ", -h for help");
               System.exit(1);
            }
         }
         if (args.length > 3)
         {
            System.err.println("Too many arguments, -h for help");
            System.exit(1);
         }
      }

      HelloApp myApp = new HelloApp(printcount, fileName);
      myApp.setShouldPrintOnStdout(printOnStdout);
      myApp.run();
   }

   // **********************************************************************
   public static void printHelp()
   {
      System.out.println("myWorld.MyGod");
      System.out.println("Print on stdout a 'Hello World !' message formatted according to");
      System.out.println("<indentation_count>, and generate an output file");
      System.out.println("");
      System.out.println("args: [-h | <indentation_count> [<file_name>] [-no_stdout]]");
      System.out.println("   -h: this help");
      System.out.println("   <indentation_count>: number of '-' for message indentation, default is 1");
      System.out.println("   <file_name>: output file name, default is my_storage.txt");
      System.out.println("      output file name contains <indentation_count> and the stdout");
      System.out.println("   -no_stdout: if specified, do not print anything on stdout,");
      System.out.println("      generate only the output file");
   }
}
