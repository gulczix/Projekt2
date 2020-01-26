package pl.edu.ug.tent.project2.service;

import org.springframework.stereotype.Service;
import pl.edu.ug.tent.project2.domain.Message;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageInMemoryService implements MessageManager {

    private static List<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        messages.add(message);
    }

    @Override
    public Message findById(String id) {
        for (Message message : messages) if (message.getId().equals(id)) return message;
        return null;
    }

    @Override
    public List<Message> getAllMessages() {
        return messages;
    }

    @Override
    public List<Message> getMessages(String id) {
        List<Message> personMess = new ArrayList<>();
        for (Message message : messages) if (message.getIdPerson().equals(id)) personMess.add(message);
        return personMess;
    }

    @Override
    public void remove(String id) {
        messages.removeIf(message -> message.getId().equals(id));
    }

    @Override
    public void edit(String id, Message message) {
        messages.removeIf(m -> m.getId().equals(id));
        message.setId(id);
        messages.add(message);
    }
}
