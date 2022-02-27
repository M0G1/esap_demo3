package com.example.esap_demo3.controller;


import com.example.esap_demo3.model.Gym;
import com.example.esap_demo3.service.GymService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/gyms")
public class GymController {

    @Autowired
    private GymService gymService;


    @GetMapping(value = "/json")//, headers="accept=application/json") // localhost:8080/car
    public ResponseEntity getGyms(){
        List<Gym> gyms = gymService.getAll();
        return ResponseEntity.ok().body(gyms);
    }


    @GetMapping(value = "/xslt",headers = "accept=application/xml") // localhost:8080/shop
    public ModelAndView getGymsXSLT() throws JsonProcessingException {
        List<Gym> gyms = gymService.getAll();
        ModelAndView modelAndView = new ModelAndView("xslt/gyms.xslt");
        Source source = new StreamSource(new ByteArrayInputStream(new XmlMapper().writeValueAsBytes(gyms)));
        modelAndView.addObject(source);
        return modelAndView;
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute(new Gym());
        return "create_gym";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Gym gym) {
        gymService.create(gym);
        return "redirect:/gyms";
    }

    @GetMapping
    public String getAll(Model model) {
        List<Gym> gyms = gymService.getAll(); // работает пока ф7
        model.addAttribute("gyms", gyms);
        return "show_gyms";
    }

    @GetMapping("/{id}/update")
    public String getUpdatePage(@PathVariable("id") Long gymId, Model model) {
        model.addAttribute("gym", gymService.get(gymId));
        return "update_gym";
    }

    @PatchMapping("/{id}/update")
    public String update(@PathVariable("id") Long gymId, @ModelAttribute Gym gym) {
        gymService.update(gymId, gym);
        return "redirect:/gyms";
    }

    @GetMapping("/{id}/season_passes")
    public String getSeasonPasses(@PathVariable("id") Long gymId, Model model) {
        model.addAttribute("seasonPasses", gymService.getSeasonPasses(gymId));
        model.addAttribute("gym", gymService.get(gymId));
        return "show_gym_season_passes";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        gymService.delete(id);
        return "redirect:/gyms";
    }
}
