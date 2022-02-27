package com.example.esap_demo3.controller;

import com.example.esap_demo3.model.*;
import com.example.esap_demo3.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.List;

@Controller
@RequestMapping("/season_passes")
public class SeasonPassController {

    @Autowired
    private SeasonPassService seasonPassService;

    @Autowired
    private GymService gymService;

    @GetMapping(value = "/json")//, headers="accept=application/json") // localhost:8080/car
    public ResponseEntity getSeasonPasses(){
        List<SeasonPass> seasonPasses = seasonPassService.getAll();
        return ResponseEntity.ok().body(seasonPasses);
    }


    @GetMapping(value = "/xslt")//,headers = "accept=application/xml") // localhost:8080/shop
    public ModelAndView getGymsXSLT() throws JsonProcessingException {
        List<SeasonPass> seasonPasses = seasonPassService.getAll();
        ModelAndView modelAndView = new ModelAndView("seasonPasses");
        Source source = new StreamSource(new ByteArrayInputStream(new XmlMapper().writeValueAsBytes(seasonPasses)));
        modelAndView.addObject(source);
        return modelAndView;
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("seasonPass", new SeasonPass());
        List<Gym> gyms = gymService.getAll();
        model.addAttribute("gyms", gyms);
        return "create_season_pass";
    }
    // season_passes/create
    @PostMapping("/create")
    public String create(@ModelAttribute SeasonPass seasonPass, @RequestParam Long gymId) {
        seasonPassService.create(seasonPass, gymId);
        return "redirect:/season_passes";
    }

    @GetMapping
    public String getAll(Model model) {
        List<SeasonPass> seasonPasses = seasonPassService.getAll();
        model.addAttribute("seasonPasses", seasonPasses);
        return "show_season_passes";
    }

    @GetMapping("/{id}/update")
    public String getUpdatePage(@PathVariable("id") Long id, Model model) {
        SeasonPass seasonPass = seasonPassService.get(id);
        model.addAttribute("seasonPass", seasonPass);
        List<Gym> gyms = gymService.getAll();
        model.addAttribute("gyms", gyms);
        return "update_season_pass";
    }

    @PatchMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute SeasonPass seasonPass, @RequestParam Long gymId) {
        seasonPassService.update(id, seasonPass, gymId);
        return "redirect:/season_passes";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        seasonPassService.delete(id);
        return "redirect:/season_passes";
    }
}
