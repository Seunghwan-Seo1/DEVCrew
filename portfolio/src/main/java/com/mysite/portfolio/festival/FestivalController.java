//전현식

package com.mysite.portfolio.festival;

import org.springframework.stereotype.Controller;
@Controller
public class FestivalController {
    private FestivalService festivalService;

    public FestivalController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

 
    }

