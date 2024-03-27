import java.util.*;

public class Unique {
    private List<String> unique;
    private int[] licz;
    private int counting;

    public Unique(List<String>lista){
        Set<String> unikatoweWartosci = new HashSet<>(lista);
        this.unique=new ArrayList<>(unikatoweWartosci);
        this.licz=new int[this.unique.size()];
        this.counting=0;
    }
    public void numOccurrences(ArrayList<String[]> data, List<Map.Entry<Integer, Double>> sortlista, int licznik, int lineLength){
        Arrays.fill(licz,0);
        Map<Integer, String> aaa= new HashMap<>();
        int x;
        for (int i = 0; i < licznik; i++) {
            x=sortlista.get(i).getKey();
            aaa.put(x,data.get(x)[lineLength-1]);
        }
        for (int i = 0; i < unique.size(); i++) {
            String uniqueVal= unique.get(i);
            for(String value : aaa.values()){
                if(uniqueVal.equals(value)){
                    licz[i]++;
                }
            }
        }
//        for (int i = 0; i < licz.length; i++) {
//            System.out.println(unique.get(i)+"   "+licz[i]);
//        }


    }
    public int getMaxCounterIndex() {
        int maxCounterIndex = -1;
        int maxCounter = Integer.MIN_VALUE;
        for (int i = 0; i < licz.length; i++) {
            if (licz[i] > maxCounter) {
                maxCounter = licz[i];
                maxCounterIndex = i;
            }
        }
        return maxCounterIndex;         //the index that has the largest number
    }
    public String getMaxType(){
        int i=getMaxCounterIndex();
        return unique.get(i);           //return the String with the largest number
    }

    public void checkType(ArrayList<String[]> datacheck, int i, int lineLength){
        if(unique.get(getMaxCounterIndex()).equals(datacheck.get(i)[lineLength-1])){
            counting++;
        }
    }
    public int getCounting() {
        return counting;
    }

    public double[] www(int lineLength){
        Scanner scanner = new Scanner(System.in);
        double[] parameters= new double[lineLength];
        System.out.println("podaj parametry ["+(lineLength)+"]: ");
        for (int i = 0; i < parameters.length; i++) {
            parameters[i]=scanner.nextDouble();
            if(parameters[i]==-111){
                throw new RuntimeException("bledne parametry");
            }
        }
        return parameters;

    }

}
