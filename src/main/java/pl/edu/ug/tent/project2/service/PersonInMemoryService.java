package pl.edu.ug.tent.project2.service;

import org.springframework.stereotype.Service;
import pl.edu.ug.tent.project2.domain.Person;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonInMemoryService implements PersonManager {

  private static List<Person> persons = new ArrayList<>();
  public void addPerson(Person person) {
    persons.add(person);
  }

  @Override
  public Person findById(String id) {
    for (Person person : persons) if (person.getId().equals(id)) return person;
    return null;
  }

  public List<Person> getAllPersons() {
    return persons;
  }

  @Override
  public void remove(String id) {
    persons.removeIf(person -> person.getId().equals(id));
  }

  @Override
  public void edit(String id, Person person) {
    persons.removeIf(p -> p.getId().equals(id));
    person.setId(id);
    persons.add(person);
  }

  @Override
  public Person findByNick(String nick, String pass) {
    for (Person person : persons) if (nick.equals(person.getNick()) && pass.equals(person.getPass())) return person;
    return null;
  }
}
