package MapGenerator;

/**
 * Created by Mikko Forsman on 30.5.2016.
 */
public class Main {
    public static void main(String[] args){
        MapFactory mf = new MapFactory().DesertCoverage(0.1f).SnowCoverage(0.1f);
        GameMap map = mf.Generate();
        map.PrintMapAscii();
    }
}
