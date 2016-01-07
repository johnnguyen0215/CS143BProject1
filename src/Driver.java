/* Name: John Nguyen
 * ID: 14419724
 * Class: CS 143B
 * Date: 1/6/2016
 */

import java.io.File;
import java.util.Scanner;


public class Driver {
	public static void main(String[] args) {
		PRManager pr = new PRManager();
		Parser p = new Parser(pr);
		p.initialize();
	}

}
