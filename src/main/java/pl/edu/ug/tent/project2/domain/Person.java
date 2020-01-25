package pl.edu.ug.tent.project2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {
  @Id
  @GeneratedValue
  private String id = UUID.randomUUID().toString();
  @Pattern(regexp = "[A-Z][a-z]+", message = "błędne (nie może mieć polskich znaków)")
  private String firstName, lastName;
  @Pattern(regexp = "[a-z0-9]{3,}", message = "musi zawierać małe litery i cyfry")
  private String nick;
  @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])([a-z0-9_-]+)$",message="musi zawierać litery i cyfry")
  private String pass;
  @Pattern(regexp = "[A-Aa-z][A-Aa-z0-9\\.]*@[A-Za-z]+.[A-Za-z]+",message = "błędny email")
  private String email;
  boolean admin = false;
  @OneToMany
  private List<Message> messages;
  /*
  @OneToMany
  private List<personMessage> personMessages;
   */

  public Person(String fN, String lN, String n, String p, String e, Boolean a) {
      this.firstName = fN;
      this.lastName = lN;
      this.nick = n;
      this.email = e;
      this.admin = a;
  }
 }
