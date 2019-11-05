import java.util.Comparator;

public class CountComparison implements Comparator<MessageCount> {

    @Override
    public int compare(MessageCount t0, MessageCount t1) {
        return  t1.count - t0.count;
    }
}
