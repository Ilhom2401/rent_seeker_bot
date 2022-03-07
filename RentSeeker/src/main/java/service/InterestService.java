package service;

import model.Interest;
import model.Like;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class InterestService {
    private static List<Interest> interestList;

    public static void getInterestFromBack() {
        List<Interest> interests = FileService.getInterest();
        interestList = Objects.requireNonNullElseGet(interests, ArrayList::new);
    }


    public static boolean changeVisible(UUID homeId,UUID userId){
        for (Interest interest : interestList)
            if(interest.getHomeId().equals(homeId) && interest.getUserId().equals(userId)){
                interest.setVisible(!interest.isVisible());
                if(!interest.isActive()){
                    interest.setActive(true);
                    HomeService.changeCountOfInterest(interest);
                }
                FileService.setInterest(interestList);
                return interest.isVisible();
            }
        Interest interest=new Interest(homeId,userId,false);
        interestList.add(interest);
        FileService.setInterest(interestList);
        return interest.isVisible();
    }
    public static boolean getVisible(UUID homeId,UUID userId){
        for (Interest interest : interestList)
            if(interest.getHomeId().equals(homeId) && interest.getUserId().equals(userId)){
                if(!interest.isActive()){
                    interest.setActive(true);
                    HomeService.changeCountOfInterest(interest);
                    FileService.setInterest(interestList);
                }
                return interest.isVisible();
            }
        Interest interest=new Interest(homeId,userId,false);
        interestList.add(interest);
        FileService.setInterest(interestList);
        return interest.isVisible();
    }

}
