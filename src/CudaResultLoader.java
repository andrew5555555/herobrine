import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CudaResultLoader {
	ArrayList<Long> seeds = new ArrayList<Long>();
	int id;
	int treeX, treeZ;
	
	public CudaResultLoader(String path) {
		
		File file = new File(path);
		
		boolean b = true;
		while (!file.exists() && file.length() == 0) {
			if (b) {
				System.out.println("waiting for " + path + "...");
				b=false;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			file = new File(path);
		}
		long lm = file.lastModified();
		while (System.currentTimeMillis()-lm < 60000) { // hacky way to avoid crashes
			file = new File(path);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		file = new File(path);
		//Scanner scanner = null;
		
		try {
			BufferedReader rb = new BufferedReader(new FileReader(file));
			id = Integer.parseInt(rb.readLine());
			treeX = Integer.parseInt(rb.readLine());
			treeZ = Integer.parseInt(rb.readLine());
			int num_seeds = Integer.parseInt(rb.readLine());
		    for (int i = 0; i < num_seeds; i++) {
		    	seeds.add(Long.parseLong(rb.readLine()));
		    }
		    rb.close();
			/*
		    scanner = new Scanner(file);
		    id = scanner.nextInt();
		    treeX = scanner.nextInt();
		    treeZ = scanner.nextInt();
		    int num_seeds = scanner.nextInt();
		    for (int i = 0; i < num_seeds; i++) {
		    	seeds.add(scanner.nextLong());
		    }
		    */
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} 
		catch (IOException e) {
		    e.printStackTrace();
		} 
		//if (scanner != null) {
		//	scanner.close();
		//}
		
		//file.delete(); // we no longer want to keep the file
	}
	
	
	
		
}
