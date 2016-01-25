/* Name: John Nguyen
 * ID: 14419724
 * Class: CS 143B
 * Date: 1/6/2016
 */


import java.util.ArrayList; 
import java.util.HashMap;

// Encapsulates a process

public class PCB {
	private String name;
	private HashMap<RCB, Integer> resources;
	private String type;
	private ArrayList<ArrayList<PCB>> readyList;
	private int priority;
	private PCB parent;
	private ArrayList<PCB> children;
	
	public PCB(String name, String type, ArrayList<ArrayList<PCB>> readyList, int priority, PCB parent){
		this.name = name;
		this.type = type;
		this.readyList = readyList;
		this.priority = priority;
		this.parent = parent;
		resources = new HashMap<RCB, Integer>();
		children = new ArrayList<PCB>();
	}
	
	public String getName(){
		return name;
	}
	
	public String getType(){
		return type;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public PCB getParent(){
		return parent;
	}
	
	public HashMap<RCB, Integer> getResources(){
		return resources;
	}
	
	public ArrayList<PCB> getChildren(){
		return children;
	}
	
	public void addChild(PCB child){
		children.add(child);
	}
	
	public void addResource(RCB resource, int units){
		resources.put(resource, units);
	}
	
	public void removeResource(RCB resource, int units){
		int allocated = resources.get(resource);
		if (units >= allocated){
			resources.remove(resource);
		}
		else{
			resources.put(resource, allocated-1);
		}
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void removeFromRL(){
		readyList.get(priority).remove(this);
	}
	
}
