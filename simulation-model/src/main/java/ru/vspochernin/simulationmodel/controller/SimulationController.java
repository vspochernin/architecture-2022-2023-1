package ru.vspochernin.simulationmodel.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import ru.vspochernin.simulationmodel.simulation.Config;
import ru.vspochernin.simulationmodel.simulation.Simulator;

@Controller
public class SimulationController {


    @GetMapping("/step/{id}")
    public ModelAndView getStepPage(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("step-results");

        // TODO: это временно.
        try {
            if (Simulator.simulationResult == null) {
                Simulator.simulationResult = Simulator.simulate(Config.generateConfigFromProperties("src/main/resources" +
                        "/input.properties"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mav.addObject("description", Simulator.simulationResult.getSteps().get(id).getDescription());
        mav.addObject("calendar", Simulator.simulationResult.getSteps().get(id).getCalendarAndStateTableRows());
        mav.addObject("buffer", Simulator.simulationResult.getSteps().get(id).getBufferTableRows());

        return mav;
    }

    @GetMapping("/auto")
    public ModelAndView getAutoPage() {
        ModelAndView mav = new ModelAndView("auto-results");

        // TODO: это временно.
        try {
            if (Simulator.simulationResult == null) {
                Simulator.simulationResult = Simulator.simulate(Config.generateConfigFromProperties("src/main/resources" +
                        "/input.properties"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mav.addObject("inputs", Simulator.simulationResult.getInputCharacteristicTableRows());
        mav.addObject("devices", Simulator.simulationResult.getDeviceCharacteristicTableRows());
        return mav;
    }
}
