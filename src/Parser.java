import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Handles all I/O

public class Parser {
	
	private FileReader fileReader;
	
	private BufferedReader bufferedReader;
	
	private PRManager processResourceManager;
	
	
	public Parser(PRManager pr){
		processResourceManager = pr;
	}
	
	public void initialize(){
		int input;
		boolean inputLoop = true;
		Scanner in = new Scanner(System.in);
		while (inputLoop){
			System.out.println("Read from file [1]. Input through command line [2]");
			input = in.nextInt();
			if (input == 1){
				terminalInput();
				inputLoop = false;
			}
			else if (input == 2){
				fileInput();
				inputLoop = false;
			}
		}
	}
	
	public void terminalInput(){
		String input = "";
		Scanner in = new Scanner(System.in);
		while (input != "quit"){
			input = in.nextLine();
			String[] arr = input.split(" ");
			
			if (arr[0] == "cr" && arr.length == 3 && (arr[2] == "1" || arr[2] == "2")){
				cr(arr);
			}
			else if(arr[0] == "de" && arr.length == 2){
				de(arr);
			}
			else if(arr[0] == "req" && arr.length == 3){
				req(arr);
			}
			else if(arr[0] == "rel" && arr.length == 3){
				rel(arr);
			}
			else if(arr[0] == "to" && arr.length == 1){
				to(arr);
			}
			else{
				System.out.println("Invalid input.");
			}
		}
	}
	
	public void fileInput(){
		
	}
	
	public void cr(String[] input){
		
	}
	
	public void de(String[] input){
		
	}
	
	public void req(String[] input){
		
	}
	public void rel(String[] input){
		
	}
	
	public void to(String[] input){
		
	}
	
	
}
