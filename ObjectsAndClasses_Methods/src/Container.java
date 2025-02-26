public class Container {
    private int count = 1;
    //private Integer count = null; (если есть необходимость в работе с классом-обёрткой)

    public void addCount(int value) {

        count = count + value;
    }

    public int getCount() {

        return count;
    }
}
