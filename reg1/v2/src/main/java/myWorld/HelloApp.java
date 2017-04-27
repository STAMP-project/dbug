package myWorld;

// **********************************************************************
import myWorld.MyStorage;

// **********************************************************************
public class HelloApp
{
   // **********************************************************************
   // public
   // **********************************************************************
   // ******** methods

   // **********************************************************************
   public HelloApp()
   {
      MyPrintCount = 1;
      MyTraces = null;
      MyTracesName = "myHelloApp.traces";
      ShouldPrintOnStdout = true;
      MyStorage.deleteFile(MyTracesName);
   }

   // **********************************************************************
   public HelloApp(int printCount)
   {
      MyPrintCount = printCount;
      MyTraces = null;
      MyTracesName = "myHelloApp.traces";
      ShouldPrintOnStdout = true;
      MyStorage.deleteFile(MyTracesName);
   }

   // **********************************************************************
   public HelloApp(String tracesName)
   {
      MyPrintCount = 1;
      MyTraces = null;
      MyTracesName = tracesName;
      ShouldPrintOnStdout = true;
      MyStorage.deleteFile(MyTracesName);
   }

   // **********************************************************************
   public HelloApp(int printCount, String tracesName)
   {
      MyPrintCount = printCount;
      MyTraces = null;
      MyTracesName = tracesName;
      ShouldPrintOnStdout = true;
      MyStorage.deleteFile(MyTracesName);
   }

   // **********************************************************************
   public void run()
   {
      String indent = "-";
      String message;
      String countString = Integer.toString(MyPrintCount);

      if (MyTracesName.length() > 0)
      {
         MyTraces = new MyStorage(MyTracesName);
      }
      else
      {
         MyTraces = new MyStorage();
      }

      MyTraces.addData(countString);

      for (int i = 1; i < MyPrintCount; i++)
      {
         indent = indent + "-";
      }
      myPrint(indent);
      myPrint(indent + " Hello World !");
      myPrint(indent);

      MyTraces.saveData();
   }

   // **********************************************************************
   // ******** attributes

   // **********************************************************************
   public int getMyPrintCount()
   {
      return(MyPrintCount);
   }

   // **********************************************************************
   public String getMyTracesName()
   {
      return(MyTracesName);
   }

   // **********************************************************************
   public boolean getShouldPrintOnStdout()
   {
      return(ShouldPrintOnStdout);
   }

   // **********
   public void setShouldPrintOnStdout(boolean value)
   {
      ShouldPrintOnStdout = value;
   }

   // **********************************************************************
   // ******** associations

   // **********************************************************************
   public int cardMyTraces()
   {
      int theCard = 0;

      if (MyTraces != null)
      {
         theCard = 1;
      }

      return(theCard);
   }

   // **********
   public MyStorage getMyTraces()
   {
      return(MyTraces);
   }

   // **********************************************************************
   // protected
   // **********************************************************************
   // ******** methods

   // **********************************************************************
   protected void myPrint(String message)
   {
      if (getShouldPrintOnStdout())
      {
         System.out.println(message);
      }
      MyTraces.addData(message);
   }

   // **********************************************************************
   // private
   // **********************************************************************
   // ******** attributes
   private int MyPrintCount;
   private String MyTracesName;
   private MyStorage MyTraces;
   private boolean ShouldPrintOnStdout;
}
