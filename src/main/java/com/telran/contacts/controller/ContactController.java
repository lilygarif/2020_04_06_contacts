package com.telran.contacts.controller;

import com.telran.contacts.dto.Contact;
import com.telran.contacts.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller   //аннотация к классу
public class ContactController {

    // @Autowired
    private ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

//    @GetMapping
//    public ModelAndView home(){
//        return ModelAndView
//
//    }

    @GetMapping("/contact")
    public String contactForm(Model model) { //отдаем в браузер формупо созданию контакта
        model.addAttribute("contact", new Contact());
        return "contact-form";
    }

    @PostMapping("/contact") //сохраняет форму контактов
    public String addContact(@ModelAttribute Contact contact) { //принимаем в браузер формупо созданию контакта
        if (contact.getId() == 0) //если контакт еще не имеет id
            contactService.add(contact); //тогда создаем пользователя
        else
            contactService.edit(contact); // в противном случае - меняем
        return "redirect:/";   //переадресация. браузер посылает запрос
    }
//    @PostMapping("/contact") //сохраняет форму контактов
//    public ModelAndView addContact(@ModelAttribute Contact contact) { //принимаем в браузер формупо созданию контакта
//        contactService.add(contact);
//        return new ModelAndView("redirect:/contacts");
//    }

    @GetMapping("/")  // обработка роута домашней страницы:
    public String home() { // переадресация на comtacts
        //return "redirect:/contacts";   //302 status
        return "forward:/contacts"; //без второго реквеста с сохранением пути
    }

    @GetMapping("/contact/{id}")
    public String getContact(@PathVariable int id, Model model) {
        Contact contact = contactService.get(id);
        model.addAttribute("contact", contact);
        return "contact";
    }

    @GetMapping("/contacts")  //слушаем страницу localhost:8080/contacts
    public String getContacts(Model model) { //отдаем в браузер форму по созданию контакта
        List<Contact> contacts = contactService.getAll();
        model.addAttribute("contacts", contacts);
        return "contacts";
    }

    @GetMapping("/contact/edit/{id}")
    public String editContact(@PathVariable int id, Model model) {
        Contact contact = contactService.get(id);
        model.addAttribute("contact", contact);
        return "contact-form";
    }
    @GetMapping("/contact/remove/{id}")
    public String removeContact(@PathVariable int id) {
        contactService.remove(id);
        return "redirect:/";
    }

}
