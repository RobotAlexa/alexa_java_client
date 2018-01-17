package com.amazon.alexa.avs.robot;

import com.amazon.alexa.avs.robot.bean.SkillInformation;
import com.amazon.alexa.avs.robot.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RobotControlDispatcher {
    private static final Logger log = LoggerFactory.getLogger(RobotControlDispatcher.class);
    private static List<RobotControlHandler> handlers;
    static {
        handlers = new ArrayList<>();
        handlers.add(new ActionHandler());
        handlers.add(new DanceHandler());
        handlers.add(new MovingHandler());
        handlers.add(new VolumeAdjustHandler());
        handlers.add(new FaceFunctionHandler());
        handlers.add(new AmazonStopHandler());
        handlers.add(new SystemUpgradeHandler());
    }

    public void dispatchHandler(SkillInformation skillInformation) {
        for (RobotControlHandler handler : handlers) {
            if (handler.canHandle(skillInformation)) {
                log.info("handle a skill: {}", skillInformation);
                handler.handle(skillInformation);
            }
        }
    }
}

