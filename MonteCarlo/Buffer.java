package MonteCarlo;

public interface Buffer <E> {
    public void insert(E item, int id);
    public E remove();
}
