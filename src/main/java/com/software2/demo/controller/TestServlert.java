package com.software2.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestServlert {
    @GetMapping("/")
    public String test(){
        System.out.println("test");
        return "homepage.html";
    }
    @GetMapping("/outlineLabelPage.html")
    public String test2(){
        System.out.println("test");
        return "outlineLabelPage";
    }
    @GetMapping("/EditWholeLabelPage.html")
    public String test3(){
        System.out.println("test");
        return "EditWholeLabelPage";
    }
    @GetMapping("/editRectangleLabelPage.html")
    public String test4(){
        System.out.println("test");
        return "editRectangleLabelPage";
    }
    @GetMapping("/editOutlineLabelPage.html")
    public String test5(){
        System.out.println("test");
        return "editOutlineLabelPage";
    }
    @GetMapping("/wholeLabelPage.html")
    public String test6(){
        System.out.println("test");
        return "wholeLabelPage";
    }
    @GetMapping("/rectangleLabelPage.html")
    public String test7(){
        System.out.println("test");
        return "rectangleLabelPage";
    }
/*    @GetMapping("/showPicture")
    public String test8(){
        System.out.println("test");
        return "showPicture";
    }*/

    @RequestMapping("/login.html")
    public String login(){
        return "login";
    }

    @RequestMapping("/personalSpacePage.html")
    public String personalSpace(){
        System.out.println("personalSpace");
        return "personalSpacePage";
    }

    @RequestMapping("/checkOutlineLabelPage.html")
    public String checkOutlineLabel(){
        System.out.println("open checkOutlineLabelPage");
        return "checkOutlineLabelPage";
    }

    @RequestMapping("/checkRectangleLabelPage.html")
    public String checkRectangleLabel(){
        System.out.println("open checkRectangleLabelPage");
        return "checkRectangleLabelPage";
    }

    @RequestMapping("/checkWholeLabelPage.html")
    public String checkWholeLabel(){
        System.out.println("open checkWholeLabelPage");
        return "checkWholeLabelPage";
    }

    @RequestMapping("/findPassword.html")
    public String findPassword(){
        System.out.println("open findPassword");
        return "findPassword";
    }

    @RequestMapping("/personalCompletionSituation.html")
    public String personalCompletionSituation(){
        System.out.println("open personalCompletionSituation");
        return "personalCompletionSituation";
    }

    @RequestMapping("/personalRequestSituation.html")
    public String personalRequestSituation(){
        System.out.println("open personalRequestSituation");
        return "personalRequestSituation";
    }

    @RequestMapping("/rechargePage.html")
    public String recharge(){
        System.out.println("open rechargePage");
        return "rechargePage";
    }

    @RequestMapping("/resetPassword.html")
    public String resetPassword(){
        System.out.println("open resetPassword");
        return "resetPassword";
    }

    @RequestMapping("/showPicture.html")
    public String showPicture(){
        System.out.println("open showPicture");
        return "showPicture";
    }

    @RequestMapping("/signup.html")
    public String signup(){
        System.out.println("open signup");
        return "signup";
    }

    @RequestMapping("/systemInformationPage.html")
    public String systemInformation(){
        System.out.println("open systemInformationPage");
        return "systemInformationPage";
    }
    @RequestMapping("/chooseNewTask.html")
    public String chooseNewTask(){
        System.out.println("open chooseNewTask");
        return "chooseNewTask";
    }
    @RequestMapping("/editPersonalInformation.html")
    public String editPersonalInformation(){
        System.out.println("open editPersonalInformation");
        return "editPersonalInformation";
    }
    @RequestMapping("/myTaskDetailOfWorker.html")
    public String myTaskDetailOfWorker(){
        System.out.println("open myTaskDetailOfWorker");
        return "myTaskDetailOfWorker";
    }
    @RequestMapping("/myTaskOfRequestor.html")
    public String myTaskOfRequestor(){
        System.out.println("open myTaskOfRequestor");
        return "myTaskOfRequestor";
    }
    @RequestMapping("/myTaskOfWorker.html")
    public String myTaskOfWorker(){
        System.out.println("open myTaskOfWorker");
        return "myTaskOfWorker";
    }
    @RequestMapping("/personalAchievements.html")
    public String personalAchievements(){
        System.out.println("personalAchievements");
        return "personalAchievements";
    }
    @RequestMapping("/personalSkill.html")
    public String personalSkill(){
        System.out.println("personalSkill");
        return "personalSkill";
    }
    @RequestMapping("/reviewTaskOfManager.html")
    public String reviewTaskOfManager(){
        System.out.println("reviewTaskOfManager");
        return "reviewTaskOfManager";
    }
    @RequestMapping("/taskAnnouncement.html")
    public String taskAnnouncement(){
        System.out.println("taskAnnouncement");
        return "taskAnnouncement";
    }
    @RequestMapping("/taskDetailOfRequestor.html")
    public String taskDetailOfRequestor(){
        System.out.println("taskDetailOfRequestor");
        return "taskDetailOfRequestor";
    }
}
