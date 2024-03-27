import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String file;
        String secfile;
        System.out.println("wybierz plik: ");
        for (File x : File.values()){
            System.out.println(x.ordinal()+1+": "+x);
        }
        int choiceFile = scanner.nextInt();
        switch(choiceFile) {
            case 1 -> {
                file = File.IRIS.getFile_name();
                secfile=File.IRIS.getSecfile_name();
            }
            case 2-> {
                file= File.CANCER.getFile_name();
                secfile = File.CANCER.getSecfile_name();
            }
            default -> throw new RuntimeException("nie bybrano pliku");
        }

        ArrayList<String[]> data = przechwytywanie(file);

        int lineLength=data.get(0).length;                                              //number of parameters+result column

        List<String>uniq = new ArrayList<>();
        for (String[] datum : data) {
            uniq.add(datum[lineLength - 1]);                                             //unique value from ressult column
        }
        Unique un=new Unique(uniq);

        double [][]xn = maxmin(data,lineLength);                                             //max and min for all parameter columns
        //////////////////////////////////////////////////////////////
        System.out.println("podaj parametr k: ");
        int count = scanner.nextInt();                                                       //how many Euclidean values
        ArrayList<String[]> datacheck = przechwytywanie(secfile);
        for (int i = 0; i < datacheck.size(); i++) {
            double[] tab=new double[lineLength-1];
            for (int j = 0; j < tab.length; j++) {
                tab[j]=Double.parseDouble(datacheck.get(i)[j]);
            }
            Map<Integer, Double> mapa=mapEuklides(data,tab,xn,lineLength);
            List<Map.Entry<Integer, Double>> sortlista = sortMap(mapa);                     //sorted map by Euclidean distance
            un.numOccurrences(data,sortlista,count,lineLength);
            un.checkType(datacheck, i, lineLength);

        }
        double wspol=un.getCounting();
        System.out.println("Prawidlowo zaklasyfikowane przyklady: "+(int)wspol+"/"+datacheck.size());
        System.out.println("Dokladnosc eksperymentu: "+(wspol/datacheck.size())*100+"%\n");

        while(true) {
            double[] parameters = un.www(lineLength-1);
            Map<Integer, Double> map =mapEuklides(data, parameters,xn,lineLength);
            List<Map.Entry<Integer, Double>> sortlista = sortMap(map);
            un.numOccurrences(data,sortlista,count,lineLength);
            System.out.println("type: "+un.getMaxType()+"\n\n");


        }


    }

    public static ArrayList<String[]> przechwytywanie(String file){
        ArrayList<String[]>data=new ArrayList<>();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                line=line.replace(",",".");
                line = line.replaceFirst("^\\s+", "");
                String[] atr = line.split("\\s+");       // "\\s+"
                data.add(atr);
            }
            fr.close();
            br.close();
//            for (String[] i : data){
//                for (String y : i){
//                    System.out.print(y+" ");
//                }
//                System.out.println();
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static double[][] maxmin(ArrayList<String[]> data,int lineLength){
        double []min= new double[lineLength-1];
        double []max= new double[lineLength-1];
        for (int i = 0; i < max.length; i++) {
            max[i]=Double.MIN_VALUE;
            min[i]=Double.MAX_VALUE;
        }

        for (String[] row : data){
            for (int i = 0; i < row.length-1; i++) {
                double temp =Double.parseDouble(row[i]);
                if(max[i]<temp){
                    max[i]=temp;        //max
                }
                if(min[i]>temp){
                    min[i]=temp;        //min
                }
            }
        }
        double[][] wynik = new double[2][];
        wynik[0] = max;
        wynik[1] = min;
        return wynik;
    }
    public static Map<Integer, Double> mapEuklides(ArrayList<String[]> data,double[] tab,double [][]xn,int lineLength){
        Map<Integer, Double> map =new HashMap<>();          //put all index numbers and their Euclidean distance here
        double[] param= new double[lineLength-1];
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < param.length; j++) {
                param[j]=Double.parseDouble(data.get(i)[j]);
            }
            double y=0;
            for (int j = 0; j < param.length; j++) {
                y+=Math.pow((tab[j]-param[j])/(xn[0][j]-xn[1][j]),2);       //calculating Euclidean distances for parameters
            }
            y=Math.sqrt(y);

            map.put(i,y);
        }
        return map;
    }

    public static List<Map.Entry<Integer, Double>> sortMap(Map<Integer, Double> map){
        List<Map.Entry<Integer, Double>> sortlista = new ArrayList<>(map.entrySet());
        //sort the list based on values from the smallest to the largest
        Collections.sort(sortlista, new Comparator<Map.Entry<Integer, Double>>() {
            @Override
            public int compare(Map.Entry<Integer, Double> entry1, Map.Entry<Integer, Double> entry2) {
                return entry1.getValue().compareTo(entry2.getValue());
            }
        });
        return sortlista;
    }
}


