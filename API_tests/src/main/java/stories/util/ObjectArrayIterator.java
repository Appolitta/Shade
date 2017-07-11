package stories.util;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Class for converting array of Object[] to the Iterator.
 */
public class ObjectArrayIterator implements Iterator<Object[]> {
    private final Iterator<?> iterator;

    public ObjectArrayIterator(Object[] objects) {
        this(Arrays.asList(objects));
    }

    public ObjectArrayIterator(List<?> objects) {
        this.iterator = objects.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Object[] next() {
        return new Object[]{iterator.next()};
    }
}
