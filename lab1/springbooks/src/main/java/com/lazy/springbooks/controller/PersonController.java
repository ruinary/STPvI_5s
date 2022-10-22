package com.lazy.springbooks.controller;

import com.lazy.springbooks.forms.PersonForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import com.lazy.springbooks.model.Person;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/*
* Аннотация @RequestMapping используется для связывания с URL для всего класса
* */

@Slf4j
@Controller
@RequestMapping
public class PersonController {
    private static List<Person> persons =new ArrayList<Person>();
    static {
        persons.add(new Person(0,"Chester","Bennington"));
        persons.add(new Person(1,"Mike","Shinoda"));
        persons.add(new Person(2,"Joe","Hahn"));
    }

    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    //Обрабатывает get-запросы
    @GetMapping(value = {"/","/index"})
    public ModelAndView index(Model model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        log.info("/index was called");
        return modelAndView;
    }

    @GetMapping(value = {"/allpersons"})
    public ModelAndView personList(Model model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("personlist");
        model.addAttribute("persons", persons);
        log.info("/allpersons was called");
        return modelAndView;
    }

    @GetMapping(value = {"/addperson"})
    public ModelAndView savePerson(Model model){
        ModelAndView modelAndView = new ModelAndView("addperson");
        PersonForm personForm = new PersonForm();
        model.addAttribute("personform", personForm);
        log.info("/addperson was called");
        return modelAndView;
    }

    @PostMapping(value = {"/addperson"})
    public ModelAndView savePerson(Model model, @ModelAttribute("personform") PersonForm personForm) {
        //заполнитель для хранения информации
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("personlist");
        int id = persons.size();
        String name = personForm.getName();
        String director = personForm.getLastname();

        if(name != null && name.length() > 0 && director != null && director.length() > 0){
            Person newPerson = new Person(id,name,director);
            persons.add(newPerson);
            model.addAttribute("persons", persons);

            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("addperson");
        log.info("/addperson was called");
        return modelAndView;
    }

    @GetMapping(value = {"/updateperson"})
    public ModelAndView updatePerson(Model model){
        ModelAndView modelAndView = new ModelAndView("updateperson");
        PersonForm personForm = new PersonForm();
        model.addAttribute("personform", personForm);
        log.info("/updateperson was called");
        return modelAndView;
    }

    @PostMapping(value = {"/updateperson"})
    public ModelAndView updatePerson(Model model, @ModelAttribute("personform") PersonForm personForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("personlist");
        int id = personForm.getId();
        String name = personForm.getName();
        String director = personForm.getLastname();

        if(id >= 0 && id <= persons.size() && name != null && name.length() > 0 && director != null && director.length() > 0){
            Person updatedPerson = new Person(id,name,director);
            persons.set(id, updatedPerson);
            model.addAttribute("persons", persons);

            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("updatePerson");
        log.info("/updateperson was called");
        return modelAndView;
    }

    @GetMapping(value = {"/deleteperson"})
    public ModelAndView deletePerson(Model model){
        ModelAndView modelAndView = new ModelAndView("deleteperson");
        PersonForm personForm = new PersonForm();
        model.addAttribute("personform", personForm);
        log.info("/deleteperson was called");
        return modelAndView;
    }

    //предоставление методов из модели
    @PostMapping(value = {"/deleteperson"})
    public ModelAndView deletePerson(Model model, @ModelAttribute("personform") PersonForm personForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("personlist");
        int id = personForm.getId();
        String name = personForm.getName();
        String director = personForm.getLastname();

        if(id >= 0 && id <= persons.size()){
            Person deletedPerson = new Person(id,name,director);
            persons.remove(id);
            model.addAttribute("persons", persons);

            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("deleteperson");
        log.info("/deleteperson was called");
        return modelAndView;
    }

}
