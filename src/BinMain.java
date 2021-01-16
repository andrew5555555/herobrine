import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BinMain {


	
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("usage: [inter num] [z or r] [num threads]");
			return;
		}
		int num_threads = Integer.parseInt(args[2]);
		
		AbstractCheckerThread[] threads = new AbstractCheckerThread[num_threads];
		for (int i = 0; i < num_threads; i++) {
			if (args[1].equals("z")) {
				System.out.println("unimplemented");
				return;
			} else if (args[1].equals("r")){
				threads[i] = new BinCheckerThread(i, num_threads, args[0]);
			}
			else {
				System.out.println("usage: [inter num] [z or r]");
				return;
			}
			threads[i].start();
		}
		for (int i = 0; i < num_threads; i++) {
			try {
				threads[i].join(); // wait for work to finish
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("zbig_canopy_filtered_"+threads[0].my_type()+threads[0].info+"_.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < num_threads; i++) {
			List<String> lines = null;
			try {
				lines = Files.readAllLines(Paths.get(threads[i].output_path));
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (String s : lines) {
				writer.println(s);
			}
		}
		writer.close();
		
		
		
	}

}
