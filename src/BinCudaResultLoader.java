import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BinCudaResultLoader {
	
	
	
	ArrayList<Long> seeds = new ArrayList<Long>();
	int id;
	int treeX, treeZ;
	
	private int readIntBack(DataInputStream din, int[] a) throws IOException {
		a[0] = din.read();
		a[1] = din.read();
		a[2] = din.read();
		a[3] = din.read();
		return a[0] + (a[1] << 8) + (a[2] << 16) + (a[3] << 24);
	}
	
	public BinCudaResultLoader(String path) {
		
		File file = new File(path);
		
		boolean b = true;
		
		if (!file.exists()) {
			return;
		}
		long[] c = new long[6];
		int[] a  = new int[4];
		try {
			DataInputStream din = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
			try {
				id = readIntBack(din, a);
			
				treeX  = readIntBack(din, a);
			    treeZ  = readIntBack(din, a);
			    int num_seeds  = readIntBack(din, a);
			    int ignore = readIntBack(din, a); // num_seeds was a long int, but these bits were always zero
			    if (ignore != 0) {
			    	System.out.println(path + ":" + "ignore not zero");
			    }
			    //System.out.println(id + " " + treeX + " " + treeZ + " " + num_seeds);
			    //System.out.println(String.format("0x%08X", treeX) + " " + String.format("0x%08X", treeZ));
			    for (int i = 0; i < num_seeds; i++) {
			    	c[0] = din.read();
			    	c[1] = din.read();
			    	c[2] = din.read();
			    	c[3] = din.read();
			    	c[4] = din.read();
			    	c[5] = din.read();
			    	if (c[5]==-1) {
			    		System.out.println("oh no " + i + " " + path);
			    	}
			    	seeds.add((c[0] << 40) + (c[1] << 32) + (c[2] << 24) + (c[3] << 16) + (c[4] << 8) + (c[5]));
			    }
			    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				din.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		
		//file.delete(); // we no longer want to keep the file
	}
}
