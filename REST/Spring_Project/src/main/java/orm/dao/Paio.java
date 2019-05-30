package orm.dao;

public class Paio<A, B> {

    public A first;
    public B second;

    public Paio(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        String ris = "";
        ris = "<" + first.toString() + ", " + second.toString() + ">";
        return ris;
    }

}
