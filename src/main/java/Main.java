import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String destOfYourInputFile = "C:\\test\\input.txt";
        String destOfYourOutPutFile = "C:\\test\\output.txt";
        List<String> listOfLines = new ArrayList<>();
        Scanner scannerInput = null;
        FileWriter fileWriter = null;
        try {
            scannerInput = new Scanner(new FileInputStream(Paths.get(destOfYourInputFile).toFile()));
            while (scannerInput.hasNext()) {
                String line = scannerInput.nextLine();
                if (!line.equals("")) {
                    listOfLines.add(line);
                }
            }
        } catch (IOException i) {
            i.printStackTrace();
        } finally {
            try {
                if (scannerInput != null) {
                    scannerInput.close();
                }
            } catch (Exception ignored) {
            }
        }
        List<String> result = new ArrayList<>();
        List<String> list1 = listOfLines.subList(1, Integer.parseInt(listOfLines.get(0)) + 1);
        List<String> list2 = listOfLines.subList(Integer.parseInt(listOfLines.get(0)) + 2, listOfLines.size());
        List<String> copyOfList1 = new ArrayList<>(list1);
        List<String> copyOfList2 = new ArrayList<>(list2);
        if (list1.size() == 1 && list2.size() == 1) {
            result.add(list1.get(0) + ":" + list2.get(0));
            return;
        }
        for (String s : list1) {
            for (String value : list2) {
                if (s.contains(value) || value.contains(s)) {
                    result.add(s + ":" + value);
                    copyOfList1.remove(s);
                    copyOfList2.remove(value);
                } else if (s.contains(" ")) {
                    String[] array = s.split(" ");
                    for (String item : array) {
                        StringBuilder str = new StringBuilder(item);
                        if (item.length() < 2) {
                            continue;
                        }
                        str.delete(item.length() - 2, item.length());
                        if (value.contains(str)) {
                            result.add(s + ":" + value);
                            copyOfList1.remove(s);
                            copyOfList2.remove(value);
                            break;
                        }
                    }
                }
            }
        }
        copyOfList1.forEach(e -> result.add(0, e + ":?"));
        copyOfList2.forEach(e -> result.add(0, e + ":?"));
        for (int i = 0; i < result.size(); i++) {
            for (int j = 1; j < result.size() - 1; j++) {
                StringBuilder stringBuilder = new StringBuilder(result.get(i));
                stringBuilder.delete(result.get(i).indexOf(":") - 1, result.get(i).length() - 1);
                if (result.get(j).contains(stringBuilder.toString())) {
                    result.remove(result.get(i));
                }
            }
        }

        try {
            fileWriter = new FileWriter(destOfYourOutPutFile);
            for (String s : result) {
                fileWriter.write(s + "\n" + "\n");

            }
        } catch (IOException i) {
            i.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (Exception ignored) {}
        }
    }
}
