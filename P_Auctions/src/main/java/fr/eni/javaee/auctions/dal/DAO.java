package fr.eni.javaee.auctions.dal;

import java.util.List;

public interface DAO<T> {
	public abstract List<T> selectAll();
}
