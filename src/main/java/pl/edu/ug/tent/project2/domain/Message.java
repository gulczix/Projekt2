package pl.edu.ug.tent.project2.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Message {
  @Id
  @GeneratedValue
  private String id = UUID.randomUUID().toString();
  @Pattern(regexp = ".+", message = "pusta")
  private String content;
  private String createDate  = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(System.currentTimeMillis());
  private String idPerson;
  @ManyToOne
  private Person person;

  public Message(String content) {
    this.content = content;
  }
  /*
  @OneToMany
  private List<personMessage> personMessages;
   */
}
