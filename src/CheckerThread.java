import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CheckerThread extends AbstractCheckerThread {
	
	
	public CheckerThread(int i, int total, String interx) {
		start_batch = i;
		step_size = total;
		this.interX = interx;
	}
	
	
	public String my_type() {
		return "";
	}
	
	public void run() {
		PrintWriter writer =null;
		PrintWriter log = null;
		for (int batchNum = start_batch; batchNum < NUM_BATCHES; batchNum+=step_size) {
			CudaResultLoader crl = new CudaResultLoader("_inter"+interX+ "/_intermediate"+batchNum+".txt");
			if (batchNum == start_batch) {
				try {
					info = crl.id + "_" +crl.treeX + "_" + crl.treeZ;
					output_path = "canopy_results/scanopy_filter_"+start_batch + "_"+ info +"_.txt";
					writer = new PrintWriter(output_path);
					log  = new PrintWriter("logs/scanopy_filter_log_"+"_"+crl.id + "_" + start_batch + "_.txt");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
			}
			
			World w_pp = new World(crl.treeX, crl.treeZ, "++");
			World w_pm = new World(crl.treeX, crl.treeZ, "+-");
			World w_mp = new World(crl.treeX, crl.treeZ, "-+");
			World w_mm = new World(crl.treeX, crl.treeZ, "--");
			
			if (batchNum == 0) {
				w_pp.disp_table();
			}
			int found = 0;
			long t_start = System.currentTimeMillis();
			for (long seed : crl.seeds) {
				int j_pp = w_pp.simulateOneTree(seed);
				int j_pm = w_pm.simulateOneTree(seed);
				int j_mp = w_mp.simulateOneTree(seed);
				int j_mm = w_mm.simulateOneTree(seed);
				if (j_pp!=-1) {
					writer.println(seed + " " + j_pp + " ++");
					found++;
				}
				if (j_pm!=-1) {
					writer.println(seed + " " + j_pm + " +-");
					found++;
				}
				if (j_mp!=-1) {
					writer.println(seed + " " + j_mp + " -+");
					found++;
				}
				if (j_mm!=-1) {
					writer.println(seed + " " + j_mm + " --");
					found++;
				}
			}
			long t_end = System.currentTimeMillis();
			double v = (crl.seeds.size()*1000.0)/ (t_end-t_start);
			log.println("checked batch " + batchNum + ": searched " + crl.seeds.size() + "; matched: " + found + "; speed = " + v + "/s");
			log.flush();
			writer.flush();
		}
		
		
		
	}
}