package de.codecentric.mule.modules.assertobjectequals.internal;

/**
 * Representation of a path in an object tree.
 */
public class Path {
    private Path predecessor;
    private String key;
    private int index;
    private int listSize;

    /**
     * Create root.
     */
    public Path() {
    }

    private Path(Path predecessor) {
        this.predecessor = predecessor;
    }

    private Path(String key, Path predecessor) {
        this(predecessor);
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        this.key = key;
    }

    private Path(int listIndex, int listSize, Path predecessor) {
        this(predecessor);
        if (listSize < 0) {
            throw new IllegalArgumentException();
        }
        if (listIndex < 0 || listIndex >= listSize) {
            throw new IllegalArgumentException("Illegal index: " + listIndex);
        }
        index = listIndex;
        this.listSize = listSize;
    }

    /**
     * Create a new path based on this with a list index.
     *
     * @param listIndex
     *            The index, should start at 0.
     * @param listSize
     *            Must be greater than <code>listIndex</code>
     * @return Created entry
     */
    public Path listEntry(int listIndex, int listSize) {
        return new Path(listIndex, listSize, this);
    }

    /**
     * Create a new path based on this with a map key.
     *
     * @param key
     *            The map key.
     * @return Created entry
     */
    public Path mapEntry(String key) {
        return new Path(key, this);
    }

    public boolean isRoot() {
        return predecessor == null;
    }

    public boolean isList() {
        return !isRoot() && key == null;
    }

    public boolean isMap() {
        return !isRoot() && key != null;
    }

    public Path getPredecessor() {
        if (isRoot()) {
            throw new IllegalStateException("root has no predecessor");
        }
        return predecessor;
    }

    public String getKey() {
        return key;
    }

    public int getIndex() {
        return index;
    }

    public int getListSize() {
        return listSize;
    }

    @Override
    public String toString() {
        String result;
        if (isRoot()) {
            result = "";
        } else {
            result = predecessor.toString() + meToString();
        }
        return result;
    }

    private String meToString() {
        if (key != null) {
            return "['" + key + "']";
        } else {
            return "[" + index + "]";
        }
    }
}
