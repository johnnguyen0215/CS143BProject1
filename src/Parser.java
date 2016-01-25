/* Name: John Nguyen
 * ID: 14419724
 * Class: CS 143B
 * Date: 1/6/2016
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Parser {
	
	private PRManager prManager; // Process and Resource Manager
	private int inputType;
	
	public Parser(){
	}
	
	public void start(){
		boolean running = true;
		while (running){
			Scanner in = new Scanner(System.in);
			int input;
			System.out.println("Input through console [1]. Read from file [2].");
			System.out.print(">> ");
			try{
				input = in.nextInt();
				if (input == 1){
					consoleInput();
					running = false;
				}
				else if (input == 2){
					fileInput();
					running = false;
				}
				else{
					System.out.println("Please input 1 or 2.\n");
				}
			}
			catch (Exception e){
				System.out.println("Please input a digit.\n");
				continue;
			}
		}
	}
	
	
	public void consoleInput(){
		inputType = 1;
		prManager = new PRManager(inputType);
		String input = "";
		boolean running = true;
		while (running){
			Scanner in = new Scanner(System.in);
			try{
				System.out.print("shell> ");
				input = in.nextLine();
				String[] arr = input.split(" ");
				if (arr[0].equals("init")){
					init();
				}
				else if (arr[0].equals("cr") && arr.length == 3 && (arr[2].equals("0") || arr[2].equals("1") || arr[2].equals("2"))){
					cr(arr[1], arr[2]);
				}
				else if(arr[0].equals("de") && arr.length == 2){
					de(arr[1]);
				}
				else if(arr[0].equals("req") && arr.length == 3){
					if (arr[1].equals("R1") && arr[2].equals("1")){
						req(arr[1], arr[2]);
					}
					else if (arr[1].equals("R2") && (arr[2].equals("1") || arr[2].equals("2"))){
						req(arr[1], arr[2]);
					}
					else if (arr[1].equals("R3") && (arr[2].equals("1") || arr[2].equals("2") || arr[2].equals("3"))){
						req(arr[1], arr[2]);
					}
					else if (arr[1].equals("R4") && (arr[2].equals("1") || arr[2].equals("2") || arr[2].equals("3") || arr[2].equals("4"))){
						req(arr[1], arr[2]);
					}
					else{
						System.out.println("Invalid request input.");
					}
				}
				else if(arr[0].equals("rel") && arr.length == 3){
					if (arr[1].equals("R1") && arr[2].equals("1")){
						rel(arr[1], arr[2]);
					}
					else if (arr[1].equals("R2") && (arr[2].equals("1") || arr[2].equals("2"))){
						rel(arr[1], arr[2]);
					}
					else if (arr[1].equals("R3") && (arr[2].equals("1") || arr[2].equals("2") || arr[3].equals("3"))){
						rel(arr[1], arr[2]);
					}
					else if (arr[1].equals("R4") && (arr[2].equals("1") || arr[2].equals("2") || arr[2].equals("3") || arr[2].equals("4"))){
						rel(arr[1], arr[2]);
					}
					else{
						System.out.println("Invalid release input.");
					}
				}
				else if(arr[0].equals("to") && arr.length == 1){
					to();
				}
				else if (arr[0].equals("pp") && arr.length == 1){
					pp();
				}
				else if (arr[0].equals("pRL") && arr.length == 1){
					pRL();
				}
				else if (arr[0].equals("pWL") && arr.length == 1){
					pWL();
				}
				else if (input.equals("quit")){
					running = false;
				}
				else{
					System.out.println("Invalid input.");
					input = "";
				}
			}
			catch (Exception e){
				
				System.out.println("Invalid input");
				continue;
			}
		}
	}
	
	public void fileInput(){
		
		try{
			inputType = 2;
			String inFile;
			Scanner in = new Scanner(System.in);
			System.out.print("Input the name of the file: " );
			inFile = in.nextLine();
			String input;
			FileReader fr = new FileReader(inFile);
			BufferedReader br = new BufferedReader(fr) ;
			String outFile = "14419724.txt";
			PrintWriter writer = new PrintWriter(outFile);
			prManager = new PRManager(inputType, writer);
			
			while((input = br.readLine()) != null){
				String[] arr = input.split(" ");
				if (input.equals("")){
					writer.println();
				}
				else if(input.equals("...")){
					writer.println("... ");
				}
				else if (arr[0].equals("init") && arr.length == 1){
					prManager = new PRManager(inputType, writer);
				}
				else if (arr[0].equals("cr") && arr.length == 3 && (arr[2].equals("0") || arr[2].equals("1") || arr[2].equals("2"))){
					cr(arr[1], arr[2]);
				}
				else if(arr[0].equals("de") && arr.length == 2){
					de(arr[1]);
				}
				else if(arr[0].equals("req") && arr.length == 3){
					if (arr[1].equals("R1") && arr[2].equals("1")){
						req(arr[1], arr[2]);
					}
					else if (arr[1].equals("R2") && (arr[2].equals("1") || arr[2].equals("2"))){
						req(arr[1], arr[2]);
					}
					else if (arr[1].equals("R3") && (arr[2].equals("1") || arr[2].equals("2") || arr[2].equals("3"))){
						req(arr[1], arr[2]);
					}
					else if (arr[1].equals("R4") && (arr[2].equals("1") || arr[2].equals("2") || arr[2].equals("3") || arr[2].equals("4"))){
						req(arr[1], arr[2]);
					}
					else{
						writer.print("error ");
					}
				}
				else if(arr[0].equals("rel") && arr.length == 3){
					if (arr[1].equals("R1") && arr[2].equals("1")){
						rel(arr[1], arr[2]);
					}
					else if (arr[1].equals("R2") && (arr[2].equals("1") || arr[2].equals("2"))){
						rel(arr[1], arr[2]);
					}
					else if (arr[1].equals("R3") && (arr[2].equals("1") || arr[2].equals("2") || arr[3].equals("3"))){
						rel(arr[1], arr[2]);
					}
					else if (arr[1].equals("R4") && (arr[2].equals("1") || arr[2].equals("2") || arr[2].equals("3") || arr[2].equals("4"))){
						rel(arr[1], arr[2]);
					}
					else{
						writer.print("error ");
					}
				}
				else if(arr[0].equals("to") && arr.length == 1){
					to();
				}
				else{
					writer.print("error ");
				}
			}
			writer.close();
			System.out.println("Wrote to file: " + outFile);
		}
		catch (Exception e){
			System.out.println("File could not be found.");
		}
			
	}
	
	public void init(){
		if (inputType == 1){
			consoleInput();
		}
		else if (inputType == 2){
			fileInput();
		}
	}
	
	public void cr(String name, String priority){
		int p = Integer.parseInt(priority);
		prManager.create(name, p);
	}
	
	public void de(String name){
		prManager.destroy(name);
	}
	
	public void req(String resourceName, String units){
		int u = Integer.parseInt(units);
		prManager.request(resourceName, u);
	}
	public void rel(String resourceName, String units){
		int u = Integer.parseInt(units);
		prManager.release(resourceName, u);
	}
	
	public void to(){
		prManager.timeOut();
	}
	
	public void pp(){
		prManager.printProcesses();
		
	}
	
	public void pRL(){
		prManager.printReadyList();
	}
	
	public void pWL(){
		prManager.printResources();
	}
	
	
}
