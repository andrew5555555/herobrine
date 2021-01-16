
public abstract class AbstractCheckerThread extends Thread {
	protected static final int NUM_BATCHES = 8390; // note: used to be 8390

	protected int start_batch;
	protected int step_size;
	public String output_path;
	public String info;
	public String interX;
	
	
	public abstract String my_type();
}
