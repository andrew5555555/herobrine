import java.util.Random;

public class AnotherTest {
	static Random field_913_j = new Random();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		long ws = 280817660010508l;
		int i = -2;
		int j = -11;
		
		
		field_913_j.setSeed(ws);
        long l1 = (field_913_j.nextLong() / 2L) * 2L + 1L;
        long l2 = (field_913_j.nextLong() / 2L) * 2L + 1L;
        field_913_j.setSeed((long)i * l1 + (long)j * l2 ^ ws);
        
        for (int t = 0; t < 3759; t++) {
        	field_913_j.nextInt();
        }
        
        System.out.println(field_913_j.nextInt(10));
        
	}

}
