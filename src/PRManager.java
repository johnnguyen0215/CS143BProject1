import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.html.HTMLDocument.Iterator;

// Infrastructure of the Process and Resource management system.

public class PRManager {
	
	private ArrayList<ArrayList<PCB>> readyList;

	private RCB R1;
	private RCB R2;
	private RCB R3;
	private RCB R4;

	private PCB runningProcess = null;
	
	public PRManager(){
		readyList = new ArrayList<ArrayList<PCB>>();
		for (int i = 0; i < 3; i++){
			ArrayList<PCB> priorityList = new ArrayList<PCB>();
			readyList.add(priorityList);
		}
		
		R1 = new RCB("R1");
		R2 = new RCB("R2");
		R3 = new RCB("R3");
		R4 = new RCB("R4");
		create("Init", 0);
	}
	
	public void create(String name, int priority){
		PCB process = new PCB(name, "ready", readyList, priority, runningProcess);
		if (process.getName() != "Init"){
			runningProcess.addChild(process);
		}
		else{
			runningProcess = process;
		}
		readyList.get(priority).add(process);
		
		scheduler();
		
	}
	
	public void destroy(String name){
		PCB pcb = getPCB(name);
		killTree(pcb);
		scheduler();
	}
	
	public void killTree(PCB pcb){
		if (pcb != null){
			HashMap<RCB, Integer> resources = pcb.getResources();
			for (RCB rcb : resources.keySet()){
				rcb.removeProcess(pcb);
			}
			
			for (int i = 0; i < pcb.getChildren().size(); i++){
				killTree(pcb.getChildren().get(i));
			}
			
			pcb.removeFromRL();
		}
	}
	
	public void request(String resourceName, int units){
		RCB rcb = getRCB(resourceName);
		if (rcb.getStatus().equals("free") && rcb.getAvailableUnits() >= units){
			rcb.decrAvailableUnits(units);
			runningProcess.addResource(rcb, units);
			if (rcb.getAvailableUnits() == 0){
				rcb.setStatus("allocated");
			}
		}
		else{
			runningProcess.setType("blocked");
			System.out.print(runningProcess.getName() +
					" is blocked; ");
			readyList.get(runningProcess.getPriority()).remove(runningProcess);
			rcb.addToWaitingList(runningProcess, units);
		}
		scheduler();
	}
	
	public void release(String resourceName, int units){
		RCB rcb = getRCB(resourceName);
		
		runningProcess.removeResource(rcb);
		if (rcb.getWL().size() == 0 && rcb.getAvailableUnits() > 0){
			rcb.setStatus("free");
		}
		else{
			
			PCB pcb = rcb.peakWL();
			
			if (rcb.getAvailableUnits() > pcb.getResources().get(rcb)){
				rcb.popFromWL();
				pcb.setType("ready");
				pcb.addResource(rcb, pcb.getResources().get(rcb));
				readyList.get(pcb.getPriority()).add(pcb);
				scheduler();
			}
			
		}
	}
	
	public PCB getPCB(String name){
		PCB pcb = null;
		for (int i = 0; i < readyList.size(); i++){
			for (int j = 0; j < readyList.get(i).size(); j++){
				if (readyList.get(i).get(j).getName().equals(name)){
					pcb = readyList.get(i).get(j);
					return pcb;
				}
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
		System.out.println("*Process " + highestPriority.getName() + 
				" is running");
	}
	
	public void printProcesses(){
		System.out.println("Processes... ");
		for (ArrayList<PCB> arr : readyList){
			for (PCB pcb : arr){
				System.out.println("Name: " + pcb.getName());
				System.out.println("Type: " + pcb.getType());
				System.out.println("Priority: " + pcb.getPriority());
				System.out.print("Resources: ");
				for (RCB rcb: pcb.getResources().keySet()){
					System.out.println(rcb.getName() + ", ");
				}
				if (pcb.getName() != "Init"){
					System.out.println();
					System.out.println("Parent: " + pcb.getParent().getName());
				}
				System.out.print("Children: ");
				for (PCB p: pcb.getChildren()){
					System.out.print(p.getName() + ", ");
				}
				System.out.println("\n");
			}
		}
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
			System.out.println("Process Name: " + pcb.getName());
		}
		System.out.println();
		
		
	}
}
