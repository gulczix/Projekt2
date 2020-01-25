package pl.edu.ug.tent.project2.service;

import pl.edu.ug.tent.project2.domain.Message;

import java.util.List;

public interface MessageManager {
    void addMessage(Message person);
    Message findById(String id);
    List<Message> getAllMessages();
    List<Message> getMessages(String id);
    void remove(String id);
    void edit(String id, Message message);
}
