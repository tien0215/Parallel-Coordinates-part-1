import java.util.ArrayList;
import java.util.HashMap;

public class Axis {

    enum ColumnType {
        NUMERIC,
        TEXT
    }

    String columnName;
    ColumnType type;
    double max;
    double min;

    ArrayList<String> stringData;
    ArrayList<Double> numberData;

    public Axis(String name, ColumnType t) {
        columnName = name;
        type = t;
        stringData=new ArrayList<>();
        numberData=new ArrayList<>();
        max=0;
        min=0;
    }


}
