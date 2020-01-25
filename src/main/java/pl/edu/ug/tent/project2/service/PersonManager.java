package pl.edu.ug.tent.project2.service;

import pl.edu.ug.tent.project2.domain.Person;

import java.util.List;

public interface PersonManager {

  void addPerson(Person person);

  Person findById(String id);

  List<Person> getAllPersons();

  void remove(String id);

  void edit(String id, Person person);

  Person findByNick(String nick, String pass);

}
