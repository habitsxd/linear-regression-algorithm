package com.mycompany.mlprojectfordemo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;
import java.text.NumberFormat;
import java.util.Arrays;

public class MLProjectForDemo {

        public static void main(String[] args) throws Exception {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        Scanner input = new Scanner(System.in);
        
        double[][] x_train = readFromCSV("/Users/maxxfieldsmith/Desktop/Machine Learning/Project/Data/train_x_data.csv"); //read all data into array
        double[][] y_train = readFromCSV("/Users/maxxfieldsmith/Desktop/Machine Learning/Project/Data/train_y_data.csv");
        double[][] x_test = readFromCSV("/Users/maxxfieldsmith/Desktop/Machine Learning/Project/Data/test_x_data.csv");
        double[][] y_test = readFromCSV("/Users/maxxfieldsmith/Desktop/Machine Learning/Project/Data/test_y_data.csv");
        
        System.out.println("Welcome to my Linear Regression Project!");
        
        System.out.println("Training the algorithm!");
        
        LinearRegression lr = new LinearRegression();
            
        double[][] new_x = lr.createBias(x_train);
        double[] weights = lr.fit(0.01,new_x,y_train);
        
        System.out.println("The fitted weights are: " + Arrays.toString(weights) + "\n");
        
        double[] test = new double[x_test.length];
        
        for(int i = 0; i < x_test.length;i++){
            test[i] = lr.predict(x_test[i], weights);
            }
        
        double meanAbsError = mAE(y_test,weights,test);
        
        System.out.println("Now we will test the algorithms accuracy!\n");
            
        System.out.println("The Mean Absolute Error(Eout): " + meanAbsError);//output real value of MSE
            
        double percentError = mAPE(y_test,meanAbsError);
            
        System.out.println("Total out of sample error percentage: " + String.format("%.2f", percentError) + "%\n"); //output percent error of algorithm
        
        System.out.println("Would you like to predict an apartment price?: (1 for yes 0 for no)\n");
        int continuePrediction = input.nextInt();
        
        while(continuePrediction == 1){
           double[] features = new double[x_test[0].length];
           System.out.println("Number of bedrooms: ");
           features[0] = input.nextDouble();
           System.out.println("Number of bathrooms: ");
           features[1] = input.nextDouble();
           System.out.println("Den available?: ");
           features[2] = input.nextDouble();
           double predic = lr.predict(features,weights);
           System.out.println("This apartment will be " + formatter.format(predic) + "\n");
           System.out.println("Would you like to predict an apartment price?: (1 for yes 0 for no)\n");
           continuePrediction = input.nextInt();
        }
     
        

        
      
       
}//end main
   public static double[][] readFromCSV(String fileName){ //read from csv files and turn into arrays
      Scanner in = null;
      String inputLine = "";
      List<String[]> data = new ArrayList<>();
      int rowc = 0;
      
      try{
          in = new Scanner(new BufferedReader(new FileReader(fileName)));
          
          while(in.hasNextLine()){
              inputLine = in.nextLine();
              String[] InArray = inputLine.split(",");
              data.add(InArray);
          }
      }
      catch(Exception e){
          System.out.println(e);
      }
      
      String[][] placeholder = new String[data.size()][0];
      placeholder = data.toArray(placeholder);
      double[][] array = new double[placeholder.length][placeholder[0].length];
      
      for(int i = 0; i < placeholder.length;i++){
          for(int j = 0;j < placeholder[0].length;j++){
              array[i][j] = Double.parseDouble(placeholder[i][j]);
          }
      }
      return array;
          
     
   }
    public static double mAE(double[][] y_data,double[] weights,double[] predictions){ //compute meanAbsoluteError
        double sumError = 0;
        int m = y_data.length;
        for(int i = 0;i < predictions.length;i++){
            sumError += predictions[i]-y_data[i][0];  
        }
        double cost = (Math.abs(sumError)) / m;
    
        return cost;
    }
    
    public static double mAPE(double y_data[][],double meanAbsoluteError){ //calculate meanabspercentage error
        double average = 0; 
        for(int i = 0; i < y_data.length; i++){ //calculate average of y_test
            average += y_data[i][0];
            }
        
        double totalAverage = average / y_data.length;
        double percentError = (meanAbsoluteError / totalAverage) * 100;
    return percentError;  
    }
}
