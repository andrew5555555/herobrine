import java.util.Arrays;
import java.util.Random;
public class World {
	private static final int SMALL_TREE_SPACING = 2;
	// query block
	
	 int x_1;
	 int z_1;
	 int x_2;
	 int z_2;
	
	int[][][] blocks = new int[32][16][32];
	int[][] leaf_height_map = new int[32][32];
	
	int xOffset = 0; // to simulate generation at -ve coordinates
	int zOffset = 0; // set to something like -32
	
	public World(int tree1x, int tree1z, String quadrant) {
		x_1 = tree1x;
		z_1 = tree1z;
		x_2 = x_1 - 7;
		z_2 = z_1 + 1;
		switch(quadrant.charAt(0)) {
			case '+': break;
			case '0': xOffset = -16; break;
			case '-' : xOffset = -32; break;
			default : throw new Error("bad quadrant: " + quadrant);
		}
		
		switch(quadrant.charAt(1)) {
			case '+': break;
			case '0': zOffset = -16; break;
			case '-' : zOffset = -32; break;
			default : throw new Error("bad quadrant: " + quadrant);
		}
		init_table();
		//disp_table();
		
	}
	
	 int[][] table = new int[16][16];
	
	// coordinates are relative to tree_1 position
	 void add_unseen(int sz, int ez, int x) {
		sz -= 2; // fudge factor for safety
		int i = x + x_1;
		for (int j = sz + z_1; j < ez + z_1; j++) {
			if ((i & 15) == i && (j & 15) == j)
			{
				table[i][j] = 9;
			}
		}
	}

	private static int min(int i, int j) {
		return Math.min(i, j);
	}


	private static int max(int i, int j) {
		return Math.max(i, j);
	}


	public void init_table() {
		for (int i = 0; i < 16; ++i)
		{
			for (int j = 0; j < 16; ++j)
			{
				table[i][j] = 0;
			}
		}

		// add locations where it's unclear if there is a tree or not


		/*
		add_unseen(4, 14, 6);
		add_unseen(7, 14, 5);
		add_unseen(8, 14, 4);
		add_unseen(9, 14, 3);
		add_unseen(10, 14, 2);
		add_unseen(11, 14, 1);
		add_unseen(12, 14, 0);
		add_unseen(12, 14, -1);
		add_unseen(12, 14, -2);
		add_unseen(13, 14, -3);
		add_unseen(13, 14, -4);
		add_unseen(13, 14, -5);
		add_unseen(14, 14, -6);
		add_unseen(14, 14, -7);
		add_unseen(14, 14, -8);
		add_unseen(14, 14, -9);
		add_unseen(14, 14, -10);
		add_unseen(14, 14, -11);
		add_unseen(14, 14, -12);
		add_unseen(14, 14, -13);
		*/
		
		int us_arr[] = { 4, 6, 7, 8,    7, 7, 5, 5,     5, 6, 6, 6,     7, 7, 7, 13,    13, 1, 1, 1 };
		for (int dx = 6, i = 0; i < 20; dx--, i++) {
			int x = x_1 + dx;
			int z0 = z_1 + us_arr[i];
			for (int z = z0; z < 16;  z++) {
				if ((x & 15) == x && (z & 15) == z) {
					table[x][z] = 9;
				}
			}
		}
		
		
		// near a small tree 3
		int x_3 = x_1 - 12;
		int z_3 = z_1 - 7;
		for (int i = max(0, x_3 - SMALL_TREE_SPACING); i <= min(15, x_3 + SMALL_TREE_SPACING); ++i)
		{
			for (int j = max(0, z_3 - SMALL_TREE_SPACING); j <= min(15, z_3 + SMALL_TREE_SPACING); ++j)
			{
				table[i][j] = 8;
			}
		}
		// near a small tree 4
		int x_4 = x_1 - 10;
		int z_4 = z_1 - 12;
		for (int i = max(0, x_4 - SMALL_TREE_SPACING); i <= min(15, x_4 + SMALL_TREE_SPACING); ++i)
		{
			for (int j = max(0, z_4 - SMALL_TREE_SPACING); j <= min(15, z_4 + SMALL_TREE_SPACING); ++j)
			{
				table[i][j] = 8;
			}
		}
		// near a small tree 5
		int x_5 = x_1 - 5;
		int z_5 = z_1 - 15;
		for (int i = max(0, x_5 - SMALL_TREE_SPACING); i <= min(15, x_5 + SMALL_TREE_SPACING); ++i)
		{
			for (int j = max(0, z_5 - SMALL_TREE_SPACING); j <= min(15, z_5 + SMALL_TREE_SPACING); ++j)
			{
				table[i][j] = 8;
			}
		}
	}
	
	void disp_table() {
		String s= "";
		for (int i = 15; i >= 0; --i)
		{
			for (int j = 0; j < 16; ++j)
			{
				s+=(table[i][j]);
			}
			s+=("\n");
		}
		s+=("\n");
		System.out.println(s);
	}
	
	public int func_600_a(int i, int j, int k) {
		if (j==-1) {
			return 2; // hacky way
		}
		if (j > 15 || j < 0) {
			System.out.println("attempeted access j " + j);
			return -1;
		}
		return blocks[i-xOffset][j][k-zOffset];
	}
	// set block
	// return true if set successful (has no effect on generation)
	public boolean func_634_a(int i, int j, int k, int l) {
		if (j > 15 || j < 0) {
			System.out.println("attempeted access j " + j);
			return false;
		}
		int before = blocks[i-xOffset][j][k-zOffset];
		blocks[i-xOffset][j][k-zOffset] = l;
		if (l == 18) {
			leaf_height_map[i-xOffset][k-zOffset] = Math.max(leaf_height_map[i-xOffset][k-zOffset], j);
		}
		return before != l;
	}
	
	public void reset() {
		
		for (int i = 0; i < 32; i++) {
			for (int k = 0; k < 32; k++) {
				leaf_height_map[i][k] = 0;
				for (int j = 0; j < 16; j++) {
					if (j!= 0 || true) { // I don't know what to do what if some leaves are from another tree?
					blocks[i][j][k] = 0;
					}
					else {
						blocks[i][j][k] = 2;
					}
				}
			}
		}
		blocks[x_1+8][0][z_1+8] = 2;
		blocks[x_2+8][1][z_2+8] = 2;
	}
	// note: these are all -1 off in the y
	static final int[][] leaves =
		{
			{ 4, 8, -2 },
			{ 4, 8, -1 },
			{ 4, 8, 0 }, // (?)
			
			//{ 4, 8, 1 },// hmmmmm
			{ 4, 8, 3 }, //
			//{ 5, 7, 2 }, // maybe part of another tree?
			{ 2, 9, 3 }, //
			{ 1, 10, 0 },
			{ 0, 10, -1 },
			{ 2, 9, -2 },
			{ 1, 9, -2 },
			{ 0, 9, -2 },
			{ -1, 9, -2 },
			{ 3, 9, -1 },
			{ 3, 8, -3 },
			{ 2, 8, -3 },
			{ 1, 8, -3 },
			{ 2, 9, 1 },
			{ 2, 9, 0 },
			{ -8, 9, -2 },
			{ -7, 9, -2 },
			//{ -8, 10, -1 },
			{ -7, 10, -1 },
			{ -6, 10, -1 },
			{ -7, 11, 0 },
			{ -3, 7, -2 } // is this leaf really visible? I guess it is
		};
	public boolean checkLeaves() {
		for (int[] coord : leaves) {
			if (leaf_height_map[x_1+coord[0]+8][z_1+coord[2]+8] != (coord[1]+1)) {
				return false;
			}
		}
		return true;
	}
	
	
	static final int[][] leavesSingle =
		{
			{ 4, 8, -2 },
			{ 4, 8, -1 },
			{ 4, 8, 0 },
			//{ 4, 8, 1 },//
			//{ 4, 8, 3 }, //
			//{ 5, 7, 2 }, // maybe part of another tree?
			//{ 2, 9, 3 }, //
			{ 1, 10, 0 },
			{ 0, 10, -1 },
			{ 2, 9, -2 },
			{ 1, 9, -2 },
			{ 0, 9, -2 },
			{ -1, 9, -2 },
			{ 3, 9, -1 },
			{ 3, 8, -3 },
			{ 2, 8, -3 },
			{ 1, 8, -3 },
			{ 2, 9, 1 },
			{ 2, 9, 0 },
			
		};
	public boolean checkLeavesSingle() {
		for (int[] coord : leavesSingle) {
			if (leaf_height_map[x_1+coord[0]+8][z_1+coord[2]+8] != (coord[1]+1)) {
				return false;
			}
		}
		return true;
	}
	
	
	static final int[][] logs = {
			{0, 5, 1},
			{1, 5, 2},
			{1, 6, 0},
			{2, 6, -1}
			
	};
	public boolean checkLogs() {
		/*
		for (int[] coord : logs) {
			if (blocks[x_1+coord[0]+8][coord[1]][z_1+coord[2]+8] != 17) {
				return false;
			}
		}*/
		return true;
	}
	
	// do extra checks
	public int simulateX(long tree_seed) {
		int r = simulate(tree_seed);
		if (r==-1) {
			return -1;
		}
		boolean extra = leaf_height_map[x_1+4+8][z_1+2+8] <= 8 && leaf_height_map[x_1+2+8][z_1+2+8] <= 9 && leaf_height_map[x_1+2+8][z_1+3+8] == 10 && leaf_height_map[x_1+4+8][z_1+1+8] == 9;
		boolean extra3 = true;//leaf_height_map[x_1+8-4][z_1+8-2] == 8 && leaf_height_map[x_1+8-5][z_1+8-2] == 8; // dodgy?
		boolean extra2 = leaf_height_map[x_1+8+4][z_1+3+8]== 9;//leaf_height_map[x_1+5+8][z_1+0+8] == 0 && leaf_height_map[x_1+5+8][z_1+1+8] == 0;
		//extra = extra && leaf_height_map[x_1+5+8][z_1+1+8] < 8;
		//extra = extra && (blocks[x_1+5+8][5][z_1+1+8] == 0);
		boolean extra7 = leaf_height_map[x_1+6+8][z_1+0+8] == 0 && blocks[x_1+8][4][z_1+8-1] == 0;
		boolean extra4 = blocks[x_1+8][4][z_1+8+1] == 18 && blocks[x_1+8][5][z_1+8-1] == 18;// && leaf_height_map[x_1+5+8][z_1+0+8] == 0;
		boolean extra5 = true;//leaf_height_map[x_1+6+8][z_1+0+8] == 0 && leaf_height_map[x_1+6+8][z_1+1+8] == 0;
		boolean extra6 = leaf_height_map[x_1+2+8][z_1+4+8] <= 9;
		boolean cavity = blocks[x_1+4+8][8][z_1+1+8] == 0;
		/*
		if (extra&&checkLogs()&&extra2&& extra3&&extra4&&extra5&&extra6&&extra7&&cavity) {
			return r;
		}
		*/
		int missingPresent = checkAbsent();
		/*
		if (cavity && (leaf_height_map[x_1+4+8][z_1+1+8] == 9)) {
			return r;
		}
		*/
		//if (missingPresent <= 6 && upperLimitCheck() && otherLeaves() && trunkLeaves()) {
		//if (trunkLeaves() && leaf_height_map[x_1+5+8][z_1+0+8] == 0) {	
		if (trunkLeaves() && leaf_height_map[x_1+8-4][z_1+8-2] == 8 && leaf_height_map[x_1+8-5][z_1+8-2] == 8 && leaf_height_map[x_1+8-3][z_1+8-2] == 8 && leaf_height_map[x_1+8-6][z_1+8-2] > 8) {
			
			return r;
		}
		return -1;
	}
	
	private int checkAbsent() {
		int errors = 0;
		int [][] data = {
				{-9, -3},
				{-8, -3},
				{-7, -3},
				{-6, -3},
				{-5, -3},
				{-4, -3},
				{-3, -3},
				{-2, -3},
				{-1, -3},
				{0, -3},
				{-2, -2}, // ? most likely
				{0, -4},
				{1, -4},
				{2, -4},
				{3, -4},
				{4, -4},
				{4, -3},
				{5, -3},
				{5, -2},
				{5, -1},
				{5, 0}, //
				{5, 1}, // two cursed leaf decays?
				{6, 1},
				{6, 2},
				//{6, 3}, // ? nah
				//{5, 3}, // ? nah
				{-9, -2} // ?
		};
		for (int[] c : data) {
			if (leaf_height_map[c[0]+8+x_1][c[1]+8+z_1] != 0) {
				errors+=1;
			}
		}
		
		
		return errors;
		
	}
	
	public boolean upperLimitCheck() {
		int[][] upperLimits = {
				{1, 9, -1},
				{2, 9, -1},
				{3, 8, -2}, 
				{3, 8, 0},
				{3, 8, 1}, 
				{3, 8, 2},
				{3, 8, 3},
				{2, 8, 2},
				{4, 7, 2},
				//{4, 7, 4}, // ? pretty sure
				{2, 8, 4}, // ?
				////////{ -3, 7, -2 } // is this leaf really visible? I guess it is
		};
		for (int[] c : upperLimits) {
			if (leaf_height_map[c[0]+x_1+8][c[2]+z_1+8] > c[1] + 1) {
				return false;
			}
		}
		return true;
	}
	
	public boolean otherLeaves() {
		int[][] lvs = {
				{4, 8, 1},
				{4, 8, 3}, // only vaguely possible leafs from third tree
				//{5, 7, 2}, // only vaguely possible leafs from third tree
				{2, 9, 3},
				//{-4, 7, -2}, // ? 
				//{-5, 7, -2}, // ?
		};
		for (int[] c : lvs) {
			if (leaf_height_map[c[0]+x_1+8][c[2]+z_1+8] != c[1] + 1) {
				return false;
			}
		}
		if (leaf_height_map[x_1+8-6][z_1+8-2] == 0) {
			return false;
		}
		return true;
	}
	
	private boolean trunkLeaves() {
		return blocks[x_1+8+1][4][z_1+8+0] == 0 &&
				blocks[x_1+8-1][4][z_1+8+0] == 0 &&
				blocks[x_1+8+0][4][z_1+8+1] == 18 &&
				blocks[x_1+8+0][4][z_1+8-1] == 0 &&
				// canopy present
				blocks[0+8+x_1][6][-2+8+z_1] == 18 &&
				blocks[-1+8+x_1][6][-2+8+z_1] == 18
				;
	}
	
	// return -1 is fail ; else number of tree attempts
	// worry: trees in unseen area changing stuff
	public int simulate(long tree_seed) {
		reset();
		Random rand = new Random(tree_seed ^ 0x5deece66dl);
		int a = rand.nextInt(10);
		if (a != 0) {
			System.out.println("not big tree chunk "+tree_seed);
			return -1;
		}
		GenBigTree gen = new GenBigTree();
		boolean got_1 = false;
		boolean got_2 = false;
		int iter = 0;
		for (; iter < 12 && (!got_1 || !got_2); iter++) { // may fail if third tree involved
			int treeX = rand.nextInt(16);
			int treeZ = rand.nextInt(16);
			int treeY = 0;
			if (treeX == x_1 && treeZ == z_1 && !got_1) {
				treeY = 1;
				got_1 = true;
			}
			else if (treeX == x_2 && treeZ == z_2 && !got_2) {
				treeY = 2;
				got_2 = true;
			}
			else if (table[treeX][treeZ] == 0 && leaf_height_map[treeX+8][treeZ+8] == 0) {
				//return -1; // tried to spawn a tree where there isn't one // can uncomment probs.
			}
			if (leaf_height_map[treeX+8][treeZ+8] != 0) {
				treeY = 3; // prevent tree from spawning there // trees don't spawn elsewhere anyway
			}
			gen.func_517_a(1.0D, 1.0D, 1.0D);
			gen.func_516_a(this, rand, treeX + 8 + xOffset, treeY, treeZ + 8 + zOffset);
			if (iter==0 && gen.field_878_e != 11) {
				return -1;
			}
		}
		
		if ( checkLeaves() && trunkLeaves() && upperLimitCheck()) {
			if (gen.field_878_e == 12) {
				System.out.println("!!!!!!!!!!!!! feildd 12");
			}
			return iter;
		} else {
			return -1;
		}
		
	}
	
	public int simulateOneTree(long tree_seed) {
		reset();
		Random rand = new Random(tree_seed ^ 0x5deece66dl);
		int a = rand.nextInt(10);
		if (a != 0) {
			System.out.println("not big tree chunk "+tree_seed);
			return -1;
		}
		GenBigTree gen = new GenBigTree();
		int iter = 0;
		boolean got_1 = false;
		for (; iter < 12 && !got_1; iter++) {
			int treeX = rand.nextInt(16);
			int treeZ = rand.nextInt(16);
			int treeY = 1;
			if (treeX == x_1 && treeZ == z_1) {
				got_1 = true;
			}
			if (treeX == x_2 && treeZ == z_2) {
				treeY = 2;
			}
			gen.func_517_a(1.0D, 1.0D, 1.0D);
			gen.func_516_a(this, rand, treeX + 8 + xOffset, treeY, treeZ + 8 + zOffset);
		}
		if (!got_1) {
			System.out.println("not got 1 "+tree_seed);
			return -1;
		}
		
		if ( checkLeavesSingle() && trunkLeaves() && upperLimitCheck()) {
			int mp = checkAbsent();
			if (mp > 4) {
				return -1;
			}
			return iter;
		} else {
			return -1;
		}
	}

	public int tempTest(long tree_seed)
	{	
		TreeState a = new TreeState();
		TreeState b = new TreeState();
		TreeState c = new TreeState();
		
		
		
		boolean got_1 = false;
		boolean got_2 = false;
		reset();
		Random rand = new Random(tree_seed ^ 0x5deece66dl);
		GenBigTree gen = new GenBigTree();
		
		rand.nextInt(10);
		int treeX = rand.nextInt(16);
		int treeZ = rand.nextInt(16);
		int treeY = 1;
		if (treeX == x_1 && treeZ == z_1) {
			got_1 = true;
		}
		if (treeX == x_2 && treeZ == z_2) {
			treeY = 2;
			got_2=true;
		}
		gen.func_517_a(1.0D, 1.0D, 1.0D);
		gen.func_516_a(this, rand, treeX + 8 + xOffset, treeY, treeZ + 8 + zOffset);
		if (gen.field_878_e != 11) {
			return 0;
		}
		a.setWorld(this);
		//System.out.println(a.count());
		reset();
		
		treeX = rand.nextInt(16);
		treeZ = rand.nextInt(16);
		treeY = 1;
		if (treeX == x_1 && treeZ == z_1) {
			got_1 = true;
		}
		if (treeX == x_2 && treeZ == z_2) {
			treeY = 2;
			got_2=true;
		}
		gen.func_517_a(1.0D, 1.0D, 1.0D);
		gen.func_516_a(this, rand, treeX + 8 + xOffset, treeY, treeZ + 8 + zOffset);
		b.setWorld(this);
		
		if (!got_1 || !got_2) {
			return 0;
		}
		reset();
		
		rand = new Random(tree_seed ^ 0x5deece66dl);
		rand.nextInt(10);
		gen = new GenBigTree();
		
		
		treeX = rand.nextInt(16);
		treeZ = rand.nextInt(16);
		treeY = 1;
		if (treeX == x_1 && treeZ == z_1) {
			
		}
		if (treeX == x_2 && treeZ == z_2) {
			treeY = 2;
		}
		gen.func_517_a(1.0D, 1.0D, 1.0D);
		gen.func_516_a(this, rand, treeX + 8 + xOffset, treeY, treeZ + 8 + zOffset);
		
		
		treeX = rand.nextInt(16);
		treeZ = rand.nextInt(16);
		treeY = 1;
		if (treeX == x_1 && treeZ == z_1) {
			
		}
		if (treeX == x_2 && treeZ == z_2) {
			treeY = 2;
		}
		gen.func_517_a(1.0D, 1.0D, 1.0D);
		gen.func_516_a(this, rand, treeX + 8 + xOffset, treeY, treeZ + 8 + zOffset);
		c.setWorld(this);
		
		
		TreeState m = new TreeState();
		m.merge(a, b);
		//System.out.println(Arrays.toString(new int[] {a.count(), b.count(), c.count()}));
		
		if (!m.equals(c)) {
			return 400;
		}
		return 200;
	}
	

}
