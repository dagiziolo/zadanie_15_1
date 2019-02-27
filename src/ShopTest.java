import java.io.File;
import java.util.*;

public class ShopTest {
    public static void main(String[] args) {
        File file = new File("zamówienia.csv");
        File file2 = new File("zamówienia_fin.csv");
        Import importFile = new Import();

        ArrayList<ShopOrder> orderLists = importFile.getImport(file); //zaczyt z pliku

        System.out.println("Zaimportowane zamówienia");
        for (ShopOrder order : orderLists) {
            System.out.println(order);
        }

        Scanner scan = new Scanner(System.in);

        ShopOrderAction[] actions = ShopOrderAction.values();
        System.out.println("Co chcesz wykonać z danymi zamówieniami?");
        for (ShopOrderAction action : actions) {
            System.out.println(action);
        }
        ShopOrderAction acctionChoice = ShopOrderAction.valueOf(scan.nextLine().toUpperCase());
        switch (acctionChoice) {
            case END: {
                System.out.println("Koniec programu");
                break;
            }
            case ADD: {
                addMethod(file2, orderLists, scan);
                break;
            }
            case CHANGE_STATUS: {
                changeMethod(file2, orderLists, scan);
                break;
            }
            case SORT: {
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
        tmpId++;
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

    private static void changeMethod(File file2, ArrayList<ShopOrder> orderLists, Scanner scan) {
        Export export = new Export();
        System.out.println("Podaj id zamówienia, które chesz zmienić:");
        int id = scan.nextInt();
        scan.nextLine();
        ShopOrder foundOrder = null;
        for (ShopOrder orderList : orderLists) {
            if (id == orderList.getId()) {
                foundOrder = orderList;
                break;
            }
        }
        try {
            System.out.println("Zamówienie " + foundOrder.getId() + " ma obecnie status: " + foundOrder.getStatus());

            switch (foundOrder.getStatus()) {
                case ANULOWANE: {
                    System.out.println("Brak możliwości zmiany statusu");
                    break;
                }
                case ZREALIZOWANE: {
                    System.out.println("Brak możliwości zmiany statusu");
                    break;
                }
                case TRANSPORT: {
                    System.out.println("Można zmienić na: " + Status.ZREALIZOWANE);
                    Status giveStatus = Status.valueOf(scan.nextLine().toUpperCase());
                    if (giveStatus == Status.ZREALIZOWANE) {
                        foundOrder.setStatus(giveStatus);
                    } else {
                        System.out.println("Nie można zmienić");
                    }
                    break;
                }
                case PRZYGOTOWANE: {
                    System.out.println("Można zmienić na: " + Status.TRANSPORT + " lub " + Status.ZREALIZOWANE + " lub " + Status.ANULOWANE);
                    Status giveStatus = Status.valueOf(scan.nextLine().toUpperCase());
                    if (giveStatus == Status.TRANSPORT || giveStatus == Status.ZREALIZOWANE || giveStatus == Status.ANULOWANE) {
                        foundOrder.setStatus(giveStatus);
                    } else {
                        System.out.println("Nie można zmienić");
                    }
                    break;
                }
                case ZLOZONE: {
                    System.out.println("Można zmienić na: " + Status.TRANSPORT + " lub " + Status.ZREALIZOWANE + " lub " + Status.ANULOWANE + " lub " + Status.PRZYGOTOWANE);
                    Status giveStatus = Status.valueOf(scan.nextLine().toUpperCase());
                    foundOrder.setStatus(giveStatus);
                    break;
                }
            }
            System.out.println("Zamówienie " + foundOrder.getId() + " ma obecnie status: " + foundOrder.getStatus());
            export.exportCsv(orderLists, file2);

        } catch (IllegalArgumentException e) {
            System.out.println("Nieprawidłowa wartość!");
        } catch (NullPointerException e) {
            System.out.println("Brak takiego zamówienia");
        }
    }
}



