
package com.mycompany.mlprojectfordemo;

import java.util.Random;


public class LinearRegression {
    public double[] fit(double learningRate,double[][] x_data, double[][] y_data){
        int n = x_data.length;
        double[] weights = initializeWeights(x_data);
        double prediction;
        int epoch = 2500;
        
        while(epoch > 0){
            double sumError = 0;
            for (int i = 0; i < weights.length;i++){ //for the length of the data set
                prediction = predict(x_data[i],weights);
                double error = prediction - y_data[i][0];
                for(int j = 0;j < x_data[0].length; j++)
                   sumError += error * x_data[i][j];
            double gradient = sumError / x_data.length;
            for(int k = 0; k < weights.length;k++){
                weights[k] = weights[k] - (learningRate * gradient);
                }
            }
            epoch--;
        }
        return weights;
        
        
        
    }//end useGradientDescent
        
    public double[][] createBias(double[][] data){
        double[][] newData = new double[data.length][data[0].length + 1];
        for (int i = 0;i < data.length; i++){
            newData[i][0] = 1;
            for (int j = 1; j < (data[0].length - 1); j++){
                newData[i][j] = data[i][j];
            }
        
        }
        return newData;
    }//end createNewX
    
    public double predict(double[] x_data,double[] weights){ //make a prediction based on individual members of X
        double prediction = 0;
        for (int i = 0; i < x_data.length; i++){
            prediction += weights[i] * x_data[i];
        }
        return prediction;
    
    }//end predict
    
    private double[] initializeWeights(double[][] x_data){  //initialize weight vectors with random numbers
        Random randNum = new Random();
        randNum.setSeed(43);
        double[] arr = new double[x_data[0].length];
        
        for(int i = 0;i < x_data[0].length; i++){
            arr[i] = randNum.nextDouble();
            
        }
        return arr;
        
    }//end initializeWeights
    
}
