package pl.edu.ug.tent.project2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.ug.tent.project2.domain.Person;
import pl.edu.ug.tent.project2.service.MessageManager;
import pl.edu.ug.tent.project2.service.PersonManager;

import javax.validation.Valid;

@Controller("controller")
public class controller {

  private JavaMailSender emailSender;
  private PersonManager pm;
  private MessageManager mm;

  @Autowired
  public controller(JavaMailSender emailSender, PersonManager pm, MessageManager mm){
    this.emailSender = emailSender;
    this.pm = pm;
    this.mm = mm;
  }

  @GetMapping("/person")
  public String person(Model model){
    model.addAttribute("persons", pm.getAllPersons());
    return "adminpanel";
  }

  @GetMapping("/")
  public String log(Model model){
    model.addAttribute("person", new Person());
    return "login";
  }
  @PostMapping("/loging")
  public String loging(Person person, Model model) {
    String id = pm.findByNick(person.getNick(), person.getPass()).getId();
    model.addAttribute("id", id);
    if (pm.findById(id).isAdmin()) {
      model.addAttribute("messages", mm.getAllMessages());
      model.addAttribute("persons", pm.getAllPersons());
      return "adminpanel";
    }
    else {
      model.addAttribute("messages", mm.getMessages(id));
      return "userpanel";
    }
  }

  @GetMapping("/register")
  public String register(Model model){
    model.addAttribute("person", new Person());
    return "register";
  }
  @PostMapping("/registering")
  public String registering(@Valid Person person, Errors errors, Model model) {
    if(errors.hasErrors()){
      return "register";
    }

    try {
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setTo(person.getEmail());
      mailMessage.setSubject("REGISTERED");
      mailMessage.setText(person.toString());
      emailSender.send(mailMessage);
    } catch (Exception e) {
    }

    pm.addPerson(person);
    model.addAttribute("person", new Person());
    return "login";
  }

  @PostMapping("panel")
  public String returning(Model model, String id){
    model.addAttribute("id", id);
    System.out.println("PANEL");
    if (pm.findById(id).isAdmin()) {
      model.addAttribute("messages", mm.getAllMessages());
      model.addAttribute("persons", pm.getAllPersons());
      System.out.println("ADMIN");
      return "adminpanel";
    }
    else {
      model.addAttribute("messages", mm.getMessages(id));
      return "userpanel";
    }
  }

  @GetMapping("/editP/{id}")
  public String editPerson(Model model, @PathVariable("id") String id) {
    Person found = pm.findById(id);
    model.addAttribute("found", found);
    return "editP";
  }
  @PostMapping("/editPing")
  public String replacePerson(@Valid Person found, Errors errors, Model model) {
    if(errors.hasErrors()){
      return "editP";
    }
    pm.edit(found.getId(), found);
    model.addAttribute("persons", pm.getAllPersons());
    return "adminpanel";
  }

  @GetMapping("/deleteP/{id}")
  public String deletePerson(@PathVariable("id") String id, Model model) {
    pm.remove(id);
    model.addAttribute("persons", pm.getAllPersons());
    return "adminpanel";
  }

  @GetMapping("/post/{id}")
  public String post(Model model, @PathVariable("id") String id){
    model.addAttribute("id", id);
    pl.edu.ug.tent.project2.domain.Message message = new pl.edu.ug.tent.project2.domain.Message();
    message.setIdPerson(id);
    model.addAttribute("message", message);
    return "post";
  }
  @PostMapping("/posting")
  public String posting(@Valid pl.edu.ug.tent.project2.domain.Message message, Errors errors, Model model) {
    if(errors.hasErrors()){
      return "post";
    }
    mm.addMessage(message);
    System.out.println("POSTING");
    model.addAttribute("id", message.getIdPerson());
    return "redirect:/panel";
  }


  @GetMapping("/editM/{id}")
  public String editM(Model model, @PathVariable("id") String id) {
    pl.edu.ug.tent.project2.domain.Message found = mm.findById(id);
    model.addAttribute("found", found);
    return "editM";
  }

  @GetMapping("/deleteM/{id}")
  public String deleteM(@PathVariable("id") String id, Model model) {
    String idaut = mm.findById(id).getIdPerson();
    mm.remove(id);
    model.addAttribute("id", idaut);
    return "redirect:/panel";
  }

  @PostMapping("/editMing")
  public String editing(@Valid pl.edu.ug.tent.project2.domain.Message found, Errors errors, Model model) {
    if(errors.hasErrors()){
      return "editM";
    }
    mm.edit(found.getId(), found);
    model.addAttribute("id", found.getIdPerson());
    return "redirect:/panel";
  }

}
