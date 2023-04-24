package com.track.yourself.service;

import com.track.yourself.models.Plan;
import com.track.yourself.models.dto.FindPlansRequest;
import com.track.yourself.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.track.yourself.util.Util.getParam;

@Service
public class PlanService {
    @Autowired
    private PlanRepository planRepository;

    public List<Plan> searchPlansByParams(FindPlansRequest plansRequest) {
        if (plansRequest.getDate() == null) {
            plansRequest.setDate(new Date());
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(plansRequest.getDate());

        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date startOfWeek = cal.getTime();

        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date endOfWeek = cal.getTime();

        plansRequest.setDescription(getParam(plansRequest.getDescription()));

        plansRequest.setStatus(getParam(plansRequest.getStatus()));

        List<Object[]> resultList = planRepository.searchPlansByParamas(
                plansRequest.getUserId(),
                startOfWeek,
                endOfWeek,
                plansRequest.getDescription(),
                plansRequest.getStatus());

        List<Plan> plans = new ArrayList<>();
        for (Object[] result : resultList) {
            Plan plan = new Plan();
            plan.setPlanId((Integer) result[0]);
            plan.setUser_id((Integer) result[1]);
            plan.setDescription((String) result[2]);
            plan.setStatus((String) result[3]);
            plan.setDate((Date) result[4]);
            plans.add(plan);
        }
        return plans;
    }
}
