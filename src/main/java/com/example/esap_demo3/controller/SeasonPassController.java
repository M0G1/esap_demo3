package com.example.esap_demo3.controller;

import com.example.esap_demo3.model.*;
import com.example.esap_demo3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/season_passes")
public class SeasonPassController {

    @Autowired
    private SeasonPassService seasonPassService;

    @Autowired
    private GymService gymService;


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
