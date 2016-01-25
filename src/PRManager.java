/* Name: John Nguyen
 * ID: 14419724
 * Class: CS 143B
 * Date: 1/6/2016
 */


import java.io.PrintWriter; 
import java.util.ArrayList;
import java.util.HashMap;

// Infrastructure of the Process and Resource management system.

public class PRManager {
	
	private ArrayList<PCB> processList;
	private ArrayList<ArrayList<PCB>> readyList;
	
	private PrintWriter writer;
	
	private int inputType;

	private RCB R1;
	private RCB R2;
	private RCB R3;
	private RCB R4;

	private PCB runningProcess = null;
	
	public PRManager(int inputType){
		this.inputType = inputType;
		processList = new ArrayList<PCB>();
		readyList = new ArrayList<ArrayList<PCB>>();
		for (int i = 0; i < 3; i++){
			ArrayList<PCB> priorityList = new ArrayList<PCB>();
			readyList.add(priorityList);
		}
		
		R1 = new RCB("R1");
		R2 = new RCB("R2");
		R3 = new RCB("R3");
		R4 = new RCB("R4");
		create("init", 0);
	}
	
	public PRManager(int inputType, PrintWriter writer){
		this.inputType = inputType;
		this.writer = writer;
		processList = new ArrayList<PCB>();
		readyList = new ArrayList<ArrayList<PCB>>();
		for (int i = 0; i < 3; i++){
			ArrayList<PCB> priorityList = new ArrayList<PCB>();
			readyList.add(priorityList);
		}
		
		R1 = new RCB("R1");
		R2 = new RCB("R2");
		R3 = new RCB("R3");
		R4 = new RCB("R4");
		create("init", 0);
	}
	
	
	public void create(String name, int priority){
		if (getPCB(name) != null){
			if (inputType == 1){
				System.out.println("A process with this name already exists.");
			}
			else if (inputType == 2){
				writer.println("error ");
			}
		}
		else{
			PCB pcb = new PCB(name, "ready", readyList, priority, runningProcess);
			if (pcb.getName().equals("init")){
				runningProcess = pcb;
			}
			else{
				runningProcess.addChild(pcb);
			}
			processList.add(pcb);
			readyList.get(priority).add(pcb);
			scheduler();
		}
		
	}
	
	public void destroy(String name){
		PCB pcb = getPCB(name);
		if(pcb == null){
			if (inputType == 1){
				System.out.println("The process you are trying to destroy does not exist.");
			}
			else if (inputType == 2){
				writer.print("error ");
			}
		}
		else{
			if (!name.equals("init")){
		
				pcb.getParent().getChildren().remove(pcb);
				killTree(pcb);
			}
			scheduler();
		}
	}
	
	public void killTree(PCB pcb){
		for (RCB rcb : pcb.getResources().keySet()){
			int units = pcb.getResources().get(rcb);
			if (rcb.getWL().size() == 0){
				rcb.setStatus("free");
			}
			else{
				PCB process = rcb.peakWL();
				rcb.incAvailableUnits(units);
				if (rcb.getAvailableUnits() >= rcb.getWL().get(process)){
					int allocatedUnits = rcb.removeFromWL(process);
					process.addResource(rcb, allocatedUnits);
					readyList.get(process.getPriority()).add(process);
				}
			}
		}
		for (PCB child : pcb.getChildren()){
			killTree(child);
		}
		
		R1.removeFromWL(pcb);
		R2.removeFromWL(pcb);
		R3.removeFromWL(pcb);
		R4.removeFromWL(pcb);
		pcb.removeFromRL();
		processList.remove(pcb); 
	}
	
	public void request(String resourceName, int units){
		RCB rcb = getRCB(resourceName);
		if (runningProcess.getName() == "init"){
			if (inputType == 1){
				System.out.println("Cannot allocate resources to init process");
			}
			else if(inputType == 2){
				writer.print("error ");
			}
		}
		if (runningProcess.getResources().containsKey(rcb) && rcb.getAvailableUnits() < units){
			if (inputType == 1){
				System.out.println("Resource does not have enough units.");
			}
			else if (inputType == 2){
				writer.print("error ");
			}
		}
		else{
			if (rcb.getStatus().equals("free") && rcb.getAvailableUnits() >= units){
				rcb.decrAvailableUnits(units);
				runningProcess.addResource(rcb, units);
				if (rcb.getAvailableUnits() == 0){
					rcb.setStatus("allocated");
				}
			}
			else{
				runningProcess.setType("blocked");
				if (inputType == 1){
					System.out.print(runningProcess.getName() +
							" is blocked; ");
				}
				readyList.get(runningProcess.getPriority()).remove(runningProcess);
				rcb.addToWaitingList(runningProcess, units);
			}
			scheduler();
		}
	}
	
	public void release(String resourceName, int units){
		RCB rcb = getRCB(resourceName);
		if (!runningProcess.getResources().containsKey(rcb)){
			if (inputType == 1){
				System.out.println("Process does not have specified resource allocated.");
			}
			else if (inputType == 2){
				writer.println("error ");
			}
		}
		else if (runningProcess.getResources().get(rcb) < units){
			if (inputType == 1){
				System.out.println("Process does not have enough units from specified resource allocated.");
			}
			else if (inputType == 2){
				writer.println("error ");
			}
		}
		else{
			runningProcess.removeResource(rcb, units);
			rcb.incAvailableUnits(units);
			if (rcb.getWL().size() == 0){
				rcb.setStatus("free");
			}
			else{
				PCB pcb = rcb.peakWL();
				if (rcb.getAvailableUnits() >= rcb.getWL().get(pcb)){
					int allocatedUnits = rcb.removeFromWL(pcb);
					pcb.addResource(rcb, allocatedUnits);
					readyList.get(pcb.getPriority()).add(pcb);
				}
			}
			scheduler();
		}
	}
	
	public PCB getPCB(String name){
		PCB pcb = null;
		for (int i =0 ; i < processList.size(); i++){
			if (processList.get(i).getName().equals(name)){
				pcb = processList.get(i);
				return pcb;
			}
		}
		return pcb;
	}
	
	public RCB getRCB(String name){
		RCB resource = null;
		if (name.equals("R1")){
			resource = R1;
		}
		else if (name.equals("R2")){
			resource = R2;
		}
		else if (name.equals("R3")){
			resource = R3;
		}
		else if (name.equals("R4")){
			resource = R4;
		}
		return resource;
	}
	
	
	public void timeOut(){
		readyList.get(runningProcess.getPriority()).remove(runningProcess);
		runningProcess.setType("ready");
		readyList.get(runningProcess.getPriority()).add(runningProcess);
		scheduler();
	}
	
	public void scheduler(){
		PCB highestPriority = findHighestPriority();
		if (runningProcess.getPriority() < highestPriority.getPriority() || 
				!runningProcess.getType().equals("running") ||
				runningProcess != null){
			preempt(highestPriority);
		}
	}
	
	public PCB findHighestPriority(){
		PCB highestPriority = null;
		for (int i = 0; i < readyList.size(); i++){
			if (readyList.get(i).size() > 0){
				highestPriority = readyList.get(i).get(0);
			}
		}
		return highestPriority;
	}
	
	public void preempt(PCB highestPriority){
		highestPriority.setType("running");
		runningProcess = highestPriority;
		if (inputType == 1){
			System.out.println("*Process " + highestPriority.getName() + 
				" is running");
		}
		else if (inputType == 2){
			writer.print(highestPriority.getName() + " ");
		}
	}
	
	public void printProcesses(){
		for (int i = 0 ; i < processList.size(); i++){
			printPCB(processList.get(i));
		}
	}
	
	public void printReadyList(){
		System.out.println("Currently Running Process: " + runningProcess.getName());
		System.out.println("Processes... ");
		for (int i = readyList.size()-1; i >= 0; i--){
			for (PCB pcb : readyList.get(i)){
				if(pcb.getName() != "Init"){
					printPCB(pcb);
				}
			}
		}
	}
	
	public void printPCB(PCB pcb){
		System.out.println("Name: " + pcb.getName());
		System.out.println("Type: " + pcb.getType());
		System.out.println("Priority: " + pcb.getPriority());
		System.out.print("Resources: ");
		for (RCB rcb: pcb.getResources().keySet()){
			System.out.print(rcb.getName() + ", ");
		}
		System.out.println();
		if (!pcb.getName().equals("init")){
			System.out.print("Parent: " + pcb.getParent().getName());
		}
		System.out.println();
		System.out.print("Children: ");
		for (PCB p: pcb.getChildren()){
			System.out.print(p.getName() + ", ");
		}
		System.out.println("\n");
	}
	
	public void printResources(){
		printRCB(R1);
		printRCB(R2);
		printRCB(R3);
		printRCB(R4);
	}
	
	public void printRCB(RCB rcb){
		System.out.println("Resources... ");
		System.out.println("Name: " + rcb.getName());
		System.out.println("Available Units: " + rcb.getAvailableUnits());
		System.out.println("Status: " + rcb.getStatus());
		System.out.println("WaitingList: ");
		for (PCB pcb : rcb.getWL().keySet()){
			printPCB(pcb);
		}
		System.out.println();
		
	}
}
