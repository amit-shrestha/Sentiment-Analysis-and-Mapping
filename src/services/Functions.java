package services;

import java.util.List;

import models.Variables;

public interface Functions {
	public boolean searchTopic(Variables v);
	public List<Variables> getAllData();
	public void filter();
}
