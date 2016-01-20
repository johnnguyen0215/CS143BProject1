import java.util.ArrayList;
import java.util.Queue;

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
		
		runningProcess.scheduler();
		
	}
	
	public void destroy(String name){
		PCB pcb = getPCB(name);
		killTree(pcb);
		runningProcess.scheduler();
	}
	
	public void killTree(PCB pcb){
		for (int i = 0; i < pcb.getResources().size(); i++){
			
		}
		
	}
	
	public void request(int rid, int units){
		RCB rcb = getRCB(rid);
		if (rcb.getStatus() == "free" && rcb.getAvailableUnits() >= units){
			rcb.decrAvailableUnits(units);
			runningProcess.addResource(rcb);
			if (rcb.getAvailableUnits() == 0){
				rcb.setStatus("allocated");
			}
		}
		else{
			runningProcess.setType("blocked");
			readyList.remove(runningProcess);
			rcb.addToWaitingList(runningProcess);
			runningProcess.scheduler();
		}
	}
	
	public void release(int rid){
		RCB rcb = getRCB(rid);
		runningProcess.removeResource(rcb);
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
	
	

	
	
	
}
