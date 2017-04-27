package myWorld;

// **********************************************************************
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.*;

// **********************************************************************
public class MyStorage
{
   // **********************************************************************
   // public
   // **********************************************************************
   // ******** methods

   // **********************************************************************
   public static void deleteFile(String fileName)
   {
      File theFile = null;

      // delete file if it already exists
      theFile = new File(fileName);
      if (theFile.exists())
      {
         theFile.delete();
      }
   }

   // **********************************************************************
   public MyStorage()
   {
      FileName = "my_storage.txt";
      MyData = new ArrayList<String>();
   }

   // **********************************************************************
   public MyStorage(String fileName)
   {
      this.FileName = fileName;
      MyData = new ArrayList<String>();
   }

   // **********************************************************************
   public void readData()
   {
      BufferedReader myBuffer = null;
      FileReader myFile = null;
      String currentLine;

      try
      {
         myFile = new FileReader(FileName);
         myBuffer = new BufferedReader(myFile);
         while ((currentLine = myBuffer.readLine()) != null)
         {
            addData(currentLine);
         }
      }
      catch(IOException e)
      {
         System.out.println("Error: cannot read " + FileName);
      }

      try
      {
         if (myBuffer != null)
         {
            myBuffer.close();
         }
         if (myFile != null)
         {
            myFile.close();
         }
      }
      catch(IOException e)
      {
         System.out.println("Error: closing " + FileName);
      }
   }

   // **********************************************************************
   public void saveData()
   {
      try
      {
         PrintStream writer = new PrintStream(new File(FileName));
         for (int i = 0; i < getDataSize(); i++)
         {
            writer.println(getData(i));
         }
         writer.close();
      }
      catch(IOException e)
      {
         System.out.println("Error: cannot write into " + FileName);
      }
   }

   // **********************************************************************
   public boolean isEqual(MyStorage anotherStorage)
   {
      boolean areEqual = (getDataSize() == anotherStorage.getDataSize());

      for (int i = 0; i < getDataSize() && areEqual; i++)
      {
         if (! (getData(i).equals(anotherStorage.getData(i))))
         {
            areEqual = false;
         } 
      }

      return(areEqual);
   }

   // **********************************************************************
   public boolean dataAreEqual(ArrayList<String> otherData)
   {
      boolean areEqual = (getDataSize() == otherData.size());

      for (int i = 0; i < getDataSize() && areEqual; i++)
      {
         if (! (getData(i).equals(otherData.get(i))))
         {
            areEqual = false;
         } 
      }

      return(areEqual);
   }

   // **********************************************************************
   // ******** attributes

   // **********************************************************************
   public String getFileName()
   {
      return(FileName);
   }

   // **********************************************************************
   public int getDataSize()
   {
      return(MyData.size());
   }

   // **********************************************************************
   public String getData(int index)
   {
      return(MyData.get(index));
   }

   // **********************************************************************
   public void addData(String aData)
   {
      MyData.add(aData);
   }

   // **********************************************************************
   public void delData(String aData)
   {
      boolean found = false;

      for (int i = 0; i < getDataSize() && ! found; i++)
      {
         if (getData(i).equals(aData))
         {
            MyData.remove(i);
            found = true;
         } 
      } 
   }

   // **********************************************************************
   // private
   // **********************************************************************
   // ******** attributes
   private String FileName;
   private ArrayList<String> MyData;
}
