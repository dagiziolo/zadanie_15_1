import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Import {
    public ArrayList<ShopOrder> getImport(File file) {
        ArrayList<ShopOrder> orderLists = new ArrayList<>();
        int i = 0;
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku");
        }
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] s = line.split(";");
            orderLists.add(new ShopOrder(Integer.valueOf(s[0]), s[1], Double.valueOf(s[2]), Status.valueOf(s[3])));
        }
        return orderLists;
    }
}
