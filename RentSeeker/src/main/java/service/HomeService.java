package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.Home;
import model.Interest;
import model.Like;
import model.User;
import util.constant.Constant;
import util.enums.BotState;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HomeService {
    private static List<Home> homeList;

    public static void addHome(Home home, String chatId, BotState state) {
        Home crtHome = getNoActiveHomeByChatId(chatId);

        if (crtHome != null) switch (state) {
            case GIVE_DISTRICT -> crtHome.setRegion(home.getRegion());
            case GIVE_ADDRESS -> crtHome.setDistrict(home.getDistrict());
            case GIVE_HOME_TYPE_SEND -> crtHome.setAddress(home.getAddress());
            case GIVE_HOME_NUMBER -> crtHome.setHomeType(home.getHomeType());
            case GIVE_HOME_AREA_SEND -> crtHome.setNumberOfRooms(home.getNumberOfRooms());
            case GIVE_HOME_PHOTO_SEND -> crtHome.setArea(home.getArea());
            case GIVE_HOME_PRICE_SEND -> {
                crtHome.setFileSize(home.getFileSize());
                crtHome.setFileId(home.getFileId());
            }
            case GIVE_HOME_DESCRIPTION -> crtHome.setPrice(home.getPrice());
            case SAVE_HOME_TO_STORE -> {
                crtHome.setDescription(home.getDescription());
                crtHome.setActive(true);
                FileService.setHomes(homeList);
                return;
            }

        }
        saveHome(crtHome);
    }

    public static void saveHome(Home home) {
        boolean cantFind = true;

        for (int i = 0; i < homeList.size(); i++)
            if (homeList.get(i).getOwnerId().equals(home.getOwnerId()) && !homeList.get(i).isActive()) {
                homeList.set(i, home);
                cantFind = false;
                break;
            }

        if (cantFind) homeList.add(home);

        FileService.setHomes(homeList);
    }

    public static void getHomeFromBack() {
        List<Home> homes = FileService.getHomes();
        if (homes == null) homeList = new ArrayList<>();
        else homeList = homes;
    }

    public static List<Home> getAllHome() {
        return homeList;
    }

    public static Home getNoActiveHomeByChatId(String chatId) {
        User user = UserService.getByChatId(chatId);
        for (Home home : homeList)
            if (home.getOwnerId().equals(user.getId()) && !home.isActive()) return home;
        return null;
    }

    public static void saveHomeByLocation(Home home, String chatId) {
        Home homeByChatId = getNoActiveHomeByChatId(chatId);
        homeByChatId.setMapUrl(home.getMapUrl());
        homeByChatId.setRegion(home.getRegion());
        homeByChatId.setAddress(home.getAddress());
        homeByChatId.setDistrict(home.getDistrict());
        saveHome(homeByChatId);
    }

    public static void changeCountOfLike(Like like) {
        for (int i = 0; i < homeList.size(); i++)
            if (homeList.get(i).getId().equals(like.getHomeId())) {
                Home home = homeList.get(i);
                home.setLikes(like.isActive() ? home.getLikes() + 1 : home.getLikes() - 1);
                homeList.set(i, home);
                break;
            }
        FileService.setHomes(homeList);
    }

    public static void changeCountOfInterest(Interest interest) {
        for (int i = 0; i < homeList.size(); i++)
            if (homeList.get(i).getId().equals(interest.getHomeId())) {
                if (homeList.get(i).getOwnerId().equals(interest.getUserId())) return;
                Home home = homeList.get(i);
                home.setInterests(home.getInterests() + 1);
                homeList.set(i, home);
                break;
            }
        FileService.setHomes(homeList);
    }

    public static boolean checkById(String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return false;
        }
        for (Home home : homeList)
            if (home.getId().equals(uuid)) return true;
        return false;
    }

    public static Home getById(UUID id) {
        for (Home home : homeList)
            if (home.getId().equals(id)) return home;
        return null;
    }

    public static boolean deleteHome(UUID homeId) {
        for (int i = 0; i < homeList.size(); i++) {
            if (homeList.get(i).getId().equals(homeId)) {
                homeList.remove(i);
                FileService.setHomes(homeList);
                return true;
            }
        }
        return false;
    }

    public static List<Home> getDayHomes() {
        List<Home> dayHomeList = new ArrayList<>();
        for (int i = homeList.size() - 1; i >= 0; i--) {
            String[] date = homeList.get(i).getCreatedDate().split("-");
            LocalDate localDate = LocalDate.of(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2].split("T")[0]));
            LocalDate localDate1 = LocalDate.now();
            if (homeList.get(i).isActive()&&localDate1.getDayOfYear() - localDate.getDayOfYear() <= 1 && localDate1.getYear() == localDate.getYear()) {
                dayHomeList.add(homeList.get(i));
            } else break;
        }
        return dayHomeList;
    }

    public static List<Home> getWeekHomes() {
        List<Home> weekHomeList = new ArrayList<>();
        for (int i = homeList.size() - 1; i >= 0; i--) {
            String[] date = homeList.get(i).getCreatedDate().split("-");
            LocalDate localDate = LocalDate.of(Integer.valueOf(date[0]), Integer.valueOf(date[1]), Integer.valueOf(date[2].split("T")[0]));
            LocalDate localDate1 = LocalDate.now();
            if ( homeList.get(i).isActive() && localDate1.getDayOfYear() - localDate.getDayOfYear() <= 1  && localDate1.getYear() == localDate.getYear()) {
                weekHomeList.add(homeList.get(i));
            } else break;
        }
        return weekHomeList;
    }

    public static String getHomeOwnerPhoneNumber(UUID ownerId) {
        for (User user : UserService.userList) {
            if (user.getId().equals(ownerId)) return user.getPhoneNumber();
        }
        return null;
    }

    public static List<Home> getLikedHomesByUserId(UUID userId){
        List<Home> homes=new ArrayList<>();
        List<UUID> homesByUserId = LikeService.getActiveHomesByUserId(userId);
        for (Home home : homeList)
            if(homesByUserId.contains(home.getId()))
                homes.add(home);
        return homes;
    }

    public static List<Home> getByOwnerId(UUID ownerId){
        List<Home> homes=new ArrayList<>();
        for (Home home : homeList)
            if(home.getOwnerId().equals(ownerId))
                homes.add(home);
        return homes;
    }

}
