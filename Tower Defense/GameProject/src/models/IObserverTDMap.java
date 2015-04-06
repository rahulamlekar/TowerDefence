package models;

/**
 *
 * This interface defines a type of observer that would react specifically in
 * different ways when a map is changed.
 */
public interface IObserverTDMap {

    /**
     *
     */
    public void TDMapUpdated();

    /**
     *
     */
    public void TDMapReinitialized();
}
