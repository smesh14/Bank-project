package egc.bankservice.utils;

public class RandomGenerator {

    public static String getRandomPin(){
        int min = 1000;
        int max = 9999;
        return ""+(int)Math.floor(Math.random()*(max-min+1)+min);
    }
}
