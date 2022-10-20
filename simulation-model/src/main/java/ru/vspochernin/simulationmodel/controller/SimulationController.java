package ru.vspochernin.simulationmodel.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ru.vspochernin.simulationmodel.simulation.Config;
import ru.vspochernin.simulationmodel.simulation.Simulator;

@Controller
public class SimulationController {

    @GetMapping("/")
    public ModelAndView getIndex() {
        ModelAndView mav = new ModelAndView("index");

        mav.addObject("config", Simulator.config);
        mav.addObject("stepscount", Simulator.simulationResult != null ? Simulator.simulationResult.getStepsCount() : 0);
        mav.addObject("modeltime", Simulator.simulationResult != null ? Simulator.simulationResult.getModelingTime() : 0);
        mav.addObject("totaltime", Simulator.simulationResult != null ? Simulator.simulationResult.getTotalSimulationTime() : 0);

        return mav;
    }

    @PostMapping("/simulate")
    public String simulate(@ModelAttribute Config config) {
        Simulator.config = config;
        Simulator.simulate();
        return "redirect:/";
    }

    @GetMapping("/step/{id}")
    public ModelAndView getStepPage(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("step-results");

        if (Simulator.simulationResult == null) {
            Simulator.generateFromProperties();
        }

        mav.addObject("cur", id);
        mav.addObject("prev", id != 0 ? id - 1 : 0);
        mav.addObject("next", id != Simulator.simulationResult.getSteps().size() - 1 ? id + 1 : id);
        mav.addObject("description", Simulator.simulationResult.getSteps().get(id).getDescription());
        mav.addObject("calendar", Simulator.simulationResult.getSteps().get(id).getCalendarAndStateTableRows());
        mav.addObject("buffer", Simulator.simulationResult.getSteps().get(id).getBufferTableRows());

        return mav;
    }

    @GetMapping("/auto")
    public ModelAndView getAutoPage() {
        ModelAndView mav = new ModelAndView("auto-results");

        if (Simulator.simulationResult == null) {
            Simulator.generateFromProperties();
        }

        mav.addObject("inputs", Simulator.simulationResult.getInputCharacteristicTableRows());
        mav.addObject("devices", Simulator.simulationResult.getDeviceCharacteristicTableRows());
        return mav;
    }
}
