import java.util.Random;

public class LetsDebug {

	public static void main(String[] args) {
		//Random r = new Random(157433031502578l);
		//System.out.println(r.nextInt(16)+8-256);
		World w = new World(13, 6, "-+");
		System.out.println("result:" + w.simulate(Long.parseLong("59658143770401") ));
		w.leaf_height_map[0][0] = 1;
		for (int i = 31; i >= 0; i--) {
			for (int j = 0; j < 32; j++) {
				String num = String.valueOf(w.leaf_height_map[i][j]);
				if (num.equals("0")) {
					num = "..";
				}
				else if (num.length() == 1) {
					num = "0" + num;
				}
				System.out.print(num + " ");
			}
			System.out.println();
		}
	}

}
