
public class TreeState {
	int[][][] data = new int[32][16][32];
	
	public void setWorld(World world) {
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 16; j++) {
				for (int k = 0; k < 32; k++) {
					if (world.blocks[i][j][k] == 18 || world.blocks[i][j][k] == 17) {
						data[i][j][k] = world.blocks[i][j][k];
					}
				}
			}
		}
	}
	
	public void merge(TreeState a, TreeState b) {
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 16; j++) {
				for (int k = 0; k < 32; k++) {
					if (a.data[i][j][k] == 18 || a.data[i][j][k] == 17 || b.data[i][j][k] == 18 || b.data[i][j][k] == 17) {
						data[i][j][k] = 18;
					}
				}
			}
		}
	}
	
	public int count() {
		int c = 0;
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 16; j++) {
				for (int k = 0; k < 32; k++) {
					if (data[i][j][k] == 18 || data[i][j][k] == 17) {
						c++;
					}
				}
			}
		
				}
		return c;
	}
	
	public boolean equals(Object o) {
		TreeState a = (TreeState) o;
		int yes = 0;
		int no = 0;
		for (int i = 0; i < 32; i++) {
			for (int j = 0; j < 16; j++) {
				for (int k = 0; k < 32; k++) {
					if ((a.data[i][j][k] == 18 || a.data[i][j][k] == 17) != (data[i][j][k] == 18|| data[i][j][k] == 17)) {
						System.out.println(i + " " + j + " " + k);
						//return false;
						no++;
					}
					else if ((a.data[i][j][k] == 18 || a.data[i][j][k] == 17)) {
						yes++;
					}
				}
			}
		}
		//System.out.println(no + ", "+yes);
		return no==0;
	}
	
}
