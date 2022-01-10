import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lee on 17-7-12.
 */
public class T {
    public static void main(String[] args) throws Exception {
        String str = "Table_Item_103.tab";

        String[] split = str.split(".");
//        String content = split[1];
//        String[] heads = content.split(";");
//        String fileName = heads[2].split("=")[1].replaceAll("\"", "");
//        System.out.println(fileName);


    }

    public static String[] replaceHead(String[] arr, String head) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].contains(head)) {
                arr[i] = head + "\n";
            }
        }
        return arr;
    }

    public static List<TableFile> getFiles(List<String> arrayList, String head) {
        int hpos = 0;
        int epos = hpos + 1;
        List<TableFile> arr = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(head)) {
                hpos = i;
                for (int x = hpos + 1; x < arrayList.size(); x++) {
                    if (arrayList.get(x).equals(head)) {
                        epos = x - 1;
                        String content = arrayList.get(hpos + 1);
                        String[] heads = content.split(";");
                        String fileName = heads[2].split("=")[1].replaceAll("\"", "");
                        StringBuffer sb = new StringBuffer();
                        for (int j = hpos + 3; j < epos; j++) {
                            if (!arrayList.get(j).equals("")) {
                                sb.append(arrayList.get(j));
                            }
                        }
                        arr.add(new TableFile(fileName, sb.toString()));
                        hpos = epos + 1;
                    }
                }
            }
        }
        return arr;
    }

    static class TableFile {
        private String fileName;
        private String fileContent;

        public TableFile(String fileName, String fileContent) {
            this.fileName = fileName;
            this.fileContent = fileContent;
        }

        @Override
        public String toString() {
            return "F{" +
                    "fileName='" + fileName + '\'' +
                    ", fileContent='" + fileContent + '\'' +
                    '}';
        }
    }

}


