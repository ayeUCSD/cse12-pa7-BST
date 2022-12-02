import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSystem {

    BST<String, FileData> nameTree;
    BST<String, ArrayList<FileData>> dateTree;

    // TODO
    public FileSystem() {
        nameTree = new BST<>();
        dateTree = new BST<>();
    }

    // TODO
    public FileSystem(String inputFile) {
        // Add your code here
        nameTree = new BST<>();
        dateTree = new BST<>();
        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(", ");
                add(data[0], data[1], data[2]);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);

        }
    }

    public void add(String name, String dir, String date) {
        if (name == null || dir == null || date == null) {
            return;
        }

        FileData entry = new FileData(name, dir, date);
        // ADD TO NAME TREE
        // if i can't add the entry, otherwise i just added it
        String r = null;
        if (!nameTree.put(name, entry)) {
            // now compare the entries
            FileData existing = nameTree.get(name);
            // if the existing date is less than the new entry date
            if (existing.lastModifiedDate.compareTo(date) < 0) {
                r = existing.lastModifiedDate;
                nameTree.replace(name, entry);
            }
            else{
                return;
            }
        }
        // time for dateTree
        if (r != null) {
            // get list of last known date to remove the duplicate
            ArrayList<FileData> list = dateTree.get(r);
            for (FileData fd : list) {
                if (fd.name.equals(name) && fd.lastModifiedDate.compareTo(date) < 0) {
                    list.remove(fd);
                    break;
                }
            }
        }
        ArrayList<FileData> list = dateTree.get(date);
        if (list != null) {
            list.add(entry);
        } else {
            list = new ArrayList<>();
            list.add(entry);
            dateTree.put(date, list);
        }
    }

    public ArrayList<String> findFileNamesByDate(String date) {
        ArrayList<String> output = new ArrayList();
        for (FileData e : dateTree.get(date)) {
            output.add(e.name);
        }
        return output;
    }

    // TODO
    public FileSystem filter(String startDate, String endDate) {
        FileSystem output = new FileSystem();

        // get all the possible dates from dateTree
        List<String> dates = dateTree.keys();

        // for every date
        for (String date : dates) {
            // if it is in the range
            if (date.compareTo(startDate) >= 0 && date.compareTo(endDate) < 0) {
                // get the arraylist at that date and put it in the filesystem
                List<FileData> temp = dateTree.get(date);
                for (FileData fd : temp) {
                    output.add(fd.name, fd.dir, fd.lastModifiedDate);
                }
            }
        }

        return output;
    }

    // TODO
    public FileSystem filter(String wildCard) {
        FileSystem output = new FileSystem();

        // get all the names from dateTree
        List<String> names = nameTree.keys();
        for (String name : names) {
            // if name contains the wildcard
            if (name.indexOf(wildCard) >= 0) {
                FileData entry = nameTree.get(name);
                output.add(entry.name, entry.dir, entry.lastModifiedDate);
            }
        }
        return output;
    }

    // TODO
    public List<String> outputNameTree() {
        List<String> output = new ArrayList<>();
        List<String> keys = nameTree.keys();
        for (String s : keys) {
            String temp = s + ": " + nameTree.get(s).toString();
            System.out.println(temp);
            output.add(s);
        }
        return output;
    }

    // TODO
    public List<String> outputDateTree() {
        List<String> output = new ArrayList<>();
        // get the dates
        List<String> keys = dateTree.keys();
        // for every single date
        for (String s : keys) {
            // get the names at that date
            List<String> names = findFileNamesByDate(s);
            // for every name
            for (String name : names) {
                // append the date and then the toString
                output.add(s + ": " + nameTree.get(name).toString());
            }
        }
        return output;
    }

}
