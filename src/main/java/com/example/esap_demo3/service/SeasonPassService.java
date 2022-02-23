package com.example.esap_demo3.service;

import com.example.esap_demo3.model.*;

import com.example.esap_demo3.repository.GymRepository;
import com.example.esap_demo3.repository.SeasonPassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SeasonPassService {

    @Autowired
    private SeasonPassRepository seasonPassService;

    @Autowired
    private GymRepository gymRepository;

    public void create(SeasonPass seasonPass, Long gymId) {
        Gym gym = gymRepository.findById(gymId).get();
        seasonPass.setGym(gym);
        seasonPassService.save(seasonPass);
    }

    public SeasonPass get(Long id) {
        return seasonPassService.findById(id).get();
    }

    public List<SeasonPass> getAll() {
        return seasonPassService.findAll().stream().sorted(Comparator.comparing(SeasonPass::getFullName)).collect(Collectors.toList());
    }

    public void update(Long seasonPassId, SeasonPass newSeasonPass, Long gymId) {
        SeasonPass seasonPass = seasonPassService.findById(seasonPassId).get();
        seasonPass.setDurationM(newSeasonPass.getDurationM());
        seasonPass.setFullName(newSeasonPass.getFullName());
        Gym gym = gymRepository.findById(gymId).get();
        seasonPass.setGym(gym);
        seasonPassService.save(seasonPass);
    }

    public void delete(Long id) {
        SeasonPass seasonPass = seasonPassService.findById(id).get();
        seasonPass.getGym().getPasses().remove(seasonPass);
        seasonPassService.deleteById(id);
    }

}
