import java.util.Random;

public class SimpleTest {
	public static void main(String[] args) {
		
		
		
		
		
		long s = 67608714704374l;
		System.out.println(String.format("0x%08X", s));
		s = ((1l<<48)-1) & (s * 0x5deece66dl + 11);
		
		System.out.println(String.format("0x%08X", s));

		Random q = new Random(s^0x5deece66dl);
		System.out.println(q.nextInt(10));
		
		/*
		long s = 261747245511566l;//543222222222222l;//258413796700707l;
		
		long x = 2;
		long z = -1;
		
		Random q= new Random();
		s=q.nextLong();
		x=-q.nextInt(4);
		z = -q.nextInt(4);
		s= s & ((1l << 48) -1);
		s=116866462264989l ;
		x = -3;
		z = -1;
		System.out.println("actauuly, s= " + s + "x="+x + "z="+z);
		Random r = new Random(s);
		long a = r.nextLong();
		long b = r.nextLong();
		System.out.println(a + " " + b);
		a = a/2 *2 +1;
		b = b/2 *2 + 1;
		System.out.println(a + " " + b);
		long s2 = a*x+b*z ^ s;
		System.out.println(s2& ((1l << 48) -1));
		long s3 = s2^0x5deece66dl;
		for (int i = 0; i < 3759; i++) {
			s3 = (11 + s3 * 0x5deece66dl) & ((1l << 48) -1);
		}
		System.out.println(s3);
		*/
	}
}
