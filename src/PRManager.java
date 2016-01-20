import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.html.HTMLDocument.Iterator;

// Infrastructure of the Process and Resource management system.

public class PRManager {
	
	private ArrayList<ArrayList<PCB>> readyList;

	RCB R1;
	RCB R2;
	RCB R3;
	RCB R4;

	private PCB runningProcess = null;
	
	public PRManager(){
		readyList = new ArrayList<ArrayList<PCB>>();
		for (int i = 0; i < 3; i++){
			ArrayList<PCB> priorityList = new ArrayList<PCB>();
			readyList.add(priorityList);
		}
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
		HashMap<RCB, Integer> resources = pcb.getResources();
		for (RCB rcb : resources.keySet()){
			rcb.getWL().remove(pcb);
		}
		
		for (int i = 0; i < pcb.getChildren().size(); i++){
			readyList.remove(pcb.getChildren().get(i));
		}
		
		
		
		
	}
	
	public void request(int rid, int units){
		RCB rcb = getRCB(rid);
		if (rcb.getStatus() == "free" && rcb.getAvailableUnits() >= units){
			rcb.decrAvailableUnits(units);
			runningProcess.addResource(rcb, units);
			if (rcb.getAvailableUnits() == 0){
				rcb.setStatus("allocated");
			}
		}
		else{
			runningProcess.setType("blocked");
			System.out.print(runningProcess.getName() +
					" is blocked;");
			readyList.remove(runningProcess);
			rcb.addToWaitingList(runningProcess, units);
			scheduler();
		}
	}
	
	public void release(int rid){
		RCB rcb = getRCB(rid);
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
				if (readyList.get(i).get(j).getName() == name){
					pcb = readyList.get(i).get(j);
					return pcb;
				}
			}
		}
		return pcb;
	}
	
	public RCB getRCB(int rid){
		RCB resource = null;
		if (rid == 1){
			resource = R1;
		}
		else if (rid == 2){
			resource = R2;
		}
		else if (rid == 3){
			resource = R3;
		}
		else if (rid == 4){
			resource = R4;
		}
		return resource;
	}
	
	
	public void timeOut(){
		
	}
	
	public void scheduler(){
		PCB highestPriority = findHighestPriority();

		if (runningProcess.getPriority() < highestPriority.getPriority() || 
				runningProcess.getType() != "running" ||
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
		System.out.println("*Process " + highestPriority.getName() + 
				" is running");
	}
}
