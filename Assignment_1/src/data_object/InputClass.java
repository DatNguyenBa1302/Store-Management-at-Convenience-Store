/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data_object;

import java.time.YearMonth;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class InputClass {
    Scanner sc = new Scanner(System.in);
    public int inputChoice(String msg, int min , int max){
        int choice= 0; 
        while (true) { 
            try {
                System.out.printf(msg+" : ");
                choice = Integer.parseInt(sc.nextLine());
                if(choice>min && choice < max)return choice;
                else System.err.println("Input choice from "+ (min+1) +" to "+(max-1));
            } catch (Exception e) {
                System.err.println("Number only! ");
            }   
        }
    }
    
    public String inputString(String msg){
        String data="";
        while (true) {            
            try {
                System.out.printf(msg+" : ");
                data = sc.nextLine();
                if(data.isEmpty()) throw new Exception();
                else return data;
            } catch (Exception e) {
                System.err.println("Input must be not Blank!");
            }
        }
    }
    
    public int inputInt(String msg){
        int data = 0;
        while(true){
        try {
                System.out.printf(msg+" : ");
                data = Integer.parseInt(sc.nextLine());
                if(data>=0) return data;
                else System.err.println("Input positive number!");
            } catch (Exception e) {
                System.err.println("Input number only! ");
            }
        }
    }
    
    public String inputDate(String msg){
        String data = "";
        while (true) {            
            try {
                data = inputString(msg);
                String[] str = data.split("/");
                if(str.length!=3) throw new Exception();
                else if(str[0].length()!=2||str[1].length()!=2||str[2].length()!=4) throw new Exception();
                else {
                    int day = Integer.parseInt(str[0]);
                    int month = Integer.parseInt(str[1]);
                    int year = Integer.parseInt(str[2]);
                    int maxDayInMonth = YearMonth.of(year,month).lengthOfMonth();
                    if(day<=maxDayInMonth) return data;
                }
            } catch (Exception e) {
                System.err.println("Wrong format (DD/MM/YYYY)!");
            }
        }
    }
    
}
