import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ShopTest {
    public static void main(String[] args) {
        File file = new File("zamówienia.csv");
        File file2 = new File("zamówienia_fin.csv");
        Import importFile = new Import();

        ArrayList<ShopOrder> orderLists = importFile.getImport(file); //zaczyt z pliku
//        HashMap<Integer,ShopOrder> orderMaps = importFile.getImportMap(file);
        Scanner scan = new Scanner(System.in);

        ShopOrderAction[] actions = ShopOrderAction.values();
        System.out.println("Co chcesz wykonać z danymi zamówieniami?");
        for (ShopOrderAction action : actions) {
            System.out.println(action);
        }
        String acctionChoice = scan.nextLine().toUpperCase();
        switch (acctionChoice) {
            case "END": {
                System.out.println("Koniec programu");
                break;
            }
            case "ADD": {
                addMethod(file2, orderLists, scan);
                break;
            }
            case "CHANGE_STATUS": {
                System.out.println("Brak funkcjonalności");
                break;
            }
            case "SORT": {
                sortMethod(orderLists, scan);
                break;
            }

            default: {
                System.out.println("Nieprawidłowa wartość!");
            }
        }
    }

    private static void addMethod(File file2, ArrayList<ShopOrder> orderLists, Scanner scan) {
        Export export = new Export();
        int tmpId = 0;
        for (ShopOrder orderList : orderLists) {
            if (orderList.getId() > tmpId) {
                tmpId = orderList.getId();
            }
        }
        tmpId ++;
        System.out.println(tmpId);
        System.out.println("Podaj nazwę zamówienia");
        String tmpName = scan.nextLine();
        String tmpName2 = tmpName + "-" + tmpId;
        System.out.println("Podaj cenę zamówienia");
        double tmpPrice = scan.nextDouble();
        scan.nextLine();
        orderLists.add(new ShopOrder(tmpId, tmpName2, tmpPrice, Status.ZLOZONE));
        export.exportCsv(orderLists, file2);
        for (ShopOrder orderList : orderLists) {
            System.out.println(orderList);
        }
    }


    private static void sortMethod(ArrayList<ShopOrder> orderLists, Scanner scan) {
        ShopOrderComparator[] comparators = ShopOrderComparator.values();
        System.out.println("Po czym posortować?");
        for (ShopOrderComparator comparator : comparators) {
            System.out.println(comparator);
        }
        String tmp2 = scan.nextLine().toUpperCase();
        try {
            ShopOrderComparator shopOrderComparator = ShopOrderComparator.valueOf(tmp2);
            Collections.sort(orderLists, shopOrderComparator.getComparator());
            for (ShopOrder orderList : orderLists) {
                System.out.println(orderList);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Nieprawidłowa wartość!");
        }
    }

}



