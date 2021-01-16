import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XCheck {

	public static void main(String[] args) {
		checkXZ(9, 2, "");
		checkXZ(9, 6, "");
		checkXZ(9, 10, "");
		checkXZ(9, 14, "");
		checkXZ(13, 2, "");
		checkXZ(13, 6, "");
		checkXZ(9, 2, "zeroes_");
		checkXZ(9, 6, "zeroes_");
		checkXZ(9, 10, "zeroes_");
		checkXZ(9, 14, "zeroes_");
		checkXZ(13, 2, "zeroes_");
		checkXZ(13, 6, "zeroes_");
	}
	
	public static void checkXZ(int x, int z, String b) {
		String path = "big_canopy_filtered_"+b + x+"_"+z+"_.txt";
		ArrayList<String> good = new ArrayList<String>();
		try {
			List<String> lines = Files.readAllLines(Paths.get(path));
			for (String s : lines) {
				String[] arr = s.split(" ");
				World w = new World(x, z, arr[2]);
				int r = w.simulateX(Long.parseLong(arr[0]));
				if (r!=-1) {
					good.add(arr[0] + " " + r + " " + arr[2]);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		try {
			PrintWriter writer = new PrintWriter("Xtra_filter_"+b+x + "_"+z+".txt");
			for (String s : good) {
				writer.println(s);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
